/*
 * Copyright (C) 2021 Roger Lovera <rloverab@yahoo.es>
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
import java.util.Objects;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class OfertaAcademica{
    private int periodoId;          
    private int cupos;
    private ArrayList<OfertaAcademicaModulo> modulos;
    private int numeroSeccion;
    private int nomenclatura;    
    public static final int ALFABETICO = 0;
    public static final int NUMERICO = 1;
    

    /**
     *
     * @param periodoId
     * @param cupos
     * @param modulos
     * @param numeroSeccion
     * @param nomenclatura
     */
    public OfertaAcademica(
            int periodoId, 
            int cupos, 
            ArrayList<OfertaAcademicaModulo> modulos, 
            int numeroSeccion, 
            int nomenclatura) {        
        this.periodoId = periodoId;
        this.cupos = cupos;
        this.modulos = modulos;
        this.numeroSeccion = numeroSeccion;
        this.nomenclatura = nomenclatura;
    }
    
    public OfertaAcademica(){
        periodoId = 0;
        cupos = 1;
        modulos = new ArrayList<>();
        numeroSeccion = 0;
        nomenclatura = 0;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodo_id) {
        this.periodoId = periodo_id;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public ArrayList<OfertaAcademicaModulo> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<OfertaAcademicaModulo> modulos) {
        this.modulos = modulos;
    }

    public int getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(int numeroSeccion) {
        this.numeroSeccion = numeroSeccion;
    }

    public int getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(int nomenclatura) {
        this.nomenclatura = nomenclatura;        
    }
    
    public String getSeccion(){
        int firstLetter = (int) 'A';
        int lastLetter = (int) 'Z';  
        int numChars;
        String seccion;
        
        seccion = "";
        numChars = lastLetter - firstLetter + 1;
                
        switch (nomenclatura) {
            case ALFABETICO:                
                seccion = seccion + Character.toString((numeroSeccion % numChars) + firstLetter);                
                while (numeroSeccion >= numChars) {
                    numeroSeccion = (numeroSeccion / numChars) - 1;
                    seccion = seccion + Character.toString((numeroSeccion % numChars) + firstLetter);
                }
                break;
            case NUMERICO:
                seccion =  String.format("%d", numeroSeccion + 1);     
        }
        
        return seccion;
    }        

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.periodoId;
        hash = 37 * hash + this.cupos;
        hash = 37 * hash + Objects.hashCode(this.modulos);
        hash = 37 * hash + this.numeroSeccion;
        hash = 37 * hash + this.nomenclatura;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OfertaAcademica other = (OfertaAcademica) obj;
        if (this.periodoId != other.periodoId) {
            return false;
        }
        if (this.cupos != other.cupos) {
            return false;
        }
        if (this.numeroSeccion != other.numeroSeccion) {
            return false;
        }
        if (this.nomenclatura != other.nomenclatura) {
            return false;
        }
        if (!Objects.equals(this.modulos, other.modulos)) {
            return false;
        }
        return true;
    }

    
    
    
}
