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

import java.util.Date;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class Docente extends Persona{
    private int docenteId;
    private boolean activo;
    
    public Docente(
            int docenteId,
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
            boolean activo) {
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
        this.docenteId = docenteId;
        this.activo = activo;        
    }
    
    public Docente(){
        this(0,0,"","","","","","",null,"",0,"","","","","","","","","",true);
    }

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
    
    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
