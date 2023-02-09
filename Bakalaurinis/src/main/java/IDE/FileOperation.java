/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author GUGU
 */
public class FileOperation {
    
    JFileChooser chooser;
    
    String fileName;
    
    public FileOperation(String initial_dir){
        
        chooser = new JFileChooser(initial_dir);
        //chooser.setCurrentDirectory(new File("."));
    }
    
    File openFile(){
        
        File temp = null;
        
        chooser.showOpenDialog(null);
        
        temp = chooser.getSelectedFile();
        
        return temp;
    }
    
    void newFile(){
        
        fileName = new String("Untitled");
        
        
    }
    
    String saveFile(String name, String data){
        
        File temp = null;
       
        
        chooser.showSaveDialog(null);
        
        //chooser.setSelectedFile(temp);
        
        temp = chooser.getSelectedFile();
        
        try {
        
           FileWriter fileWriter = new FileWriter(temp);

           fileWriter.write(data);
           
           fileWriter.close();
        }
        
        catch (IOException e){
                
                System.out.println("execption save call");
            }
        
        return "";
    }
}


