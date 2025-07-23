package com.Livraria_Digital.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;
}
