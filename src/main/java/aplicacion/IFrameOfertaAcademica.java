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
import clases.Modulo;
import clases.Nivel;
import clases.OfertaAcademica;
import clases.Unidad;
import clases.Periodo;
import clases.Queries;
import clases.Resolucion;
import clases.SpinnerEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class IFrameOfertaAcademica extends javax.swing.JInternalFrame {

    private final Queries queries;
    private Resolucion resolucion;
    private final ArrayList<Docente> docentes;
    private ArrayList<OfertaAcademica> ofertasAcademicas;
    
    /**
     * Creates new form VentanaOfertaAcademica
     *
     * @param queries
     */
    public IFrameOfertaAcademica(Queries queries) {
        initComponents();
        ofertasAcademicas = new ArrayList<>();
        this.queries = queries;

        docentes = queries.getDocentes(true);
        fillComboBoxPeriodo();
        prepareTableSecciones();
        prepareTableModulos();
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
            carreras =  queries.getCarreras();           
            
            carreras.forEach(carrera -> items.add(new Item(carrera)));
                    
            Controls.fillComboBoxItem(cbxCarrera, items, null);
        }
        
        cbxCarrera.setSelectedIndex(-1);
        cbxCarrera.setEnabled(cbxCarrera.getItemCount() > 0);
    }

    private void fillComboBoxNivel() {
        ArrayList<Item> items;
        ArrayList<Nivel> niveles;
        
        cbxNivel.removeAllItems();
        if(resolucion != null && cbxCarrera.getSelectedIndex() >= 0){
            items = new ArrayList<>();
            niveles = queries.getNiveles(
                    ((Item) cbxCarrera.getSelectedItem()).getLabel(),
                    resolucion.getResolucion(),
                    resolucion.getActa(),
                    resolucion.getFecha());
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
            
            unidades = queries.getPlanEstudio3(
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

    private void fillTableSecciones(){
        DefaultTableModel dtm;        
        
        dtm = (DefaultTableModel) tblSecciones.getModel();
        
        Controls.removeAllRowsTable(tblSecciones);
        
        ofertasAcademicas.forEach(ofertaAcademica -> {
            Object[] objects;            
            objects = new Object[] {                
                ofertaAcademica.getSeccion(),
                ofertaAcademica.getCupos()
            };
            
            dtm.addRow(objects);
        });        
    }
    
    private void fillTableModulos(){
        DefaultTableModel dtm;
        int index;
        
        Controls.removeAllRowsTable(tblModulos);
        dtm = (DefaultTableModel) tblModulos.getModel();
        index = tblSecciones.getSelectedRow();      
                
        if(index >= 0){
            ofertasAcademicas.get(index).getModulos().forEach(modulo -> {
                Object[] objects;
                String fullname;
                
                fullname = null;

                for (Docente docente : docentes){
                    if(docente.getDocenteId() == modulo.getDocente_id()){
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
    
    private OfertaAcademica.Nomenclatura getNomenclatura(){
        switch(cbxSecciones.getSelectedIndex()){
            case 0:
                return OfertaAcademica.Nomenclatura.ALFABETICO;
            default:
                return OfertaAcademica.Nomenclatura.NUMERICO;
        }
    }
    
    private void addRow() {
        Unidad unidad;
        ArrayList<Modulo> modulos;
        Periodo periodo;        
        OfertaAcademica ofertaAcademica;
        
        modulos = new ArrayList<>();
        
        periodo = (Periodo) ((Item)cbxPeriodo.getSelectedItem()).getValue();
        unidad = (Unidad) ((Item)cbxMateria.getSelectedItem()).getValue();                
        unidad.getModulos().forEach(modulo -> {
            try {
                modulos.add((Modulo) modulo.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(IFrameOfertaAcademica.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ofertaAcademica = new OfertaAcademica(
                0, 
                periodo.getId(),
                0 , 
                1, 
                modulos, 
                ofertasAcademicas.size(), 
                getNomenclatura());
        ofertasAcademicas.add(ofertaAcademica);
        
        fillTableSecciones();
    }

    private void removeRow() {
        if(ofertasAcademicas.size() > 0){
            ofertasAcademicas.remove(ofertasAcademicas.size() - 1);
        }
        
        fillTableSecciones();
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
                btnBorrar.setEnabled(dtm.getRowCount() > 0);
                
                if (ofertasAcademicas.size() > 0) {
                    for (int i = 0; i < dtm.getRowCount(); i++) {
                        ofertaAcademica = ofertasAcademicas.get(i);
                        ofertaAcademica.setNumeroSeccion(i);
                        ofertaAcademica.setCupos((int) dtm.getValueAt(i, 1));
                        ofertaAcademica.setNomenclatura(getNomenclatura());
                    }                    
                }                 
            }
        });
        
        dlsm.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                fillTableModulos();                
            }
        });

        btnBorrar.setEnabled(dtm.getRowCount() > 0);        
    }

    private void prepareTableModulos(){
        DefaultTableModel dtm;
        TableColumnModel tcm;
        TableColumnModel tcmh;
        int[] width;
        String[] items;
        
        dtm = (DefaultTableModel) tblModulos.getModel();
        tcm = tblModulos.getColumnModel();
        tcmh = tblModulos.getTableHeader().getColumnModel();        
        width = new int[]{0, 200};
        items = new String[docentes.size()];
        
        for (int i = 0; i < docentes.size(); i++) {
            items[i] = docentes.get(i).toString();
        }
        
        tcm.getColumn(2).setCellEditor(new ComboBoxEditor(items));
        tblModulos.setRowHeight(21);  
        
        //removeAllRowsTableModule();
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
                OfertaAcademica ofertaAcademica;                
                int index;
                
                if (tblModulos.getRowCount() > 0) {
                    index = tblSecciones.getSelectedRow();
                    if (index >= 0) {
                        ofertaAcademica = ofertasAcademicas.get(index);
                        if (tblModulos.getRowCount() == ofertaAcademica.getModulos().size()) {
                            for(int i = 0; i < ofertaAcademica.getModulos().size(); i++){                                
                                ofertaAcademica
                                        .getModulos()
                                        .get(i)
                                        .setDocente_id(tblModulos.getValueAt(i, 2) == null ? -1 : getDocenteId(tblModulos.getValueAt(i, 2).toString()));                                
                            }
                        }
                    }
                }                
            }
        });
    }
    
    private int getDocenteId(String fullname){
        int id;
        id = -1;
        
        for(Docente docente : docentes){
            if(docente.toString().equalsIgnoreCase(fullname)){
                id = docente.getDocenteId();
                break;
            }
        }
        
        return id;
    }
    
    private void updateSecciones(){
        boolean enabled;
        DefaultTableModel dtm;
        
        enabled = cbxSecciones.getSelectedIndex() >= 0;
        
        btnAgregar.setEnabled(enabled);
        tblSecciones.setEnabled(enabled);
        
        if(!enabled){
            Controls.removeAllRowsTable(tblSecciones);
        }else{
            dtm = (DefaultTableModel) tblSecciones.getModel();
            
            for(int i = 0; i < ofertasAcademicas.size(); i++){             
                ofertasAcademicas.get(i).setNomenclatura(getNomenclatura());
                dtm.setValueAt(ofertasAcademicas.get(i).getSeccion(), i, 0);
                
            }
        }
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
        jPanel3 = new javax.swing.JPanel();
        lblMateria = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        lblSecciones = new javax.swing.JLabel();
        cbxSecciones = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setTitle("Oferta académica");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPeriodo.setText("Periodo");

        cbxPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxPeriodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPeriodoItemStateChanged(evt);
            }
        });
        cbxPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPeriodoActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMateria.setText("Materia");

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxMateria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMateriaItemStateChanged(evt);
            }
        });

        lblSecciones.setText("Secciones");

        cbxSecciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alfabéticas", "Numéricas" }));
        cbxSecciones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxSecciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSeccionesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMateria))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSecciones)
                    .addComponent(cbxSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardar.setText("Guardar");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Módulos"));

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Información"));

        jLabel1.setText("Secciones:");

        jLabel2.setText("Cupos:");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbxCarreraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCarreraItemStateChanged
        if (cbxCarrera.getSelectedIndex() >= 0) {            
            resolucion = queries.getResoluciones(((Item)cbxCarrera.getSelectedItem()).toString(),1).get(0);
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
        boolean enabled;
        
        enabled = cbxMateria.getSelectedIndex() >= 0;
        
        if(!enabled){
            cbxSecciones.setSelectedIndex(-1);            
        }else{
            if (cbxPeriodo.getSelectedIndex() >= 0 && cbxCarrera.getSelectedIndex() >= 0 && cbxNivel.getSelectedIndex() >= 0 && cbxMateria.getSelectedIndex() >= 0) {
                ofertasAcademicas = queries.getOfertasAcademicas(
                        ((Periodo) ((Item) cbxPeriodo.getModel().getSelectedItem()).getValue()).getId(),
                        ((Carrera) ((Item) cbxCarrera.getModel().getSelectedItem()).getValue()).getId(),
                        ((Nivel) ((Item) cbxNivel.getModel().getSelectedItem()).getValue()).getId(),
                        ((Unidad) ((Item) cbxMateria.getModel().getSelectedItem()).getValue()).getId());
                
                if (ofertasAcademicas.size() > 0) {
                    switch(ofertasAcademicas.get(0).getNomenclatura()){
                        case ALFABETICO:
                            cbxSecciones.setSelectedIndex(0);
                            break;
                        default:
                            cbxSecciones.setSelectedIndex(1);
                    }
                    
                    
                    //cbxSecciones.setSelectedIndex(ofertasAcademicas.get(0).getNomenclatura());
                    fillTableSecciones();
                } else {
                    cbxSecciones.setSelectedIndex(0);
                }
            }            
        }
        
        //fillTableSecciones();
        cbxSecciones.setEnabled(enabled);
    }//GEN-LAST:event_cbxMateriaItemStateChanged

    private void cbxSeccionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSeccionesItemStateChanged
        updateSecciones();
    }//GEN-LAST:event_cbxSeccionesItemStateChanged

    private void cbxPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPeriodoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxCarrera;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JComboBox<String> cbxNivel;
    private javax.swing.JComboBox<String> cbxPeriodo;
    private javax.swing.JComboBox<String> cbxSecciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblSecciones;
    private javax.swing.JTable tblModulos;
    private javax.swing.JTable tblSecciones;
    // End of variables declaration//GEN-END:variables
}
