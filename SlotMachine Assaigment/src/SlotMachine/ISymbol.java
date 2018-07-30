/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotMachine;
/**
 *
 * @author w1565407
 */
public interface ISymbol {

    public void setImage(String fileName);
    public String getImage();
    public void setValue(int v);
    public int getValue();
    
}