package com.Livraria_Digital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    private LocalDate dataNascimento;
}
