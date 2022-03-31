package com.sofka.BibliotecaReactive.controller;

import com.sofka.BibliotecaReactive.model.Biblioteca;
import com.sofka.BibliotecaReactive.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    @PostMapping("/bibliotecaReactiva")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Biblioteca> save(@RequestBody Biblioteca biblioteca){
        return service.guardar(biblioteca);
    }

    @GetMapping("/bibliotecaReactiva")
    public Flux<Biblioteca> findAll(){
        return service.findAll();
    }

    @GetMapping("/bibliotecaReactiva/{id}/disponibilidad")
    public Mono<String> consultarDisponibilidad(@PathVariable("id") String id){
        return service.consultarDisponibilidad(id);
    }

    @GetMapping("/bibliotecaReactiva/{id}/prestar")
    public Mono<String> prestarRecurso(@PathVariable("id") String id){
        return service.prestarRecurso(id);
    }

    @GetMapping("/bibliotecaReactiva/{categoria}/recomendar")
    public Flux<Biblioteca> recomendacion(@PathVariable("categoria") String categoria){
        return service.recomendacion(categoria);
    }

    @GetMapping("/bibliotecaReactiva/{id}/devolver")
    public Mono<String> devolverRecurso(@PathVariable("id") String id){
        return service.devolverRecurso(id);
    }

    @DeleteMapping("/bibliotecaReactiva/{Id}/delete")
    private Mono<ResponseEntity<Biblioteca>> delete(@PathVariable("Id") String id) {
        return service.delete(id)
                .flatMap(biblioteca -> Mono.just(ResponseEntity.ok(biblioteca)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
