package com.Livraria_Digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LivroImportacaoDTO {

    @NotBlank
    private String url;

    @NotNull
    private Long autorId;

    @NotNull
    private Long categoriaId;

    public LivroImportacaoDTO() {}

    public LivroImportacaoDTO(String url, Long autorId, Long categoriaId) {
        this.url = url;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
}
