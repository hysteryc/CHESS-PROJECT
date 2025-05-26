package CHESS_ASP2;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece 
{
    public King(boolean isWhite) 
    {
        super(isWhite);
    }
    
    // Gets the unicode symbol associated with the King
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♔" : "♚";  
    }

    // Checking whether the move is valid based upon pieces movement rules
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
         Piece target = (board.getPieceAt(endRow, endCol));
        if (target != null && target.isWhite() == this.isWhite()) {
        return false;
    }
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        return rowDiff <= 1 && colDiff <= 1;  
    }

    @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        int startRow = from.getRow();
        int startCol = from.getCol();
        int[][] kingMoves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} 
        };

        for (int[] move : kingMoves) 
        {
            int endRow = startRow + move[0];
            int endCol = startCol + move[1];
            if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
                legalMoves.add(new Coordinate(endRow, endCol));
            }
        }
        return legalMoves;
    }
    
    
}
