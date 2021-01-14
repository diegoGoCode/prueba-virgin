package com.prueba.pruebavirgin.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cedula;

    @NotEmpty(message = "El tipo de documento es requerido")
    @Column(nullable = false)
    private String tipoDocumento;

    @NotEmpty(message = "El nombre es requerido")
    private String nombres;
    @NotEmpty(message = "El apellido es requerido")
    private String apellidos;
    @NotEmpty(message = "La fecha de nacimiento es requerida")
    private Date fechaNacimiento;
    @NotEmpty(message = "La direccion es requerida")
    private String direccion;
    @NotEmpty(message = "El numero de celular es requerido")
    private String celular;

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
