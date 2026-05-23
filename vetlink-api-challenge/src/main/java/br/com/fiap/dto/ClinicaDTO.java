package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClinicaDTO {

    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "\\d{14}",
            message = "CNPJ deve conter 14 dígitos"
    )
    private String cnpj;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "Telefone deve conter 11 dígitos"
    )
    private String telefone;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotNull(message = "Nota da clínica é obrigatória")
    private Integer notaClinica;
}