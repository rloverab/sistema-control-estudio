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
package clases;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Esta clase permite transferir de forma automática el contenido del atributo text al atributo toolTip de un objeto JTextField
 * @author Rogr Lovera
 */
public class TextFieldToToolTip implements DocumentListener{
    private JTextField textField;

    public TextFieldToToolTip(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        updateToolTip();
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        updateToolTip();
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        updateToolTip();
    }
    
    private void updateToolTip(){
        if(textField.getText().isEmpty()){
            textField.setToolTipText(null);
        }else{
            textField.setToolTipText(textField.getText());
        }
    }
    
}
