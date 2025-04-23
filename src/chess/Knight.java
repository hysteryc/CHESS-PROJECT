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
    public boolean validMove(Board board, Coordinate destination, Coordinate origin)
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
        boolean lShapedMove = ((Math.abs(deltaRow) == 2 && Math.abs(deltaFile) == 1) || (Math.abs(deltaRow) == 1 && Math.abs(deltaFile) == 2));
        
        System.out.println("\nKnight: " + pieceType);
        System.out.println("deltaFile:" + deltaFile);
        System.out.println("deltaRow: " + deltaRow);
        
        if(!lShapedMove)
        {
            return false;
        }
        
        
        
        
        int destinationPiece = board.checkSquare(destination);
        
        if(destinationPiece >= 0 && pieceType < 0) return true;
        
        else if(destinationPiece <= 0 && pieceType > 0) return true;
        
        return false;
        
    }
    
}
