/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */
public class Square 
{
    int row;
    int file;
    int tileValue;
    int pieceType;
    Piece piece;
    
    /*
    10 = white square
    20 = black square
    
    11 = white pawn
    12 = white rook
    13 = white knight
    14 = white bishop
    15 = white queen
    16 = white king
    
    21 = black pawn
    22 = black rook
    23 = black knight
    24 = black bishop
    25 = black queen
    26 = black king
    */
    
    public Square(int file, int row, Piece piece)
    { 
        this.file = file;
        this.row = row;
        this.piece = piece;
    }
    
    public int getFile()
    {
        return file;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public Piece getPiece() 
    { 
        return piece; 
    }
    
    public char getCharValue() //Return piece char representation
    {
            return switch (pieceType) {
                
                case 11 -> '♙';
                case 12 -> '♖';
                case 13 -> '♘';
                case 14 -> '♗';    
                case 15 -> '♕';
                case 16 -> '♔';
                
                case -11 -> '♟';
                case -12 -> '♜';
                case -13 -> '♞';
                case -14 -> '♝';
                case -15 -> '♛';
                case -16 -> '♚';
                default -> ' ';
            };
      
    }
    
    public void changePiece(int value, Piece piece)
    {
        this.pieceType = value;
        this.piece = piece;
    }
    
    public char getTileValue() //Return tile char representation
    {
            if(tileValue == 10) return '▭';
            return '▅';
    }
    
}

