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
public class Piece { //will be used soon
    int row;
    int file;
    int pieceType;
    int material;
    boolean isWhite;
    boolean passantable;
    
    public Piece(int row, int file, boolean isWhite)
    {
        this.row = row;
        this.file = file;
        this.isWhite = isWhite;
        passantable = false;
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
    
    
    public Piece(int file, int row, int pieceType)
    {
        this.row = row;
        this.file = file;
        this.pieceType = pieceType;
    }
    
    
    public boolean withinBounds(Coordinate destination) // returns true if within bounds
    {
        return ((destination.file <= 8 && destination.file >= 1) && (destination.row <= 8 && destination.row >= 1));
    }
    
    public boolean validMoveWhite(Board board, Coordinate destination, Coordinate origin)
    {
        return true;
    }
    
    public boolean validMoveBlack(Board board, Coordinate destination, Coordinate origin) 
    {
        return true;
    }      
            
    public boolean enPassantMove(Board board, Coordinate destination, Coordinate origin) 
    {
        return true;
    } 
}

