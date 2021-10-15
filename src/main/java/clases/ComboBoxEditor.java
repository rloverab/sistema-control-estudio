/*
 * Copyright (C) 2021 Roger Lovera <rloverab@yahoo.es>
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

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Roger Lovera <rloverab@yahoo.es>
 */
public class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor{
    final JComboBox comboBox = new JComboBox();

    public ComboBoxEditor(Object[] items) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        for (Object item : items) {
            model.addElement(item);
        }
        
        comboBox.setModel(model);
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if(e instanceof MouseEvent){
            return ((MouseEvent) e).getClickCount() >= 2;
        }else if(e instanceof KeyEvent){              
            return ((KeyEvent) e).getKeyCode() == KeyEvent.VK_F2;
        }
        
        return super.isCellEditable(e); 
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value != null){
            comboBox.setSelectedItem(value);
        }else{
            comboBox.setSelectedIndex(-1);
        }        
        return comboBox;
    }    
}
