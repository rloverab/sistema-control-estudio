/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author roger
 */
public final class Formatter extends DefaultFormatterFactory{    
    private MaskFormatter mask;

    //Contructor
    public Formatter(String format, char placeHolder) {        
        try {
            mask = new MaskFormatter(format);
            mask.setPlaceholderCharacter(placeHolder);
            this.setDefaultFormatter(mask);
        } catch (ParseException ex) {
            Logger.getLogger(Formatter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Formatter(){
        this("",' ');
    }
}
