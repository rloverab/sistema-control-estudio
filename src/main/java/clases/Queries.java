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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicios.ConnectionDB;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class Queries {

    private final ConnectionDB conn;

    public Queries(ConnectionDB conn) {
        this.conn = conn;
    }

    //Setters
    //Getters
    public Persona getPersonas(String cedula) {
        ResultSet rs;
        Persona persona;

        rs = conn.executeStoredProcedureWithResultSet("select_persona", cedula);
        persona = null;

        if (rs != null) {
            try {
                if (rs.next()) {
                    persona = new Persona();

                    persona.setPersonaId(rs.getInt("persona_id"));
                    persona.setCedula(rs.getString("cedula"));
                    persona.setNombre1(rs.getString("nombre1"));
                    persona.setNombre2(rs.getString("nombre2"));
                    persona.setApellido1(rs.getString("apellido1"));
                    persona.setApellido2(rs.getString("apellido2"));
                    persona.setSexo(rs.getString("sexo"));
                    persona.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    persona.setLugarNacimiento(rs.getString("lugar_nacimiento"));
                    persona.setEdad(rs.getInt("edad"));
                    persona.setEstadoCivil(rs.getString("estado_civil"));
                    persona.setEtnia(rs.getString("etnia"));
                    persona.setEstado(rs.getString("estado"));
                    persona.setMunicipio(rs.getString("municipio"));
                    persona.setParroquia(rs.getString("parroquia"));
                    persona.setDireccion(rs.getString("direccion"));
                    persona.setTelefonoLocal(rs.getString("telefono_local"));
                    persona.setTelefonoMovil(rs.getString("telefono_movil"));
                    persona.setCorreoElectronico(rs.getString("correo_electronico"));
                }
            } catch (SQLException ex) {
                persona = null;
                Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return persona;
    }

    private ArrayList<String> getArrayList(
            String field,
            String storeProcedure,
            Object... params) {
        ResultSet rs;
        ArrayList<String> arrayList;

        arrayList = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet(storeProcedure, params);

        try {
            while (rs.next()) {
                arrayList.add(rs.getString(field));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayList;
    }

    private ArrayList<Object[]> getArrayListObjects(
            String[] fields,
            String storeProcedure,
            Object... params) {
        ResultSet rs;
        ArrayList<Object[]> arrayList;
        Object[] objects;

        arrayList = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet(storeProcedure, params);

        try {
            while (rs.next()) {
                objects = new Object[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    switch (rs.getMetaData().getColumnType(i + 1)) {
                        case java.sql.Types.INTEGER:
                        case java.sql.Types.TINYINT:
                            objects[i] = rs.getInt(fields[i]);
                            break;
                        case java.sql.Types.VARCHAR:
                            objects[i] = rs.getString(fields[i]);
                            break;
                        case java.sql.Types.BOOLEAN:
                            objects[i] = rs.getBoolean(fields[i]);
                            break;
                        case java.sql.Types.DECIMAL:
                        case java.sql.Types.DOUBLE:
                            objects[i] = rs.getDouble(fields[i]);
                            break;
                        case java.sql.Types.DATE:
                            objects[i] = rs.getDate(fields[i]);
                            break;
                    }
                }

                arrayList.add(objects);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayList;
    }

    public ArrayList<String> getSexos() {
        return getArrayList(
                "sexo",
                "select_sexos");
    }

    public ArrayList<String> getEstadosCiviles() {
        return getArrayList(
                "estado_civil",
                "select_estados_civiles");
    }

    public ArrayList<String> getEtnias() {
        return getArrayList(
                "etnia",
                "select_etnias");
    }

    public ArrayList<String> getEstados() {
        return getArrayList(
                "estado",
                "select_estados");
    }

    public ArrayList<String> getMunicipios(String estado) {
        return getArrayList(
                "municipio",
                "select_municipios",
                estado);
    }

    public ArrayList<String> getParroquias(String estado, String municipio) {
        return getArrayList(
                "parroquia",
                "select_parroquias",
                estado,
                municipio);
    }

    public ArrayList<String> getCondiciones() {
        return getArrayList(
                "condicion",
                "select_condiciones");
    }

    public ArrayList<String> getDetalles(String condicion) {
        return getArrayList(
                "detalle",
                "select_detalles",
                condicion);
    }
    /*
    public ArrayList<String> getCarreras() {
        return getArrayList(
                "carrera",
                "select_carreras");
    }
    */
    
    public ArrayList<Carrera> getCarreras(){
        ResultSet rs;
        ArrayList<Carrera> arrayList;
        Carrera carrera;
        
        arrayList = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet("select_carreras");
        
        try {
            while (rs.next()) {
                carrera = new Carrera(
                        rs.getInt("id"),
                        rs.getString("carrera"));
                arrayList.add(carrera);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayList;
    }

    public ArrayList<Resolucion> getResoluciones(String carrera) {
        ResultSet rs;
        ArrayList<Resolucion> arrayList;
        Resolucion resolucion;

        arrayList = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet("select_planes_estudio_resoluciones", carrera);

        try {
            while (rs.next()) {
                resolucion = new Resolucion(
                        rs.getInt("id"),
                        rs.getInt("resolucion"),
                        rs.getInt("acta"),
                        rs.getDate("fecha"));
                arrayList.add(resolucion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayList;
    }
    
    public ArrayList<Resolucion> getResoluciones(String carrera, int limite){
        ArrayList<Resolucion> resoluciones;
        ArrayList<Resolucion> auxResoluciones;
        
        resoluciones = getResoluciones(carrera);
        auxResoluciones = new ArrayList<>();
        
        for(int i = 0; i <= resoluciones.size() && i < limite; i++){
            auxResoluciones.add(resoluciones.get(i));
        }
        
        return auxResoluciones;
    }
    
    public ArrayList<Nivel> getNiveles(String carrera, int resolucion, int acta, Date fecha){
        ResultSet rs;
        ArrayList<Nivel> niveles;
        Nivel nivel;
        
        niveles = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet(
                "select_planes_estudio_niveles",
                carrera,
                resolucion,
                acta,
                fecha);
        
        try {
            while (rs.next()) {
                nivel = new Nivel(
                        rs.getInt("id"),
                        rs.getString("nivel"),
                        rs.getInt("orden"));
                niveles.add(nivel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return niveles;
    }    

    public ArrayList<String> getEstudianteDocumentos(int estudiante_id, boolean consignado) {
        return getArrayList(
                "documento",
                "select_estudiante_documentos",
                estudiante_id,
                consignado);
    }

    public ArrayList<String> getDocumentos() {
        return getArrayList(
                "documento",
                "select_documentos",
                true);
    }

    public Estudiante getEstudiante(String cedula) {
        ResultSet rs;
        Estudiante estudiante;
        DatosAcademicos datosAcademicos;
        String detalle;

        rs = conn.executeStoredProcedureWithResultSet("select_estudiante", cedula);
        estudiante = null;

        try {
            while (rs.next()) {
                if (estudiante == null) {
                    estudiante = new Estudiante();
                }
                if (estudiante.getDatosAcademicos().isEmpty()) {
                    estudiante.setPersonaId(rs.getInt("persona_id"));
                    estudiante.setCedula(rs.getString("cedula"));
                    estudiante.setNombre1(rs.getString("nombre1"));
                    estudiante.setNombre2(rs.getString("nombre2"));
                    estudiante.setApellido1(rs.getString("apellido1"));
                    estudiante.setApellido2(rs.getString("apellido2"));
                    estudiante.setSexo(rs.getString("sexo"));
                    estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    estudiante.setLugarNacimiento(rs.getString("lugar_nacimiento"));
                    estudiante.setEdad(rs.getInt("edad"));
                    estudiante.setEstadoCivil(rs.getString("estado_civil"));
                    estudiante.setEtnia(rs.getString("etnia"));
                    estudiante.setEstado(rs.getString("estado"));
                    estudiante.setMunicipio(rs.getString("municipio"));
                    estudiante.setParroquia(rs.getString("parroquia"));
                    estudiante.setDireccion(rs.getString("direccion"));
                    estudiante.setTelefonoLocal(rs.getString("telefono_local"));
                    estudiante.setTelefonoMovil(rs.getString("telefono_movil"));
                    estudiante.setCorreoElectronico(rs.getString("correo_electronico"));
                }

                detalle = rs.getString("detalle");

                datosAcademicos = new DatosAcademicos(
                        rs.getInt("estudiante_id"),
                        rs.getString("carrera"),
                        rs.getString("condicion"),
                        detalle);

                for (String documento : getEstudianteDocumentos(datosAcademicos.getEstudianteId(), true)) {
                    datosAcademicos.addDocumento(new Documento(documento, true));
                }

                for (String documento : getEstudianteDocumentos(datosAcademicos.getEstudianteId(), false)) {
                    datosAcademicos.addDocumento(new Documento(documento, false));
                }

                estudiante.addDatosAcademicos(datosAcademicos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return estudiante;
    }

    public Docente getDocente(String cedula) {
        ResultSet rs;
        Docente docente;

        rs = conn.executeStoredProcedureWithResultSet("select_docente", cedula);
        docente = null;

        try {
            while (rs.next()) {
                /*
                if (docente == null) {
                    docente = new Docente();
                }
                */
                docente = new Docente();
                docente.setDocenteId(rs.getInt("docente_id"));
                docente.setPersonaId(rs.getInt("persona_id"));
                docente.setCedula(rs.getString("cedula"));
                docente.setNombre1(rs.getString("nombre1"));
                docente.setNombre2(rs.getString("nombre2"));
                docente.setApellido1(rs.getString("apellido1"));
                docente.setApellido2(rs.getString("apellido2"));
                docente.setSexo(rs.getString("sexo"));
                docente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                docente.setLugarNacimiento(rs.getString("lugar_nacimiento"));
                docente.setEdad(rs.getInt("edad"));
                docente.setEstadoCivil(rs.getString("estado_civil"));
                docente.setEtnia(rs.getString("etnia"));
                docente.setEstado(rs.getString("estado"));
                docente.setMunicipio(rs.getString("municipio"));
                docente.setParroquia(rs.getString("parroquia"));
                docente.setDireccion(rs.getString("direccion"));
                docente.setTelefonoLocal(rs.getString("telefono_local"));
                docente.setTelefonoMovil(rs.getString("telefono_movil"));
                docente.setCorreoElectronico(rs.getString("correo_electronico"));
                docente.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            docente = null;
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return docente;
    }
    
    public ArrayList<OfertaAcademica> getOfertasAcademicas(int periodo_id, int carrera_id, int nivel_id, int unidad_id){
        ResultSet rs;
        ArrayList<OfertaAcademica> ofertasAcademicas;
        OfertaAcademica ofertaAcademica;
        
        ofertasAcademicas = new ArrayList<>();
        
        rs = conn.executeStoredProcedureWithResultSet("select_ofertas_academicas_modulos", periodo_id, carrera_id, nivel_id, unidad_id);
        
        try {
            while(rs.next()){
                /*
                ofertaAcademica = new OfertaAcademica(
                        rs.getInt("id"),                         
                        rs.getInt("periodo_id"),
                        rs.getInt("plan_estudio_modulo_id"),                        
                        rs.getInt("cupos"),
                        rs.getInt("id_seccion"),
                        rs.getInt("id_nomenclatura")
                );
                
                ofertasAcademicas.add(ofertaAcademica);
                */
            }
        } catch (SQLException ex) {            
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ofertasAcademicas;
    }
    
    public ArrayList<Docente> getDocentes(boolean activo){
        ResultSet rs;
        ArrayList<Docente> docentes;
        Docente docente;
        
        docentes = new ArrayList<>();
        
        rs = conn.executeStoredProcedureWithResultSet("select_docentes", activo);
        
        try {
            while(rs.next()){
                docente = new Docente();
                docente.setDocenteId(rs.getInt("docente_id"));
                docente.setPersonaId(rs.getInt("persona_id"));
                docente.setCedula(rs.getString("cedula"));
                docente.setNombre1(rs.getString("nombre1"));
                docente.setNombre2(rs.getString("nombre2"));
                docente.setApellido1(rs.getString("apellido1"));
                docente.setApellido2(rs.getString("apellido2"));
                docente.setSexo(rs.getString("sexo"));
                docente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                docente.setLugarNacimiento(rs.getString("lugar_nacimiento"));
                docente.setEdad(rs.getInt("edad"));
                docente.setEstadoCivil(rs.getString("estado_civil"));
                docente.setEtnia(rs.getString("etnia"));
                docente.setEstado(rs.getString("estado"));
                docente.setMunicipio(rs.getString("municipio"));
                docente.setParroquia(rs.getString("parroquia"));
                docente.setDireccion(rs.getString("direccion"));
                docente.setTelefonoLocal(rs.getString("telefono_local"));
                docente.setTelefonoMovil(rs.getString("telefono_movil"));
                docente.setCorreoElectronico(rs.getString("correo_electronico"));
                docente.setActivo(rs.getBoolean("activo"));
                
                docentes.add(docente);
            }
        } catch (SQLException ex) {            
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return docentes;
    }

    public ArrayList<Object[]> getPlanEstudio(String carrera, String nivel, int resolucion, int acta, Date fecha) {
        String[] fields = {"id", "codigo", "unidad", "hta", "htas", "uc"};

        return getArrayListObjects(fields, "select_plan_estudio", carrera, nivel, resolucion, acta, fecha);
    }
    
    /*
    public ArrayList<Unidad> getPlanEstudio2(String carrera, String nivel, int resolucion, int acta, Date fecha){
        ResultSet rsUnidades;
        ResultSet rsModulos;
        ArrayList<Unidad> materias;        
        Unidad materia;
        Modulo modulo;
        
        materias = new ArrayList<>();
        
        rsUnidades = conn.executeStoredProcedureWithResultSet("select_plan_estudio", carrera, nivel, resolucion, acta, fecha);
        
        try {
            while(rsUnidades.next()){                
                materia = new Unidad(
                        rsUnidades.getInt("id"),
                        rsUnidades.getString("codigo"),
                        rsUnidades.getString("unidad"),
                        rsUnidades.getInt("hta"),
                        rsUnidades.getInt("htas"),
                        rsUnidades.getInt("uc"));
                
                rsModulos = conn.executeStoredProcedureWithResultSet(
                        "select_plan_estudio_modulo", 
                        materia.getId());
                
                while(rsModulos.next()){
                    modulo = new Modulo(
                            rsModulos.getInt("id"), 
                            rsModulos.getString("modulo"), 
                            rsModulos.getInt("hta"), 
                            rsModulos.getInt("htas"));
                    materia.getModulos().add(modulo);
                }
                
                materias.add(materia);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return materias;
    }
    */
    
    public ArrayList<Unidad> getPlanEstudio3(int carrera_id, int nivel_id, int resolucion_id){
        ResultSet rsUnidades;
        ResultSet rsModulos;
        ArrayList<Unidad> materias;        
        Unidad unidad;
        Modulo modulo;
        
        materias = new ArrayList<>();        
        
        rsUnidades = conn.executeStoredProcedureWithResultSet(
                "select_plan_estudio", 
                carrera_id >= 0 ? carrera_id : null, 
                nivel_id >= 0 ? nivel_id : null, 
                resolucion_id >= 0 ? resolucion_id : null);
        
        try {
            while(rsUnidades.next()){                
                unidad = new Unidad(
                        rsUnidades.getInt("id"),
                        rsUnidades.getString("codigo"),
                        rsUnidades.getString("unidad"),
                        rsUnidades.getInt("hta"),
                        rsUnidades.getInt("htas"),
                        rsUnidades.getInt("uc"));
                
                rsModulos = conn.executeStoredProcedureWithResultSet(
                        "select_plan_estudio_modulo", 
                        unidad.getId());
                
                while(rsModulos.next()){
                    modulo = new Modulo(
                            rsModulos.getInt("id"), 
                            rsModulos.getString("modulo"), 
                            rsModulos.getInt("hta"), 
                            rsModulos.getInt("htas"));
                    unidad.getModulos().add(modulo);
                }
                
                materias.add(unidad);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return materias;
    }

    //Actions
    public Status updateEstudiante(Estudiante estudiante) {
        Status status;

        status = Status.OK;

        try {
            conn.startTransaction();

            for (DatosAcademicos dato : estudiante.getDatosAcademicos()) {
                status = conn.executeStoredProcedure(
                        "update_estudiante",
                        dato.getEstudianteId(),
                        estudiante.getCedula(),
                        estudiante.getNombre1(),
                        estudiante.getNombre2(),
                        estudiante.getApellido1(),
                        estudiante.getApellido2(),
                        estudiante.getSexo(),
                        estudiante.getFechaNacimiento(),
                        estudiante.getLugarNacimiento(),
                        estudiante.getEstadoCivil(),
                        estudiante.getEtnia(),
                        estudiante.getEstado(),
                        estudiante.getMunicipio(),
                        estudiante.getParroquia(),
                        estudiante.getDireccion(),
                        estudiante.getTelefonoLocal(false),
                        estudiante.getTelefonoMovil(false),
                        estudiante.getCorreoElectronico(),
                        dato.getCarrera(),
                        dato.getCondicion(),
                        dato.getDetalle());

                if (status == Status.OK) {
                    status = updateEstudianteDocumento(dato);
                }

                if (status != Status.OK) {
                    conn.rollback();
                    break;
                }
            }

            if (status == Status.OK) {
                conn.commit();
            }
        } catch (SQLException ex) {
            if (conn.getConnection() != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

            status = Status.ERROR;
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public Status updateDocente(Docente docente) {
        Status status;

        try {
            conn.startTransaction();

            status = conn.executeStoredProcedure(
                    "update_docente",
                    docente.getDocenteId(),
                    docente.getCedula(),
                    docente.getNombre1(),
                    docente.getNombre2(),
                    docente.getApellido1(),
                    docente.getApellido2(),
                    docente.getSexo(),
                    docente.getFechaNacimiento(),
                    docente.getLugarNacimiento(),
                    docente.getEstadoCivil(),
                    docente.getEtnia(),
                    docente.getEstado(),
                    docente.getMunicipio(),
                    docente.getParroquia(),
                    docente.getDireccion(),
                    docente.getTelefonoLocal(false),
                    docente.getTelefonoMovil(false),
                    docente.getCorreoElectronico(),
                    docente.isActivo());

            if (status == Status.OK) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException ex) {
            if (conn.getConnection() != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

            status = Status.ERROR;
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public Status insertEstudiante(Estudiante estudiante) {
        Status status;

        status = Status.OK;

        try {
            conn.startTransaction();

            for (DatosAcademicos datos : estudiante.getDatosAcademicos()) {
                status = conn.executeStoredProcedure(
                        "insert_estudiante",
                        estudiante.getCedula(), //cédula
                        estudiante.getNombre1(), //nombre1
                        estudiante.getNombre2(), //nombre2
                        estudiante.getApellido1(), //apellido1
                        estudiante.getApellido2(), //apellido2
                        estudiante.getSexo(), //sexo
                        estudiante.getFechaNacimiento(), //fecha_nacimiento
                        estudiante.getLugarNacimiento(), //lugar_nacimiento
                        estudiante.getEstadoCivil(), //estado_civil
                        estudiante.getEtnia(), //etnia
                        estudiante.getEstado(), //estado
                        estudiante.getMunicipio(), //municipio
                        estudiante.getParroquia(), //parroquia
                        estudiante.getDireccion(), //direccion
                        estudiante.getTelefonoLocal(false), //telefono_local
                        estudiante.getTelefonoMovil(false), //telefono_movil
                        estudiante.getCorreoElectronico(), //correo_electronico
                        datos.getCarrera(), //carrera                    
                        datos.getCondicion(), //codicion
                        datos.getDetalle()); //detalle

                datos.setEstudianteId(
                        getEstudiante(estudiante.getCedula()).getDatosAcademicos().get(0).getEstudianteId());

                if (status == Status.OK) {
                    status = updateEstudianteDocumento(datos);
                }

                if (status != Status.OK) {
                    conn.rollback();
                    break;
                }
            }

            if (status == Status.OK) {
                conn.commit();
            }
        } catch (SQLException ex) {
            if (conn.getConnection() != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public Status insertDocente(Docente docente) {
        Status status;

        try {
            conn.startTransaction();
            status = conn.executeStoredProcedure(
                    "insert_docente",
                    docente.getCedula(), //cédula
                    docente.getNombre1(), //nombre1
                    docente.getNombre2(), //nombre2
                    docente.getApellido1(), //apellido1
                    docente.getApellido2(), //apellido2
                    docente.getSexo(), //sexo
                    docente.getFechaNacimiento(), //fecha_nacimiento
                    docente.getLugarNacimiento(), //lugar_nacimiento
                    docente.getEstadoCivil(), //estado_civil
                    docente.getEtnia(), //etnia
                    docente.getEstado(), //estado
                    docente.getMunicipio(), //municipio
                    docente.getParroquia(), //parroquia
                    docente.getDireccion(), //direccion
                    docente.getTelefonoLocal(false), //telefono_local
                    docente.getTelefonoMovil(false), //telefono_movil
                    docente.getCorreoElectronico(), //correo_electronico
                    docente.isActivo()); //activo

            if (status == Status.OK) {
                conn.commit();
            }else{
                conn.rollback();
            }
            
            docente.setDocenteId(getDocente(docente.getCedula()).getDocenteId());
        } catch (SQLException ex) {
            if (conn.getConnection() != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            status = Status.ERROR;
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public Status updateEstudianteDocumento(DatosAcademicos datosAcademicos) {
        Status status;

        status = Status.OK;

        for (Documento documento : datosAcademicos.getDocumentos()) {
            status = conn.executeStoredProcedure(
                    "update_estudiante_documento",
                    datosAcademicos.getEstudianteId(),
                    documento.getDocumento(),
                    documento.isConsignado());
            if (Status.OK != status) {
                break;
            }
        }

        return status;
    }
    
    public ArrayList<Periodo> getPeriodos(){
        ResultSet rs;
        ArrayList<Periodo> arrayList;
        Periodo periodo;

        arrayList = new ArrayList<>();
        rs = conn.executeStoredProcedureWithResultSet("select_periodos");

        try {
            while (rs.next()) {
                periodo = new Periodo(
                        rs.getInt("id"),
                        rs.getString("periodo"),
                        rs.getDate("fecha_inicial"),
                        rs.getDate("fecha_final"),                        
                        rs.getBoolean("vigente"));
                arrayList.add(periodo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayList;
    }
    
    public Status insertPeriodo(Periodo periodo){
        return conn.executeStoredProcedure(
                "insert_periodo", 
                periodo.getPeriodo(), 
                periodo.getFechaInicial(), 
                periodo.getFechaFinal());
    }
    
    public Status updatePeriodo(Periodo periodo){
        return conn.executeStoredProcedure(
                "update_periodo", 
                periodo.getId(), 
                periodo.getPeriodo(), 
                periodo.getFechaInicial(), 
                periodo.getFechaFinal());
    }
}
