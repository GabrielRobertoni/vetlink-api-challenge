package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedicamentoDTO {

    private Long id;

    @NotBlank(message = "Nome do medicamento é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotNull(message = "Dosagem é obrigatória")
    private Double dosagem;

    @NotBlank(message = "Frequência é obrigatória")
    private String frequencia;

    private String observacoes;

    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}