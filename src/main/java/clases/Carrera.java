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
public class Carrera {
    private int id;
    private String carrera;

    public Carrera(int id, String carrera) {
        this.id = id;
        this.carrera = carrera;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public String getCarrera() {
        return carrera;
    }

    @Override
    public String toString() {
        return carrera;
    }
}
