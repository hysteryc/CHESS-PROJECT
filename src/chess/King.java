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
    
    
    public boolean ValidMove(Board board, Coordinate destination, Coordinate origin)
    {
        int deltaRow = Math.abs(destination.row - origin.row);   
        int deltaFile = Math.abs(destination.file - origin.file);
        
        if(deltaRow > 1) return false;
        if(deltaFile > 1) return false;
        
        if(!(deltaRow == 0 && deltaFile == 0))
        {
            int destinationPiece = board.checkSquare(destination);
            return (pieceType > 0 && destinationPiece > 0) || (pieceType < 0 && destinationPiece < 0);
        }
        return false;
    }
    
    
    
    
}
