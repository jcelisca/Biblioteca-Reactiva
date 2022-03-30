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

    @PostMapping("/biblioteca")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Biblioteca> save(@RequestBody Biblioteca biblioteca){
        return service.save(biblioteca);
    }

    @GetMapping("/biblioteca")
    public Flux<Biblioteca> findAll(){
        return service.findAll();
    }

    @GetMapping("/biblioteca/{Id}/disponibilidad")
    public Mono<String> consultarDisponibilidad(@PathVariable("Id") String id){
        return service.consultarDisponibilidad(id);
    }

    @GetMapping("/biblioteca/{Id}/prestar")
    public Mono<String> prestarRecurso(@PathVariable("Id") String id){
        return service.prestarRecurso(id);
    }

    @GetMapping("/biblioteca/{categoria}/recomendar")
    public Flux<Biblioteca> recomendacion(@PathVariable("categoria") String categoria){
        return service.recomendacion(categoria);
    }

    @GetMapping("/biblioteca/{Id}/devolver")
    public Mono<String> devolverRecurso(@PathVariable("Id") String id){
        return service.devolverRecurso(id);
    }

    @DeleteMapping("/biblioteca/{Id}/delete")
    private Mono<ResponseEntity<Biblioteca>> delete(@PathVariable("Id") String id) {
        return service.delete(id)
                .flatMap(biblioteca -> Mono.just(ResponseEntity.ok(biblioteca)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
