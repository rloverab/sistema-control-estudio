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
public class OfertaAcademicaModulo extends Modulo implements Cloneable{
    private int ofertaAcademicaId;
    private Integer docenteId;

    public OfertaAcademicaModulo(int ofertaAcademicaId, Integer docenteId, Integer planesEstudioModulosId, int id, String modulo, int hta, int htas) {
        super(id, modulo, hta, htas);
        this.ofertaAcademicaId = ofertaAcademicaId;
        this.docenteId = docenteId;
        this.planesEstudioModulosId = planesEstudioModulosId;
    }

    public OfertaAcademicaModulo(int ofertaAcademicaId, Integer docenteId, Integer planesEstudioModulosId) {
        this.ofertaAcademicaId = ofertaAcademicaId;
        this.docenteId = docenteId;
        this.planesEstudioModulosId = planesEstudioModulosId;
    }

    public int getOfertaAcademicaId() {
        return ofertaAcademicaId;
    }

    public void setOfertaAcademicaId(int ofertaAcademicaId) {
        this.ofertaAcademicaId = ofertaAcademicaId;
    }

    public Integer getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }
   
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.ofertaAcademicaId;
        hash = 47 * hash + Objects.hashCode(this.docenteId);
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
        final OfertaAcademicaModulo other = (OfertaAcademicaModulo) obj;
        if (this.ofertaAcademicaId != other.ofertaAcademicaId) {
            return false;
        }
        if (!Objects.equals(this.docenteId, other.docenteId)) {
            return false;
        }
        return true;
    }

    
}
