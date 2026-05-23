package br.com.fiap.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AssinaturaDTO {

    private Long id;

    @NotBlank(message = "Tipo de plano é obrigatório")
    private String tipoPlano;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    private LocalDate dataFim;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Valor mensal é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "Valor deve ser maior que 0"
    )
    private Double valorMensal;

    @NotBlank(message = "Renovação automática é obrigatória")
    private String renovacaoAutomatica;

    @NotBlank(message = "Método de pagamento é obrigatório")
    private String metodoPagamento;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}