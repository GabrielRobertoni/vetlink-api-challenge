package br.com.fiap.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "T_VET_ASSINATURAS")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Assinatura {

    @Id
    @Column(name = "CD_ASSINATURA")
    private Long id;

    @NotBlank(message = "Tipo do plano é obrigatório")
    @Column(
            name = "DS_TIPOPLANO",
            nullable = false,
            length = 100
    )
    private String tipoPlano;

    @NotNull(message = "Data de início é obrigatória")
    @Column(
            name = "DT_INICIO",
            nullable = false
    )
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Column(
            name = "DT_FIM",
            nullable = false
    )
    private LocalDate dataFim;

    @NotBlank(message = "Status é obrigatório")
    @Column(
            name = "DS_STATUS",
            nullable = false,
            length = 50
    )
    private String status;

    @NotNull(message = "Valor mensal é obrigatório")
    @Column(
            name = "VALORMENSAL",
            nullable = false
    )
    private Double valorMensal;

    @NotBlank(message = "Renovação automática é obrigatória")
    @Column(
            name = "DS_RENOVACAOAUT",
            nullable = false,
            length = 1
    )
    private String renovacaoAutomatica;

    @NotBlank(message = "Método de pagamento é obrigatório")
    @Column(
            name = "DS_METODOPAGAMENTO",
            nullable = false,
            length = 50
    )
    private String metodoPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TUTOR", nullable = false)
    private Tutor tutor;
}