package me.Dylan.Chess;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model {
	private boolean light = true;
	private boolean dark = false;
	private boolean currentTurn = true;
	private int[][] boardArray = new int[8][8]; // going to add a zero for chess point values for black
	private String toString = "";
	private boolean primarySide; //the side that faces the player
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private ChangeEvent ce;
	private ArrayList<Integer> takenPieces = new ArrayList<Integer>();
	public Model(boolean side){
		primarySide = side;
		for(int row = 0; row < 8; row ++){
			for(int column = 0; column < 8; column++){
				boardArray[row][column] = 0; //just to set as empty for now
			}
		}
		
		reset();
		
	}
	
	public void reset(){
		
		for(int row = 0; row <8; row ++){
			for(int column = 0; column< 8; column++){
				boardArray[row][column] = 0;
			}
		}
		if(primarySide){
			for(int column = 0; column< 8; column++){
				boardArray[6][column] = 1; // 1 equals white pawn, 10 equals black pawn.
			}
			boardArray[7][0] = White.ROOK.value(); // 5 equals white rook, 50 equals black rook etc
			boardArray[7][1] = White.KNIGHT.value();
			boardArray[7][2] = White.BISHOP.value();
			boardArray[7][3] = White.QUEEN.value();
			boardArray[7][4] = White.KING.value();
			boardArray[7][5] = White.BISHOP.value();
			boardArray[7][6] = White.KNIGHT.value();
			boardArray[7][7] = White.ROOK.value();
			
			for(int column = 0; column< 8; column++){
				boardArray[1][column] = 11; // 1 equals white pawn, 10 equals black pawn.
			}
			boardArray[0][0] = Black.ROOK.value(); // 5 equals white rook, 50 equals black rook etc
			boardArray[0][1] = Black.KNIGHT.value();
			boardArray[0][2] = Black.BISHOP.value();
			boardArray[0][3] = Black.QUEEN.value();
			boardArray[0][4] = Black.KING.value();
			boardArray[0][5] = Black.BISHOP.value();
			boardArray[0][6] = Black.KNIGHT.value();
			boardArray[0][7] = Black.ROOK.value();
			
			
			
		}else{
			
			
			
			for(int column = 0; column< 8; column++){
				boardArray[6][column] = 11; // 1 equals white pawn, 10 equals black pawn.
			}
			boardArray[7][0] = Black.ROOK.value(); // 5 equals white rook, 50 equals black rook etc
			boardArray[7][1] = Black.KNIGHT.value();
			boardArray[7][2] = Black.BISHOP.value();
			boardArray[7][3] = Black.QUEEN.value();
			boardArray[7][4] = Black.KING.value();
			boardArray[7][5] = Black.BISHOP.value();
			boardArray[7][6] = Black.KNIGHT.value();
			boardArray[7][7] = Black.ROOK.value();
			
			for(int column = 0; column< 8; column++){
				boardArray[0][column] = 1; // 1 equals white pawn, 10 equals black pawn.
			}
			boardArray[0][0] = White.ROOK.value(); // 5 equals white rook, 50 equals black rook etc
			boardArray[0][1] = White.KNIGHT.value();
			boardArray[0][2] = White.BISHOP.value();
			boardArray[0][3] = White.QUEEN.value();
			boardArray[0][4] = White.KING.value();
			boardArray[0][5] = White.BISHOP.value();
			boardArray[0][6] = White.KNIGHT.value();
			boardArray[0][7] = White.ROOK.value();
		}
		notifyChangeListeners();
	}
	
	public String toString(){
		
		for(int row = 0; row < 8; row ++){
			for(int column = 0; column < 8; column++){
				if(boardArray[row][column]== White.PAWN.value()){
					toString = toString + "WP ";
				}else if(boardArray[row][column] == White.KNIGHT.value()){
					toString = toString + "WN ";
				}else if(boardArray[row][column] == White.BISHOP.value()){
					toString = toString + "WB ";
				}else if(boardArray[row][column] == White.QUEEN.value()){
					toString = toString + "WQ ";
				}else if(boardArray[row][column] == White.KING.value()){
					toString = toString + "WK ";
				}else if(boardArray[row][column] == White.ROOK.value()){
					toString = toString + "WR ";
				}else if(boardArray[row][column]== Black.PAWN.value()){
					toString = toString + "BP ";
				}else if(boardArray[row][column] == Black.KNIGHT.value()){
					toString = toString + "BN ";
				}else if(boardArray[row][column] == Black.BISHOP.value()){
					toString = toString + "BB ";
				}else if(boardArray[row][column] == Black.QUEEN.value()){
					toString = toString + "BQ ";
				}else if(boardArray[row][column] == Black.KING.value()){
					toString = toString + "BK ";
				}else if(boardArray[row][column] == Black.ROOK.value()){
					toString = toString + "BR ";
				}else if(boardArray[row][column] == 0){
					toString = toString + ". ";
				}
				if(column == 7){
					toString = toString + "\n";
				}
				
			}
		}
		return toString;
		
	}
	
	public int whatPiece(int row, int column){
		return boardArray[row][column];
	}
	
	public void occupy(int row, int column,boolean side, String piece){
		if(piece.toLowerCase() == "pawn"){
			if(side){
				boardArray[row][column] = White.PAWN.value();
			}else{
				boardArray[row][column] = Black.PAWN.value();
			}
		}else if(piece.toLowerCase() == "king"){
			if(side){
				boardArray[row][column] = White.KING.value();
			}else{
				boardArray[row][column] = Black.KING.value();
			}
		}else if(piece.toLowerCase() == "rook"){
			if(side){
				boardArray[row][column] = White.ROOK.value();
			}else{
				boardArray[row][column] = Black.ROOK.value();
			}
		}else if(piece.toLowerCase() == "knight"){
			if(side){
				boardArray[row][column] = White.KNIGHT.value();
			}else{
				boardArray[row][column] = Black.KNIGHT.value();
			}
		}else if(piece.toLowerCase() == "queen"){
			if(side){
				boardArray[row][column] = White.QUEEN.value();
			}else{
				boardArray[row][column] = Black.QUEEN.value();
			}
		}else if(piece.toLowerCase() == "bishop"){
			if(side){
				boardArray[row][column] = White.BISHOP.value();
			}else{
				boardArray[row][column] = Black.BISHOP.value();
			}
		}
		
		
		notifyChangeListeners();
		
	}
	
	public void takeTurn(){
		currentTurn = !currentTurn;
	    
	}
	
	public boolean getTurn(){
		return currentTurn;
	}
	public void takePiece(int row, int column){
		takenPieces.add(boardArray[row][column]);
	}
	
	public int getCount(boolean side){
		int whiteScore = 0;
		int blackScore = 0;
		
		for(int i = 0; i <takenPieces.size(); i++){
			if(takenPieces.get(i) == 1){
				blackScore = blackScore + 1;
			}else if(takenPieces.get(i) == 3){
				blackScore = blackScore + 3;
			}else if(takenPieces.get(i) == 4){
				blackScore = blackScore + 4;
			}else if(takenPieces.get(i) == 5){
				blackScore = blackScore + 5;
			}else if(takenPieces.get(i) == 9){
				blackScore = blackScore + 9;
			}else if(takenPieces.get(i) == 11){
				whiteScore = whiteScore + 1;
			}else if(takenPieces.get(i) == 30){
				whiteScore = whiteScore + 3;
			}else if(takenPieces.get(i) == 40){
				whiteScore = whiteScore + 4;
			}else if(takenPieces.get(i) == 50){
				whiteScore = whiteScore + 5;
			}else if(takenPieces.get(i) == 90){
				whiteScore = whiteScore + 9;
			}
		}
		if(side){
			return whiteScore - blackScore;
		}
		return blackScore = whiteScore;
	}
	
	public boolean isOccupied(int row, int column){
		if(boardArray[row][column] != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isOccupiedBy(int row, int column, boolean side){
		if(!side && (boardArray[row][column] == Black.PAWN.value() || boardArray[row][column] == Black.KNIGHT.value() || boardArray[row][column] == Black.BISHOP.value() || boardArray[row][column] == Black.ROOK.value() ||boardArray[row][column] == Black.QUEEN.value() || boardArray[row][column] == Black.KING.value())){
			return true;
		}else if(side && (boardArray[row][column] == White.PAWN.value() || boardArray[row][column] == White.KNIGHT.value() || boardArray[row][column] == White.BISHOP.value() || boardArray[row][column] == White.ROOK.value() ||boardArray[row][column] == White.QUEEN.value() || boardArray[row][column] == White.KING.value())){
			return true;
		}else{
			return false;
		}
	}
	
	public void addChangeListener(ChangeListener cl){
		listeners.add(cl);
		
	}
	public void removeChangeListener(ChangeListener cl){
		listeners.remove(cl);
	}
	
	public void notifyChangeListeners(){
		for(ChangeListener name: listeners){
			name.stateChanged(ce = new ChangeEvent(name));
		}
	}
	public static void main(String [] args){
		Model m = new Model(true);
		System.out.println(m.toString());
		
	}

}
