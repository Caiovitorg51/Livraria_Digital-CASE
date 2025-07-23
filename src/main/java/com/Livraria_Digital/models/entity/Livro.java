package com.Livraria_Digital.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String titulo;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^(\\d{10}|\\d{13})$")
    private String isbn;

    private Integer anoPublicacao;

    @DecimalMin("0.0")
    private BigDecimal preco;

    @ManyToOne private Autor autor;

    @ManyToOne
    private Categoria categoria;
}
