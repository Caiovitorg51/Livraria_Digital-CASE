package com.Livraria_Digital.dto;

import com.Livraria_Digital.models.entity.Livro;
import com.Livraria_Digital.validation.AnoNaoFuturo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class LivroDTO {
    private Long id;

    @NotBlank
    private String titulo;

    @Pattern(
            regexp = "^(\\d{10}|\\d{13})$",
            message = "ISBN deve conter 10 ou 13 dígitos numéricos"
    )
    private String isbn;

    @Min(0)
    @AnoNaoFuturo
    private Integer anoPublicacao;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal preco;

    @NotNull
    private Long autorId;

    @NotNull
    private Long categoriaId;

    public LivroDTO() {}

    public LivroDTO(Long id, String titulo, String isbn, Integer anoPublicacao, BigDecimal preco, Long autorId, Long categoriaId) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
    }

    public LivroDTO(Livro salvo) {
        // Este construtor precisa ser implementado conforme sua entidade Livro
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
}
