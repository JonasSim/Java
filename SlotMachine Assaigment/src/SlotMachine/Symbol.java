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
public class Symbol implements ISymbol{
    private int value;
	private String fileName;

	@Override
	public void setImage(String fileName) {
		this.fileName =  fileName;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return fileName;
	}

	@Override
	public void setValue(int v) {
		this.value = v;
	}

	@Override
	public int getValue() {
		return value;
	}

}

