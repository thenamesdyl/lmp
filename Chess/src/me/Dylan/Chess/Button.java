package me.Dylan.Chess;

import javax.swing.JButton;

public class Button extends JButton{
	private int row;
	private int column;
	public Button(int row, int column){
		super();
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
}
