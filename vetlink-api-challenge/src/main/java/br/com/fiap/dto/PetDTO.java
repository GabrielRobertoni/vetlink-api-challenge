package br.com.fiap.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PetDTO {

    private Long id;

    @NotBlank(message = "Nome do pet é obrigatório")
    @Size(
            min = 2,
            max = 100,
            message = "Nome deve ter entre 2 e 100 caracteres"
    )
    private String nome;

    @NotBlank(message = "Espécie é obrigatória")
    private String especie;

    @NotBlank(message = "Raça é obrigatória")
    private String raca;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(
            regexp = "^(M|F)$",
            message = "Sexo deve ser M ou F"
    )
    private String sexo;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(
            value = "0.1",
            message = "Peso deve ser maior que 0"
    )
    private Double peso;

    private Long microchip;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}