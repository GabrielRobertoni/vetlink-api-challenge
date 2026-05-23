package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TutorDTO {

    private Long id;

    @NotBlank(message = "Nome do tutor é obrigatório")
    @Size(min = 3, max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}",
            message = "CPF deve conter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{11}",
            message = "Telefone deve conter 11 dígitos")
    private String telefone;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "^(M|F)$",
            message = "Sexo deve ser M ou F")
    private String sexo;

    @NotBlank(message = "Situação é obrigatória")
    private String status;
}