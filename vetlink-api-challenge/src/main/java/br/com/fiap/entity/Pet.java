package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_VET_PET")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Pet {

    @Id
    @Column(name = "CD_PET")
    private Long id;

    @NotBlank(message = "Nome do pet é obrigatório")
    @Size(
            min = 2,
            max = 100,
            message = "Nome deve ter entre 2 e 100 caracteres"
    )
    @Column(
            name = "DS_NOME",
            nullable = false,
            length = 100
    )
    private String nome;

    @NotBlank(message = "Espécie é obrigatória")
    @Column(
            name = "DS_ESPECIE",
            nullable = false,
            length = 100
    )
    private String especie;

    @NotBlank(message = "Raça é obrigatória")
    @Column(
            name = "DS_RACA",
            nullable = false,
            length = 100
    )
    private String raca;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(
            regexp = "^(M|F)$",
            message = "Sexo deve ser M ou F"
    )
    @Column(
            name = "DS_SEXO",
            nullable = false,
            length = 1
    )
    private String sexo;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(
            value = "0.1",
            message = "Peso deve ser maior que 0"
    )
    @Column(
            name = "NR_PESO",
            nullable = false
    )
    private Double peso;

    @Column(name = "NR_MICROCHIP")
    private Long microchip;

    @NotBlank(message = "Status é obrigatório")
    @Column(
            name = "DS_STATUS",
            nullable = false,
            length = 50
    )
    private String status;

    // =========================
    // TUTOR
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TUTOR", nullable = false)
    @JsonBackReference
    private Tutor tutor;

    // =========================
    // MEDICAMENTOS
    // =========================

    @OneToMany(
            mappedBy = "pet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Medicamento> medicamentos =
            new ArrayList<>();

    // =========================
    // VACINAS
    // =========================

    @OneToMany(
            mappedBy = "pet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Vacina> vacinas =
            new ArrayList<>();
}