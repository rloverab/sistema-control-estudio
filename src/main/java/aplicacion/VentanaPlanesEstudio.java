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

import java.sql.Date;
import servicios.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roger Lovera
 */
public class VentanaPlanesEstudio extends javax.swing.JInternalFrame {

    private final ConnectionDB conn;
    private final SimpleDateFormat sdf;

    /**
     * Creates new form PlanesEstudio
     *
     * @param conn
     */
    public VentanaPlanesEstudio(ConnectionDB conn) {
        initComponents();
        this.conn = conn;
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (this.conn != null) {
            fillComboBoxCarreras();
            fillTablePlanEstudio();
        }
    }

    //Setters    
    //Getters
    //Actions
    private void fillComboBoxCarreras() {
        ResultSet rs;
        
        cbxCarreras.removeAllItems();
        cbxCarreras.addItem("Seleccione...");
        
        rs = conn.executeStoredProcedureWithResultSet("select_carreras");
        
        try {
            while (rs.next()) {
                cbxCarreras.addItem(rs.getString("carrera"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPlanesEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillComboBoxNiveles() {
        ResultSet rs;
        Date fechaAprobacion;

        cbxNiveles.removeAllItems();
        cbxNiveles.addItem("Seleccione...");

        try {
            fechaAprobacion = new Date(sdf.parse(cbxFechaAprobacion.getSelectedItem().toString()).getTime());

            rs = conn.executeStoredProcedureWithResultSet(
                    "select_planes_estudio_niveles",
                    cbxCarreras.getSelectedItem().toString(),
                    fechaAprobacion);
            
            while (rs.next()) {
                cbxNiveles.addItem(rs.getString("nivel"));
            }
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(VentanaPlanesEstudio.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }

    private void fillComboBoxFechaAprobacion() {
        ResultSet rs;

        cbxFechaAprobacion.removeAllItems();
        cbxFechaAprobacion.addItem("Seleccione...");
        rs = conn.executeStoredProcedureWithResultSet("select_planes_estudio_fechas_aprobacion", cbxCarreras.getSelectedItem().toString());
        try {
            while (rs.next()) {                
                cbxFechaAprobacion.addItem(sdf.format(rs.getDate("fecha_aprobacion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPlanesEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillTablePlanEstudio() {
        ResultSet rs;
        Date fechaAprobacion;

        String[] header = {"id", "Código", "Unidad", "HTA", "HTAS", "UC"};
        Object[] fila;
        int[] widths = {0, 140, -1, 40, 50, 40};
        DefaultTableModel model;
        
        model = new DefaultTableModel(header, 0);
        model.setColumnCount(header.length);
        model.setColumnIdentifiers(header);

        try {
            if (cbxNiveles.getSelectedIndex() > 0) {
                fechaAprobacion = new Date(sdf.parse(cbxFechaAprobacion.getSelectedItem().toString()).getTime());
                
                rs = conn.executeStoredProcedureWithResultSet(
                        "select_plan_estudio",
                        cbxCarreras.getSelectedItem().toString(),
                        cbxNiveles.getSelectedItem().toString(),
                        fechaAprobacion);
                
                while (rs.next()) {
                    fila = new Object[]{
                        rs.getInt(1), //id
                        rs.getString(3),//codigo
                        rs.getString(4),//unidad
                        rs.getInt(6), //hta
                        rs.getInt(7), //htas
                        rs.getInt(8) //uc
                    };

                    model.addRow(fila);
                }
            }
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(VentanaPlanesEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblPlanEstudio.setModel(model);
        
        for (int i = 0; i < widths.length; i++) {
            if (widths[i] >= 0) {
                tblPlanEstudio.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                tblPlanEstudio.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                tblPlanEstudio.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                tblPlanEstudio.getTableHeader().getColumnModel().getColumn(i).setMinWidth(widths[i]);
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
        cbxCarreras = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxFechaAprobacion = new javax.swing.JComboBox<>();
        btnMostrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbxNiveles = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlanEstudio = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setTitle("Planes de estudio");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        cbxCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCarreras.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCarrerasItemStateChanged(evt);
            }
        });

        jLabel1.setText("Carrera");

        jLabel2.setText("Fecha de aprobación");

        cbxFechaAprobacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFechaAprobacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxFechaAprobacionItemStateChanged(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxFechaAprobacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCarreras, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btnMostrar)
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
                    .addComponent(cbxFechaAprobacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbxNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
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
            cbxFechaAprobacion.removeAllItems();
            cbxFechaAprobacion.setEnabled(false);
        } else {
            fillComboBoxFechaAprobacion();
            cbxFechaAprobacion.setEnabled(true);
        }
    }//GEN-LAST:event_cbxCarrerasItemStateChanged

    private void cbxFechaAprobacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxFechaAprobacionItemStateChanged
        if (cbxFechaAprobacion.getSelectedIndex() <= 0) {
            cbxNiveles.removeAllItems();
            cbxNiveles.setEnabled(false);
        } else {
            fillComboBoxNiveles();
            cbxNiveles.setEnabled(true);
        }
    }//GEN-LAST:event_cbxFechaAprobacionItemStateChanged

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cbxNivelesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNivelesItemStateChanged
        boolean enabled;

        enabled = (cbxNiveles.getSelectedIndex() > 0);

        btnMostrar.setEnabled(enabled);
        if (tblPlanEstudio.getRowCount() > 0) {
            ((DefaultTableModel) tblPlanEstudio.getModel()).setRowCount(0);
        }
    }//GEN-LAST:event_cbxNivelesItemStateChanged

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        fillTablePlanEstudio();
        btnMostrar.setEnabled(false);

    }//GEN-LAST:event_btnMostrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JComboBox<String> cbxCarreras;
    private javax.swing.JComboBox<String> cbxFechaAprobacion;
    private javax.swing.JComboBox<String> cbxNiveles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPlanEstudio;
    // End of variables declaration//GEN-END:variables
}
