package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VacinaDTO {

    private Long id;

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(
            min = 3,
            max = 50,
            message = "Nome deve ter entre 3 e 50 caracteres"
    )
    private String nome;

    @NotBlank(message = "Fabricante é obrigatório")
    private String fabricante;

    @NotBlank(message = "Lote é obrigatório")
    private String lote;

    @NotNull(message = "Data de aplicação é obrigatória")
    private LocalDate dataAplicacao;

    @NotNull(message = "Data de reforço é obrigatória")
    private LocalDate dataReforco;

    private String observacoes;

    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}