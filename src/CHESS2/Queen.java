package CHESS2;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece 
{
    public Queen(boolean isWhite) 
    {
        super(isWhite);
    }

    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♕" : "♛";  
    }
    
    //implements movement isvalid move from rook and bishop as it is a queen
    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) 
    {
        return new Rook(isWhite()).isValidMove(startRow, startCol, endRow, endCol, board) ||
               new Bishop(isWhite()).isValidMove(startRow, startCol, endRow, endCol, board);
    }
    
    @Override
    public List<Coordinate> generateLegalMoves(Board board, Coordinate from) 
    {
        List<Coordinate> legalMoves = new ArrayList<>();
        legalMoves.addAll(new Rook(isWhite()).generateLegalMoves(board, from));
        legalMoves.addAll(new Bishop(isWhite()).generateLegalMoves(board, from));
        return legalMoves;
    }
}
