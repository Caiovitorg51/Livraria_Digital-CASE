package com.Livraria_Digital.controller;

import com.Livraria_Digital.dto.*;
import com.Livraria_Digital.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<LivroDTO> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id:\\d+}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<LivroDTO> criar(@RequestBody @Valid LivroDTO dto) {
        return ResponseEntity.ok(livroService.criar(dto));
    }

    @PutMapping("/{id}")
    public LivroDTO atualizar(@PathVariable Long id, @RequestBody @Valid LivroDTO dto) {
        return livroService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        livroService.deletar(id);
    }

    @GetMapping("/search")
    public List<LivroDTO> buscarPorTitulo(@RequestParam String titulo) {
        return livroService.buscarPorTitulo(titulo);
    }

    @GetMapping(params = "autor")
    public List<LivroDTO> buscarPorAutor(@RequestParam Long autor) {
        return livroService.buscarPorAutor(autor);
    }

    @GetMapping(params = "categoria")
    public List<LivroDTO> buscarPorCategoria(@RequestParam Long categoria) {
        return livroService.buscarPorCategoria(categoria);
    }

    @GetMapping(params = "ano")
    public List<LivroDTO> buscarPorAno(@RequestParam Integer ano) {
        return livroService.buscarPorAno(ano);
    }

    @PostMapping("/importar")
    public ResponseEntity<LivroDTO> importarLivro(@RequestBody @Valid LivroImportacaoDTO dto) throws IOException {
        return ResponseEntity.ok(livroService.importarLivro(dto));
    }
}

