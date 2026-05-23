package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VeterinarioDTO {

    private Long id;

    @NotBlank(message = "Nome do veterinário é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotBlank(message = "CRMV é obrigatório")
    private String crmv;

    @NotBlank(message = "Especialidade é obrigatória")
    private String especialidade;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "Telefone deve conter 11 dígitos"
    )
    private String telefone;

    @NotNull(message = "ID da clínica é obrigatório")
    private Long clinicaId;
}