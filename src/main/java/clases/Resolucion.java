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

import java.sql.Date;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class Resolucion {
    private final int id;
    private final int resolucion;
    private final int acta;
    private final Date fecha;

    public Resolucion(int id, int resolucion, int acta, Date fecha) {
        this.id = id;
        this.resolucion = resolucion;
        this.acta = acta;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }
    
    public int getResolucion() {
        return resolucion;
    }

    public int getActa() {
        return acta;
    }

    public Date getFecha() {        
        return fecha;
    }
    
    @Override
    public String toString() {        
        return String.format(
                "Resolución: Nº %d - Acta: Nº %d - Fecha: %s", 
                resolucion,
                acta,
                fecha.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
