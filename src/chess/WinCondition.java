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
        for(Square square : board.getSquares())
        {
            if(square.piece != null)
            {
                Piece attacker = square.piece;
                if((isWhite && attacker.pieceType < 0) || (!isWhite && attacker.pieceType > 0))
                {
                    if(attacker.validMove(board, kingPosition, new Coordinate(square.row, square.file)))
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

        for (int row = 1; row <= 8; row++) 
        {
            for (int file = 1; file <= 8; file++) 
            {
                Square square = board.getSquare(file, row);
                Piece piece = square.piece;

                if (piece != null && ((isWhite && piece.pieceType > 0) || (!isWhite && piece.pieceType < 0))) 
                {
                    Coordinate origin = new Coordinate(row, file);

                    for (int destRow = 1; destRow <= 8; destRow++) 
                    {
                        for (int destFile = 1; destFile <= 8; destFile++) 
                        {
                            Coordinate destination = new Coordinate(destRow, destFile);

                            if (piece.validMove(board, destination, origin)) 
                            {
                                Piece captured = board.getPiece(destination);
                                board.movePiece(origin, destination);
                                boolean stillInCheck = inCheck(board, isWhite);
                                board.movePiece(destination, origin);
                                if (captured != null) 
                                {
                                    board.placePiece(captured, destination);
                                }
                                if (!stillInCheck) 
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
        for(int row = 1; row <= 8; row++)
        {
            for(int file = 1; file <= 8; file++)
            {
                Coordinate current = new Coordinate(row, file);
                Piece piece = board.getPiece(current);
                if(piece instanceof King && ((isWhite && piece.pieceType > 0) || (!isWhite && piece.pieceType < 0)))
                {
                    return current;
                }
            }
        }
        return null;
    }
}
