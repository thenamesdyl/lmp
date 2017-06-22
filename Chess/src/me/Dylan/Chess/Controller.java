package me.Dylan.Chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements ChangeListener, ActionListener{
	private Model model;
	public Controller(Model m){
		this.model = m;
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
	}
	
	public static void main(String[] args){
		Model m = new Model(true);
		Controller c = new Controller(m);
		BoardView bv = new BoardView(m,c);
	}
	

}
