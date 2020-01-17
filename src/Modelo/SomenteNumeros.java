/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Cleber Nascimento
 */
public class SomenteNumeros extends PlainDocument{
    int tamanhoMax;
    //METODO DA ABSTRACT DOCUMENTS
    public SomenteNumeros(int tamanhoMax){
        this.tamanhoMax = tamanhoMax;
    }
    //sobrescrevendo o método insertString o qual será chamado toda fez que algum texto for inserido no componente
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            //condição se veio algum texto para ser inserido, senão utilizamos um return para finalizar o método.
            if (str == null) 
                return;  
                    
             String stringAntiga = getText (0, getLength() );//pegando o texto já presente no componente. 
             int tamanhoNovo = stringAntiga.length() + str.length(); 
                        
             if (tamanhoNovo <= tamanhoMax) 
                 super.insertString(offset, str.replaceAll("[^0-9|^.]", ""), attr);        
    }
}