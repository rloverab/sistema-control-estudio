/*
 * Copyright (C) 2021 Roger Lovera <roger.lovera>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package clases;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Roger Lovera
 */
public class Estudiante extends Persona {

    private ArrayList<DatosAcademicos> datosAcademicos;

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
        this(0, 0, "", "", "", "", "", "", null, "", 0, "", "", "", "", "", "", "", "", "");

        datosAcademicos = new ArrayList<>();
        datosAcademicos.add(new DatosAcademicos(
                estudianteId,
                carrera,
                condicionEstado,
                condicionDetalle));
    }

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
            String correoElectronico) {
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
                correoElectronico);
        datosAcademicos = new ArrayList<>();
    }

    public Estudiante() {
        this(0, 0, "", "", "", "", "", "", null, "", 0, "", "", "", "", "", "", "", "", "");
    }

    //Setters
    public void setPersona(Persona persona) {
        setPersonaId(persona.getPersonaId());
        setCedula(persona.getCedula());
        setNombre1(persona.getNombre1());
        setNombre2(persona.getNombre2());
        setApellido1(persona.getApellido1());
        setApellido2(persona.getApellido2());
        setFechaNacimiento(persona.getFechaNacimiento());
        setLugarNacimiento(persona.getLugarNacimiento());
        setEdad(persona.getEdad());
        setSexo(persona.getSexo());
        setEstadoCivil(persona.getEstadoCivil());
        setEtnia(persona.getEtnia());
        setEstado(persona.getEstado());
        setMunicipio(persona.getMunicipio());
        setParroquia(persona.getParroquia());
        setDireccion(persona.getDireccion());
        setTelefonoLocal(persona.getTelefonoLocal(false));
        setTelefonoMovil(persona.getTelefonoMovil(false));
        setCorreoElectronico(persona.getCorreoElectronico());
    }

    //Getters
    public ArrayList<DatosAcademicos> getDatosAcademicos() {
        return datosAcademicos;
    }
    
    public ArrayList<String> getCarreras(){
        ArrayList<String> carreras = new ArrayList<>();
        
        datosAcademicos.forEach(e -> carreras.add(e.getCarrera()));

        return carreras;
    }
    
    //Actions
    public void addDatosAcademicos(DatosAcademicos datosAcademicos) {
        this.datosAcademicos.add(datosAcademicos);
    }

    public void removeDatosAcademicos(DatosAcademicos datosAcademicos) {
        this.datosAcademicos.remove(datosAcademicos);
    }

    public void removeDatosAcademicos(int index) {
        datosAcademicos.remove(index);
    }

    @Override
    public String toString() {
        return "Estudiante{" + "datosAcademicos=" + datosAcademicos + '}';
    }
    
    
}
