package me.Dylan.Chess;

public enum White {
	PAWN(1),
    BISHOP(4),
    KNIGHT(3),
    ROOK(5),
	QUEEN(9),
	KING(10);

    private final int value;

    White(int value) {
        this.value = value;
    }
    int value() {
        return this.value;
    }

}
