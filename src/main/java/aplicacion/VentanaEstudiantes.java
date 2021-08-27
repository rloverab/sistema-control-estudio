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
import clases.DatosAcademicos;
import clases.Estudiante;
import clases.Documento;
import clases.Formatter;
import clases.Persona;
import clases.TextFieldToToolTip;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera
 */
public class VentanaEstudiantes extends javax.swing.JInternalFrame {

    private final Consultas consultas;
    private Estudiante estudiante;
    private Persona persona;
    private Accion accion;

    private static enum Accion {
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
        CARRERA,
        CONDICION,
        DETALLE,
        DOCUMENTOS
    }

    /**
     * Creates new form Estudiantes
     *
     * @param consultas
     */
    public VentanaEstudiantes(Consultas consultas) {
        initComponents();
        persona = null;
        estudiante = null;
        this.consultas = consultas;
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
            if (component instanceof JTextFieldDateEditor) {
                ((JTextFieldDateEditor) component).setEditable(false);
                ((JTextFieldDateEditor) component).setOpaque(true);
            }
        }

        if (this.consultas != null) {
            fillComboBoxSexos();
            fillComboBoxEstadosCiviles();
            fillComboBoxEstados();
            fillComboBoxEtnias();
            fillComboBoxCondiciones();
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

    private void fillComboBoxCondiciones() {
        Controls.fillComboBox(
                cbxCondiciones,
                consultas.getCondiciones(),
                null);
    }

    private void fillComboBoxDetalles() {
        boolean activo;

        if (cbxCondiciones.getSelectedIndex() > -1) {
            Controls.fillComboBox(
                    cbxDetalles,
                    consultas.getDetalles(cbxCondiciones.getSelectedItem().toString()),
                    null);

            activo = cbxDetalles.getItemCount() > 0;

            cbxDetalles.setSelectedIndex(-1);
            cbxDetalles.setVisible(activo);
            lblDetalle.setVisible(activo);
        }
    }

    private void fillComboBoxCarreras() {
        if (estudiante != null) {
            if (estudiante.getDatosAcademicos().isEmpty()) {
                Controls.fillComboBox(
                        cbxCarreras,
                        consultas.getCarreras(),
                        null);
                cbxCarreras.setSelectedIndex(-1);
            } else {
                Controls.fillComboBox(
                        cbxCarreras,
                        estudiante.getCarreras(),
                        null);
            }
        }
    }

    private void fillListDocumentos() {
        switch (accion) {
            case ACTUALIZAR:
            case MOSTRAR:
                if (estudiante != null && cbxCarreras.getSelectedIndex() >= 0) {
                    Controls.fillList(
                            listDocumentosPendientes,
                            estudiante
                                    .getDatosAcademicos()
                                    .get(cbxCarreras.getSelectedIndex()).getDocumentos(false));
                    Controls.fillList(
                            listDocumentosConsignados,
                            estudiante
                                    .getDatosAcademicos()
                                    .get(cbxCarreras.getSelectedIndex()).getDocumentos(true));
                }

                break;
            case NUEVO:
                Controls.fillList(
                        listDocumentosPendientes,
                        consultas.getDocumentos());
                break;
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
        cbxCondiciones.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxDetalles.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        cbxDetalles.setVisible((accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR) && cbxDetalles.getItemCount() > 0);
        listDocumentosPendientes.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        listDocumentosConsignados.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);

        btnBuscar.setVisible(accion == Accion.BUSCAR);
        btnComprobar.setVisible(accion == Accion.COMPROBAR);
        btnActualizar.setEnabled(accion == Accion.MOSTRAR);
        btnNuevo.setEnabled(accion == Accion.BUSCAR);
        btnGuardar.setEnabled(accion == Accion.NUEVO || accion == Accion.ACTUALIZAR);
        btnReiniciar.setEnabled(accion == Accion.COMPROBAR || accion == Accion.NUEVO || accion == Accion.MOSTRAR || accion == Accion.ACTUALIZAR);
    }

    private boolean searchEstudiante() {
        String letra;
        String numero;
        String cedula;

        letra = cbxCedulaLetras.getSelectedIndex() > -1 ? cbxCedulaLetras.getSelectedItem().toString().trim() : "";
        numero = txtCedulaNumero.getText().trim();

        if (!letra.isEmpty() && !numero.isEmpty()) {
            cedula = letra + numero;
            estudiante = consultas.getEstudiante(cedula);
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

    private void fillFields() {
        if (estudiante != null) {
            cleanFields();

            cbxCedulaLetras.setSelectedItem(estudiante.getCedulaLetra());
            txtCedulaNumero.setText(estudiante.getCedulaNumero());
            txtNombre1.setText(estudiante.getNombre1());
            txtNombre2.setText(estudiante.getNombre2());
            txtApellido1.setText(estudiante.getApellido1());
            txtApellido2.setText(estudiante.getApellido2());
            dateFechaNacimiento.setDate(estudiante.getFechaNacimiento());
            txtLugarNacimiento.setText(estudiante.getLugarNacimiento());
            lblEdad2.setText(
                    String.format(
                            "%d %s",
                            estudiante.getEdad(),
                            estudiante.getEdad() != 1 ? "años" : "año"
                    )
            );
            cbxSexos.setSelectedItem(estudiante.getSexo());
            cbxEstadosCiviles.setSelectedItem(estudiante.getEstadoCivil());
            cbxEstados.setSelectedItem(estudiante.getEstado());
            cbxMunicipios.setSelectedItem(estudiante.getMunicipio());
            cbxParroquias.setSelectedItem(estudiante.getParroquia());
            txtDireccion.setText(estudiante.getDireccion());
            txtTelefonoLocal.setText(estudiante.getTelefonoLocal(false));
            txtTelefonoMovil.setText(estudiante.getTelefonoMovil(false));
            txtCorreoElectronico.setText(estudiante.getCorreoElectronico());
            cbxEtnias.setSelectedItem(estudiante.getEtnia());

            fillComboBoxCarreras();

            if (!estudiante.getDatosAcademicos().isEmpty()) {
                cbxCarreras.setSelectedItem(estudiante.getDatosAcademicos().get(0).getCarrera());
                cbxCondiciones.setSelectedItem(estudiante.getDatosAcademicos().get(0).getCondicion());
                cbxDetalles.setSelectedItem(estudiante.getDatosAcademicos().get(0).getDetalle());
            }

            fillListDocumentos();
        }
    }

    private void saveChanges() {
        Status status;

        updateEstudiante();

        switch (accion) {
            case ACTUALIZAR:
                status = consultas.updateEstudiante(estudiante);
                break;
            case NUEVO:
                status = consultas.insertEstudiante(estudiante);
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

    private void searchPersona() {
        String letra;
        String numero;
        String cedula;

        letra = cbxCedulaLetras.getSelectedItem().toString().trim();
        numero = txtCedulaNumero.getText().trim();
        cedula = letra + numero;

        persona = consultas.getPersonas(cedula);
    }

    private void moveDocumentos(JList originList, JList destintionList) {
        Controls.moveElementJList(originList, destintionList);
    }

    private void moveAllDocumentos(JList originList, JList destinationList) {
        int size = originList.getModel().getSize();

        originList.setSelectionInterval(0, size - 1);

        moveDocumentos(originList, destinationList);
    }

    private void newMode() {
        clean();
        accion = Accion.COMPROBAR;
        prepareFields();
    }

    private void updateMode() {
        accion = Accion.ACTUALIZAR;
        prepareFields();
    }

    private void search() {
        if (searchEstudiante()) {
            if (estudiante != null) {
                accion = Accion.MOSTRAR;
                fillFields();
            } else {
                JOptionPane.showInternalMessageDialog(
                        this,
                        "Estudiante no registrado",
                        "Buscar estudiante",
                        JOptionPane.ERROR_MESSAGE);
                accion = Accion.BUSCAR;
            }
            prepareFields();
        }
    }

    private void check() {
        searchPersona();
        accion = Accion.NUEVO;

        if (persona != null) {
            estudiante = new Estudiante();
            estudiante.setPersona(persona);
            fillFields();
        } else {
            estudiante = new Estudiante();
            fillComboBoxCarreras();
            fillListDocumentos();
        }

        prepareFields();
    }

    private void cleanEstudiante() {
        estudiante = null;
    }

    private void cleanPersona() {
        persona = null;
    }

    private void cleanFields() {
        cbxCedulaLetras.setSelectedIndex(-1);
        txtCedulaNumero.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        dateFechaNacimiento.setDate(null);
        lblEdad2.setText("");
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
        if (cbxCarreras.getItemCount() > 0) {
            cbxCarreras.removeAllItems();
        }
        cbxCondiciones.setSelectedIndex(-1);

        fillComboBoxDetalles();

        if (!((DefaultListModel) listDocumentosPendientes.getModel()).isEmpty()) {
            ((DefaultListModel) listDocumentosPendientes.getModel()).removeAllElements();
        }

        if (!((DefaultListModel) listDocumentosConsignados.getModel()).isEmpty()) {
            ((DefaultListModel) listDocumentosConsignados.getModel()).removeAllElements();
        }

    }

    private void clean() {
        cleanEstudiante();
        cleanPersona();
        cleanFields();
    }

    private void reset() {
        clean();
        accion = Accion.BUSCAR;
        prepareFields();
    }

    private void updateEstudiante() {
        for (Campos campo : Campos.values()) {
            updateEstudiante(campo);
        }
    }

    private void updateEstudiante(Campos campos) {
        String cedula;
        String letra;
        String numero;
        int index;

        if (estudiante != null) {
            switch (campos) {
                case PRIMER_APELLIDO:
                    estudiante.setApellido1(txtApellido1.getText().trim());
                    break;
                case SEGUNDO_APELLIDO:
                    estudiante.setApellido2(txtApellido2.getText().trim());
                    break;
                case PRIMER_NOMBRE:
                    estudiante.setNombre1(txtNombre1.getText().trim());
                    break;
                case SEGUNDO_NOMBRE:
                    estudiante.setNombre2(txtNombre2.getText().trim());
                    break;
                case CEDULA:
                    letra = cbxCedulaLetras.getSelectedItem().toString().trim();
                    numero = txtCedulaNumero.getText().trim();
                    cedula = letra + numero;
                    estudiante.setCedula(cedula);
                    break;
                case FECHA_DE_NACIMIENTO:
                    estudiante.setFechaNacimiento(dateFechaNacimiento.getDate());
                    break;
                case LUGAR_DE_NACIMIENTO:
                    estudiante.setLugarNacimiento(txtLugarNacimiento.getText().trim());
                    break;
                case SEXO:
                    estudiante.setSexo(cbxSexos.getSelectedItem().toString());
                    break;
                case ESTADO_CIVIL:
                    estudiante.setEstadoCivil(cbxEstadosCiviles.getSelectedItem().toString());
                    break;
                case ESTADO:
                    if (cbxEstados.getSelectedIndex() >= 0) {
                        estudiante.setEstado(cbxEstados.getSelectedItem().toString());
                    } else {
                        estudiante.setEstado("");
                    }
                    break;
                case MUNICIPIO:
                    if (cbxMunicipios.getSelectedIndex() >= 0) {
                        estudiante.setMunicipio(cbxMunicipios.getSelectedItem().toString());
                    } else {
                        estudiante.setMunicipio("");
                    }
                    break;
                case PARROQUIA:
                    if (cbxParroquias.getSelectedIndex() >= 0) {
                        estudiante.setParroquia(cbxParroquias.getSelectedItem().toString());
                    } else {
                        estudiante.setParroquia("");
                    }
                    break;
                case DIRECCION:
                    estudiante.setDireccion(txtDireccion.getText().trim());
                    break;
                case TELEFONO_LOCAL:
                    estudiante.setTelefonoLocal(txtTelefonoLocal.getText().contains("_") ? "" : txtTelefonoLocal.getText());
                    break;
                case TELEFONO_MOVIL:
                    estudiante.setTelefonoMovil(txtTelefonoMovil.getText().contains("_") ? "" : txtTelefonoMovil.getText());
                    break;
                case CORREO_ELECTRONICO:
                    estudiante.setCorreoElectronico(txtCorreoElectronico.getText().trim());
                    break;
                case ETNIAS:
                    estudiante.setEtnia(cbxEtnias.getSelectedItem().toString());
                    break;
                case CARRERA:
                    if (accion == Accion.NUEVO) {
                        if (estudiante.getDatosAcademicos().isEmpty()) {
                            estudiante.addDatosAcademicos(new DatosAcademicos());
                        }
                        index = 0;
                    } else {
                        index = cbxCarreras.getSelectedIndex();
                    }

                    estudiante
                            .getDatosAcademicos()
                            .get(index)
                            .setCarrera(cbxCarreras.getSelectedItem().toString());
                    break;
                case CONDICION:
                    index = 0;

                    if (accion != Accion.NUEVO) {
                        index = cbxCarreras.getSelectedIndex();
                    }

                    estudiante
                            .getDatosAcademicos()
                            .get(index)
                            .setCondicion(cbxCondiciones.getSelectedItem().toString());

                    break;
                case DETALLE:
                    index = 0;

                    if (accion != Accion.NUEVO) {
                        index = cbxCarreras.getSelectedIndex();
                    }

                    estudiante
                            .getDatosAcademicos()
                            .get(index)
                            .setDetalle(cbxDetalles.getSelectedIndex() >= 0 ? cbxDetalles.getSelectedItem().toString() : "");

                    break;
                case DOCUMENTOS:
                    index = 0;

                    if (accion != Accion.NUEVO) {
                        index = cbxCarreras.getSelectedIndex();
                    }

                    estudiante
                            .getDatosAcademicos()
                            .get(index)
                            .getDocumentos()
                            .clear();

                    for (int i = 0; i < listDocumentosConsignados.getModel().getSize(); i++) {
                        estudiante
                                .getDatosAcademicos()
                                .get(index)
                                .addDocumento(new Documento(
                                        listDocumentosConsignados
                                                .getModel()
                                                .getElementAt(i),
                                        true));
                    }

                    for (int i = 0; i < listDocumentosPendientes.getModel().getSize(); i++) {
                        estudiante
                                .getDatosAcademicos()
                                .get(index)
                                .addDocumento(new Documento(
                                        listDocumentosPendientes
                                                .getModel()
                                                .getElementAt(i),
                                        false));
                    }

                    break;
            }
        }
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
        cbxSexos = new javax.swing.JComboBox<>();
        lblEstadoCivil = new javax.swing.JLabel();
        cbxEstadosCiviles = new javax.swing.JComboBox<>();
        dateFechaNacimiento = new com.toedter.calendar.JDateChooser();
        paneDireccionHabitacion = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        cbxEstados = new javax.swing.JComboBox<>();
        lblMunicipio = new javax.swing.JLabel();
        cbxMunicipios = new javax.swing.JComboBox<>();
        lblParroquia = new javax.swing.JLabel();
        cbxParroquias = new javax.swing.JComboBox<>();
        txtDireccion = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        paneInformacionContacto = new javax.swing.JPanel();
        lblTelefonoHabitacion = new javax.swing.JLabel();
        txtTelefonoLocal = new javax.swing.JFormattedTextField();
        lblTelefonoCelular = new javax.swing.JLabel();
        txtTelefonoMovil = new javax.swing.JFormattedTextField();
        lblCorreoElectronico = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        paneEtnia = new javax.swing.JPanel();
        cbxEtnias = new javax.swing.JComboBox<>();
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
        cbxCarreras = new javax.swing.JComboBox<>();
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
        cbxCedulaLetras = new javax.swing.JComboBox<>();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblCondicion = new javax.swing.JLabel();
        cbxCondiciones = new javax.swing.JComboBox<>();
        lblDetalle = new javax.swing.JLabel();
        cbxDetalles = new javax.swing.JComboBox<>();
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

        txtApellido1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellido1KeyTyped(evt);
            }
        });

        txtNombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre1KeyTyped(evt);
            }
        });

        txtApellido2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellido2KeyTyped(evt);
            }
        });

        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre2KeyTyped(evt);
            }
        });

        lblPrimero.setText("Primer");

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

        cbxSexos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSexosItemStateChanged(evt);
            }
        });

        lblEstadoCivil.setText("Estado civil");

        cbxEstadosCiviles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadosCivilesItemStateChanged(evt);
            }
        });

        dateFechaNacimiento.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout paneInformacionPersonalLayout = new javax.swing.GroupLayout(paneInformacionPersonal);
        paneInformacionPersonal.setLayout(paneInformacionPersonalLayout);
        paneInformacionPersonalLayout.setHorizontalGroup(
            paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneInformacionPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneInformacionPersonalLayout.createSequentialGroup()
                        .addComponent(lblSexo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSexos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstadoCivil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxEstadosCiviles, 0, 165, Short.MAX_VALUE))
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
                        .addComponent(lblLugarNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLugarNacimiento)))
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
                    .addComponent(cbxSexos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstadoCivil)
                    .addComponent(cbxEstadosCiviles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        paneDireccionHabitacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección de habitación"));

        lblEstado.setText("Estado");

        cbxEstados.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadosItemStateChanged(evt);
            }
        });

        lblMunicipio.setText("Municipio");

        cbxMunicipios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMunicipiosItemStateChanged(evt);
            }
        });

        lblParroquia.setText("Parroquia");

        cbxParroquias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxParroquiasItemStateChanged(evt);
            }
        });

        lblDireccion.setText("Dirección");

        javax.swing.GroupLayout paneDireccionHabitacionLayout = new javax.swing.GroupLayout(paneDireccionHabitacion);
        paneDireccionHabitacion.setLayout(paneDireccionHabitacionLayout);
        paneDireccionHabitacionLayout.setHorizontalGroup(
            paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMunicipio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblParroquia)
                            .addComponent(cbxParroquias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paneDireccionHabitacionLayout.createSequentialGroup()
                        .addComponent(lblDireccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDireccion)))
                .addContainerGap())
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
                    .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxParroquias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneDireccionHabitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneInformacionContacto.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de contacto"));

        lblTelefonoHabitacion.setText("Teléfono local");

        txtTelefonoLocal.setToolTipText("Formato: (####)-######\nEjemplo: (0416)-5555555");

        lblTelefonoCelular.setText("Teléfono móvil");

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
                        .addGap(24, 24, 24)
                        .addComponent(lblTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCorreoElectronico))
                .addGap(6, 6, 6))
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

        cbxEtnias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEtniasItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout paneEtniaLayout = new javax.swing.GroupLayout(paneEtnia);
        paneEtnia.setLayout(paneEtniaLayout);
        paneEtniaLayout.setHorizontalGroup(
            paneEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneEtniaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxEtnias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        paneEtniaLayout.setVerticalGroup(
            paneEtniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneEtniaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxEtnias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        cbxCarreras.setPreferredSize(new java.awt.Dimension(174, 24));
        cbxCarreras.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCarrerasItemStateChanged(evt);
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
                        .addComponent(cbxCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(cbxCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        cbxCedulaLetras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V", "E" }));

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

        lblCondicion.setText("Condición");

        cbxCondiciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCondiciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCondicionesItemStateChanged(evt);
            }
        });

        lblDetalle.setText("Activo");

        cbxDetalles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDetalles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxDetallesItemStateChanged(evt);
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
                    .addComponent(cbxCondiciones, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCondicion)
                    .addComponent(cbxCondiciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDetalle)
                    .addComponent(cbxDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(cbxCedulaLetras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(cbxCedulaLetras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cbxCondicionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCondicionesItemStateChanged
        fillComboBoxDetalles();
        Controls.updateComboBoxTooltip(cbxCondiciones);
    }//GEN-LAST:event_cbxCondicionesItemStateChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        newMode();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        updateMode();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (isValidFieldsCheckFind()) {
            search();
        }
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

    private void cbxDetallesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxDetallesItemStateChanged
        Controls.updateComboBoxTooltip(cbxDetalles);
    }//GEN-LAST:event_cbxDetallesItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (isValidFieldsSave()) {
            saveChanges();
            accion = Accion.MOSTRAR;
            prepareFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        reset();
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void listDocumentosConsignadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listDocumentosConsignadosMouseClicked
        if (evt.getClickCount() == 2) {
            moveDocumentos(listDocumentosConsignados, listDocumentosPendientes);
        }
    }//GEN-LAST:event_listDocumentosConsignadosMouseClicked

    private void listDocumentosPendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listDocumentosPendientesMouseClicked
        if (evt.getClickCount() == 2) {
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

    private void cbxCarrerasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCarrerasItemStateChanged
        if (cbxCarreras.getSelectedIndex() >= 0) {
            if (estudiante != null) {
                if (!estudiante.getDatosAcademicos().isEmpty()) {
                    estudiante.getDatosAcademicos().forEach(e -> {
                        if (cbxCarreras.getSelectedItem().equals(e.getCarrera())) {
                            cbxCondiciones.setSelectedItem(e.getCondicion());
                            fillComboBoxDetalles();
                            cbxDetalles.setSelectedItem(e.getDetalle());
                            fillListDocumentos();
                        }
                    });
                } else {
                    estudiante.addDatosAcademicos(
                            new DatosAcademicos(
                                    0,
                                    cbxCarreras.getSelectedItem().toString(),
                                    "",
                                    ""));
                    cbxCondiciones.setSelectedIndex(-1);
                }

            }
        }
    }//GEN-LAST:event_cbxCarrerasItemStateChanged

    private void cbxEtniasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEtniasItemStateChanged
        Controls.updateComboBoxTooltip(cbxEtnias);
    }//GEN-LAST:event_cbxEtniasItemStateChanged

    private void cbxEstadosCivilesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadosCivilesItemStateChanged
        Controls.updateComboBoxTooltip(cbxEstadosCiviles);
    }//GEN-LAST:event_cbxEstadosCivilesItemStateChanged

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        if (isValidFieldsCheckFind()) {
            check();
        }

    }//GEN-LAST:event_btnComprobarActionPerformed

    private void cbxSexosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSexosItemStateChanged
        Controls.updateComboBoxTooltip(cbxSexos);
    }//GEN-LAST:event_cbxSexosItemStateChanged

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

    private void txtApellido1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellido1KeyTyped
        if (!Controls.isCorrectFormatTextField(
                txtApellido1,
                15,
                true,
                evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellido1KeyTyped

    private void txtApellido2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellido2KeyTyped
        if (!Controls.isCorrectFormatTextField(
                txtApellido2,
                15,
                true,
                evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellido2KeyTyped

    private void txtNombre1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyTyped
        if (!Controls.isCorrectFormatTextField(
                txtNombre1,
                15,
                true,
                evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombre1KeyTyped

    private void txtNombre2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyTyped
        if (!Controls.isCorrectFormatTextField(
                txtNombre2,
                15,
                true,
                evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombre2KeyTyped


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
    private javax.swing.JComboBox<String> cbxCarreras;
    private javax.swing.JComboBox<String> cbxCedulaLetras;
    private javax.swing.JComboBox<String> cbxCondiciones;
    private javax.swing.JComboBox<String> cbxDetalles;
    private javax.swing.JComboBox<String> cbxEstados;
    private javax.swing.JComboBox<String> cbxEstadosCiviles;
    private javax.swing.JComboBox<String> cbxEtnias;
    private javax.swing.JComboBox<String> cbxMunicipios;
    private javax.swing.JComboBox<String> cbxParroquias;
    private javax.swing.JComboBox<String> cbxSexos;
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
    private javax.swing.JLabel lblDireccion;
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
