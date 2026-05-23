package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConsultaDTO {

    private Long id;

    @NotNull(message = "Data da consulta é obrigatória")
    private LocalDateTime dataConsulta;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "Valor deve ser maior que 0"
    )
    private Double valor;

    private String observacoes;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;

    @NotNull(message = "ID do veterinário é obrigatório")
    private Long veterinarioId;

    @NotNull(message = "ID da clínica é obrigatório")
    private Long clinicaId;
}