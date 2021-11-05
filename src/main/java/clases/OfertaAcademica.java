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
import java.util.Collections;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class OfertaAcademica{
    private int id;    
    private int periodoId;
    private int planEstudioModuloId;        
    private int cupos;
    private ArrayList<Modulo> modulos;
    private int numeroSeccion;
    private Nomenclatura nomenclatura;
    public enum Nomenclatura{        
        ALFABETICO,
        NUMERICO
    };

    /**
     *
     * @param id
     * @param periodoId
     * @param planEstudioModuloId
     * @param cupos
     * @param modulos
     * @param numeroSeccion
     * @param nomenclatura
     */
    public OfertaAcademica(int id, int periodoId, int planEstudioModuloId, int cupos, ArrayList<Modulo> modulos, int numeroSeccion, Nomenclatura nomenclatura) {
        this.id = id;
        this.periodoId = periodoId;
        this.planEstudioModuloId = planEstudioModuloId;
        this.cupos = cupos;
        this.modulos = modulos;
        this.numeroSeccion = numeroSeccion;
        this.nomenclatura = nomenclatura;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodo_id) {
        this.periodoId = periodo_id;
    }

    public int getPlanEstudioModuloId() {
        return planEstudioModuloId;
    }

    public void setPlanEstudioModuloId(int plan_estudio_modulo_id) {
        this.planEstudioModuloId = plan_estudio_modulo_id;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public ArrayList<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<Modulo> modulos) {
        this.modulos = modulos;
    }

    public int getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(int numeroSeccion) {
        this.numeroSeccion = numeroSeccion;
    }

    public Nomenclatura getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(Nomenclatura nomenclatura) {
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
        System.out.println(seccion);
        return seccion;
    }    
}
