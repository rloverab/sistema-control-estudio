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

import clases.Consultas;
import clases.Controls;
import clases.Docente;
import clases.Formatter;
import clases.Persona;
import clases.TextFieldToToolTip;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import servicios.ConnectionDB;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class VentanaDocentes extends javax.swing.JInternalFrame {
    private final ConnectionDB conn;
    private final Consultas consultas;
    private Docente docente;
    private Persona persona;
    private Accion accion;
    
    private enum Accion {
        ACTUALIZAR,
        BUSCAR,
        COMPROBAR,
        MOSTRAR,
        NUEVO
    }
    
    private static enum Campos {
        PRIMER_APELLIDO,
        SEGUNDO_APELLIDO,
        PRIMER_NOMBRE,
        SEGUNDO_NOMBRE,
        CEDULA,
        FECHA_DE_NACIMIENTO,
        LUGAR_DE_NACIMIENTO,
        SEXO,
        ESTADO_CIVIL,
        ESTADO,
        MUNICIPIO,
        PARROQUIA,
        DIRECCION,
        TELEFONO_LOCAL,
        TELEFONO_MOVIL,
        CORREO_ELECTRONICO,
        ETNIAS,
        CONDICION
    }

    /**
     * Creates new form VentanaDocentes
     * @param conn
     */
    public VentanaDocentes(ConnectionDB conn) {
        initComponents();
        this.conn = conn;
        
        //controls = new Controls(conn);
        consultas =  new Consultas(conn);
        
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
            if(component instanceof JTextFieldDateEditor){
                ((JTextFieldDateEditor)component).setEditable(false);
                ((JTextFieldDateEditor)component).setOpaque(true);
            }
        }
        
        if (this.conn != null) {
            fillComboBoxSexos();
            fillComboBoxEstadosCiviles();
            fillComboBoxEstados();
            fillComboBoxEtnias();
            //fillComboBoxCondicion();
        }

        reset();
    }

    //Setters
    //Getters
    //Actions
    
    private void fillComboBoxSexos() {        
        Controls.fillComboBox(cbxSexos, consultas.getSexos(), null);        
    }

    private void fillComboBoxEstadosCiviles() {        
        Controls.fillComboBox(cbxEstadosCiviles, consultas.getEstadosCiviles(), null);
    }

    private void fillComboBoxEtnias() {        
        Controls.fillComboBox(cbxEtnias, consultas.getEtnias(), null);
    }

    private void fillComboBoxEstados() {
        Controls.fillComboBox(cbxEstados, consultas.getEstados(), null);
    }

    private void fillComboBoxMunicipios() {
        if (cbxEstados.getSelectedIndex() >= 0) {
            cbxMunicipios.setEnabled(true);            
            Controls.fillComboBox(
                    cbxMunicipios, 
                    consultas.getMunicipios(cbxEstados.getSelectedItem().toString()), 
                    null);
        } else {
            cbxMunicipios.removeAllItems();
            cbxMunicipios.setEnabled(false);
        }
    }

    private void fillComboBoxParroquias() {
        if (cbxMunicipios.getSelectedIndex() >= 0) {
            cbxParroquias.setEnabled(true);            
            Controls.fillComboBox(
                    cbxParroquias, 
                    consultas.getParroquias(
                            cbxEstados.getSelectedItem().toString(), 
                            cbxMunicipios.getSelectedItem().toString()), 
                    null);
            
        } else {
            cbxParroquias.removeAllItems();
            cbxParroquias.setEnabled(false);
        }

    }

    private void searchPersona() {
        String cedula;

        cedula = cbxCedulaLetras.getSelectedItem().toString() + txtCedulaNumero.getText().trim();
        
        persona = consultas.getPersonas(cedula);
    }
    
    private boolean searchDocente(){
        String letra;
        String numero;
        String cedula;

        letra = cbxCedulaLetras.getSelectedIndex() > -1 ? cbxCedulaLetras.getSelectedItem().toString().trim() : "";
        numero = txtCedulaNumero.getText().trim();

        if (!letra.isEmpty() && !numero.isEmpty()) {
            cedula = letra + numero;
            docente = consultas.getDocente(cedula);
            return true;
        } else {
            JOptionPane.showInternalMessageDialog(
                    this,
                    "Debe ingresar una cédula de identidad",
                    "Buscar estudiante",
                    JOptionPane.ERROR_MESSAGE);

            if (letra.isEmpty()) {
                cbxCedulaLetras.requestFocus();
            } else {
                txtCedulaNumero.requestFocus();
            }

            return false;
        }
    }
    
    private void search(){
        if (searchDocente()) {
            if (docente != null) {
                accion = Accion.MOSTRAR;
                fillFields();
            } else {
                JOptionPane.showInternalMessageDialog(
                        this,
                        "Docente no registrado",
                        "Buscar estudiante",
                        JOptionPane.ERROR_MESSAGE);
                accion = Accion.BUSCAR;
            }
            prepareFields();
        }
    }
    
    private void newMode() {
        clean();
        accion = Accion.COMPROBAR;
        prepareFields();
        //fillListDocumentos(0);
    }
    
    private void updateMode() {
        accion = Accion.ACTUALIZAR;
        prepareFields();
    }
    
    private void reset() {
        clean();
        accion = Accion.BUSCAR;
        prepareFields();
    }
    
    private void check() {
        searchPersona();
        accion = Accion.NUEVO;
        
        docente = new Docente();
        if (persona != null) {            
            docente.setPersona(persona);
            fillFields();
        }
        /*
        if (persona != null) {
//            fillFields();
        } else {
//            fillListDocumentos(0);
//            fillComboBoxCarreras();
        }
        */
        prepareFields();        
    }
    
    private void cleanDocente(){
        docente = null;
    }
    
    private void cleanFields() {
        cbxCedulaLetras.setSelectedIndex(-1);
        txtCedulaNumero.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        dateFechaNacimiento.setDate(null);
        //lblEdad2.setText("");
        txtLugarNacimiento.setText("");
        cbxSexos.setSelectedIndex(-1);
        cbxEstadosCiviles.setSelectedIndex(-1);
        cbxParroquias.setSelectedIndex(-1);
        cbxMunicipios.setSelectedIndex(-1);
        cbxEstados.setSelectedIndex(-1);
        txtDireccion.setText("");
        txtTelefonoLocal.setText("");
        txtTelefonoMovil.setText("");
        txtCorreoElectronico.setText("");
        cbxEtnias.setSelectedIndex(-1);
        chkActivo.setSelected(false);
    }
    
    private void fillFields(){
        if (docente != null) {
            cleanFields();

            cbxCedulaLetras.setSelectedItem(docente.getCedulaLetra());
            txtCedulaNumero.setText(docente.getCedulaNumero());
            txtNombre1.setText(docente.getNombre1());
            txtNombre2.setText(docente.getNombre2());
            txtApellido1.setText(docente.getApellido1());
            txtApellido2.setText(docente.getApellido2());
            dateFechaNacimiento.setDate(docente.getFechaNacimiento());
            txtLugarNacimiento.setText(docente.getLugarNacimiento());
            /*
            lblEdad2.setText(
                    String.format(
                            "%d %s",
                            estudiante.getEdad(),
                            estudiante.getEdad() != 1 ? "años" : "año"
                    )
            );
            */
            cbxSexos.setSelectedItem(docente.getSexo());
            cbxEstadosCiviles.setSelectedItem(docente.getEstadoCivil());
            cbxEstados.setSelectedItem(docente.getEstado());
            cbxMunicipios.setSelectedItem(docente.getMunicipio());
            cbxParroquias.setSelectedItem(docente.getParroquia());
            txtDireccion.setText(docente.getDireccion());
            txtTelefonoLocal.setText(docente.getTelefonoLocal(false));
            txtTelefonoMovil.setText(docente.getTelefonoMovil(false));
            txtCorreoElectronico.setText(docente.getCorreoElectronico());
            cbxEtnias.setSelectedItem(docente.getEtnia());
            chkActivo.setSelected(docente.isActivo());
        }
    }
    
    private void prepareFields() {
        cbxCedulaLetras.setEnabled(accion == Accion.BUSCAR || accion == Accion.COMPROBAR || accion == Accion.ACTUALIZAR);
        txtCedulaNumero.setEnabled(accion == Accion.BUSCAR || accion == Accion.COMPROBAR || accion == Accion.ACTUALIZAR);
        txtNombre1.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtNombre2.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtApellido1.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtApellido2.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        dateFechaNacimiento.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtLugarNacimiento.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxSexos.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEstadosCiviles.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEstados.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxMunicipios.setEnabled((accion == Accion.NUEVO && cbxMunicipios.getSelectedIndex() >= 0) || accion == Accion.ACTUALIZAR);
        cbxParroquias.setEnabled((accion == Accion.NUEVO && cbxParroquias.getSelectedIndex() >= 0) || accion == Accion.ACTUALIZAR);
        txtDireccion.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtTelefonoLocal.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtTelefonoMovil.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        txtCorreoElectronico.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxEtnias.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        chkActivo.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        //cbxCondiciones.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        //cbxDetalles.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        //cbxDetalles.setVisible((accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR) && cbxDetalles.getItemCount() > 0);
        //listDocumentosPendientes.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        //listDocumentosConsignados.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);

        btnBuscar.setVisible(accion == Accion.BUSCAR);
        btnComprobar.setVisible(accion == Accion.COMPROBAR);
        btnActualizar.setEnabled(accion == Accion.MOSTRAR);
        btnNuevo.setEnabled(accion == Accion.BUSCAR);
        btnGuardar.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        btnReiniciar.setEnabled(accion == Accion.COMPROBAR || accion == Accion.NUEVO || accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR);
    }
    
    private void updateDocente() {
        for (Campos campo : Campos.values()) {
            updateDocente(campo);
        }
    }
    
    private void updateDocente(Campos campos){
        String cedula;
        String letra;
        String numero;

        if (docente != null) {
            switch (campos) {
                case PRIMER_APELLIDO:
                    docente.setApellido1(txtApellido1.getText().trim());
                    break;
                case SEGUNDO_APELLIDO:
                    docente.setApellido2(txtApellido2.getText().trim());
                    break;
                case PRIMER_NOMBRE:
                    docente.setNombre1(txtNombre1.getText().trim());
                    break;
                case SEGUNDO_NOMBRE:
                    docente.setNombre2(txtNombre2.getText().trim());
                    break;
                case CEDULA:
                    letra = cbxCedulaLetras.getSelectedItem().toString().trim();
                    numero = txtCedulaNumero.getText().trim();
                    cedula = letra + numero;
                    docente.setCedula(cedula);
                    break;
                case FECHA_DE_NACIMIENTO:
                    docente.setFechaNacimiento(dateFechaNacimiento.getDate());
                    break;
                case LUGAR_DE_NACIMIENTO:
                    docente.setLugarNacimiento(txtLugarNacimiento.getText().trim());
                    break;
                case SEXO:
                    docente.setSexo(cbxSexos.getSelectedItem().toString());
                    break;
                case ESTADO_CIVIL:
                    docente.setEstadoCivil(cbxEstadosCiviles.getSelectedItem().toString());
                    break;
                case ESTADO:
                    if (cbxEstados.getSelectedIndex() >= 0) {
                        docente.setEstado(cbxEstados.getSelectedItem().toString());
                    } else {
                        docente.setEstado("");
                    }
                    break;
                case MUNICIPIO:
                    if (cbxMunicipios.getSelectedIndex() >= 0) {
                        docente.setMunicipio(cbxMunicipios.getSelectedItem().toString());
                    } else {
                        docente.setMunicipio("");
                    }
                    break;
                case PARROQUIA:
                    if (cbxParroquias.getSelectedIndex() >= 0) {
                        docente.setParroquia(cbxParroquias.getSelectedItem().toString());
                    } else {
                        docente.setParroquia("");
                    }
                    break;
                case DIRECCION:
                    docente.setDireccion(txtDireccion.getText().trim());
                    break;
                case TELEFONO_LOCAL:
                    docente.setTelefonoLocal(txtTelefonoLocal.getText().contains("_") ? "" : txtTelefonoLocal.getText());
                    break;
                case TELEFONO_MOVIL:
                    docente.setTelefonoMovil(txtTelefonoMovil.getText().contains("_") ? "" : txtTelefonoMovil.getText());
                    break;
                case CORREO_ELECTRONICO:
                    docente.setCorreoElectronico(txtCorreoElectronico.getText().trim());
                    break;
                case ETNIAS:
                    docente.setEtnia(cbxEtnias.getSelectedItem().toString());
                    break;
                case CONDICION:
                    docente.setActivo(chkActivo.isSelected());
                    break;
            }
        }
    }
    
    private void saveChanges(){
        Status status;

        updateDocente();

        switch (accion) {
            case ACTUALIZAR:
                status = consultas.updateDocente(docente);
                break;
            case NUEVO:
                status = consultas.insertDocente(docente);
                break;
            default:
                status = Status.ERROR;
                break;
        }

        switch (status) {
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
    /*
    private void cleanEstudiante() {
        if (!estudiantes.isEmpty()) {
            estudiantes.clear();
        }
    }
    */

    private void cleanPersona() {
        persona = null;
    }

    private void clean() {
        cleanDocente();        
        cleanPersona();
        cleanFields();
    }
    
    private boolean isValidFieldsCheckFind() {
        ArrayList<Object[]> fields = new ArrayList<>();

        fields.add(new Object[]{
            "Cédula Venezolana o Extranjera",
            cbxCedulaLetras,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Número de cédula",
            txtCedulaNumero,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_NUMERO_ENTERO,
                Controls.Verificar.ES_TEXTO_NO_VACIO
            }
        });

        return Controls.isValidFields(fields, true);
    }
    
    private boolean isValidFieldsSave() {
        ArrayList<Object[]> fields = new ArrayList<>();

        fields.add(new Object[]{
            "Cédula Venezolana o Extranjera",
            cbxCedulaLetras,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Número de cédula",
            txtCedulaNumero,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_NUMERO_ENTERO,
                Controls.Verificar.ES_TEXTO_NO_VACIO
            }
        });
        fields.add(new Object[]{
            "Primer apellido",
            txtApellido1,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_TEXTO_NO_VACIO,
                Controls.Verificar.ES_ALFABETICO_CON_ESPACIOS
            }
        });
        fields.add(new Object[]{
            "Segundo apellido",
            txtApellido2,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_ALFABETICO_CON_ESPACIOS
            }
        });
        fields.add(new Object[]{
            "Primer nombre",
            txtNombre1,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_TEXTO_NO_VACIO,
                Controls.Verificar.ES_ALFABETICO_CON_ESPACIOS}
        });
        fields.add(new Object[]{
            "Segundo nombre",
            txtNombre2,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_ALFABETICO_CON_ESPACIOS}
        });
        fields.add(new Object[]{
            "Fecha de nacimiento",
            new javax.swing.JTextField(dateFechaNacimiento.getDate() == null ? "" : "-"),
            dateFechaNacimiento,
            new Controls.Verificar[]{
                Controls.Verificar.ES_TEXTO_NO_VACIO
            }
        });
        fields.add(new Object[]{
            "Lugar de nacimiento",
            txtLugarNacimiento,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_TEXTO_NO_VACIO}
        });
        fields.add(new Object[]{
            "Sexo",
            cbxSexos,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Estado civil",
            cbxEstadosCiviles,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Estado",
            cbxEstados,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Municipio",
            cbxMunicipios,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Parroquia",
            cbxParroquias,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        fields.add(new Object[]{
            "Dirección",
            txtDireccion,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ES_TEXTO_NO_VACIO}
        });
        fields.add(new Object[]{
            "Etnia",
            cbxEtnias,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        /*
        fields.add(new Object[]{
            "Condición",
            cbxCondiciones,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        if (cbxDetalles.isVisible()) {
            fields.add(new Object[]{
                "Activo",
                cbxDetalles,
                null,
                new Controls.Verificar[]{
                    Controls.Verificar.ELEMENTO_SELECCIONADO}
            });
        }
        fields.add(new Object[]{
            "PNF",
            cbxCarreras,
            null,
            new Controls.Verificar[]{
                Controls.Verificar.ELEMENTO_SELECCIONADO}
        });
        */

        return Controls.isValidFields(fields, true);
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
        cbxCedulaLetras = new javax.swing.JComboBox<>();
        txtCedulaNumero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        lblPrimer = new javax.swing.JLabel();
        lblSegundo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnComprobar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        panelCondición = new javax.swing.JPanel();
        chkActivo = new javax.swing.JCheckBox();
        tabDatos = new javax.swing.JTabbedPane();
        panelDatosPersonales = new javax.swing.JPanel();
        panelInformacionPersonal = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        dateFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtLugarNacimiento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbxSexos = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbxEstadosCiviles = new javax.swing.JComboBox<>();
        panelDireccionHabitacion = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cbxEstados = new javax.swing.JComboBox<>();
        cbxMunicipios = new javax.swing.JComboBox<>();
        cbxParroquias = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        panelInformacionContacto = new javax.swing.JPanel();
        lblTelefonoLocal = new javax.swing.JLabel();
        lblCorreoElectronico = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        lblTelefonoMovil = new javax.swing.JLabel();
        txtTelefonoLocal = new javax.swing.JFormattedTextField();
        txtTelefonoMovil = new javax.swing.JFormattedTextField();
        panelEtnia = new javax.swing.JPanel();
        cbxEtnias = new javax.swing.JComboBox<>();
        panelHistorial = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setTitle("Docentes");

        lblCedula.setText("Cédula");

        cbxCedulaLetras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));

        txtCedulaNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaNumeroKeyTyped(evt);
            }
        });

        jLabel2.setText("Nombres");

        jLabel3.setText("Apellidos");

        lblPrimer.setText("Primer");

        lblSegundo.setText("Segundo");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnComprobar.setText("Comprobar");
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
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

        panelCondición.setBorder(javax.swing.BorderFactory.createTitledBorder("Condición"));

        chkActivo.setText("Docente activo");

        javax.swing.GroupLayout panelCondiciónLayout = new javax.swing.GroupLayout(panelCondición);
        panelCondición.setLayout(panelCondiciónLayout);
        panelCondiciónLayout.setHorizontalGroup(
            panelCondiciónLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCondiciónLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkActivo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCondiciónLayout.setVerticalGroup(
            panelCondiciónLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCondiciónLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkActivo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInformacionPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder("Información personal"));

        jLabel7.setText("Fecha de nacimiento");

        dateFechaNacimiento.setDateFormatString("dd/MM/yyyy");

        jLabel8.setText("Lugar de nacimiento");

        jLabel9.setText("Sexo");

        cbxSexos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));
        cbxSexos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSexosItemStateChanged(evt);
            }
        });

        jLabel10.setText("Estado civil");

        cbxEstadosCiviles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxEstadosCiviles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadosCivilesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelInformacionPersonalLayout = new javax.swing.GroupLayout(panelInformacionPersonal);
        panelInformacionPersonal.setLayout(panelInformacionPersonalLayout);
        panelInformacionPersonalLayout.setHorizontalGroup(
            panelInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionPersonalLayout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLugarNacimiento))
            .addGroup(panelInformacionPersonalLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelInformacionPersonalLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxSexos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxEstadosCiviles, 0, 159, Short.MAX_VALUE))
        );
        panelInformacionPersonalLayout.setVerticalGroup(
            panelInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionPersonalLayout.createSequentialGroup()
                .addGroup(panelInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLugarNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxSexos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbxEstadosCiviles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelDireccionHabitacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección de habitación"));

        jLabel11.setText("Estado");

        cbxEstados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxEstados.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadosItemStateChanged(evt);
            }
        });

        cbxMunicipios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMunicipios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMunicipiosItemStateChanged(evt);
            }
        });

        cbxParroquias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxParroquias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxParroquiasItemStateChanged(evt);
            }
        });

        jLabel12.setText("Municipio");

        jLabel13.setText("Parroquia");

        jLabel17.setText("Dirección");

        javax.swing.GroupLayout panelDireccionHabitacionLayout = new javax.swing.GroupLayout(panelDireccionHabitacion);
        panelDireccionHabitacion.setLayout(panelDireccionHabitacionLayout);
        panelDireccionHabitacionLayout.setHorizontalGroup(
            panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireccionHabitacionLayout.createSequentialGroup()
                .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDireccionHabitacionLayout.createSequentialGroup()
                        .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(cbxParroquias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelDireccionHabitacionLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccion)))
                .addGap(6, 6, 6))
        );
        panelDireccionHabitacionLayout.setVerticalGroup(
            panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireccionHabitacionLayout.createSequentialGroup()
                .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxParroquias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelInformacionContacto.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de contacto"));

        lblTelefonoLocal.setText("Telefóno local");

        lblCorreoElectronico.setText("Correo electrónico");

        lblTelefonoMovil.setText("Teléfono Móvil");

        javax.swing.GroupLayout panelInformacionContactoLayout = new javax.swing.GroupLayout(panelInformacionContacto);
        panelInformacionContacto.setLayout(panelInformacionContactoLayout);
        panelInformacionContactoLayout.setHorizontalGroup(
            panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionContactoLayout.createSequentialGroup()
                .addGroup(panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCorreoElectronico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTelefonoLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelInformacionContactoLayout.createSequentialGroup()
                        .addComponent(txtTelefonoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTelefonoMovil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelInformacionContactoLayout.setVerticalGroup(
            panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionContactoLayout.createSequentialGroup()
                .addGroup(panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefonoLocal)
                    .addComponent(lblTelefonoMovil)
                    .addComponent(txtTelefonoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacionContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreoElectronico)
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelEtnia.setBorder(javax.swing.BorderFactory.createTitledBorder("Étnia"));

        cbxEtnias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelEtniaLayout = new javax.swing.GroupLayout(panelEtnia);
        panelEtnia.setLayout(panelEtniaLayout);
        panelEtniaLayout.setHorizontalGroup(
            panelEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEtniaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxEtnias, 0, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEtniaLayout.setVerticalGroup(
            panelEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEtniaLayout.createSequentialGroup()
                .addComponent(cbxEtnias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDatosPersonalesLayout = new javax.swing.GroupLayout(panelDatosPersonales);
        panelDatosPersonales.setLayout(panelDatosPersonalesLayout);
        panelDatosPersonalesLayout.setHorizontalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(panelInformacionContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelEtnia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(panelInformacionPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDireccionHabitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDatosPersonalesLayout.setVerticalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInformacionPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDireccionHabitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInformacionContacto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEtnia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDatos.addTab("Datos personales", panelDatosPersonales);

        javax.swing.GroupLayout panelHistorialLayout = new javax.swing.GroupLayout(panelHistorial);
        panelHistorial.setLayout(panelHistorialLayout);
        panelHistorialLayout.setHorizontalGroup(
            panelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 944, Short.MAX_VALUE)
        );
        panelHistorialLayout.setVerticalGroup(
            panelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        tabDatos.addTab("Historial", panelHistorial);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabDatos)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCedula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxCedulaLetras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCedulaNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnComprobar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPrimer)
                                            .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSegundo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelCondición, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCerrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSegundo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCedula)
                                .addComponent(cbxCedulaLetras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCedulaNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBuscar)
                                .addComponent(btnComprobar)
                                .addComponent(btnNuevo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPrimer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnActualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReiniciar))))
                    .addComponent(panelCondición, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCerrar))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxSexosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSexosItemStateChanged
        Controls.updateComboBoxTooltip(cbxSexos);
    }//GEN-LAST:event_cbxSexosItemStateChanged

    private void cbxEstadosCivilesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadosCivilesItemStateChanged
        Controls.updateComboBoxTooltip(cbxEstadosCiviles);
    }//GEN-LAST:event_cbxEstadosCivilesItemStateChanged

    private void cbxEstadosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadosItemStateChanged
        fillComboBoxMunicipios();
        Controls.updateComboBoxTooltip(cbxEstados);
    }//GEN-LAST:event_cbxEstadosItemStateChanged

    private void cbxMunicipiosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMunicipiosItemStateChanged
        fillComboBoxParroquias();
        Controls.updateComboBoxTooltip(cbxMunicipios);
    }//GEN-LAST:event_cbxMunicipiosItemStateChanged

    private void cbxParroquiasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxParroquiasItemStateChanged
        Controls.updateComboBoxTooltip(cbxParroquias);
    }//GEN-LAST:event_cbxParroquiasItemStateChanged

    private void txtCedulaNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaNumeroKeyTyped
        char key;
        key = evt.getKeyChar();

        if (txtCedulaNumero.getText().length() >= 8) {
            evt.consume();
        } else if (key < '0' || key > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaNumeroKeyTyped

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        newMode();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        if (isValidFieldsCheckFind()) {
            check();
        }
    }//GEN-LAST:event_btnComprobarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (isValidFieldsSave()) {            
            saveChanges();
            accion = Accion.MOSTRAR;
            prepareFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (isValidFieldsCheckFind()) {
            //searchPersona();
            search();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        updateMode();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        reset();
    }//GEN-LAST:event_btnReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JComboBox<String> cbxCedulaLetras;
    private javax.swing.JComboBox<String> cbxEstados;
    private javax.swing.JComboBox<String> cbxEstadosCiviles;
    private javax.swing.JComboBox<String> cbxEtnias;
    private javax.swing.JComboBox<String> cbxMunicipios;
    private javax.swing.JComboBox<String> cbxParroquias;
    private javax.swing.JComboBox<String> cbxSexos;
    private javax.swing.JCheckBox chkActivo;
    private com.toedter.calendar.JDateChooser dateFechaNacimiento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCorreoElectronico;
    private javax.swing.JLabel lblPrimer;
    private javax.swing.JLabel lblSegundo;
    private javax.swing.JLabel lblTelefonoLocal;
    private javax.swing.JLabel lblTelefonoMovil;
    private javax.swing.JPanel panelCondición;
    private javax.swing.JPanel panelDatosPersonales;
    private javax.swing.JPanel panelDireccionHabitacion;
    private javax.swing.JPanel panelEtnia;
    private javax.swing.JPanel panelHistorial;
    private javax.swing.JPanel panelInformacionContacto;
    private javax.swing.JPanel panelInformacionPersonal;
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
