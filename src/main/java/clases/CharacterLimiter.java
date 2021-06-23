/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Esta clase permite definir límites de caracteres en un JTextField
 * @author Roger Lovera
 */
public class CharacterLimiter extends PlainDocument{
    private final JTextField editor;
    private final int limit;

    public CharacterLimiter(JTextField editor, int limit) {
        this.editor = editor;
        this.limit = limit;
    }
    
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if((this.editor.getText().length()+str.length())>this.limit){            
            return;
        }
        super.insertString(offs, str, a); 
    }
}
