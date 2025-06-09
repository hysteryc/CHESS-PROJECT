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

//Square is the variable type that comprises the board
//No complicated logic here, just basic getters and setters

public class Square 
{
    private final int row; 
    private final int file; 
    private Piece piece; 
    private final JButton button;

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
    
    // Getter for button
    public JButton getButton() 
    {
        return button;
    }
    
    // Setter for image
    public void setImage(ImageIcon icon) 
    {
        button.setIcon(icon);
    }
    
    // Getter for File
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
