package com.sofka.BibliotecaReactive.service;

import com.sofka.BibliotecaReactive.model.Biblioteca;
import com.sofka.BibliotecaReactive.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository repository;

    public Mono<Biblioteca> delete(String Id){
        return repository.findById(Id)
                .flatMap(p->repository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Biblioteca> save(Biblioteca biblioteca){
        return repository.save(biblioteca);
    }

    public Flux<Biblioteca> findAll(){
        return repository.findAll();
    }

    public Mono<Biblioteca> findById(String Id){
        return repository.findById(Id);
    }

    public Flux<Biblioteca> recomendacion(String categoria){
        return repository.findByCategoria(categoria);
    }

    public Mono<String> consultarDisponibilidad(String Id){
        return repository.findById(Id)
                .flatMap(biblioteca -> {
                    if(biblioteca.getDisponibilidad()> 0){
                        return Mono.just("Hay disponibles "+biblioteca.getDisponibilidad()+
                                " recursos de "+biblioteca.getName());
                    }
                    return Mono.just("No hay disponibles. El último ejemplar se prestó el "
                            +biblioteca.getFechaPrestamo());
                });
    }

    public Mono<String> prestarRecurso(String Id){
        Biblioteca b = new Biblioteca();
        return repository.findById(Id)
                .flatMap(biblioteca -> {
                    if(biblioteca.getDisponibilidad()>0){
                        if(biblioteca.getEstado().equals("No Prestado")){
                            b.setId(Id);
                            b.setName(biblioteca.getName());
                            b.setCategoria(biblioteca.getCategoria());
                            b.setDisponibilidad(biblioteca.getDisponibilidad()-1);
                            b.setEstado("Prestado");
                            b.setFechaPrestamo(LocalDate.now());
                            save(b);
                            return Mono.just("Se presta el libro: "+b.getName()+". Quedan: "
                                    +b.getDisponibilidad());
                        }
                        b.setId(Id);
                        b.setName(biblioteca.getName());
                        b.setCategoria(biblioteca.getCategoria());
                        b.setDisponibilidad(biblioteca.getDisponibilidad()-1);
                        b.setEstado(biblioteca.getEstado());
                        b.setFechaPrestamo(LocalDate.now());
                        save(b);
                        return Mono.just("Se presta el libro: "+b.getName()+". Quedan: "
                                +b.getDisponibilidad());
                    }
                    return Mono.just("No hay disponibles. El último ejemplar se prestó el "
                            +biblioteca.getFechaPrestamo());
                });
    }

    public Mono<String> devolverRecurso(String Id){
        Biblioteca b = new Biblioteca();
        return repository.findById(Id)
                .flatMap(biblioteca -> {
                    if(biblioteca.getEstado().equals("Prestado")){
                        b.setId(Id);
                        b.setName(biblioteca.getName());
                        b.setCategoria(biblioteca.getCategoria());
                        b.setDisponibilidad(biblioteca.getDisponibilidad()+1);
                        b.setEstado("No Prestado");
                        b.setFechaPrestamo(LocalDate.now());
                        save(b);
                        return Mono.just("Se devolvio el libro "+b.getName());
                    }
                    return Mono.just("El recurso no se puede devolver porque no se encuentra en préstamo");
                });
    }
}
