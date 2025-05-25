/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.Scanner;

/**
 *
 * @author teddy
 */

public class Pawn extends Piece
{
    boolean firstMove = true;
    boolean passantable = false;
    Scanner scanner = new Scanner(System.in);
    //Missing en passant
    //Missing promotion
    
    public Pawn(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 1;
        passantable = false;
    }
    
    
   public boolean attacking(Board board, Coordinate targetCoordinate, Coordinate currentCoordinate)
   {
        int colourPerspective = 1;
        int deltaFile = targetCoordinate.file - currentCoordinate.file;
        int deltaRow = targetCoordinate.row - currentCoordinate.row;

        if (pieceType < 0) {
            colourPerspective = -1;
            firstMove = currentCoordinate.row == 7;
        }

        // Only allow attacking diagonally
        if (deltaRow == 1 * colourPerspective && Math.abs(deltaFile) == 1) {
            int destinationPiece = board.checkSquare(targetCoordinate);

            // Check if the piece at the target is of opposite color
            if (colourPerspective == -1 && destinationPiece > 0) return true;
            if (colourPerspective == 1 && destinationPiece < 0) return true;
        }

        return false;
    }


    @Override
    public boolean validMove(Board board, Coordinate destination, Coordinate origin) { 
    // Basic validation check    
    int colourPerspective = 1;
    
    if(pieceType < 0) 
    {
        colourPerspective = -1;
        firstMove = origin.row == 7;
    }
    else firstMove = origin.row == 2;
    
    if (!withinBounds(destination)) return false;
    
    if (row == destination.row && file == destination.file) return false;
    
    int deltaFile = destination.file - origin.file;
    int deltaRow = destination.row - origin.row;
    
    System.out.println("\nPawn: " + pieceType);
    System.out.println("deltaFile: " + deltaFile);
    System.out.println("deltaRow: " + deltaRow);

    // Pawns can only move forward (positive deltaFile for white)
    if (deltaRow >= 0  && colourPerspective < 0) {
        System.out.println("Pawns must move forward");
        return false;
    }
    else if (deltaRow <= 0  && colourPerspective > 0) {
        System.out.println("Pawns must move forward");
        return false;
    }

    // Normal forward move (1 square)
    if (deltaRow == 1*colourPerspective && deltaFile == 0) {
        boolean isEmpty = board.checkSquare(destination) == 0;
        System.out.println("Normal move - destination " + (isEmpty ? "empty" : "occupied"));
        return isEmpty;
    }

    // Initial two-square move
    if (firstMove && deltaRow == 2*colourPerspective && deltaFile == 0) {
        Coordinate intermediate = new Coordinate(file, row+1*colourPerspective);
        boolean pathClear = board.checkSquare(intermediate) == 0 && 
                          board.checkSquare(destination) == 0;
        System.out.println("Two-square move - path " + (pathClear ? "clear" : "blocked"));
        passantable = true;
        return pathClear;
    }
            
    // Diagonal capture
    if (deltaRow == 1*colourPerspective && Math.abs(deltaFile) == 1) {
        int destinationPiece = board.checkSquare(destination);
        
        boolean canCapture = false;
        if(colourPerspective == -1) canCapture = (destinationPiece > 0); // Assuming black pieces are negative
        else canCapture = (destinationPiece < 0); 
         
        System.out.println("Capture attempt - " + (canCapture ? "valid" : "invalid"));
        return canCapture;
    }
    

    System.out.println("Invalid pawn move pattern");
    return false;
}
}
    