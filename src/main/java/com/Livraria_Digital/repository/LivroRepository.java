package com.Livraria_Digital.repository;

import com.Livraria_Digital.models.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByCategoriaId(Long categoriaId);
    List<Livro> findByAnoPublicacao(Integer ano);
    List<Livro> findByAutorId(Long autorId);
    boolean existsByIsbn(String isbn);
}