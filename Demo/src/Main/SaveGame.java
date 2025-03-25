/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class SaveGame implements Serializable{
    private boolean[] stage_clear = {false,false,false,false,false,false,false,false,false,false}; // 10 stage
    
    public void set_Stage_Num(int index){
        stage_clear[index] = true;
    }
    
    public void check_stage(){
        for (int i=0;i <= stage_clear.length-1;i++){
            System.out.println(stage_clear[i]);
        }
    }
    
    public boolean get_Stage_Num(int index){
        return stage_clear[index-1];
    }
    
}
