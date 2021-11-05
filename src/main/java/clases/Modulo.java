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

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class Modulo implements Cloneable{
    private final int id;
    private final String modulo;
    private final int hta;
    private final int htas;
    private int docente_id;

    public Modulo(int id, String modulo, int hta, int htas) {
        this.id = id;
        this.modulo = modulo;
        this.hta = hta;
        this.htas = htas;   
        docente_id = -1;
    }

    public int getDocente_id() {
        return docente_id;
    }

    public void setDocente_id(int docente_id) {
        this.docente_id = docente_id;
    }
    
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

    @Override
    public String toString() {
        return modulo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    
}
