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
package clases;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author roger
 */
public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor{
    final JSpinner spinner = new JSpinner();

    public SpinnerEditor(String[] items) {
        spinner.setModel(new SpinnerListModel(Arrays.asList(items)));
    }

    public SpinnerEditor(int current, int min, int max, int stepSize) {
        spinner.setModel(new SpinnerNumberModel(current, min, max, stepSize));
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
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
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        
        if(o != null){
            spinner.setValue(o);
        }        
        return spinner;
    }
    
}
