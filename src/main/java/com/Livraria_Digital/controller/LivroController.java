package com.Livraria_Digital.controller;

import com.Livraria_Digital.dto.*;
import com.Livraria_Digital.exception.LivroDuplicadoException;
import com.Livraria_Digital.repository.AutorRepository;
import com.Livraria_Digital.repository.CategoriaRepository;
import com.Livraria_Digital.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroController(LivroService livroService,
                           AutorRepository autorRepository,
                           CategoriaRepository categoriaRepository) {
        this.livroService = livroService;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
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
    public ResponseEntity<?> importarLivro(@RequestBody LivroImportacaoDTO dto) {
        if (!autorRepository.existsById(dto.getAutorId())) {
            return ResponseEntity.badRequest().body("Autor não encontrado para o id: " + dto.getAutorId());
        }

        if (!categoriaRepository.existsById(dto.getCategoriaId())) {
            return ResponseEntity.badRequest().body("Categoria não encontrada para o id: " + dto.getCategoriaId());
        }
        try {
            LivroDTO livroSalvo = livroService.importarLivroDeUrl(dto);
            return ResponseEntity.ok(livroSalvo);
        } catch (LivroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro ao acessar a URL fornecida.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao importar o livro.");
        }
    }
}

