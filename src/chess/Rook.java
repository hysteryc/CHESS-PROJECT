/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author teddy
 */
public class Rook extends Piece
{
    public Rook(int file, int row, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 5;
    }
    
    private boolean pathClear(Board board, Coordinate origin, Coordinate destination)
    {
        int rowDirection = Integer.compare(destination.row, origin.row);
        int fileDirection = Integer.compare(destination.file, origin.file);
        
        int r = origin.row + rowDirection;
        int f = origin.file + fileDirection;
        
        while(r != destination.row || f != destination.file)
        {
            Coordinate check = new Coordinate(f, r);
            if(board.checkSquare(check) != 0)
            {
                return false;
            }
            r += rowDirection;
            f += fileDirection;
        }
        return true;        
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
        
        int deltaFile = destination.file - origin.file;
        int deltaRow = destination.row - origin.row;
        
        if(deltaRow != 0 && deltaFile != 0)
        {
            return false;
        }
        
        if(!pathClear(board, origin, destination))
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
        int deltaRow = destination.row - origin.row;
        int deltaFile = destination.file - origin.file;

        if (deltaRow != 0 && deltaFile != 0) 
        {
            return false;
        }

        if (!pathClear(board, origin, destination)) 
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
