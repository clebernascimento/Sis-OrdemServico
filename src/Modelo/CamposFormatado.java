/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.NavigationFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;

/**
 *
 * @author Cleber Nascimento
 */
public class CamposFormatado{
    static PlainDocument document = new PlainDocument(){
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
        if(offs > 6){
            if(getLength() < 3){
                if(getText(0, getLength()).indexOf(".") == 3){
                    super.insertString(offs, str.replaceAll("[^0-9]", ""), a);
                }else{
                    super.insertString(3, ".", a);
                    for(int i = 0; i < getLength(); i++){
                        remove(getText(0,getLength()).indexOf("."),".".length());   
                    }
                    super.insertString(3, ".", a);
                }
            }else{
                  if(getText(0,getLength()).indexOf(".")==0){
                    super.insertString(offs, str.replaceAll("[^0-9]",""), a);
                }else{
                    super.insertString(offs, ".".concat(str.replaceAll("[^0-9]","")), a);
                    }
                }   
            }
        }
 };
    static NavigationFilter nav = new NavigationFilter(){
        @Override
        public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
            super.setDot(fb,document.getLength(), bias); //To change body of generated methods, choose Tools | Templates.
        }
    };
    public static NavigationFilter getNav(){
        return nav;
    }
    public static PlainDocument getDocument(){
        return document;
    }
}