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

import java.sql.Date;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class Periodo {
    private int id;
    private String periodo;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean activo;

    public Periodo(int id, String periodo, Date fechaInicial, Date fechaFinal, boolean activo) {
        this.id = id;
        this.periodo = periodo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }    
}
