/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author teddy
 */
public class WinCondition 
{
    
    //checkmate
    
    //check
    public boolean inCheck(Board board, boolean isWhite)
    {
        Coordinate kingPosition = findKing(board, isWhite);
        for(int row = 0; row < 8; row++)
        {
            for(int file = 0; file < 8; file++)
            {
                Coordinate coordinate = new Coordinate(row, file);
                Piece piece = board.getPiece(coordinate);
                if(piece != null && piece.pieceType > 0 != isWhite)
                {
                    boolean attackable = isWhite
                        ? piece.validMove(board, kingPosition, coordinate)
                        : piece.validMove(board, kingPosition, coordinate);        
                    if(attackable)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //checkmate
    public boolean isCheckmate(Board board, boolean isWhite) 
    {
        if (!inCheck(board, isWhite)) 
        {
            return false;
        }

        for (int row = 0; row < 8; row++) 
        {
            for (int file = 0; file < 8; file++) 
            {
                Coordinate origin = new Coordinate(row, file);
                Piece piece = board.getPiece(origin);

                if (piece != null && piece.pieceType > 0 == isWhite) 
                {
                    for (int toRow = 0; toRow < 8; toRow++) 
                    {
                        for (int toFile = 0; toFile < 8; toFile++) 
                        {
                            Coordinate destination = new Coordinate(toRow, toFile);

                            boolean valid = isWhite
                                ? piece.validMove(board, destination, origin)
                                : piece.validMove(board, destination, origin);

                            if (valid) 
                            {
                                Board simulate = board.copy(); 
                                simulate.movePiece(origin, destination);
                            
                                if (!inCheck(simulate, isWhite)) 
                                {
                                    return false; 
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; 
    }
    
    // find king on board.
    private Coordinate findKing(Board board, boolean isWhite)
    {
        for(int row = 0; row < 8; row++)
        {
            for(int file = 0; file < 8; file++)
            {
                Coordinate king = new Coordinate(row, file);
                Piece piece = board.getPiece(king);
                if(piece instanceof King && piece.pieceType > 0 == isWhite)
                {
                    return king;
                }
            }
        }
        return null;
    }
}
