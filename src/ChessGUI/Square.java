/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

import javax.swing.*;

/**
 *
 * @author karlo
 */
public class Square 
{
    private int row; 
    private int file; 
    private Piece piece; 
    private JButton button;

    public Square(int file, int row, JButton button) 
    {
        this.row = row;
        this.file = file;
        this.piece = null;
        this.button = button;
    }
    
    // Getter for Row
    public int getRow() 
    {
        return row;
    }
    
    public JButton getButton() 
    {
        return button;
    }
    
    public void setImage(ImageIcon icon) 
    {
        button.setIcon(icon);
    }
    
    // Getter for Col
    public int getFile() 
    {
        return file;
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
    @Override
    public String toString() 
    {
        return (piece != null) ? piece.getSymbol() : "▭";
    }
    
    
    
}
