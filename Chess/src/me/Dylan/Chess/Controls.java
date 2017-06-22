package me.Dylan.Chess;

public interface Controls {
	
	public boolean movePossible(int Piece, int row, int col, int pieceRow, int pieceCol);
	public boolean isTurns(boolean player);
	public boolean takePiece(int firstPiece, int pieceRow, int pieceCol, int secondPiece, int secPieceRow, int secPieceCol);
	

}
