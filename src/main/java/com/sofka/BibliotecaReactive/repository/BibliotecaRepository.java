package com.sofka.BibliotecaReactive.repository;

import com.sofka.BibliotecaReactive.model.Biblioteca;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BibliotecaRepository extends ReactiveMongoRepository<Biblioteca, String> {
    Flux<Biblioteca> findByCategoria(String categoria);
}
