package com.kosmos.hospitalmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "consultorio", indexes = {
        @Index(name = "idx_numero", columnList = "numero"),
        @Index(name = "idx_piso", columnList = "piso")
})
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número del consultorio es obligatorio")
    @Min(value = 1, message = "El número del consultorio debe ser mayor que 0")
    private Integer numero;

    @NotNull(message = "El piso es obligatorio")
    @Size(min = 1, max = 50, message = "El piso debe tener entre 1 y 50 caracteres")
    private String piso;

    public Consultorio() {
    }

    public Consultorio(Integer numero, String piso) {
        this.numero = numero;
        this.piso = piso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }
}
