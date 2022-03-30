package com.sofka.BibliotecaReactive;

import com.sofka.BibliotecaReactive.model.Biblioteca;
import com.sofka.BibliotecaReactive.service.BibliotecaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebFluxTest
@ExtendWith(SpringExtension.class)
public class BibliotecaControllerTest {

    @MockBean
    private BibliotecaService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("GET / todos los recursos")
    void findAll() throws Exception{
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        Flux<Biblioteca> list = Flux.just(biblioteca);

        when(service.findAll()).thenReturn(list);

        webTestClient.get()
                        .uri("/biblioteca")
                                .exchange()
                                        .expectStatus().isOk()
                        .expectBody().jsonPath("$",hasSize(1));

    }
    @Test
    @DisplayName("GET / consultar disponibilidad")
    void consultarDisponibilidad(){
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.consultarDisponibilidad("tytyty5")).thenReturn(Mono.just("Hay disponibles 2 libros de Libro"));
        webTestClient.get()
                .uri("/biblioteca/{Id}/disponibilidad","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Hay disponibles 2 libros de Libro");
    }

    @Test
    @DisplayName("GET / prestar recurso")
    void prestarRecurso(){
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.prestarRecurso("tytyty5")).thenReturn(Mono.just("Se presta el libro Soledad. Quedan: 1"));
        webTestClient.get()
                .uri("/biblioteca/{Id}/prestar","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Se presta el libro Soledad. Quedan: 1");

    }

    @Test
    @DisplayName("GET / recomendar")
    void recomendar(){
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        biblioteca.setCategoria("Ficcion");
        Flux<Biblioteca> list = Flux.just(biblioteca);

        when(service.recomendacion("Ficcion")).thenReturn(list);

        webTestClient.get()
                .uri("/biblioteca/{categoria}/recomendar","Ficcion")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$[0].id",is("tytyty5"));
    }

    @Test
    @DisplayName("GET / devolver recurso")
    void devolverRecurso(){
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.devolverRecurso("tytyty5")).thenReturn(Mono.just("Se devolvio el libro Soledad"));
        webTestClient.get()
                .uri("/biblioteca/{Id}/devolver","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Se devolvio el libro Soledad");
    }
}
