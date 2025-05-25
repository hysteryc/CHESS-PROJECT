/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

/**
 *
 * @author teddy
 */
public class Square 
{
    private int row; 
    private int col; 
    private Piece piece; 

    public Square(int row, int col) 
    {
        this.row = row;
        this.col = col;
        this.piece = null;
    }
    
    // Getter for Row
    public int getRow() 
    {
        return row;
    }

    // Getter for Col
    public int getCol() 
    {
        return col;
    }
    
    // Getter for Piece
    public Piece getPiece() 
    {
        return piece;
    }
    
    // Setter for Piece
    public void setPiece(Piece piece) 
    {
        this.piece = piece;
    }

    // Checks if Piece is Empty
    public boolean isEmpty() 
    {
        return piece == null;
    }

    // toString for Empty Square
    public String toString() 
    {
        return (piece != null) ? piece.getSymbol() : "▭";
    }
    
}
