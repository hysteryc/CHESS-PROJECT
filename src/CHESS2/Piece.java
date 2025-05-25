/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

import java.util.List;

/**
 *
 * @author teddy
 */
public abstract class Piece 
{
    protected boolean isWhite;
    private Square currentSquare;

    public Piece(boolean isWhite) 
    {
        this.isWhite = isWhite;
    }

    public boolean isWhite() 
    {
        return isWhite;
    }

    public boolean isOpponent(Piece other) 
    {
        return other != null && this.isWhite != other.isWhite();
    }
    
    // returns the current Square for piece
    public Square getCurrentSquare() 
    {
        return currentSquare;
    }
    
    // Sets the current Square for piece
    public void setCurrentSquare(Square currentSquare) 
    {
        this.currentSquare = currentSquare;
    }
    
    // Each piece will return its own Unicode symbol
    public abstract String getSymbol();

    // Validate movement logic (based on board state) and piece movement rules
    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board);
    
    // Each piece will generate its own legal moves
    public abstract List<Coordinate> generateLegalMoves(Board board, Coordinate from);
}
