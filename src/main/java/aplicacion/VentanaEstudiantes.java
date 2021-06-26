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
package aplicacion;

import clases.Estudiante;
import clases.Formatter;
import clases.Persona;
import clases.TextFieldToToolTip;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import servicios.ConnectionDB;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera
 */
public class VentanaEstudiantes extends javax.swing.JInternalFrame {    
    private final ConnectionDB conn;
    private final ArrayList<Estudiante> estudiantes;
    private Persona persona;
    private Accion accion;       
    
    
    private enum Accion {
        ACTUALIZAR,
        BUSCAR, 
        COMPROBAR,
        MOSTRAR, 
        NUEVO
    }

    /**
     * Creates new form Estudiantes
     *
     * @param conn
     */
    public VentanaEstudiantes(ConnectionDB conn) {
        initComponents();        
        this.conn = conn;
        estudiantes = new ArrayList<>();  
        persona = null;

        txtCedulaNumero.getDocument().addDocumentListener(new TextFieldToToolTip(txtCedulaNumero));
        txtNombre1.getDocument().addDocumentListener(new TextFieldToToolTip(txtNombre1));
        txtNombre2.getDocument().addDocumentListener(new TextFieldToToolTip(txtNombre2));
        txtApellido1.getDocument().addDocumentListener(new TextFieldToToolTip(txtApellido1));
        txtApellido2.getDocument().addDocumentListener(new TextFieldToToolTip(txtApellido2));
        txtDireccion.getDocument().addDocumentListener(new TextFieldToToolTip(txtDireccion));
        txtCorreoElectronico.getDocument().addDocumentListener(new TextFieldToToolTip(txtCorreoElectronico));
        txtLugarNacimiento.getDocument().addDocumentListener(new TextFieldToToolTip(txtLugarNacimiento));
        txtTelefonoLocal.setFormatterFactory(new Formatter("(####)-###.##.##", '_'));
        txtTelefonoMovil.setFormatterFactory(new Formatter("(####)-###.##.##", '_'));
        dateFechaNacimiento.setDateFormatString("dd/MM/yyy");
        for (Component component : dateFechaNacimiento.getComponents()) {
            System.out.println(component.toString());
            if(component instanceof JTextFieldDateEditor){
                ((JTextFieldDateEditor)component).setEditable(false);
                ((JTextFieldDateEditor)component).setOpaque(true);
            }
        }
        
        if (this.conn != null) {
            fillComboBoxSexo();
            fillComboBoxEstadoCivil();
            fillComboBoxEstado();
            fillComboBoxEtnia();
            fillComboBoxCondicion();
        }

        reset();
    }

    //Setters
    //Getters
    //Actions
    private void fillComboBoxSexo() {
        fillComboBox(
                cbxSexo,
                null,
                "sexo",
                "select_sexos");
    }

    private void fillComboBoxEstadoCivil() {
        fillComboBox(
                cbxEstadoCivil,
                null,
                "estado_civil",
                "select_estados_civiles");
    }

    private void fillComboBoxEtnia() {
        fillComboBox(
                cbxEtnia,
                null,
                "etnia",
                "select_etnias");
    }

    private void fillComboBoxEstado() {
        fillComboBox(
                cbxEstado,
                null,
                "estado",
                "select_estados");
    }

    private void fillComboBoxMunicipio() {
        if (cbxEstado.getSelectedIndex() >= 0) {
            cbxMunicipio.setEnabled(true);
            fillComboBox(
                    cbxMunicipio,
                    null,
                    "municipio",
                    "select_municipios",
                    cbxEstado.getSelectedItem().toString());
        } else {
            cbxMunicipio.removeAllItems();
            cbxMunicipio.setEnabled(false);
        }
    }

    private void fillComboBoxParroquia() {
        if (cbxMunicipio.getSelectedIndex() >= 0) {
            cbxParroquia.setEnabled(true);
            fillComboBox(
                    cbxParroquia,
                    null,
                    "parroquia",
                    "select_parroquias",
                    cbxEstado.getSelectedItem().toString(),
                    cbxMunicipio.getSelectedItem().toString());
        } else {
            cbxParroquia.removeAllItems();
            cbxParroquia.setEnabled(false);
        }

    }

    private void fillComboBoxCondicion() {
        fillComboBox(cbxCondicion,
                null,
                "condicion",
                "select_condiciones");
    }

    private void fillComboBoxDetalle() {
        boolean activo;

        if (cbxCondicion.getSelectedIndex() > -1) {
            fillComboBox(cbxDetalle,
                    null,
                    "detalle",
                    "select_detalles",
                    cbxCondicion.getSelectedItem().toString());

            activo = cbxDetalle.getItemCount() > 0;
                        
            cbxDetalle.setSelectedIndex(-1);
            cbxDetalle.setVisible(activo);
            lblDetalle.setVisible(activo);
        }
    }
    
    private void fillComboBoxCarreras(){
        if(!estudiantes.isEmpty()){
                estudiantes.forEach(e -> {
                    cbxCarrera.addItem(e.getCarrera());
                });
        }else{            
            fillComboBox(
                    cbxCarrera, 
                    null, 
                    "carrera", 
                    "select_carreras");
            cbxCarrera.setSelectedIndex(-1);
        }
    }

    private void fillComboBox(
            JComboBox comboBox,
            String itemInicial,
            String field,
            String storeProcedure,
            Object... params) {
        ResultSet rs;

        if (comboBox.getItemCount() > 0) {
            comboBox.removeAllItems();
        }

        if (itemInicial != null) {
            comboBox.addItem(itemInicial);
        }

        rs = conn.executeStoredProcedureWithResultSet(storeProcedure, params);
        try {
            while (rs.next()) {
                comboBox.addItem(rs.getString(field));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillListDocumentos(int estudiante_id) {
        ResultSet rs;        
        
        DefaultListModel modeloConsignados = (DefaultListModel)listDocumentosConsignados.getModel();
        DefaultListModel modeloPendientes = (DefaultListModel)listDocumentosPendientes.getModel();
        
        modeloConsignados.removeAllElements();
        modeloPendientes.removeAllElements();
        
        switch(accion){
            case ACTUALIZAR:
            case MOSTRAR:                
                rs = conn.executeStoredProcedureWithResultSet("select_estudiante_documentos", estudiante_id);
                
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            if (rs.getBoolean("consignado")) {
                                modeloConsignados.addElement(rs.getString("documento"));
                            } else {
                                modeloPendientes.addElement(rs.getString("documento"));
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case NUEVO:
                rs = conn.executeStoredProcedureWithResultSet("select_documentos", true);
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            modeloPendientes.addElement(rs.getString("documento"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }
    
    private void updateComboBoxTooltip(JComboBox comboBox) {
        if (comboBox.getSelectedIndex() > -1) {
            comboBox.setToolTipText(comboBox.getSelectedItem().toString());
        } else {
            comboBox.setToolTipText(null);
        }
    }

    private void prepareFields() {
        cbxCedulaLetra.setEnabled(accion == Accion.BUSCAR || accion == Accion.COMPROBAR || accion == Accion.ACTUALIZAR);
        txtCedulaNumero.setEnabled(accion == Accion.BUSCAR || accion == Accion.COMPROBAR || accion == Accion.ACTUALIZAR);
        txtNombre1.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtNombre2.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtApellido1.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtApellido2.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        dateFechaNacimiento.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtLugarNacimiento.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxSexo.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEstadoCivil.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEstado.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxMunicipio.setEnabled((accion == Accion.NUEVO && cbxMunicipio.getSelectedIndex() >= 0) || accion == Accion.ACTUALIZAR);
        cbxParroquia.setEnabled((accion == Accion.NUEVO && cbxParroquia.getSelectedIndex() >= 0) || accion == Accion.ACTUALIZAR);
        txtDireccion.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtTelefonoLocal.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtTelefonoMovil.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtCorreoElectronico.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEtnia.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxCondicion.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxDetalle.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxDetalle.setVisible((accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR) && cbxDetalle.getItemCount() > 0);
        listDocumentosPendientes.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        listDocumentosConsignados.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        
        btnBuscar.setVisible(accion == Accion.BUSCAR);
        btnComprobar.setVisible(accion == Accion.COMPROBAR);        
        btnActualizar.setEnabled(accion == Accion.MOSTRAR);
        btnNuevo.setEnabled(accion == Accion.BUSCAR);
        btnGuardar.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        btnReiniciar.setEnabled(accion == Accion.COMPROBAR || accion == Accion.NUEVO || accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR);        
    }

    private void searchEstudiante() {
        String cedula;
        ResultSet rs;
        Estudiante estudiante;
        
        if(cbxCedulaLetra.getSelectedIndex() > -1){
            cedula = cbxCedulaLetra.getSelectedItem().toString() + txtCedulaNumero.getText().trim();
        }else{
            cedula = "";
        }
        
        
        if(cedula.isBlank()){
            JOptionPane.showInternalMessageDialog(
                    this, 
                    "Debe ingresar una cédula de identidad", 
                    "Buscar estudiante", 
                    JOptionPane.ERROR_MESSAGE);
        }else{        
            rs = conn.executeStoredProcedureWithResultSet("select_estudiante", cedula);

            if (rs != null) {
                estudiantes.clear();
                try {
                    while (rs.next()) {
                        estudiante = new Estudiante();
                        estudiante.setEstudianteId(rs.getInt("estudiante_id"));
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
                        estudiante.setCarrera(rs.getString("carrera"));
                        estudiante.setCondicion(rs.getString("condicion"));
                        estudiante.setDetalle(rs.getString("detalle"));

                        estudiantes.add(estudiante);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void fillFields() {
        Estudiante estudiante;
        
        if(!estudiantes.isEmpty()){
            estudiante = estudiantes.get(0);
        }else if(persona != null){
            estudiante =  new Estudiante();
            
            estudiante.setEstudianteId(0);
            estudiante.setPersonaId(persona.getPersonaId());
            estudiante.setCedula(persona.getCedula());
            estudiante.setNombre1(persona.getNombre1());
            estudiante.setNombre2(persona.getNombre2());
            estudiante.setApellido1(persona.getApellido1());
            estudiante.setApellido2(persona.getApellido2());
            estudiante.setFechaNacimiento(persona.getFechaNacimiento());
            estudiante.setLugarNacimiento(persona.getLugarNacimiento());
            estudiante.setEdad(persona.getEdad());
            estudiante.setSexo(persona.getSexo());
            estudiante.setEstadoCivil(persona.getEstadoCivil());
            estudiante.setEtnia(persona.getEtnia());
            estudiante.setEstado(persona.getEstado());
            estudiante.setMunicipio(persona.getMunicipio());
            estudiante.setParroquia(persona.getParroquia());
            estudiante.setDetalle(persona.getDireccion());
            estudiante.setTelefonoLocal(persona.getTelefonoLocal(false));
            estudiante.setTelefonoMovil(persona.getTelefonoMovil(false));
            estudiante.setCorreoElectronico(persona.getCorreoElectronico());
        }else{
            estudiante = null;
        }
        
        if(estudiante != null){
            cleanFields();
            
            cbxCedulaLetra.setSelectedItem(estudiante.getCedulaLetra());
            txtCedulaNumero.setText(estudiante.getCedulaNumero());
            txtNombre1.setText(estudiante.getNombre1());
            txtNombre2.setText(estudiante.getNombre2());
            txtApellido1.setText(estudiante.getApellido1());
            txtApellido2.setText(estudiante.getApellido2());
            //txtFechaNacimiento.setText(estudiante.getFechaNacimiento("dd/MM/yyyy"));
            dateFechaNacimiento.setDate(estudiante.getFechaNacimiento());
            txtLugarNacimiento.setText(estudiante.getLugarNacimiento());
            lblEdad2.setText(
                    String.format(
                            "%d %s",
                            estudiante.getEdad(),
                            estudiante.getEdad() != 1 ? "años" : "año"
                    )
            );
            cbxSexo.setSelectedItem(estudiante.getSexo());
            cbxEstadoCivil.setSelectedItem(estudiante.getEstadoCivil());
            cbxEstado.setSelectedItem(estudiante.getEstado());
            cbxMunicipio.setSelectedItem(estudiante.getMunicipio());
            cbxParroquia.setSelectedItem(estudiante.getParroquia());
            txtDireccion.setText(estudiante.getDireccion());
            txtTelefonoLocal.setText(estudiante.getTelefonoLocal(false));
            txtTelefonoMovil.setText(estudiante.getTelefonoMovil(false));
            txtCorreoElectronico.setText(estudiante.getCorreoElectronico());
            cbxEtnia.setSelectedItem(estudiante.getEtnia());
            
            fillComboBoxCarreras();
                        
            if(!estudiantes.isEmpty()){
                cbxCarrera.setSelectedItem(estudiante.getCarrera());                
                fillListDocumentos(estudiante.getEstudianteId());
            }
        }
        
        /*
        if (!estudiantes.isEmpty()) {            
            cleanFields();
            
            estudiante = estudiantes.get(0);
            
            cbxCedulaLetra.setSelectedItem(estudiante.getCedulaLetra());
            txtCedulaNumero.setText(estudiante.getCedulaNumero());
            txtNombre1.setText(estudiante.getNombre1());
            txtNombre2.setText(estudiante.getNombre2());
            txtApellido1.setText(estudiante.getApellido1());
            txtApellido2.setText(estudiante.getApellido2());
            dateFechaNacimiento.setText(estudiante.getFechaNacimiento("dd/MM/yyyy"));
            txtLugarNacimiento.setText(estudiante.getLugarNacimiento());
            lblEdad2.setText(
                    String.format(
                            "%d %s",
                            estudiante.getEdad(),
                            estudiante.getEdad() != 1 ? "años" : "año"
                    )
            );
            cbxSexo.setSelectedItem(estudiante.getSexo());
            cbxEstadoCivil.setSelectedItem(estudiante.getEstadoCivil());
            cbxEstado.setSelectedItem(estudiante.getEstado());
            cbxMunicipio.setSelectedItem(estudiante.getMunicipio());
            cbxMunicipio.setEnabled(false);
            cbxParroquia.setSelectedItem(estudiante.getParroquia());
            cbxParroquia.setEnabled(false);
            txtDireccion.setText(estudiante.getDireccion());
            txtTelefonoLocal.setText(estudiante.getTelefonoLocal(false));
            txtTelefonoMovil.setText(estudiante.getTelefonoMovil(false));
            txtCorreoElectronico.setText(estudiante.getCorreoElectronico());
            cbxEtnia.setSelectedItem(estudiante.getEtnia());

            estudiantes.forEach(e -> {
                cbxCarrera.addItem(e.getCarrera());
            });

            cbxCarrera.setSelectedItem(estudiante.getCarrera());
            
            fillListDocumentos(estudiante.getPersonaId());
        }
        */
    }

    private void saveChanges(){
        Status status;
        int size;
        int estudiante_id;        
        
        try {
            conn.startTransaction();
            
            switch (accion) {
                case ACTUALIZAR:
                    status = conn.executeStoredProcedure(
                            "update_estudiante",
                            estudiantes.get(cbxCarrera.getSelectedIndex()).getEstudianteId(), //estudiante_id
                            cbxCedulaLetra.getSelectedItem().toString() + txtCedulaNumero.getText(), //cédula
                            txtNombre1.getText(), //nombre1
                            txtNombre2.getText(), //nombre2
                            txtApellido1.getText(), //apellido1
                            txtApellido2.getText(), //apellido2
                            cbxSexo.getSelectedItem().toString(), //sexo
                            dateFechaNacimiento.getDate(), //fecha_nacimiento
                            txtLugarNacimiento.getText(), //lugar_nacimiento
                            cbxEstadoCivil.getSelectedItem().toString(), //estado_civil
                            cbxEtnia.getSelectedItem().toString(), //etnia
                            cbxEstado.getSelectedItem().toString(), //estado
                            cbxMunicipio.getSelectedItem().toString(), //municipio
                            cbxParroquia.getSelectedItem().toString(), //parroquia
                            txtDireccion.getText().trim(), //direccion
                            txtTelefonoLocal.getText().contains("_") ? "" : txtTelefonoLocal.getText(), //telefono_local
                            txtTelefonoMovil.getText().contains("_") ? "" : txtTelefonoMovil.getText(), //telefono_movil
                            txtCorreoElectronico.getText().trim(), //correo_electronico
                            cbxCarrera.getSelectedItem().toString(), //carrera
                            cbxCondicion.getSelectedItem().toString(), //codicion
                            cbxDetalle.getSelectedIndex() > -1 ? cbxDetalle.getSelectedItem().toString() : ""); //detalle
                    break;
                case NUEVO:
                    status = conn.executeStoredProcedure(
                            "insert_estudiante",
                            cbxCedulaLetra.getSelectedItem().toString() + txtCedulaNumero.getText(), //cédula
                            txtNombre1.getText(), //nombre1
                            txtNombre2.getText(), //nombre2
                            txtApellido1.getText(), //apellido1
                            txtApellido2.getText(), //apellido2
                            cbxSexo.getSelectedItem().toString(), //sexo
                            //txtFechaNacimiento.getText(), //fecha_nacimiento
                            dateFechaNacimiento.getDate(), //fecha_nacimiento
                            txtLugarNacimiento.getText(), //lugar_nacimiento
                            cbxEstadoCivil.getSelectedItem().toString(), //estado_civil
                            cbxEtnia.getSelectedItem().toString(), //etnia
                            cbxEstado.getSelectedItem().toString(), //estado
                            cbxMunicipio.getSelectedItem().toString(), //municipio
                            cbxParroquia.getSelectedItem().toString(), //parroquia
                            txtDireccion.getText().trim(), //direccion
                            txtTelefonoLocal.getText().contains("_") ? "" : txtTelefonoLocal.getText(), //telefono_local
                            txtTelefonoMovil.getText().contains("_") ? "" : txtTelefonoMovil.getText(), //telefono_movil
                            txtCorreoElectronico.getText().trim(), //correo_electronico
                            //cbxCarrera.getSelectedItem().toString(), //carrera
                            "Terapia Ocupacional", //carrera
                            cbxCondicion.getSelectedItem().toString(), //codicion
                            cbxDetalle.getSelectedIndex() > -1 ? cbxDetalle.getSelectedItem().toString() : ""); //detalle                
                    break;
                default:
                    status = Status.ERROR;
                    break;
            }
            
            if (status == Status.OK) {                
                if(accion == Accion.NUEVO){
                    searchEstudiante();
                }
                //estudiante_id = estudiantes.get(cbxCarrera.getSelectedIndex()).getId();
                estudiante_id = estudiantes.get(0).getPersonaId();
                System.out.println(estudiante_id);
                
                size = listDocumentosPendientes.getModel().getSize();
                
                for (int i = 0; i < size; i++) {
                    status = conn.executeStoredProcedure(
                            "update_estudiante_documento",
                            estudiante_id,
                            listDocumentosPendientes.getModel().getElementAt(i),
                            false);
                }

                size = listDocumentosConsignados.getModel().getSize();
                for (int i = 0; i < size; i++) {
                    status = conn.executeStoredProcedure("update_estudiante_documento",
                            estudiante_id,
                            listDocumentosConsignados.getModel().getElementAt(i),
                            true);
                }

                conn.commit();
            }else{                
                conn.rollback();
                status = Status.ERROR;
            }
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex1);
            }
            status = Status.ERROR;
            Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        switch(status){
            case OK:
                JOptionPane.showInternalMessageDialog(
                        this, 
                        "Datos guardados exitósamente", 
                        "Guardar cambios", 
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case ERROR:
                JOptionPane.showInternalMessageDialog(
                        this, 
                        "Ocurrió un error al guardar los cambios", 
                        "Guardar cambios", 
                        JOptionPane.ERROR_MESSAGE);
                break;                
            case EXIST:                
        }                
    }
    
    private void searchPersona(){        
        ResultSet rs;
        String cedula;
        
        cedula = cbxCedulaLetra.getSelectedItem().toString() + txtCedulaNumero.getText().trim();
        
        rs = conn.executeStoredProcedureWithResultSet("select_persona", cedula);
        
        if(rs != null){
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
                Logger.getLogger(VentanaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void moveDocumentos(JList origen, JList destino){
        Object item;
        for(Iterator selected = origen.getSelectedValuesList().iterator(); selected.hasNext();){
            item = selected.next();
            ((DefaultListModel)destino.getModel()).addElement(item);
            ((DefaultListModel)origen.getModel()).removeElement(item);
        }
    }
    
    private void moveAllDocumentos(JList origen, JList destino){
        int size = origen.getModel().getSize();        
        
        origen.setSelectionInterval(0, size - 1);
        
        moveDocumentos(origen, destino);
    }
    
    private void newMode(){
        clean();
        accion = Accion.COMPROBAR;
        prepareFields();
        fillListDocumentos(0);
    }
    
    private void updateMode(){
        accion = Accion.ACTUALIZAR;
        prepareFields();
    }
    
    private void search(){
        searchEstudiante();
        if (!estudiantes.isEmpty()) {            
            accion = Accion.MOSTRAR;            
            fillFields();
            prepareFields();
        } else {
            JOptionPane.showInternalMessageDialog(
                    this,
                    "Estudiante no registrado",
                    "Buscar estudiante",
                    JOptionPane.ERROR_MESSAGE);
            cleanFields();
            accion = Accion.BUSCAR;
            prepareFields();
        }        
    }
    
    private void check(){        
        searchPersona();
        accion = Accion.NUEVO;  
        if(persona != null){                      
            fillFields();            
        }else{
            fillListDocumentos(0);
            fillComboBoxCarreras();
        }
        prepareFields();
    }
    
    private void cleanEstudiante(){        
        if(!estudiantes.isEmpty()){
            estudiantes.clear();
        }
    }
    
    private void cleanPersona(){
        persona = null;
    }
    
    private void cleanFields() {
        cbxCedulaLetra.setSelectedIndex(-1);
        txtCedulaNumero.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        //txtFechaNacimiento.setText("");
        dateFechaNacimiento.setDate(null);
        lblEdad2.setText("");
        txtLugarNacimiento.setText("");
        cbxSexo.setSelectedIndex(-1);
        cbxEstadoCivil.setSelectedIndex(-1);
        cbxParroquia.setSelectedIndex(-1);        
        cbxMunicipio.setSelectedIndex(-1);
        cbxEstado.setSelectedIndex(-1);        
        txtDireccion.setText("");
        txtTelefonoLocal.setText("");
        txtTelefonoMovil.setText("");
        txtCorreoElectronico.setText("");
        cbxEtnia.setSelectedIndex(-1);
        if(cbxCarrera.getItemCount() > 0){
            cbxCarrera.removeAllItems();    
        }
        cbxCondicion.setSelectedIndex(-1);
        fillComboBoxDetalle();
        
        if(!((DefaultListModel)listDocumentosPendientes.getModel()).isEmpty()){
            ((DefaultListModel)listDocumentosPendientes.getModel()).removeAllElements();
        }
        
        if(!((DefaultListModel)listDocumentosConsignados.getModel()).isEmpty()){
            ((DefaultListModel)listDocumentosConsignados.getModel()).removeAllElements();
        }
        
    }

    private void clean(){
        cleanEstudiante();
        cleanPersona();
        cleanFields();
    }
    
    private void reset(){
        clean();
        accion = Accion.BUSCAR;
        prepareFields();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCedula = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        lblPrimero = new javax.swing.JLabel();
        lblSegundo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        tabDatos = new javax.swing.JTabbedPane();
        paneDatosPersonales = new javax.swing.JPanel();
        paneInformacionPersonal = new javax.swing.JPanel();
        lblFechaNacimiento = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        lblEdad2 = new javax.swing.JLabel();
        lblLugarNacimiento = new javax.swing.JLabel();
        txtLugarNacimiento = new javax.swing.JTextField();
        lblSexo = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        lblEstadoCivil = new javax.swing.JLabel();
        cbxEstadoCivil = new javax.swing.JComboBox<>();
        dateFechaNacimiento = new com.toedter.calendar.JDateChooser();
        paneDireccionHabitacion = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        lblMunicipio = new javax.swing.JLabel();
        cbxMunicipio = new javax.swing.JComboBox<>();
        lblParroquia = new javax.swing.JLabel();
        cbxParroquia = new javax.swing.JComboBox<>();
        txtDireccion = new javax.swing.JTextField();
        paneInformacionContacto = new javax.swing.JPanel();
        lblTelefonoHabitacion = new javax.swing.JLabel();
        txtTelefonoLocal = new javax.swing.JFormattedTextField();
        lblTelefonoCelular = new javax.swing.JLabel();
        txtTelefonoMovil = new javax.swing.JFormattedTextField();
        lblCorreoElectronico = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        paneEtnia = new javax.swing.JPanel();
        cbxEtnia = new javax.swing.JComboBox<>();
        paneDatosAcademicos = new javax.swing.JPanel();
        lblPNF = new javax.swing.JLabel();
        lblTrayecto = new javax.swing.JLabel();
        lblCohorte = new javax.swing.JLabel();
        lblCreditosAprobados = new javax.swing.JLabel();
        lblCreditosCursados = new javax.swing.JLabel();
        lblIndiceRendimiento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxCarrera = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        paneExpedienteNotas = new javax.swing.JPanel();
        paneDocumentos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnAgregarTodos = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEliminarTodos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listDocumentosPendientes = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        listDocumentosConsignados = new javax.swing.JList<>();
        cbxCedulaLetra = new javax.swing.JComboBox<>();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblCondicion = new javax.swing.JLabel();
        cbxCondicion = new javax.swing.JComboBox<>();
        lblDetalle = new javax.swing.JLabel();
        cbxDetalle = new javax.swing.JComboBox<>();
        txtCedulaNumero = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        btnComprobar = new javax.swing.JButton();

        setTitle("Estudiantes");
        setPreferredSize(new java.awt.Dimension(1004, 600));

        lblCedula.setText("Cédula");

        lblApellidos.setText("Apellidos");

        lblNombres.setText("Nombres");

        lblPrimero.setText("Primero");

        lblSegundo.setText("Segundo");

        btnBuscar.setText("Buscar");
        btnBuscar.setPreferredSize(new java.awt.Dimension(118, 25));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tabDatos.setPreferredSize(new java.awt.Dimension(980, 312));

        paneInformacionPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder("Información personal"));

        lblFechaNacimiento.setText("Fecha de nacimiento");

        lblEdad.setText("Edad:");

        lblEdad2.setText("##");

        lblLugarNacimiento.setText("Lugar de nacimiento");

        lblSexo.setText("Sexo");

        cbxSexo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSexoItemStateChanged(evt);
            }
        });

        lblEstadoCivil.setText("Estado civil");

        cbxEstadoCivil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadoCivilItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout paneInformacionPersonalLayout = new javax.swing.GroupLayout(paneInformacionPersonal);
        paneInformacionPersonal.setLayout(paneInformacionPersonalLayout);
        paneInformacionPersonalLayout.setHorizontalGroup(
            paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                        .addComponent(lblLugarNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLugarNacimiento))
                    .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                        .addComponent(lblFechaNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEdad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEdad2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                        .addComponent(lblSexo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstadoCivil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxEstadoCivil, 0, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneInformacionPersonalLayout.setVerticalGroup(
            paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFechaNacimiento)
                        .addComponent(lblEdad)
                        .addComponent(lblEdad2))
                    .addComponent(dateFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLugarNacimiento)
                    .addComponent(txtLugarNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSexo)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstadoCivil)
                    .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        paneDireccionHabitacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección de habitación"));

        lblEstado.setText("Estado");

        cbxEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadoItemStateChanged(evt);
            }
        });

        lblMunicipio.setText("Municipio");

        cbxMunicipio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMunicipioItemStateChanged(evt);
            }
        });

        lblParroquia.setText("Parroquia");

        cbxParroquia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxParroquiaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout paneDireccionHabitacionLayout = new javax.swing.GroupLayout(paneDireccionHabitacion);
        paneDireccionHabitacion.setLayout(paneDireccionHabitacionLayout);
        paneDireccionHabitacionLayout.setHorizontalGroup(
            paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDireccion)
                    .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMunicipio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblParroquia)
                            .addComponent(cbxParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneDireccionHabitacionLayout.setVerticalGroup(
            paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(lblMunicipio)
                    .addComponent(lblParroquia))
                .addGap(9, 9, 9)
                .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneInformacionContacto.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de contacto"));

        lblTelefonoHabitacion.setText("Teléfono local");

        txtTelefonoLocal.setToolTipText("Formato: (####)-######\nEjemplo: (0416)-5555555");

        lblTelefonoCelular.setText("Teléfono celular");

        txtTelefonoMovil.setToolTipText("Formato: (####)-######\nEjemplo: (0416)-5555555");

        lblCorreoElectronico.setText("Correo electrónico");

        javax.swing.GroupLayout paneInformacionContactoLayout = new javax.swing.GroupLayout(paneInformacionContacto);
        paneInformacionContacto.setLayout(paneInformacionContactoLayout);
        paneInformacionContactoLayout.setHorizontalGroup(
            paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInformacionContactoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefonoHabitacion)
                    .addComponent(lblCorreoElectronico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneInformacionContactoLayout.createSequentialGroup()
                        .addComponent(txtTelefonoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblTelefonoCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCorreoElectronico))
                .addContainerGap())
        );
        paneInformacionContactoLayout.setVerticalGroup(
            paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInformacionContactoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefonoHabitacion)
                    .addComponent(txtTelefonoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoCelular)
                    .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paneInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreoElectronico)
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        paneEtnia.setBorder(javax.swing.BorderFactory.createTitledBorder("Etnia"));

        cbxEtnia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEtniaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout paneEtniaLayout = new javax.swing.GroupLayout(paneEtnia);
        paneEtnia.setLayout(paneEtniaLayout);
        paneEtniaLayout.setHorizontalGroup(
            paneEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneEtniaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxEtnia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        paneEtniaLayout.setVerticalGroup(
            paneEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneEtniaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxEtnia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout paneDatosPersonalesLayout = new javax.swing.GroupLayout(paneDatosPersonales);
        paneDatosPersonales.setLayout(paneDatosPersonalesLayout);
        paneDatosPersonalesLayout.setHorizontalGroup(
            paneDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(paneInformacionContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paneEtnia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(paneInformacionPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paneDireccionHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        paneDatosPersonalesLayout.setVerticalGroup(
            paneDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneInformacionPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneDireccionHabitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(paneDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneEtnia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneInformacionContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabDatos.addTab("Información personal", paneDatosPersonales);

        lblPNF.setText("PNF");

        lblTrayecto.setText("Trayecto");

        lblCohorte.setText("Cohorte");

        lblCreditosAprobados.setText("Créditos aprobados");

        lblCreditosCursados.setText("Créditos cursados");

        lblIndiceRendimiento.setText("Índice rendimiento");

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        cbxCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fisioterapia", "Terapia Ocupacional", "Fonoaudiología", "Órtesis y Prótesis" }));
        cbxCarrera.setPreferredSize(new java.awt.Dimension(174, 24));
        cbxCarrera.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCarreraItemStateChanged(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setText("Unidades inscritas");

        javax.swing.GroupLayout paneDatosAcademicosLayout = new javax.swing.GroupLayout(paneDatosAcademicos);
        paneDatosAcademicos.setLayout(paneDatosAcademicosLayout);
        paneDatosAcademicosLayout.setHorizontalGroup(
            paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblTrayecto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblCohorte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblCreditosAprobados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblCreditosCursados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblIndiceRendimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(lblPNF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE))
                .addContainerGap())
        );
        paneDatosAcademicosLayout.setVerticalGroup(
            paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPNF)
                    .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneDatosAcademicosLayout.createSequentialGroup()
                        .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTrayecto)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCohorte)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCreditosAprobados)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCreditosCursados)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIndiceRendimiento)
                            .addComponent(jLabel5)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabDatos.addTab("Datos académicos", paneDatosAcademicos);

        javax.swing.GroupLayout paneExpedienteNotasLayout = new javax.swing.GroupLayout(paneExpedienteNotas);
        paneExpedienteNotas.setLayout(paneExpedienteNotasLayout);
        paneExpedienteNotasLayout.setHorizontalGroup(
            paneExpedienteNotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 975, Short.MAX_VALUE)
        );
        paneExpedienteNotasLayout.setVerticalGroup(
            paneExpedienteNotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 312, Short.MAX_VALUE)
        );

        tabDatos.addTab("Expediente de notas", paneExpedienteNotas);

        paneDocumentos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregar.setText(">");
        btnAgregar.setToolTipText("Agregar documento seleccionado");
        btnAgregar.setPreferredSize(new java.awt.Dimension(55, 25));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar);

        btnAgregarTodos.setText(">>");
        btnAgregarTodos.setToolTipText("Agregar todos los documentos");
        btnAgregarTodos.setPreferredSize(new java.awt.Dimension(55, 25));
        btnAgregarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTodosActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregarTodos);

        btnEliminar.setText("<");
        btnEliminar.setToolTipText("Eliminar documento seleccionado");
        btnEliminar.setPreferredSize(new java.awt.Dimension(55, 25));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar);

        btnEliminarTodos.setText("<<");
        btnEliminarTodos.setToolTipText("Eliminar todos los documentos");
        btnEliminarTodos.setPreferredSize(new java.awt.Dimension(55, 25));
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarTodos);

        paneDocumentos.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 100, 130));

        jLabel7.setText("Documentos pendientes");

        listDocumentosPendientes.setModel(new DefaultListModel());
        listDocumentosPendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listDocumentosPendientesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(listDocumentosPendientes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        paneDocumentos.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 430, 260));

        jLabel8.setText("Documentos consignados");

        listDocumentosConsignados.setModel(new DefaultListModel());
        listDocumentosConsignados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listDocumentosConsignadosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(listDocumentosConsignados);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        paneDocumentos.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 430, 261));

        tabDatos.addTab("Documentos", paneDocumentos);

        cbxCedulaLetra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Condición"));
        jPanel1.setPreferredSize(new java.awt.Dimension(306, 120));

        lblCondicion.setText("Estado");

        cbxCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCondicion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCondicionItemStateChanged(evt);
            }
        });

        lblDetalle.setText("Activo");

        cbxDetalle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDetalle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxDetalleItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCondicion, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCondicion)
                    .addComponent(cbxCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDetalle)
                    .addComponent(cbxDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtCedulaNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaNumeroKeyTyped(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnReiniciar.setText("Reiniciar");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReiniciar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReiniciar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnComprobar.setText("Comprobar");
        btnComprobar.setPreferredSize(new java.awt.Dimension(118, 25));
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCerrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidos)
                    .addComponent(lblNombres)
                    .addComponent(lblCedula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxCedulaLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedulaNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnComprobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre1)
                            .addComponent(lblPrimero)
                            .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSegundo)
                            .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCedula)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxCedulaLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCedulaNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnComprobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPrimero)
                                .addComponent(lblSegundo))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblApellidos)
                                .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(13, 13, 13)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombres)
                                .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(tabDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnGuardar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cbxCondicionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCondicionItemStateChanged
        fillComboBoxDetalle();
        updateComboBoxTooltip(cbxCondicion);
    }//GEN-LAST:event_cbxCondicionItemStateChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        newMode();        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        updateMode();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        search();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCedulaNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaNumeroKeyTyped
        char key;
        key = evt.getKeyChar();

        if (txtCedulaNumero.getText().length() >= 8) {
            evt.consume();
        } else if (key < '0' || key > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaNumeroKeyTyped

    private void cbxDetalleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxDetalleItemStateChanged
        updateComboBoxTooltip(cbxDetalle);
    }//GEN-LAST:event_cbxDetalleItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        saveChanges();
        search();
        /*
        searchEstudiante();        
        if(!estudiantes.isEmpty()){   
            cleanFields();            
            accion = Accion.MOSTRAR;            
            prepareFields();
            fillFields(); 
        }
        */
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        reset();
        /*
        cleanFields();
        prepareFields(Accion.INICIAL);
        */
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void listDocumentosConsignadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listDocumentosConsignadosMouseClicked
        if(evt.getClickCount() == 2){
            moveDocumentos(listDocumentosConsignados, listDocumentosPendientes);
        }
    }//GEN-LAST:event_listDocumentosConsignadosMouseClicked

    private void listDocumentosPendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listDocumentosPendientesMouseClicked
        if(evt.getClickCount() == 2){
            moveDocumentos(listDocumentosPendientes, listDocumentosConsignados);
        }
    }//GEN-LAST:event_listDocumentosPendientesMouseClicked

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
        moveAllDocumentos(listDocumentosConsignados, listDocumentosPendientes);
    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        moveDocumentos(listDocumentosConsignados, listDocumentosPendientes);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTodosActionPerformed
        moveAllDocumentos(listDocumentosPendientes, listDocumentosConsignados);
    }//GEN-LAST:event_btnAgregarTodosActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        moveDocumentos(listDocumentosPendientes, listDocumentosConsignados);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cbxCarreraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCarreraItemStateChanged
        if(cbxCarrera.getSelectedIndex() >= 0){
            estudiantes.forEach(estudiante -> {
                if (cbxCarrera.getSelectedItem().equals(estudiante.getCarrera())) {
                    cbxCondicion.setSelectedItem(estudiante.getCondicion());
                    fillComboBoxDetalle();
                    cbxDetalle.setSelectedItem(estudiante.getDetalle());
                    fillListDocumentos(estudiante.getPersonaId());
                }
            });
        }else if(estudiantes.isEmpty()){
            cbxCondicion.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_cbxCarreraItemStateChanged

    private void cbxEtniaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEtniaItemStateChanged
        updateComboBoxTooltip(cbxEtnia);
    }//GEN-LAST:event_cbxEtniaItemStateChanged

    private void cbxParroquiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxParroquiaItemStateChanged
        updateComboBoxTooltip(cbxParroquia);
    }//GEN-LAST:event_cbxParroquiaItemStateChanged

    private void cbxMunicipioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMunicipioItemStateChanged
        fillComboBoxParroquia();
        updateComboBoxTooltip(cbxMunicipio);
    }//GEN-LAST:event_cbxMunicipioItemStateChanged

    private void cbxEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadoItemStateChanged
        fillComboBoxMunicipio();
        updateComboBoxTooltip(cbxEstado);
    }//GEN-LAST:event_cbxEstadoItemStateChanged

    private void cbxEstadoCivilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadoCivilItemStateChanged
        updateComboBoxTooltip(cbxEstadoCivil);
    }//GEN-LAST:event_cbxEstadoCivilItemStateChanged

    private void cbxSexoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSexoItemStateChanged
        updateComboBoxTooltip(cbxSexo);
    }//GEN-LAST:event_cbxSexoItemStateChanged

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        check();
    }//GEN-LAST:event_btnComprobarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarTodos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JComboBox<String> cbxCarrera;
    private javax.swing.JComboBox<String> cbxCedulaLetra;
    private javax.swing.JComboBox<String> cbxCondicion;
    private javax.swing.JComboBox<String> cbxDetalle;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxEstadoCivil;
    private javax.swing.JComboBox<String> cbxEtnia;
    private javax.swing.JComboBox<String> cbxMunicipio;
    private javax.swing.JComboBox<String> cbxParroquia;
    private javax.swing.JComboBox<String> cbxSexo;
    private com.toedter.calendar.JDateChooser dateFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCohorte;
    private javax.swing.JLabel lblCondicion;
    private javax.swing.JLabel lblCorreoElectronico;
    private javax.swing.JLabel lblCreditosAprobados;
    private javax.swing.JLabel lblCreditosCursados;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblEdad2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblIndiceRendimiento;
    private javax.swing.JLabel lblLugarNacimiento;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblPNF;
    private javax.swing.JLabel lblParroquia;
    private javax.swing.JLabel lblPrimero;
    private javax.swing.JLabel lblSegundo;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefonoCelular;
    private javax.swing.JLabel lblTelefonoHabitacion;
    private javax.swing.JLabel lblTrayecto;
    private javax.swing.JList<String> listDocumentosConsignados;
    private javax.swing.JList<String> listDocumentosPendientes;
    private javax.swing.JPanel paneDatosAcademicos;
    private javax.swing.JPanel paneDatosPersonales;
    private javax.swing.JPanel paneDireccionHabitacion;
    private javax.swing.JPanel paneDocumentos;
    private javax.swing.JPanel paneEtnia;
    private javax.swing.JPanel paneExpedienteNotas;
    private javax.swing.JPanel paneInformacionContacto;
    private javax.swing.JPanel paneInformacionPersonal;
    private javax.swing.JTabbedPane tabDatos;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellido2;
    private javax.swing.JTextField txtCedulaNumero;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtLugarNacimiento;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JFormattedTextField txtTelefonoLocal;
    private javax.swing.JFormattedTextField txtTelefonoMovil;
    // End of variables declaration//GEN-END:variables
}
