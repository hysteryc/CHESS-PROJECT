/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author teddy
 */
public class King extends Piece
{
    public King(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 0;
    }
    
    private boolean isValidMove(Board board, Coordinate destination, Coordinate origin, boolean isWhite)
    {
        int deltaRow = Math.abs(destination.row - origin.row);   
        int deltaFile = Math.abs(destination.file - origin.file);
        if(deltaRow <= 1 && deltaFile <= 1 && !(deltaRow == 0 && deltaFile == 0))
        {
            int destinationPiece = board.checkSquare(destination);
            
            if((isWhite && destinationPiece > 0) || (!isWhite && destinationPiece < 0))
            {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean validMoveWhite(Board board, Coordinate destination, Coordinate origin)
    {
        return isValidMove(board, destination, origin, true);
    }
    
    @Override
    public boolean validMoveBlack(Board board, Coordinate destination, Coordinate origin)
    {
        return isValidMove(board, destination, origin, false);
    }
    
    
    
}
