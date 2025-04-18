/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author teddy
 */

public class Pawn extends Piece
{
    boolean firstMove = true;
    
    //Missing en passant
    //Missing promotion
    
    public Pawn(int row, int file, boolean isWhite)
    {
        super(row, file, isWhite);
        this.material = 1;
        this.pieceType = 1;
    }
    
    @Override
    public boolean validMoveWhite(Board board, Coordinate destination) { 
    // Basic validation checks
    
    if (!withinBounds(destination)) {
        
        return false;
    }
    
    if (row == destination.row && file == destination.file) {
        
        return false;
    }

    int deltaFile = destination.file - file;
    int deltaRow = destination.row - row;
    
    System.out.println(deltaFile);
    System.out.println(deltaRow);

    // Pawns can only move forward (positive deltaFile for white)
    if (deltaRow <= 0) {
        System.out.println("Pawns must move forward");
        return false;
    }

    // Normal forward move (1 square)
    if (deltaRow == 1 && deltaFile == 0) {
        boolean isEmpty = board.checkSquare(destination) == 0;
        System.out.println("Normal move - destination " + (isEmpty ? "empty" : "occupied"));
        return isEmpty;
    }

    // Initial two-square move
    if (firstMove && deltaRow == 2 && deltaFile == 0) {
        Coordinate intermediate = new Coordinate(file, row+1);
        boolean pathClear = board.checkSquare(intermediate) == 0 && 
                          board.checkSquare(destination) == 0;
        System.out.println("Two-square move - path " + (pathClear ? "clear" : "blocked"));
        return pathClear;
    }
            
    // Diagonal capture
    if (deltaRow == 1 && Math.abs(deltaFile) == 1) {
        int destinationPiece = board.checkSquare(destination);
        boolean canCapture = destinationPiece < 0; // Assuming black pieces are negative
        System.out.println("Capture attempt - " + (canCapture ? "valid" : "invalid"));
        return canCapture;
    }

    System.out.println("Invalid pawn move pattern");
    return false;
}
}
