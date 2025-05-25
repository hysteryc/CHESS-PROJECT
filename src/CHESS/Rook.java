package CHESS;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece 
{
    public Rook(boolean isWhite) 
    {
        super(isWhite);
    }
    
    // Unicode for Rook
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♖" : "♜"; 
    }
    
    // Valid Move for Rooks based on rules and current Boardstate
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
        if (startRow != endRow && startCol != endCol) 
        {
            return false; 
        }

        if (startRow == endRow) 
        {
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);
            for (int col = minCol + 1; col < maxCol; col++) 
            {
                if (!board.isSquareEmpty(startRow, col)) 
                {
                    return false;  
                }
            }
        } else if (startCol == endCol) 
        {
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);
            for (int row = minRow + 1; row < maxRow; row++) 
            {
                if (!board.isSquareEmpty(row, startCol)) 
                {
                    return false; 
                }
            }
        }
        return true; 
    }

    @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        int row = from.getRow();
        int col = from.getCol();


        int[][] directions = 
        {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        for (int[] dir : directions) 
        {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) 
            {
                Piece targetPiece = board.getPieceAt(newRow, newCol);
                if (targetPiece == null) 
                {
                    legalMoves.add(new Coordinate(newRow, newCol)); 
                } 
                else 
                {
                    if (targetPiece.isWhite() != this.isWhite()) 
                    {
                        legalMoves.add(new Coordinate(newRow, newCol)); 
                    }
                    break; 
                }
                newRow += dir[0];
                newCol += dir[1];
            }
        }
        return legalMoves;
    }
}
