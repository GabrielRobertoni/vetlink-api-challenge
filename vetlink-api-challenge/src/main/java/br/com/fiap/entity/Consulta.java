package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_VET_CONSULTA")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Consulta {

    @Id
    @Column(name = "ID_CONSULTA")
    private Long id;

    @NotNull(message = "Data da consulta é obrigatória")
    @Column(name = "DT_CONSULTA", nullable = false)
    private LocalDateTime dataConsulta;

    @NotBlank(message = "Status é obrigatório")
    @Column(
            name = "DS_STATUS",
            nullable = false,
            length = 20
    )
    private String status;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "Valor deve ser maior que 0"
    )
    @Column(name = "NR_VALOR", nullable = false)
    private Double valor;

    @Column(
            name = "DS_OBSERVACOES",
            length = 500
    )
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TUTOR", nullable = false)
    @JsonBackReference
    private Tutor tutor;

    @Column(name = "VETERINARIO_ID", nullable = false)
    private Long veterinarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLINICA", nullable = false)
    @JsonBackReference
    private Clinica clinica;
}