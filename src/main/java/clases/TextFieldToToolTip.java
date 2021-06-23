/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
