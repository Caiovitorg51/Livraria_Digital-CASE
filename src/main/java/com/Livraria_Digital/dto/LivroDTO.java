package com.Livraria_Digital.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import com.Livraria_Digital.validation.AnoNaoFuturo;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank(message = "ISBN é obrigatório")
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
}
