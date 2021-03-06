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

import clases.Carrera;
import clases.Queries;
import clases.Controls;
import clases.Item;
import clases.Nivel;
import clases.Reports;
import clases.Resolucion;
import clases.Unidad;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roger Lovera
 */
public class IFramePlanesEstudio extends javax.swing.JInternalFrame {
    private final Queries queries;
    private final Reports reports;

    /**
     * Creates new form PlanesEstudio
     *
     * @param queries
     * @param reports
     */    
    public IFramePlanesEstudio(
            Queries queries, 
            Reports reports) {
        initComponents();
        this.queries = queries;
        this.reports = reports;

        if (queries != null) {
            fillComboBoxCarreras();
            prepareTablePlanEstudio();
        }else{
            JOptionPane.showMessageDialog(
                    this.rootPane, 
                    "Error cargando las consultas", 
                    "ERROR", 
                    JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    //Setters    
    //Getters
    //Actions
    private void fillComboBoxCarreras() {
        ArrayList<Item> items;
        ArrayList<Carrera> carreras;
        
        items = new ArrayList<>();
        carreras = queries.getCarreras();
        
        carreras.forEach(carrera -> items.add(new Item(carrera)));
        
        Controls.fillComboBoxItem(
                cbxCarreras,
                items,
                "Seleccione...");
    }

    private void fillComboBoxResoluciones() {
        ArrayList<Resolucion> resoluciones;
        ArrayList<Item> items;
        Carrera carrera;
        
        cbxResoluciones.removeAllItems();
        
        carrera = (Carrera) ((Item) cbxCarreras.getSelectedItem()).getValue();
        items = new ArrayList<>();
        resoluciones = queries.getResoluciones(carrera.getCarrera());
        
        resoluciones.forEach(resolucion -> items.add(new Item(resolucion)));
        
        Controls.fillComboBoxItem(
                cbxResoluciones, 
                items, 
                "Seleccione...");        
    }

    private void fillComboBoxNiveles() {
        ArrayList<Item> items;
        ArrayList<Nivel> niveles;
        Carrera carrera;
        Resolucion resolucion;
        
        items = new ArrayList<>();
        carrera = (Carrera) ((Item)cbxCarreras.getSelectedItem()).getValue();
        resolucion = (Resolucion) ((Item)cbxResoluciones.getSelectedItem()).getValue();                
        niveles = queries.getNiveles(carrera.getId(), resolucion.getId());
        niveles.forEach(nivel -> items.add(new Item(nivel)));
        Controls.fillComboBoxItem(cbxNiveles, items, "Todos");
    }
    
    private void prepareTablePlanEstudio(){        
        int[] widths = {0, 140, -1, 40, 50, 40};        
        String[] header = {"id", "C??digo", "Unidad", "HTA", "HTAS", "UC"};
        
        Controls.prepareTable(tblPlanEstudio, header, widths);        
    }

    private void fillTablePlanEstudio() {        
        ArrayList<Object[]> rows;    
        ArrayList<Unidad> unidades;
        Carrera carrera;
        Resolucion resolucion;
        Nivel nivel;
        Integer carrera_id;
        Integer resolucion_id;
        Integer nivel_id;
        
        rows = new ArrayList<>();
        
        carrera = (Carrera) ((Item)cbxCarreras.getSelectedItem()).getValue();
        resolucion = (Resolucion) ((Item)cbxResoluciones.getSelectedItem()).getValue();
        nivel = (Nivel) ((Item)cbxNiveles.getSelectedItem()).getValue();
        
        carrera_id = carrera != null ? carrera.getId() : null;
        resolucion_id = resolucion != null ? resolucion.getId() : null;
        nivel_id = nivel != null ? nivel.getId() : null;
            
        unidades = queries.getPlanEstudio(
                carrera_id, 
                nivel_id, 
                resolucion_id);

        unidades.forEach(unidad -> {
            rows.add(new Object[]{
                unidad.getId(),
                unidad.getCodigo(),
                unidad.getUnidad(),
                unidad.getHTA(),
                unidad.getHTAS(),
                unidad.getUC()
            });
        });
        
        Controls.fillTable(tblPlanEstudio, rows);
    }

    private void showPlanEstudio() {
        fillTablePlanEstudio();
        btnMostrar.setEnabled(false);
    }

    private void exportPlanEstudio() {        
        Carrera carrera;
        Nivel nivel;
        Resolucion resolucion;
        Integer carrera_id;
        Integer nivel_id;
        Integer resolucion_id;
        
        carrera = (Carrera) ((Item)cbxCarreras.getSelectedItem()).getValue();
        nivel = (Nivel) ((Item)cbxNiveles.getSelectedItem()).getValue();
        resolucion = (Resolucion) ((Item)cbxResoluciones.getSelectedItem()).getValue();
        
        carrera_id = carrera != null ? carrera.getId() : null;
        nivel_id = nivel != null ? nivel.getId() : null;
        resolucion_id = resolucion != null ? resolucion.getId() : null;
        
        reports.generateReportPlanEstudio(
                carrera_id, 
                nivel_id, 
                resolucion_id);        
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
        cbxCarreras = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxResoluciones = new javax.swing.JComboBox<>();
        btnMostrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbxNiveles = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlanEstudio = new javax.swing.JTable();
        btnExportar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setTitle("Planes de estudio");
        setPreferredSize(new java.awt.Dimension(598, 498));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        cbxCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCarreras.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCarrerasItemStateChanged(evt);
            }
        });

        jLabel1.setText("Carrera");

        jLabel2.setText("Aprobaci??n");

        cbxResoluciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxResoluciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxResolucionesItemStateChanged(evt);
            }
        });

        btnMostrar.setText("Mostrar");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        jLabel3.setText("Nivel");

        cbxNiveles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxNiveles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNivelesItemStateChanged(evt);
            }
        });

        tblPlanEstudio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPlanEstudio);

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxNiveles, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxCarreras, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxResoluciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxResoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCerrar)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCarrerasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCarrerasItemStateChanged
        if (cbxCarreras.getSelectedIndex() <= 0) {
            cbxResoluciones.removeAllItems();
            cbxResoluciones.setEnabled(false);
        } else {
            fillComboBoxResoluciones();
            cbxResoluciones.setEnabled(true);
        }
    }//GEN-LAST:event_cbxCarrerasItemStateChanged

    private void cbxResolucionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxResolucionesItemStateChanged
        boolean enabled;

        enabled = cbxResoluciones.getSelectedIndex() > 0;
        btnMostrar.setEnabled(enabled);
        btnExportar.setEnabled(enabled);        

        if (enabled) {
            fillComboBoxNiveles();
        } else {
            cbxNiveles.removeAllItems();
        }

        cbxNiveles.setEnabled(enabled);
    }//GEN-LAST:event_cbxResolucionesItemStateChanged

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cbxNivelesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNivelesItemStateChanged
        boolean enabled;

        enabled = (cbxNiveles.getSelectedIndex() >= 0);

        btnMostrar.setEnabled(enabled);

        if (tblPlanEstudio.getRowCount() > 0) {
            ((DefaultTableModel) tblPlanEstudio.getModel()).setRowCount(0);
        }
    }//GEN-LAST:event_cbxNivelesItemStateChanged

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        showPlanEstudio();
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        exportPlanEstudio();
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JComboBox<String> cbxCarreras;
    private javax.swing.JComboBox<String> cbxNiveles;
    private javax.swing.JComboBox<String> cbxResoluciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPlanEstudio;
    // End of variables declaration//GEN-END:variables
}
