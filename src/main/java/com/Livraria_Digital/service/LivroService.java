package com.Livraria_Digital.service;

import com.Livraria_Digital.dto.LivroDTO;
import com.Livraria_Digital.dto.LivroImportacaoDTO;
import com.Livraria_Digital.exception.LivroDuplicadoException;
import com.Livraria_Digital.models.entity.Autor;
import com.Livraria_Digital.models.entity.Categoria;
import com.Livraria_Digital.models.entity.Livro;
import com.Livraria_Digital.repository.AutorRepository;
import com.Livraria_Digital.repository.CategoriaRepository;
import com.Livraria_Digital.repository.LivroRepository;
import com.Livraria_Digital.scrapping.LivroScrapingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroScrapingService scrapingService;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        CategoriaRepository categoriaRepository,
                        LivroScrapingService scrapingService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.scrapingService = scrapingService;
    }

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public LivroDTO buscarPorId(Long id) {
        return livroRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }

    public LivroDTO criar(@Valid LivroDTO dto) {
        if (livroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException("ISBN já cadastrado");
        }
        Livro livro = toEntity(dto);
        return toDTO(livroRepository.save(livro));
    }

    public LivroDTO atualizar(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPreco(dto.getPreco());
        livro.setAutor(autorRepository.findById(dto.getAutorId()).orElseThrow());
        livro.setCategoria(categoriaRepository.findById(dto.getCategoriaId()).orElseThrow());

        return toDTO(livroRepository.save(livro));
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    public List<LivroDTO> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<LivroDTO> buscarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<LivroDTO> buscarPorCategoria(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<LivroDTO> buscarPorAno(Integer ano) {
        return livroRepository.findByAnoPublicacao(ano).stream().map(this::toDTO).collect(Collectors.toList());
    }

//    public LivroDTO importarLivro(LivroImportacaoDTO importacao) throws IOException {
//        LivroDTO dto = scrapingService.extrairDadosLivro(importacao.getUrl());
//
//        if (livroRepository.existsByIsbn(dto.getIsbn())) {
//            throw new IllegalArgumentException("Livro já existe pelo ISBN");
//        }
//
//        dto.setAutorId(importacao.getAutorId());
//        dto.setCategoriaId(importacao.getCategoriaId());
//
//        return criar(dto);
//    }

    public LivroDTO importarLivroDeUrl(LivroImportacaoDTO dto) throws IOException {
        LivroDTO scraped = scrapingService.extrairDadosLivro(dto.getUrl());

        scraped.setAutorId(dto.getAutorId());
        scraped.setCategoriaId(dto.getCategoriaId());

        if (livroRepository.existsByTituloIgnoreCase(scraped.getTitulo())) {
            throw new LivroDuplicadoException("Livro já existente com o título: " + scraped.getTitulo());
        }

        return salvar(scraped);
    }


    public boolean existePorTitulo(String titulo) {
        return livroRepository.existsByTituloIgnoreCase(titulo);
    }

    public LivroDTO salvar(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setPreco(dto.getPreco());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setIsbn(dto.getIsbn());

        livro.setAutor(autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado")));

        livro.setCategoria(categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));

        Livro salvo = livroRepository.save(livro);

        return new LivroDTO(salvo); // ou converter manualmente, se não tiver construtor
    }


    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setIsbn(livro.getIsbn());
        dto.setAnoPublicacao(livro.getAnoPublicacao());
        dto.setPreco(livro.getPreco());
        dto.setAutorId(livro.getAutor().getId());
        dto.setCategoriaId(livro.getCategoria().getId());
        return dto;
    }

    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPreco(dto.getPreco());
        livro.setAutor(autorRepository.findById(dto.getAutorId()).orElseThrow());
        livro.setCategoria(categoriaRepository.findById(dto.getCategoriaId()).orElseThrow());
        return livro;
    }
}
