package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_VET_CLINICA")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Clinica {

    @Id
    @Column(name = "ID_CLINICA")
    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "\\d{14}",
            message = "CNPJ deve conter 14 dígitos"
    )
    @Column(
            name = "NR_CNPJ",
            nullable = false,
            length = 14
    )
    private String cnpj;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "Telefone deve conter 11 dígitos"
    )
    @Column(
            name = "NR_TELEFONE",
            nullable = false,
            length = 11
    )
    private String telefone;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(
            name = "DS_ENDERECO",
            nullable = false,
            length = 150
    )
    private String endereco;

    @NotNull(message = "Nota da clínica é obrigatória")
    @Column(
            name = "NR_NOTACLINICA",
            nullable = false
    )
    private Integer notaClinica;

    @OneToMany(
            mappedBy = "clinica",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Veterinario> veterinarios = new ArrayList<>();

    @OneToMany(
            mappedBy = "clinica",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany(
            mappedBy = "clinica",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Exame> exames = new ArrayList<>();
}