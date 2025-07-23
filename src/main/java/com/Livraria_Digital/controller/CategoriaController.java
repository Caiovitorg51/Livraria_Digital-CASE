package com.Livraria_Digital.controller;

import com.Livraria_Digital.dto.*;
import com.Livraria_Digital.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final LivroService livroService;

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService, LivroService livroService) {
        this.categoriaService = categoriaService;
        this.livroService = livroService;
    }

    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@RequestBody @Valid CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.criar(dto));
    }

    @GetMapping("/categorias/{categoriaId}/livros")
    public List<LivroDTO> listarLivrosPorCategoria(@PathVariable Long categoriaId) {
        return livroService.buscarPorCategoria(categoriaId);
    }
}