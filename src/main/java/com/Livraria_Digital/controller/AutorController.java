package com.Livraria_Digital.controller;

import com.Livraria_Digital.dto.*;
import com.Livraria_Digital.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final LivroService livroService;

    private final AutorService autorService;

    public AutorController(AutorService autorService, LivroService livroService) {
        this.autorService = autorService;
        this.livroService = livroService;
    }

    @GetMapping
    public List<AutorDTO> listarTodos() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    public AutorDTO buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> criar(@RequestBody @Valid AutorDTO dto) {
        return ResponseEntity.ok(autorService.criar(dto));
    }

    @PutMapping("/{id}")
    public AutorDTO atualizar(@PathVariable Long id, @RequestBody @Valid AutorDTO dto) {
        return autorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        autorService.deletar(id);
    }

    @GetMapping("/autores/{autorId}/livros")
    public List<LivroDTO> listarLivrosPorAutor(@PathVariable Long autorId) {
        return livroService.buscarPorAutor(autorId);
    }
}