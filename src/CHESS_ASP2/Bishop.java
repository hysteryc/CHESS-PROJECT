package CHESS_ASP2;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece 
{
    public Bishop(boolean isWhite) 
    {
        super(isWhite);
    }
    
    // Gets the unicode symbol associated with the Bishop
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♗" : "♝"; 
    }

    // Checks Whether the move made is valid to the pieces movement rules.
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) 
        {
            return false;
        }

        int rowDirection = endRow > startRow ? 1 : -1;
        int colDirection = endCol > startCol ? 1 : -1;
        int row = startRow + rowDirection;
        int col = startCol + colDirection;
        while (row != endRow && col != endCol) 
        {
            if (!board.isSquareEmpty(row, col)) 
            {
                return false;  
            }
            row += rowDirection;
            col += colDirection;
        }
        return true;
    }

    // Generates a list of the legal moves able to be performed at the pieces current location
    @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        int startRow = from.getRow();
        int startCol = from.getCol();
        int[][] directions = 
        {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) 
        {
            int row = startRow;
            int col = startCol;
            while (true) 
            {
                row += direction[0];
                col += direction[1];
                if (row >= 1 && row <= 8 && col >= 1 && col <= 8) 
                {
                    legalMoves.add(new Coordinate(row, col));
                } 
                else 
                {
                    break;  
                }
            }
        }
        return legalMoves;
    }
}