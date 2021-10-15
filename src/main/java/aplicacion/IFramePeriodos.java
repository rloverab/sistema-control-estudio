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

import clases.CharacterLimiter;
import clases.Periodo;
import clases.Queries;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import servicios.ConnectionDB.Status;

/**
 *
 * @author Roger Lovera
 */
public class IFramePeriodos extends javax.swing.JInternalFrame {
    private final Queries queries;
    private int periodo_id = -1;

    /**
     * Creates new form Periodos
     *
     * @param queries
     */
    public IFramePeriodos(Queries queries) {
        initComponents();
        this.queries = queries;
        
        txtPeriodo.setDocument(new CharacterLimiter(txtPeriodo, 12));
        dateFechaInicial.setDateFormatString("dd/MM/yyyy");
        dateFechaFinal.setDateFormatString("dd/MM/yyyy");

        for (Component component : dateFechaInicial.getComponents()) {
            //System.out.println(component.toString());
            if (component instanceof JTextFieldDateEditor) {
                ((JTextFieldDateEditor) component).setEditable(false);
                ((JTextFieldDateEditor) component).setOpaque(true);
            }
        }

        for (Component component : dateFechaFinal.getComponents()) {
            //System.out.println(component.toString());
            if (component instanceof JTextFieldDateEditor) {
                ((JTextFieldDateEditor) component).setEditable(false);
                ((JTextFieldDateEditor) component).setOpaque(true);
            }
        }

        fillTablaPeriodos();
        setEnabledFields(false);
    }

    //Setters
    //Getters
    //Actions    
    private void fillTablaPeriodos() {
        String[] header = {"id", "Periodo", "Fecha inicial", "Fecha final", "Vigente"};
        Object[] fila;
        int[] widths = {0, -1, 100, 100, 100};
        DefaultTableModel dtm;
        ArrayList<Periodo> periodos;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        dtm = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        TableCellRenderer tcl = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = sdf.format(value);
                }                
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

        };

        tblPeriodos.setModel(dtm);

        for (var i = 0; i < widths.length; i++) {
            if (widths[i] >= 0) {
                tblPeriodos.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                tblPeriodos.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                tblPeriodos.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                tblPeriodos.getTableHeader().getColumnModel().getColumn(i).setMinWidth(widths[i]);
            }
        }       
        
        periodos = queries.getPeriodos();
        
        for (Periodo periodo : periodos) {
            fila = new Object[] {
                    periodo.getId(),                    //id
                    periodo.getPeriodo(),               //periodo
                    periodo.getFechaInicial(),          //fecha_inicial
                    periodo.getFechaFinal(),            //fecha_final
                    periodo.isActivo() ? "Si" : "No"    //vigente
            };
            dtm.addRow(fila);
        }

        for (int i = 0; i < tblPeriodos.getColumnModel().getColumnCount(); i++) {
            tblPeriodos.getColumnModel().getColumn(i).setCellRenderer(tcl);
        }

        this.periodo_id = -1;
    }

    private Status insertPeriodo(String periodo, java.util.Date fechaInicial, java.util.Date fechaFinal) {
        Date auxFechaInicial;
        Date auxFechaFinal;        
        Periodo auxPeriodo;
        
        auxFechaInicial = new Date(fechaInicial.getTime());
        auxFechaFinal = new Date(fechaFinal.getTime());        
        auxPeriodo = new Periodo(-1, periodo, auxFechaInicial, auxFechaFinal, false);
        
        return queries.insertPeriodo(auxPeriodo);
    }

    private Status updatePeriodo(int id, String periodo, java.util.Date fechaInicial, java.util.Date fechaFinal) {
        Date auxFechaInicial;
        Date auxFechaFinal;        
        Periodo auxPeriodo;
        
        auxFechaInicial = new Date(fechaInicial.getTime());
        auxFechaFinal = new Date(fechaFinal.getTime());        
        auxPeriodo = new Periodo(id, periodo, auxFechaInicial, auxFechaFinal, false);
        
        return queries.updatePeriodo(auxPeriodo);
    }

    private void setEnabledFields(boolean enabled) {
        txtPeriodo.setText("");
        txtPeriodo.setEnabled(enabled);
        txtPeriodo.setOpaque(!enabled);
        if (!enabled) {
            dateFechaInicial.setDate(null);
            dateFechaFinal.setDate(null);
        }
        dateFechaInicial.setEnabled(enabled);
        dateFechaInicial.setOpaque(enabled);
        dateFechaFinal.setEnabled(enabled);
        dateFechaFinal.setOpaque(enabled);
        btnNuevo.setEnabled(!enabled);
        btnNuevo.setOpaque(enabled);
        btnGuardar.setEnabled(enabled);
        btnGuardar.setOpaque(!enabled);
        btnCancelar.setEnabled(enabled);
        btnCancelar.setOpaque(!enabled);
    }
    
    private void saveChanges(){
        String dialogTitle = periodo_id < 0 ? "Nuevo periodo" : "Actualizar periodo";
        String periodo = this.txtPeriodo.getText().trim();
        java.util.Date fechaInicial = this.dateFechaInicial.getDate();
        java.util.Date fechaFinal = this.dateFechaFinal.getDate();                
        Status status;

        if (periodo.trim().isEmpty() || fechaInicial == null || fechaFinal == null) {
            JOptionPane.showInternalMessageDialog(
                    this.panelDatos,
                    "Debe llenar todos los campos",
                    dialogTitle,
                    JOptionPane.ERROR_MESSAGE
            );
        } else if (fechaInicial.getTime() > fechaFinal.getTime()) {
            JOptionPane.showInternalMessageDialog(
                    this.panelDatos,
                    "La fecha inicial no puede ser posterior a la fecha final",
                    dialogTitle,
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            if (periodo_id == -1) {
                status = insertPeriodo(periodo,fechaInicial,fechaFinal);
            } else {
                status = updatePeriodo(periodo_id, periodo, fechaInicial, fechaFinal);
            }

            switch (status) {
                case OK:
                    JOptionPane.showInternalMessageDialog(
                            this.panelDatos,
                            String.format(
                                    "Periodo %s exitósamente", 
                                    periodo_id < 0 ? "registrado" : "actualizado") ,
                            dialogTitle,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    fillTablaPeriodos();
                    setEnabledFields(false);
                    break;
                case ERROR:
                    JOptionPane.showInternalMessageDialog(
                            this.panelDatos,
                            String.format(
                                    "Ocurrió un error al %s los datos.\nPeriodo: %s.", 
                                    periodo_id < 0 ? "registrar" : "actualizar",
                                    periodo),
                            dialogTitle,
                            JOptionPane.ERROR_MESSAGE
                    );
                    break;
                case EXIST:
                    JOptionPane.showInternalMessageDialog(
                            this.panelDatos,
                            String.format(
                                    "%s\nPeriodo: %s.",
                                    periodo_id < 0 ? "Este periodo ya se encuentra registrado." : "No puede haber dos períodos con la misma denominación.",
                                    periodo),
                            dialogTitle,
                            JOptionPane.WARNING_MESSAGE
                    );                    
                    break;
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

        panelDatos = new javax.swing.JPanel();
        lblPeriodo = new javax.swing.JLabel();
        lblFechaInicial = new javax.swing.JLabel();
        lblFechaFinal = new javax.swing.JLabel();
        txtPeriodo = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        dateFechaInicial = new com.toedter.calendar.JDateChooser();
        dateFechaFinal = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeriodos = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setTitle("Periodos");

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblPeriodo.setText("Periodo");

        lblFechaInicial.setText("Fecha inicial");

        lblFechaFinal.setText("Fecha final");

        txtPeriodo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPeriodoKeyTyped(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        dateFechaInicial.setDateFormatString("dd/MM/yyyy");

        dateFechaFinal.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addComponent(lblPeriodo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFechaInicial)
                        .addGap(3, 3, 3)
                        .addComponent(dateFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFechaFinal)
                        .addGap(3, 3, 3)
                        .addComponent(dateFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPeriodo)
                        .addComponent(txtPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFechaInicial)
                        .addComponent(lblFechaFinal))
                    .addComponent(dateFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevo))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblPeriodos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPeriodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeriodosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeriodos);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrar)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        saveChanges();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        setEnabledFields(true);
        periodo_id = -1;
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tblPeriodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeriodosMouseClicked
        int selectedRow = tblPeriodos.getSelectedRow();
        TableModel tm = tblPeriodos.getModel();

        if (evt.getClickCount() == 2) {
            if (selectedRow > -1) {
                setEnabledFields(true);
                periodo_id = (int) tblPeriodos.getModel().getValueAt(selectedRow, 0);
                txtPeriodo.setText(tblPeriodos.getModel().getValueAt(selectedRow, 1).toString());
                dateFechaInicial.setDate(new java.util.Date(((Date) tm.getValueAt(selectedRow, 2)).getTime()));
                dateFechaFinal.setDate(new java.util.Date(((Date) tm.getValueAt(selectedRow, 3)).getTime()));
            }
        }
    }//GEN-LAST:event_tblPeriodosMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setEnabledFields(false);
        periodo_id = -1;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtPeriodoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPeriodoKeyTyped
        if (evt.getKeyChar() == ' ') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPeriodoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private com.toedter.calendar.JDateChooser dateFechaFinal;
    private com.toedter.calendar.JDateChooser dateFechaInicial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechaFinal;
    private javax.swing.JLabel lblFechaInicial;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JTable tblPeriodos;
    private javax.swing.JTextField txtPeriodo;
    // End of variables declaration//GEN-END:variables
}
