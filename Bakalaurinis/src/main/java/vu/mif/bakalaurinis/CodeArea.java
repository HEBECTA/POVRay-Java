/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vu.mif.bakalaurinis;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author GUGU
 */
public class CodeArea extends JPanel {
    
    JScrollPane scrollPane;
    JTextArea textArea;
    
    String fullFilePath;
    
    boolean saved;
    
    CodeArea(){
        
        this.setLayout(new BorderLayout());
        
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
       
       
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        
        this.add(scrollPane);
        
        this.saved = true;
        
    
    }
    
    void loadText(String txt){
        
        saved = false;
        
        textArea.append(txt+"\n");
 
        int index = txt.indexOf("#add#");
        
        System.out.println(txt);
   
        System.out.println("index: " + index);
    }
    
    String getFullFilePath(){
        
        return fullFilePath;
    }
    
    void setAbsolutePath(String path){
        
        this.fullFilePath = path;
    }
    
    String getText(){
        
        return textArea.getText();
    }
    
    boolean containsString(String phrase){
        
        String txt = textArea.getText();
        
        if ( txt.indexOf(phrase) > 0 ){
            
            return true;
        }
        
        return false;
    }
    
}

