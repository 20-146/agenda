package com.darnet.agenda.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Inversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cantidad;
    private LocalDate fecha;
    private Integer categoria;
    private double gastosIncluidos;
    private String observaciones;




}
