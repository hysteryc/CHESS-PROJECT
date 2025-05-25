package CHESS;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece 
{
    public Pawn(boolean isWhite) 
    {
        super(isWhite);
    }

    // Get method for unicode symbol for Pawn
    @Override
    public String getSymbol() 
    {
        return isWhite ? "♙" : "♟";
    }

    // Checking whether Move is Valid based on pieces movement rules.
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
        int direction = isWhite ? -1 : 1;
        int startRank = isWhite ? 6 : 1;

        Piece target = board.getPieceAt(endRow, endCol);

        if (startCol == endCol && target == null) 
        {
            if (endRow == startRow + direction) return true;
            if (startRow == startRank && endRow == startRow + 2 * direction && board.getPieceAt(startRow + direction, startCol) == null) 
            {
                return true;
            }
        }

        if (Math.abs(endCol - startCol) == 1 && endRow == startRow + direction && target != null && isOpponent(target)) 
        {
            return true;
        }

        return false;
    }
    
     @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        int direction = isWhite() ? 1 : -1;
        int startRow = from.getRow();
        int startCol = from.getCol();

        // One square forward (if empty)
        if (board.isSquareEmpty(startRow + direction, startCol)) 
        {
            legalMoves.add(new Coordinate(startRow + direction, startCol));
        }
        
        // Two squares forward from the starting position (if empty and the pawn hasn't moved yet)
        if ((isWhite() && startRow == 2 || !isWhite() && startRow == 7) &&
            board.isSquareEmpty(startRow + 2 * direction, startCol) &&
            board.isSquareEmpty(startRow + direction, startCol)) 
        {
            legalMoves.add(new Coordinate(startRow + 2 * direction, startCol));
        }

        if (board.isOpponentPiece(startRow + direction, startCol, isWhite())) 
        {
            legalMoves.add(new Coordinate(startRow + direction, startCol));
        }
        if (board.isOpponentPiece(startRow + direction, startCol, isWhite())) 
        {
            legalMoves.add(new Coordinate(startRow + direction, startCol));
        }

        return legalMoves;
    }
}
