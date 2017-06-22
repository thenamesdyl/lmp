package me.Dylan.Chess;

public enum Black {
	PAWN(11),
    BISHOP(40),
    KNIGHT(30),
    ROOK(50),
	QUEEN(90),
	KING(100);

    private final int value;

    Black(int value) {
        this.value = value;
    }
    int value() {
        return this.value;
    }

}
