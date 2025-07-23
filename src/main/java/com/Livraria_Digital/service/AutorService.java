package com.Livraria_Digital.service;

import com.Livraria_Digital.dto.AutorDTO;
import com.Livraria_Digital.models.entity.Autor;
import com.Livraria_Digital.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        return toDTO(autor);
    }

    public AutorDTO criar(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.getNome());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());
        return toDTO(autorRepository.save(autor));
    }

    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        autor.setNome(dto.getNome());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());
        return toDTO(autorRepository.save(autor));
    }

    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }

    private AutorDTO toDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        dto.setEmail(autor.getEmail());
        dto.setDataNascimento(autor.getDataNascimento());
        return dto;
    }
}