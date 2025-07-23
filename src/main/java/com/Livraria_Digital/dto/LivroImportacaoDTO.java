package com.Livraria_Digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroImportacaoDTO {
    @NotBlank
    private String url;

    @NotNull
    private Long autorId;

    @NotNull
    private Long categoriaId;
}