/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.ArrayList;

/**
 *
 * @author teddy
 */
public class King extends Piece
{
    
    WinCondition win = new WinCondition();
    public King(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 0;
    }
    
    @Override
    

    public boolean validMove(Board board, Coordinate destination, Coordinate origin) {
    int deltaRow = Math.abs(destination.row - origin.row);
    int deltaFile = Math.abs(destination.file - origin.file);
    
    // King must move exactly one square (remove deltaRow+deltaFile==0 check)
    if (deltaRow > 1 || deltaFile > 1) {
        return false;
    }

    // Check destination not occupied by friendly piece (simplified condition)
    int destinationPiece = board.checkSquare(destination);
    if ((pieceType * destinationPiece) > 0) { // Same sign check
        return false;
    }

    return legalMove(board, destination, origin);
}

    public boolean legalMove(Board board, Coordinate destination, Coordinate origin) {
    ArrayList<Piece> attackers = pieceType > 0 ? board.black : board.white;

    for (Piece piece : attackers) {
        Coordinate attackerPos = new Coordinate(piece.file, piece.row);

        // Simplified king adjacency check
        if (piece instanceof King) {
            if (Math.abs(destination.file - piece.file) <= 1 && 
                Math.abs(destination.row - piece.row) <= 1) {
                return false;
            }
            continue;
        }

        // Fixed pawn attack check (use piece's position, not origin)
        if (piece instanceof Pawn) {
            if (((Pawn)piece).attacking(board, destination, attackerPos)) {
                return false;
            }
            continue;
        }

        if (piece.validMove(board, destination, attackerPos)) {
            return false;
        }
    }

    // Simplified simulation (remove reviveKing calls)
    Piece victim = board.movePiece(origin, destination);
    boolean inCheck = win.check(board, pieceType > 0);
   
    board.undoMovePiece(origin, destination, victim);
    
    return !inCheck;
}

}

