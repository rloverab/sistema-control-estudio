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

import java.util.Objects;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class Modulo implements Cloneable{
    private final int id;
    private final String modulo;
    private final int hta;
    private final int htas;
    protected Integer planesEstudioModulosId;

    public Modulo(int id, String modulo, int hta, int htas) {
        this.id = id;        
        this.modulo = modulo;
        this.hta = hta;
        this.htas = htas;   
        //docenteId = null;                
        //planesEstudioModulosId = null;
    }
    
    public Modulo(){
        this(0, "", 0, 0);        
    }

    /*
    public Integer getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }
    */
    
    public int getId() {
        return id;
    }

    public String getModulo() {
        return modulo;
    }

    public int getHTA() {
        return hta;
    }

    public int getHTAS() {
        return htas;
    }
    
    public Integer getPlanesEstudioModulosId() {
        return planesEstudioModulosId;
    }

    public void setPlanesEstudioModulosId(Integer planesEstudioSModuloId) {
        this.planesEstudioModulosId = planesEstudioSModuloId;
    }
    
    @Override
    public String toString() {
        return modulo;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.modulo);
        hash = 31 * hash + this.hta;
        hash = 31 * hash + this.htas;
        hash = 31 * hash + Objects.hashCode(this.planesEstudioModulosId);
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
        final Modulo other = (Modulo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.hta != other.hta) {
            return false;
        }
        if (this.htas != other.htas) {
            return false;
        }
        if (!Objects.equals(this.modulo, other.modulo)) {
            return false;
        }
        if (!Objects.equals(this.planesEstudioModulosId, other.planesEstudioModulosId)) {
            return false;
        }
        return true;
    }
    
    
}
