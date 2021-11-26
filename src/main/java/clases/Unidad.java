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

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class Unidad {
    private int id;
    private String codigo;
    private String unidad;    
    //private int hta;
    //private int htas;    
    private int uc;
    private final ArrayList<Modulo> modulos;

    public Unidad(
            int id, 
            String codigo, 
            String unidad/*, int hta, int htas*/, 
            int uc) {
        this.id = id;
        this.codigo = codigo;
        this.unidad = unidad;
        //this.hta = hta;
        //this.htas = htas;
        this.uc = uc;
        modulos = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    /*
    public void setHTA(int hta) {
        this.hta = hta;
    }
    */
    /*
    public void setHTAS(int htas) {
        this.htas = htas;
    }
    */
    public void setUC(int uc) {
        this.uc = uc;
    }
    
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getUnidad() {
        return unidad;
    }

    public int getHTA() {
        int _hta;
        _hta = 0;
        
        for(Modulo modulo : modulos){
            _hta += modulo.getHTA();
        }
        
        return _hta;
    }

    public int getHTAS() {
        int _htas;
        _htas = 0;
        
        for(Modulo modulo : modulos){
            _htas += modulo.getHTAS();
        }
        
        return _htas;
    }

    public int getUC() {
        return uc;
    }

    public ArrayList<Modulo> getModulos() {
        return modulos;
    }
    
    @Override
    public String toString() {
        return String.format("%s - (%s)", unidad, codigo);
    }
}
