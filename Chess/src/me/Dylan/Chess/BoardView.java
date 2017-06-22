package me.Dylan.Chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class BoardView implements ChangeListener, ActionListener{
	private Model model;
	private JFrame jf;
	private List<Button> buttons = new ArrayList<Button>();
	private JLabel jl;
	private Image blackpawn, whitepawn, whiteking, whitebishop, whiterook, whiteknight, whitequeen, blackking, blackqueen, blackrook, blackknight, blackbishop;
	private Dimension d = new Dimension(300,50);
	private GridLayout gl = new GridLayout();
	private Controller controller;
	private int counter;
	public BoardView(Model m, Controller controller){
		this.model = m;
		this.controller = controller;
		model.addChangeListener(this);
		jf = new JFrame();
		gl.setRows(8);
		gl.setColumns(8);
		jf.setLayout(gl);
		jf.setMinimumSize(d);
		jf.setTitle("Chess Game - By Dylan Burton");
		jf.setMaximumSize(d);
		for(int i = 0; i < 8; i++){
			for(int i2 = 0; i2<8; i2++ ){
				buttons.add(new Button(i,i2));
				buttons.get(counter).addActionListener(this);
				buttons.get(counter).setBorderPainted(false);
				jf.add(buttons.get(counter));
				//some logic for black and white squares. Essentially if counter is even and column is even and row is odd than make black. If counter is even column is even and row is even make white. Than the elses fill the ones in between
				if(counter % 2 == 0 && i2 % 2 == 0 && i %2 != 0){
					buttons.get(counter).setBackground(new Color(0x926239));
				}else if(counter % 2 == 0 && i2%2 == 0 && i %2 == 0){
					buttons.get(counter).setBackground(new Color(0xf8c89f));
				}else if(i % 2 != 0){
					buttons.get(counter).setBackground(new Color(0xf8c89f));
				}else if(i % 2 == 0){
					buttons.get(counter).setBackground(new Color(0x926239));
				}
				counter = counter + 1;
			}
		}
		
		counter = 0;
		
	
		updateView();
		
		
		jf.pack();
		jf.setVisible(true);
		
	}
	public void updateView(){
		try {
			whitepawn = ImageIO.read(getClass().getResource("whitepawn.png"));
			blackpawn = ImageIO.read(getClass().getResource("blackpawn.png"));
			whitequeen = ImageIO.read(getClass().getResource("whitequeen.png"));
			whiteknight = ImageIO.read(getClass().getResource("whiteknight.png"));
			whitebishop = ImageIO.read(getClass().getResource("whitebishop.png"));
			whiterook = ImageIO.read(getClass().getResource("whiterook.png"));
			whiteking = ImageIO.read(getClass().getResource("whiteking.png"));
			blackking = ImageIO.read(getClass().getResource("blackking.png"));
			blackrook = ImageIO.read(getClass().getResource("blackrook.png"));
			blackknight = ImageIO.read(getClass().getResource("blackknight.png"));
			blackbishop = ImageIO.read(getClass().getResource("blackbishop.png"));
			blackqueen = ImageIO.read(getClass().getResource("blackqueen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int row = 0; row < 8; row++){
			for(int column = 0; column < 8; column++){
				if(model.isOccupied(buttons.get(counter).getRow(), buttons.get(counter).getCol())){
					buttons.get(counter).setEnabled(true);
					if(model.whatPiece(row, column) == White.PAWN.value()){
						buttons.get(counter).setIcon(new ImageIcon(whitepawn));
					}else if(model.whatPiece(row, column) == White.ROOK.value()){
						buttons.get(counter).setIcon(new ImageIcon(whiterook));
					}else if(model.whatPiece(row, column) == White.KNIGHT.value()){
						buttons.get(counter).setIcon(new ImageIcon(whiteknight));
					}else if(model.whatPiece(row, column) == White.BISHOP.value()){
						buttons.get(counter).setIcon(new ImageIcon(whitebishop));
					}else if(model.whatPiece(row, column) == White.QUEEN.value()){
						buttons.get(counter).setIcon(new ImageIcon(whitequeen));
					}else if(model.whatPiece(row, column) == White.KING.value()){
						buttons.get(counter).setIcon(new ImageIcon(whiteking));
					}else if(model.whatPiece(row, column) == Black.PAWN.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackpawn));
					}else if(model.whatPiece(row, column) == Black.KNIGHT.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackknight));
					}else if(model.whatPiece(row, column) == Black.BISHOP.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackbishop));
					}else if(model.whatPiece(row, column) == Black.ROOK.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackrook));
					}else if(model.whatPiece(row, column) == Black.QUEEN.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackqueen));
					}else if(model.whatPiece(row, column) == Black.KING.value()){
						buttons.get(counter).setIcon(new ImageIcon(blackking));
					}
				}else{
					buttons.get(counter).setEnabled(false);
				}
					
				counter = counter + 1;
			}
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateView();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.actionPerformed(e);
		
	}
	

}
