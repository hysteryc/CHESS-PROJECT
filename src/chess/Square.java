/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */
public class Square {
    
    int row;
    int file;
    int tileValue;
    int piece;
            
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
    
    public Square(int file, int row)
    { 
        this.file = file;
        this.row = row;
    }
    
    public char getCharValue() //Return piece char representation
    {
            return switch (piece) {
                
                case 11 -> '♙';
                case 12 -> '♖';
                case 13 -> '♘';
                case 14 -> '♗';    
                case 15 -> '♕';
                case 16 -> '♔';
                
                case 21 -> '♟';
                case 22 -> '♜';
                case 23 -> '♞';
                case 24 -> '♝';
                case 25 -> '♛';
                case 26 -> '♚';
                default -> ' ';
            };
      
    }
    
    public char getTileValue() //Return tile char representation
    {
            if(tileValue == 10) return '▭';
            return '▅';
    }
    
    
    public int fileToInt(char file) // Will use later for user inputs
    {
        return switch (file) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            default -> 0;
        };
        
    }
}

