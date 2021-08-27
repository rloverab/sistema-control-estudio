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

import java.util.ArrayList;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class DatosAcademicos {
    private int estudianteId;
    private String carrera;
    private String condicion;
    private String detalle;
    private final ArrayList<Documento> documentos;

    public DatosAcademicos(int estudianteId, String carrera, String condicion, String detalle) {
        this.estudianteId = estudianteId;
        this.carrera = carrera;
        this.condicion = condicion;
        this.detalle = detalle;
        documentos = new ArrayList<>();
    }

    public DatosAcademicos() {
        this(0, "", "", "");
    }
    
    public void setEstudianteId(int estudianteId){
        this.estudianteId = estudianteId;        
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public void setDetalle(String condicion) {
        this.detalle = condicion;
    }
    
    public int getEstudianteId(){
        return estudianteId;
    }
    
    public String getCarrera() {
        return carrera;
    }

    public String getCondicion() {
        return condicion;
    }

    public String getDetalle() {
        return detalle;
    }
    
    public ArrayList<String> getDocumentos(boolean consignado){
        ArrayList<String> arrayDocumentos;
        
        arrayDocumentos = new ArrayList<>();
        
        documentos
                .stream()
                .filter(documento -> consignado == documento.isConsignado())
                .forEach(documento -> arrayDocumentos.add(documento.getDocumento()));
        
        return arrayDocumentos;
    }
    
    public ArrayList<Documento> getDocumentos(){
        return documentos;
    }
    
    public void addDocumento(Documento documento){
        documentos.add(documento);
    }
    
    public void removeDocumento(Documento documento){
        documentos.remove(documento);
    }
    
    public void removeDocumento(int index){
        documentos.remove(index);
    }

    @Override
    public String toString() {
        return "DatosAcademicos{" + "estudianteId=" + estudianteId + ", carrera=" + carrera + ", condicion=" + condicion + ", detalle=" + detalle + ", documentos=" + documentos + '}';
    }
    
    
}