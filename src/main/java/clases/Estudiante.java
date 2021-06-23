/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Date;

/**
 *
 * @author Roger Lovera
 */
public class Estudiante extends Persona{
    private int estudianteId;
    private String carrera;
    private String condicion;
    private String detalle;

    //Constructor

    public Estudiante(
            int estudianteId,
            int personaId, 
            String cedula, 
            String nombre1, 
            String nombre2, 
            String apellido1, 
            String apellido2, 
            String sexo, 
            Date fechaNacimiento, 
            String lugarNacimiento, 
            int edad, 
            String estadoCivil, 
            String etnia, 
            String estado, 
            String municipio, 
            String parroquia, 
            String direccion, 
            String telefonoLocal, 
            String telefonoMovil, 
            String correoElectronico, 
            String carrera, 
            String condicionEstado, 
            String condicionDetalle) {
        super(                
                personaId, 
                cedula, 
                nombre1, 
                nombre2, 
                apellido1, 
                apellido2, 
                sexo, 
                fechaNacimiento, 
                lugarNacimiento, 
                edad, 
                estadoCivil, 
                etnia, 
                estado, 
                municipio, 
                parroquia, 
                direccion, 
                telefonoLocal, 
                telefonoMovil, 
                correoElectronico
        );
        this.estudianteId = estudianteId;
        this.carrera = carrera;
        this.condicion = condicionEstado;
        this.detalle = condicionDetalle;
    }
    
    public Estudiante() {
        this(0, 0, "", "", "", "", "", "", null, "", 0, "", "", "", "", "", "", "", "", "", "", "", "");
    }
    
    //Setters
    
    public void setEstudianteId(int estudiante_id) {
        this.estudianteId = estudiante_id;
    }    
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public void setDetalle(String condicion) {
        this.detalle = condicion;
    }
        
    //Getters
    
    public int getEstudianteId() {
        return estudianteId;
    }    
    
    public String getCarrera() {
        return carrera;
    }

    public String getCondicion() {
        return condicion;
    }

    public String getDetalle() {
        return detalle;
    }
    
    
}
