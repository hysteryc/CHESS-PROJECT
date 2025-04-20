/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author teddy
 */
public class Knight extends Piece
{
    public Knight(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 3;
    }
    
    
    @Override
    public boolean validMoveWhite(Board board, Coordinate destination, Coordinate origin)
    {
        if(!withinBounds(destination))
        {
            return false;
        }
        
        if(origin.row == destination.row && origin.file == destination.file)
        {
            return false;
        }
        
        int deltaFile = Math.abs(destination.file - origin.file);
        int deltaRow = Math.abs(destination.row - origin.row);
        boolean lShapedMove = ((deltaRow == 2 && deltaFile == 1) || (deltaRow == 1 && deltaFile == 2));
        
        if(!lShapedMove)
        {
            return false;
        }
        
        int destinationPiece = board.checkSquare(destination);
        if(destinationPiece > 0)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean validMoveBlack(Board board, Coordinate destination, Coordinate origin) 
    {
        if (!withinBounds(destination)) 
        {
            return false;
        }

        if (origin.row == destination.row && origin.file == destination.file) 
        {
            return false;
        }

        int deltaRow = Math.abs(destination.row - origin.row);
        int deltaFile = Math.abs(destination.file - origin.file);

        boolean lShapedMove = (deltaRow == 2 && deltaFile == 1) || (deltaRow == 1 && deltaFile == 2);

        if (!lShapedMove) 
        {
            return false;
        }

        int destinationPiece = board.checkSquare(destination);
        if (destinationPiece < 0) 
        {
            return false;
        }
        return true;
    }
}
