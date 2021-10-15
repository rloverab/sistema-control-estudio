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

import clases.ComboBoxEditor;
import clases.Controls;
import clases.Docente;
import clases.Materia;
import clases.Periodo;
import clases.Queries;
import clases.Resolucion;
import clases.SpinnerEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author roger
 */
public class IFrameOfertaAcademica extends javax.swing.JInternalFrame {

    private Queries queries;
    private Resolucion resolucion;
    private ArrayList<Docente> docentes;

    /**
     * Creates new form VentanaOfertaAcademica
     *
     * @param queries
     */
    public IFrameOfertaAcademica(Queries queries) {
        initComponents();
        this.queries = queries;

        docentes = queries.getDocentes(true);
        fillComboBoxPeriodo();
        prepareTableSecciones();
        getSeccion(0, false);
    }    

    private void fillComboBoxPeriodo() {
        ArrayList<Periodo> periodos;
        ArrayList<String> items;

        periodos = queries.getPeriodos();
        items = new ArrayList<>();

        periodos.forEach(periodo -> {
            items.add(periodo.getPeriodo());
        });

        Controls.fillComboBox(cbxPeriodo, items, null);

        cbxPeriodo.setSelectedIndex(-1);
        cbxPeriodo.setEnabled(cbxPeriodo.getItemCount() > 0);
    }

    private void fillComboBoxCarrera() {
        cbxCarrera.removeAllItems();
        if (cbxPeriodo.getSelectedIndex() >= 0) {
            Controls.fillComboBox(cbxCarrera, queries.getCarreras(), null);
        }
        cbxCarrera.setSelectedIndex(-1);
        cbxCarrera.setEnabled(cbxCarrera.getItemCount() > 0);
    }

    private void fillComboBoxNivel() {
        cbxNivel.removeAllItems();
        if (resolucion != null && cbxCarrera.getSelectedIndex() >= 0) {
            Controls.fillComboBox(
                    cbxNivel,
                    queries.getNiveles(
                            (String) cbxCarrera.getSelectedItem(),
                            resolucion.getResolucion(),
                            resolucion.getActa(),
                            resolucion.getFecha()),
                    null);
        }
        cbxNivel.setSelectedIndex(-1);
        cbxNivel.setEnabled(cbxNivel.getItemCount() > 0);
    }

    private void fillComboBoxMateria() {
        ArrayList<String> arrayList;
        ArrayList<Materia> materias;

        arrayList = new ArrayList<>();

        cbxMateria.removeAllItems();
        if (cbxNivel.getSelectedIndex() >= 0) {
            materias = queries.getPlanEstudio2(
                    (String) cbxCarrera.getSelectedItem(),
                    (String) cbxNivel.getSelectedItem(),
                    resolucion.getResolucion(),
                    resolucion.getActa(),
                    resolucion.getFecha());

            if (materias.size() > 0) {
                materias.forEach(materia -> {
                    arrayList.add(materia.toString());
                });
                Controls.fillComboBox(
                        cbxMateria,
                        arrayList,
                        null);
            }
        }
        cbxMateria.setSelectedIndex(-1);
        cbxMateria.setEnabled(cbxMateria.getItemCount() > 0);
    }

    private void addRow() {
        DefaultTableModel dtm;

        dtm = (DefaultTableModel) tblSecciones.getModel();

        dtm.addRow(new Object[]{null, getSeccion(dtm.getRowCount(), cbxSecciones.getSelectedIndex() == 1), null, null});
    }

    private void removeRow() {
        DefaultTableModel dtm;

        dtm = (DefaultTableModel) tblSecciones.getModel();

        if (dtm.getRowCount() > 0) {
            dtm.removeRow(dtm.getRowCount() - 1);
        }
    }

    private void prepareTableSecciones() {
        DefaultTableModel dtm;
        TableColumnModel tcm;
        TableColumnModel tcmh;
        int[] width;
        String[] items;

        dtm = (DefaultTableModel) tblSecciones.getModel();
        tcm = tblSecciones.getColumnModel();
        tcmh = tblSecciones.getTableHeader().getColumnModel();
        width = new int[]{0, 60, 60};
        //items = new String[] {"ROGER LOVERA", "ROSIRYS BONILLA", "JUAN CARLOS LOVERA"};

        items = new String[docentes.size()];

        for (int i = 0; i < docentes.size(); i++) {
            items[i] = docentes.get(i).toString();
        }

        tcm.getColumn(2).setCellEditor(new SpinnerEditor(1, 1, 100, 1));
        tcm.getColumn(3).setCellEditor(new ComboBoxEditor(items));
        tblSecciones.setRowHeight(21);

        dtm.getDataVector().removeAllElements();

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
                btnBorrar.setEnabled(dtm.getRowCount() > 0);
            }
        });

        btnBorrar.setEnabled(dtm.getRowCount() > 0);
    }

    private void cleanTableSecciones() {
        DefaultTableModel dtm;

        dtm = (DefaultTableModel) tblSecciones.getModel();

        //dtm.getDataVector().removeAllElements();
        
        for(int i = dtm.getRowCount() - 1; i >= 0; i--){
            dtm.removeRow(i);
        }
        
        
    }
        
    private String getSeccion(int id, boolean numerico){
        int seccion = id;
        int a = (int) 'A';
        int z = (int) 'Z';       
        
        ArrayList<String> letras = new ArrayList<>();
        ArrayList<String> letrasAux = new ArrayList<>();
        
        for(int i = a; i <= z; i++){
            letras.add(Character.toString(i));            
        }        
        
        if(numerico){
            return String.format("%d", seccion + 1);
        }else{                        
            letrasAux.add(letras.get(seccion % letras.size()));
            
            while(seccion >= letras.size()){                 
                seccion = (seccion / letras.size()) - 1;
                letrasAux.add(letras.get(seccion % letras.size()));                
            }            
            
            Collections.reverse(letrasAux);
            
            return String.join("",letrasAux);            
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSecciones = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();

        setTitle("Oferta académica");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        tblSecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Sección", "Cupos", "Docente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
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

        btnAgregar.setText("+");
        btnAgregar.setToolTipText("Agregar sección");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBorrar.setText("-");
        btnBorrar.setToolTipText("Eliminar sección");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBorrar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
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
            resolucion = queries.getResoluciones((String) cbxCarrera.getSelectedItem(), 1).get(0);
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
        }
        
        cbxSecciones.setEnabled(enabled);
    }//GEN-LAST:event_cbxMateriaItemStateChanged

    private void cbxSeccionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSeccionesItemStateChanged
        boolean enabled;
        DefaultTableModel dtm;
        
        enabled = cbxSecciones.getSelectedIndex() >= 0;
        
        btnAgregar.setEnabled(enabled);
        tblSecciones.setEnabled(enabled);
        
        if(!enabled){
            cleanTableSecciones();
        }else{
            dtm = (DefaultTableModel) tblSecciones.getModel();
            for(int i = 0; i < dtm.getRowCount(); i++){
                dtm.setValueAt(getSeccion(i, cbxSecciones.getSelectedIndex() == 1), i, 1);
            }
        }
    }//GEN-LAST:event_cbxSeccionesItemStateChanged

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblSecciones;
    private javax.swing.JTable tblSecciones;
    // End of variables declaration//GEN-END:variables
}
