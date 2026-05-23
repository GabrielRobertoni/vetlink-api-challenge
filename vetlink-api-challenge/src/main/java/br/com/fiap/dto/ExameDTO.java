package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExameDTO {

    private Long id;

    @NotBlank(message = "Nome do exame é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotNull(message = "Data da solicitação é obrigatória")
    private LocalDate dataSolicitacao;

    private LocalDate dataResultado;

    private String resultado;

    private String observacoes;

    @NotNull(message = "ID da consulta é obrigatório")
    private Long consultaId;

    @NotNull(message = "ID da clínica é obrigatório")
    private Long clinicaId;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}