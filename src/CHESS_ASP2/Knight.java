package CHESS_ASP2;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece 
{
    public Knight(boolean isWhite) 
    {
        super(isWhite);
    }
    
    //Gets the unicode symbol associated with the Knight
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♘" : "♞";  
    }

    // Checking whether move is valid based upon Pieces movement rules
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    // Method for Testing for next iteration with GUI Implemeted (For next assignment)
    @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        int startRow = from.getRow();
        int startCol = from.getCol();
        int[][] knightMoves = 
        {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) 
        {
            int endRow = startRow + move[0];
            int endCol = startCol + move[1];
            if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) 
            {
                legalMoves.add(new Coordinate(endRow, endCol));
            }
        }

        return legalMoves;
    }
}
