/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Cleber Nascimento
 */
public class ApenasLetrasCaracteres extends PlainDocument{
    @Override
   public void insertString(int offs, String str,
            javax.swing.text.AttributeSet a) throws BadLocationException {
        // normalmente apenas uma letra é inserida por vez,
        // mas fazendo assim também previne caso o usuário
        // cole algum texto
        for (int i = 0; i < str.length(); i++)
            if (!Character.isLetter(str.charAt(i)) && str.charAt(i) != '.' && str.charAt(i) != '@' && str.charAt(i) != '_' && str.charAt(i) != '-')
                return;
       // Aceita apenas 1 letra digitada por vez
        int tamMax = 30;
        if ((getLength() + str.length()) <= tamMax)
 
            super.insertString(offs, str, a);
    }
}
