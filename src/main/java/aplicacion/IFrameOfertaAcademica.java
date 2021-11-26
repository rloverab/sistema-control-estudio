/*
 * Copyright (C) 2021 roger
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

import clases.Carrera;
import clases.ComboBoxEditor;
import clases.Controls;
import clases.Docente;
import clases.Item;
import clases.Nivel;
import clases.OfertaAcademica;
import clases.OfertaAcademicaModulo;
import clases.Unidad;
import clases.Periodo;
import clases.Queries;
import clases.Resolucion;
import clases.SpinnerEditor;
import java.util.ArrayList;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class IFrameOfertaAcademica extends javax.swing.JInternalFrame {

    private final Queries queries;
    private Resolucion resolucion;
    private final ArrayList<Docente> docentes;
    private ArrayList<OfertaAcademica> ofertasAcademicas;
    private int hash;
    private boolean isValueChangedSecciones;

    /**
     * Creates new form VentanaOfertaAcademica
     *
     * @param queries
     */
    public IFrameOfertaAcademica(Queries queries) {
        initComponents();

        ofertasAcademicas = new ArrayList<>();
        isValueChangedSecciones = false;

        this.queries = queries;
        docentes = queries.getDocentes(true);

        fillComboBoxPeriodo();
        prepareTableSecciones();
        prepareTableModulos();

        hash = ofertasAcademicas.hashCode();
    }

    private void fillComboBoxPeriodo() {
        ArrayList<Periodo> periodos;
        ArrayList<Item> items;

        periodos = queries.getPeriodos();
        items = new ArrayList<>();

        periodos.forEach(periodo -> {
            items.add(new Item(periodo));
        });

        Controls.fillComboBoxItem(cbxPeriodo, items, null);

        cbxPeriodo.setSelectedIndex(-1);
        cbxPeriodo.setEnabled(cbxPeriodo.getItemCount() > 0);
    }

    private void fillComboBoxCarrera() {
        ArrayList<Item> items;
        ArrayList<Carrera> carreras;

        cbxCarrera.removeAllItems();

        if (cbxPeriodo.getSelectedIndex() >= 0) {
            items = new ArrayList<>();
            carreras = queries.getCarreras();

            carreras.forEach(carrera -> items.add(new Item(carrera)));

            Controls.fillComboBoxItem(cbxCarrera, items, null, -1);
        }
        
        cbxCarrera.setEnabled(cbxCarrera.getItemCount() > 0);
    }

    private void fillComboBoxNivel() {
        ArrayList<Item> items;
        ArrayList<Nivel> niveles;
        Carrera carrera;

        cbxNivel.removeAllItems();
        if (resolucion != null && cbxCarrera.getSelectedIndex() >= 0) {
            items = new ArrayList<>();
            carrera = (Carrera) ((Item) cbxCarrera.getSelectedItem()).getValue();
            niveles = queries.getNiveles(carrera.getId(), resolucion.getId());
            niveles.forEach(nivel -> items.add(new Item(nivel)));
            Controls.fillComboBoxItem(cbxNivel, items, null);
        }

        cbxNivel.setSelectedIndex(-1);
        cbxNivel.setEnabled(cbxNivel.getItemCount() > 0);
    }

    private void fillComboBoxMateria() {
        ArrayList<Unidad> unidades;
        ArrayList<Item> items;
        Carrera carrera;
        Nivel nivel;

        cbxMateria.removeAllItems();

        if (cbxCarrera.getItemCount() >= 0 && cbxNivel.getSelectedIndex() >= 0) {
            carrera = (Carrera) ((Item) cbxCarrera.getSelectedItem()).getValue();
            nivel = (Nivel) ((Item) cbxNivel.getSelectedItem()).getValue();

            unidades = queries.getPlanEstudio(
                    carrera.getId(),
                    nivel.getId(),
                    resolucion.getId());

            if (unidades.size() > 0) {
                items = new ArrayList<>();

                unidades.forEach(materia -> {
                    items.add(new Item(materia));
                });
                Controls.fillComboBoxItem(
                        cbxMateria,
                        items,
                        null);
            }
        }
        cbxMateria.setSelectedIndex(-1);
        cbxMateria.setEnabled(cbxMateria.getItemCount() > 0);
    }

    private void fillTableSecciones() {
        DefaultTableModel dtm;
        Object[] objects;

        dtm = (DefaultTableModel) tblSecciones.getModel();

        Controls.removeAllRowsTable(tblSecciones);

        for (OfertaAcademica ofertaAcademica : ofertasAcademicas) {

            objects = new Object[]{
                ofertaAcademica.getSeccion(),
                ofertaAcademica.getCupos()
            };

            dtm.addRow(objects);
        }

        tblSecciones.scrollRectToVisible(tblSecciones.getCellRect(tblSecciones.getRowCount() - 1, 0, true));
    }

    private void fillTableModulos() {
        DefaultTableModel dtm;
        ArrayList<OfertaAcademicaModulo> modulos;
        int index;

        Controls.removeAllRowsTable(tblModulos);
        dtm = (DefaultTableModel) tblModulos.getModel();
        index = tblSecciones.getSelectedRow();

        if (index >= 0) {
            modulos = ofertasAcademicas.get(index).getModulos();

            modulos.forEach(modulo -> {
                Object[] objects;
                String fullname;

                fullname = "Por determinar";

                for (Docente docente : docentes) {
                    if (modulo.getDocenteId() != null && docente.getDocenteId() == modulo.getDocenteId()) {
                        fullname = docente.toString();
                        break;
                    }
                }
                objects = new Object[]{
                    modulo.getId(),
                    modulo.getModulo(),
                    fullname};

                dtm.addRow(objects);
            });
        }
    }

    private int getNomenclatura() {
        switch (cbxSecciones.getSelectedIndex()) {
            case 0:
                return OfertaAcademica.ALFABETICO;
            case 1:
            default:
                return OfertaAcademica.NUMERICO;
        }
    }

    private void updateButtonGuardar() {
        boolean enabled;

        System.out.println("hash: " + hash);
        System.out.println("hashCode(): " + ofertasAcademicas.hashCode());
        System.out.println("-".repeat(10));

        enabled = tblSecciones.getRowCount() > 0 && (hash != ofertasAcademicas.hashCode());

        btnGuardar.setEnabled(enabled);
    }

    private void addRow() {
        Unidad unidad;
        ArrayList<OfertaAcademicaModulo> modulos;
        Periodo periodo;
        OfertaAcademica ofertaAcademica;
        int selectedRow;

        modulos = new ArrayList<>();

        periodo = (Periodo) ((Item) cbxPeriodo.getSelectedItem()).getValue();
        unidad = (Unidad) ((Item) cbxMateria.getSelectedItem()).getValue();

        unidad.getModulos().forEach(modulo -> {
            OfertaAcademicaModulo ofertaAcademicaModulo;
            ofertaAcademicaModulo = new OfertaAcademicaModulo(
                    0,
                    null,
                    modulo.getPlanesEstudioModulosId(),
                    modulo.getId(),
                    modulo.getModulo(),
                    modulo.getHTA(),
                    modulo.getHTAS());
            modulos.add(ofertaAcademicaModulo);
        });

        ofertaAcademica = new OfertaAcademica(
                periodo.getId(),
                1,
                modulos,
                ofertasAcademicas.size(),
                getNomenclatura()
        );

        ofertasAcademicas.add(ofertaAcademica);

        fillTableSecciones();

        selectedRow = tblSecciones.getRowCount() - 1;

        if (selectedRow >= 0) {
            tblSecciones.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    private void removeRow() {
        int selectedRow;
        int size;
        int index;
        boolean saved;
        ArrayList<OfertaAcademicaModulo> modulos;
        String message;
        String title_;
        int messageType;

        size = ofertasAcademicas.size();
        index = size - 1;
        saved = false;

        if (index > 0) {
            modulos = ofertasAcademicas.get(index).getModulos();

            for (OfertaAcademicaModulo modulo : modulos) {
                if (modulo.getOfertaAcademicaId() > 0) {
                    saved = true;
                    break;
                }
            }

            if (!saved) {
                ofertasAcademicas.remove(index);

                fillTableSecciones();

                selectedRow = tblSecciones.getRowCount() - 1;

                if (selectedRow >= 0) {
                    tblSecciones.setRowSelectionInterval(selectedRow, selectedRow);
                }
            } else {
                message = "Las secciones restantes ya se encuentran guardadas "
                        + "en la base de datos. No pueden ser eliminadas.";
                title_ = "Eliminar sección";
                messageType = JOptionPane.INFORMATION_MESSAGE;
                JOptionPane.showInternalMessageDialog(this, message, title_, messageType);
            }
        }
    }

    private void prepareTableSecciones() {
        DefaultTableModel dtm;
        TableColumnModel tcm;
        TableColumnModel tcmh;
        DefaultListSelectionModel dlsm;
        int[] width;

        dtm = (DefaultTableModel) tblSecciones.getModel();
        tcm = tblSecciones.getColumnModel();
        tcmh = tblSecciones.getTableHeader().getColumnModel();
        dlsm = (DefaultListSelectionModel) tblSecciones.getSelectionModel();
        width = new int[]{60};

        tcm.getColumn(1).setCellEditor(new SpinnerEditor(1, 1, 100, 1));
        tblSecciones.setRowHeight(21);
        Controls.removeAllRowsTable(tblSecciones);

        for (int i = 0; i < width.length; i++) {
            if (width[i] >= 0) {
                tcmh.getColumn(i).setMaxWidth(width[i]);
                tcmh.getColumn(i).setMinWidth(width[i]);
                tcmh.getColumn(i).setPreferredWidth(width[i]);
                tcm.getColumn(i).setMaxWidth(tcmh.getColumn(i).getMaxWidth());
                tcm.getColumn(i).setMinWidth(tcmh.getColumn(i).getMinWidth());
                tcm.getColumn(i).setPreferredWidth(tcmh.getColumn(i).getPreferredWidth());
            }
        }

        dtm.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                OfertaAcademica ofertaAcademica;
                int filas = dtm.getRowCount();
                int secciones = ofertasAcademicas.size();
                btnBorrar.setEnabled(dtm.getRowCount() > 0);

                if (secciones > 0 && filas == secciones) {
                    for (int i = 0; i < dtm.getRowCount(); i++) {
                        ofertaAcademica = ofertasAcademicas.get(i);
                        ofertaAcademica.setNumeroSeccion(i);
                        ofertaAcademica.setCupos((int) dtm.getValueAt(i, 1));
                        ofertaAcademica.setNomenclatura(getNomenclatura());
                    }
                }

                updateCantidadSecciones();
                updateCantidadCupos();
                updateButtonGuardar();
            }
        });

        dlsm.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                isValueChangedSecciones = true;
                fillTableModulos();
                isValueChangedSecciones = false;
            }
        });

        btnBorrar.setEnabled(dtm.getRowCount() > 0);
    }

    private void prepareTableModulos() {
        DefaultTableModel dtm;
        TableColumnModel tcm;
        TableColumnModel tcmh;
        int[] width;
        String[] items;

        dtm = (DefaultTableModel) tblModulos.getModel();
        tcm = tblModulos.getColumnModel();
        tcmh = tblModulos.getTableHeader().getColumnModel();
        width = new int[]{0, 300};
        items = new String[docentes.size() + 1];

        items[0] = "Por determinar";

        for (int i = 0; i < docentes.size(); i++) {
            items[i + 1] = docentes.get(i).toString();
        }

        tcm.getColumn(2).setCellEditor(new ComboBoxEditor(items));
        tblModulos.setRowHeight(21);

        Controls.removeAllRowsTable(tblModulos);

        for (int i = 0; i < width.length; i++) {
            if (width[i] >= 0) {
                tcmh.getColumn(i).setMaxWidth(width[i]);
                tcmh.getColumn(i).setMinWidth(width[i]);
                tcmh.getColumn(i).setPreferredWidth(width[i]);
                tcm.getColumn(i).setMaxWidth(tcmh.getColumn(i).getMaxWidth());
                tcm.getColumn(i).setMinWidth(tcmh.getColumn(i).getMinWidth());
                tcm.getColumn(i).setPreferredWidth(tcmh.getColumn(i).getPreferredWidth());
            }
        }

        dtm.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                String seccion;
                int indexSeccion;
                OfertaAcademicaModulo modulo;
                Integer docenteId;

                if (isValueChangedSecciones) {
                    return;
                }

                if (tblModulos.getRowCount() > 0) {
                    indexSeccion = tblSecciones.getSelectedRow();

                    if (indexSeccion >= 0) {
                        seccion = tblSecciones.getValueAt(indexSeccion, 0).toString();

                        for (OfertaAcademica ofertaAcademica : ofertasAcademicas) {
                            if (ofertaAcademica.getSeccion().equalsIgnoreCase(seccion)) {
                                for (int i = 0; i < tblModulos.getRowCount(); i++) {
                                    modulo = ofertaAcademica.getModulos().get(i);
                                    docenteId = getDocenteId(tblModulos.getValueAt(i, 2).toString());

                                    System.out.println("docenteId: " + docenteId);
                                    System.out.println("modulo.getDocenteId(): " + modulo.getDocenteId());

                                    if (modulo.getDocenteId() == null || modulo.getDocenteId().compareTo(docenteId) != 0) {
                                        modulo.setDocenteId(docenteId);
                                    }

                                }

                                break;
                            }
                        }
                    }
                }

                updateButtonGuardar();
            }
        });
    }

    private int getDocenteId(String fullname) {
        int id;

        id = 0;

        for (Docente docente : docentes) {
            if (docente.toString().equalsIgnoreCase(fullname)) {

                id = docente.getDocenteId();
                break;
            }
        }

        return id;
    }

    private void updateSecciones() {
        boolean enabled;
        DefaultTableModel dtm;
        int selectedRow;

        enabled = cbxSecciones.getSelectedIndex() >= 0;

        btnAgregar.setEnabled(enabled);
        tblSecciones.setEnabled(enabled);

        if (!enabled) {
            Controls.removeAllRowsTable(tblSecciones);
        } else {
            selectedRow = tblSecciones.getSelectedRow();
            Controls.removeAllRowsTable(tblSecciones);
            dtm = (DefaultTableModel) tblSecciones.getModel();
            for (int i = 0; i < ofertasAcademicas.size(); i++) {
                ofertasAcademicas.get(i).setNomenclatura(getNomenclatura());
                dtm.addRow(new Object[]{
                    ofertasAcademicas.get(i).getSeccion(),
                    ofertasAcademicas.get(i).getCupos()
                });
            }
            if (selectedRow >= 0 && selectedRow < tblSecciones.getRowCount()) {
                tblSecciones.setRowSelectionInterval(selectedRow, selectedRow);
            }
        }
    }

    private int getCantidadCupos() {
        boolean isSelectedPeriodo;
        boolean isSelectedCarrera;
        boolean isSelectedNivel;
        boolean isSelectedMateria;
        int cupos;

        isSelectedPeriodo = cbxPeriodo.getSelectedIndex() >= 0;
        isSelectedCarrera = cbxCarrera.isEnabled() && cbxCarrera.getSelectedIndex() >= 0;
        isSelectedNivel = cbxNivel.isEnabled() && cbxNivel.getSelectedIndex() >= 0;
        isSelectedMateria = cbxMateria.isEnabled() && cbxMateria.getSelectedIndex() >= 0;
        cupos = 0;

        if (isSelectedPeriodo && isSelectedCarrera && isSelectedNivel && isSelectedMateria) {
            for (OfertaAcademica ofertaAcademica : ofertasAcademicas) {
                cupos += ofertaAcademica.getCupos();
            }
        }

        return cupos;
    }

    private int getCantidadSecciones() {
        boolean isSelectedPeriodo;
        boolean isSelectedCarrera;
        boolean isSelectedNivel;
        boolean isSelectedMateria;
        int secciones;

        isSelectedPeriodo = cbxPeriodo.getSelectedIndex() >= 0;
        isSelectedCarrera = cbxCarrera.isEnabled() && cbxCarrera.getSelectedIndex() >= 0;
        isSelectedNivel = cbxNivel.isEnabled() && cbxNivel.getSelectedIndex() >= 0;
        isSelectedMateria = cbxMateria.isEnabled() && cbxMateria.getSelectedIndex() >= 0;
        secciones = 0;

        if (isSelectedPeriodo && isSelectedCarrera && isSelectedNivel && isSelectedMateria) {
            secciones = ofertasAcademicas.size();
        }

        return secciones;
    }

    private void updateCantidadSecciones() {
        lblCantidadSecciones.setText("" + getCantidadSecciones());
    }

    private void updateCantidadCupos() {
        lblCantidadCupos.setText("" + getCantidadCupos());
    }

    private void saveChanges() {
        String title_;
        String message;
        int optionType;
        int result;
        int messageType;
        Status statusInsert;
        Status statusUpdate;

        title_ = "Guardar cambios";
        message = "¿Desea guardar los cambios realizados?\n\n"
                + "Advertencia: Las secciones nuevas serán permanentes, "
                + "el resto de los datos relacionados si podrán ser modificados.";
        optionType = JOptionPane.YES_NO_OPTION;
        messageType = JOptionPane.QUESTION_MESSAGE;

        result = JOptionPane.showInternalConfirmDialog(this, message, title_, optionType, messageType);

        if (result == JOptionPane.YES_OPTION) {
            statusInsert = queries.insertOfertaAcademica(ofertasAcademicas);
            statusUpdate = queries.updateOfertaAcademica(ofertasAcademicas);

            if ((Status.OK == statusInsert) && (Status.OK == statusUpdate)) {
                message = "Datos guardados";
                messageType = JOptionPane.INFORMATION_MESSAGE;
                updateOfertasAcademicas();
            } else {
                message = "Datos no guardados";
                messageType = JOptionPane.ERROR_MESSAGE;
            }

            JOptionPane.showInternalMessageDialog(this, message, title_, messageType);
        }

    }

    private void updateOfertasAcademicas() {
        Periodo periodo;
        Carrera carrera;
        Nivel nivel;
        Unidad unidad;

        periodo = (Periodo) ((Item) cbxPeriodo.getModel().getSelectedItem()).getValue();
        carrera = (Carrera) ((Item) cbxCarrera.getModel().getSelectedItem()).getValue();
        nivel = (Nivel) ((Item) cbxNivel.getModel().getSelectedItem()).getValue();
        unidad = (Unidad) ((Item) cbxMateria.getModel().getSelectedItem()).getValue();
        ofertasAcademicas = queries.getOfertasAcademicas(
                periodo.getId(),
                carrera.getId(),
                nivel.getId(),
                unidad.getId()
        );

        System.out.println("Secciones: " + ofertasAcademicas.size());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblPeriodo = new javax.swing.JLabel();
        cbxPeriodo = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        cbxCarrera = new javax.swing.JComboBox<>();
        lblNivel = new javax.swing.JLabel();
        cbxNivel = new javax.swing.JComboBox<>();
        lblMateria = new javax.swing.JLabel();
        lblSecciones = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        cbxSecciones = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCarrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSecciones = new javax.swing.JTable();
        btnBorrar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblModulos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCantidadSecciones = new javax.swing.JLabel();
        lblCantidadCupos = new javax.swing.JLabel();

        setTitle("Oferta académica");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parámetros"));

        lblPeriodo.setText("Periodo");

        cbxPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxPeriodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPeriodoItemStateChanged(evt);
            }
        });

        lblCarrera.setText("Carrera");

        cbxCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCarrera.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCarreraItemStateChanged(evt);
            }
        });

        lblNivel.setText("Nivel");

        cbxNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxNivel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNivelItemStateChanged(evt);
            }
        });

        lblMateria.setText("Materia");

        lblSecciones.setText("Secciones");

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxMateria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMateriaItemStateChanged(evt);
            }
        });

        cbxSecciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alfabéticas", "Numéricas" }));
        cbxSecciones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxSecciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSeccionesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSecciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPeriodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarrera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMateria))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSecciones)
                    .addComponent(cbxSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCarrar.setText("Cerrar");
        btnCarrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarrarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Secciones"));

        tblSecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sección", "Cupos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSecciones.getTableHeader().setResizingAllowed(false);
        tblSecciones.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblSecciones);

        btnBorrar.setText("-");
        btnBorrar.setToolTipText("Eliminar sección");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnAgregar.setText("+");
        btnAgregar.setToolTipText("Agregar sección");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBorrar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Módulos"));

        tblModulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Módulo", "Docente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblModulos.getTableHeader().setResizingAllowed(false);
        tblModulos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblModulos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Información"));

        jLabel1.setText("Secciones:");

        jLabel2.setText("Cupos:");

        lblCantidadSecciones.setText("0");

        lblCantidadCupos.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCantidadCupos)
                    .addComponent(lblCantidadSecciones))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblCantidadSecciones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCantidadCupos))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCarrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCarrar))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCarrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarrarActionPerformed
        /*
        String title_;
        String message;
        int optionType;
        int messageType;
        int response;

        response = JOptionPane.NO_OPTION;

        if (hash != ofertasAcademicas.hashCode()) {
            title_ = "Guardar cambios";
            message = "¿Desea guardar los cambios realizados antes de cerrar?";
            optionType = JOptionPane.YES_NO_CANCEL_OPTION;
            messageType = JOptionPane.QUESTION_MESSAGE;
            response = JOptionPane.showInternalConfirmDialog(this, message, title_, optionType, messageType);
        }
        

        switch (response) {
            case JOptionPane.YES_OPTION:
                saveChanges();
            case JOptionPane.NO_OPTION:
                dispose();
        }*/
        if (hash != ofertasAcademicas.hashCode()) {            
            saveChanges();
        }
        dispose();
    }//GEN-LAST:event_btnCarrarActionPerformed

    private void cbxCarreraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCarreraItemStateChanged
        if (cbxCarrera.getSelectedIndex() >= 0) {
            resolucion = queries.getResoluciones(((Item) cbxCarrera.getSelectedItem()).toString(), 1).get(0);
        } else {
            resolucion = null;
        }
        fillComboBoxNivel();

    }//GEN-LAST:event_cbxCarreraItemStateChanged

    private void cbxPeriodoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPeriodoItemStateChanged
        fillComboBoxCarrera();
    }//GEN-LAST:event_cbxPeriodoItemStateChanged

    private void cbxNivelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNivelItemStateChanged
        fillComboBoxMateria();
    }//GEN-LAST:event_cbxNivelItemStateChanged

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        addRow();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        removeRow();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void cbxMateriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMateriaItemStateChanged
        boolean isSelectedPeriodo;
        boolean isSelectedCarrera;
        boolean isSelectedNivel;
        boolean isSelectedMateria;
        boolean isSelectedSecciones;
        boolean enabled;
        /*Periodo periodo;
        Carrera carrera;
        Nivel nivel;
        Unidad unidad;*/

        enabled = cbxMateria.getSelectedIndex() >= 0;

        if (!enabled) {
            cbxSecciones.setSelectedIndex(-1);
            while (ofertasAcademicas.size() > 0) {
                ofertasAcademicas.remove(ofertasAcademicas.size() - 1);
            }
        } else {
            /*
            periodo = (Periodo) ((Item) cbxPeriodo.getModel().getSelectedItem()).getValue();
            carrera = (Carrera) ((Item) cbxCarrera.getModel().getSelectedItem()).getValue();
            nivel = (Nivel) ((Item) cbxNivel.getModel().getSelectedItem()).getValue();
            unidad = (Unidad) ((Item) cbxMateria.getModel().getSelectedItem()).getValue();
            ofertasAcademicas = queries.getOfertasAcademicas(
                    periodo.getId(),
                    carrera.getId(),
                    nivel.getId(),
                    unidad.getId()
            );*/
            updateOfertasAcademicas();
            hash = ofertasAcademicas.hashCode();

            if (ofertasAcademicas.size() > 0) {
                cbxSecciones.setSelectedIndex(ofertasAcademicas.get(0).getNomenclatura());
            } else {
                cbxSecciones.setSelectedIndex(-1);
            }
        }

        cbxSecciones.setEnabled(enabled);

        isSelectedPeriodo = cbxPeriodo.isEnabled() && cbxPeriodo.getSelectedIndex() >= 0;
        isSelectedCarrera = cbxCarrera.isEnabled() && cbxCarrera.getSelectedIndex() >= 0;
        isSelectedNivel = cbxNivel.isEnabled() && cbxNivel.getSelectedIndex() >= 0;
        isSelectedMateria = cbxMateria.isEnabled() && cbxMateria.getSelectedIndex() >= 0;
        isSelectedSecciones = cbxSecciones.isEnabled() && cbxSecciones.getSelectedIndex() >= 0;

        if (isSelectedPeriodo && isSelectedCarrera && isSelectedNivel && isSelectedMateria && isSelectedSecciones) {
            fillTableSecciones();
        }
    }//GEN-LAST:event_cbxMateriaItemStateChanged

    private void cbxSeccionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSeccionesItemStateChanged
        boolean enabled;
        enabled = cbxSecciones.isEnabled() && cbxSecciones.getSelectedIndex() >= 0;
        btnAgregar.setEnabled(enabled);
        updateSecciones();
    }//GEN-LAST:event_cbxSeccionesItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        for (OfertaAcademica ofertaAcademica : ofertasAcademicas) {
            System.out.println("Módulos");
            ofertaAcademica.getModulos().forEach(modulo -> {
                System.out.println("hashCode(): " + modulo.hashCode());
            });
        }

        saveChanges();
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCarrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxCarrera;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JComboBox<String> cbxNivel;
    private javax.swing.JComboBox<String> cbxPeriodo;
    private javax.swing.JComboBox<String> cbxSecciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantidadCupos;
    private javax.swing.JLabel lblCantidadSecciones;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblSecciones;
    private javax.swing.JTable tblModulos;
    private javax.swing.JTable tblSecciones;
    // End of variables declaration//GEN-END:variables
}
