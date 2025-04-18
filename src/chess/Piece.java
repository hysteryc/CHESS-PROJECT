/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.List;

/**
 *
 * @author teddy
 */
public class Piece 
{ 
    int row;
    int file;
    int pieceType;
    int material;
    boolean isWhite;
    
    public Piece(int row, int file, boolean isWhite)
    {
        this.row = row;
        this.file = file;
        this.isWhite = isWhite;
    }
    
    public int getFile()
    {
        return file;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public boolean isWhite()
    {
        return isWhite;
    }
    
    public void setPosition(int row, int file)
    {
        this.row = row;
        this.file = file;
    }
    
    
    public boolean validMoveWhite(Board board, Coordinate destination) 
    {
        return true;
    }
    
    public boolean withinBounds(Coordinate coordinate)
    {
        return coordinate.file >= 1 && coordinate.file <= 8 && coordinate.row >= 1 && coordinate.row <= 8;
    }

    
    
}

