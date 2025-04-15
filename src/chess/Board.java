/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */
import java.util.ArrayList;

/**
 *
 * @author karlo
 */


/*

    Pieces are initialised as numbers  

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

public class Board {
    
    ArrayList<Square> board = new ArrayList<>();
    
    ArrayList<Piece> white = new ArrayList<>(); //When we do pieces
    ArrayList<Piece> black = new ArrayList<>();
    
    
    
    
     public Board() //Every position is assigned a tile colour (tileValue) and (if applicable) a piece value
    {
        boolean colour = false; 
        for(int row = 1; row <= 8; row++)
        {
            colour = !colour;
            for(int file = 1; file <= 8; file++)
            {
                Square square = new Square(file, row);
                
                if(colour) //colour assigning
                {
                    square.tileValue = 10;
                    colour = !colour;
                }
                else
                {
                    square.tileValue = 20;
                    colour = !colour;
                }   
                
                
                
                if(row == 7)  //piece assigning
                {
                    square.piece = 11;
                }
                else if(row == 2)
                {
                    square.piece = 21;
                }
                else if(row == 1 && file == 1 || row == 1 && file == 8)
                {
                    square.piece = 22;
                }
                else if(row == 1 && file == 2 || row == 1 && file == 7)
                {
                    square.piece = 23;
                }
                else if(row == 1 && file == 3 || row == 1 && file == 6)
                {
                    square.piece = 24;
                }
                else if(row == 1 && file == 4)
                {
                    square.piece = 25;
                }
                else if(row == 1 && file == 5)
                {
                    square.piece = 26;
                }
                else if(row == 8 && file == 1 || row == 8 && file == 8)
                {
                    square.piece = 12;
                }
                else if(row == 8 && file == 2 || row == 8 && file == 7)
                {
                    square.piece = 13;
                }
                else if(row == 8 && file == 3 || row == 8 && file == 6)
                {
                    square.piece = 14;
                }
                else if(row == 8 && file == 4)
                {
                    square.piece = 15;
                }
                else if(row == 8 && file == 5)
                {
                    square.piece = 16;
                }
                
                board.add(square);
                }
        }
    }
    
    public ArrayList<Square> getBoard() 
    {
        return board;
    }
    
    public void drawBoard() //turns the collection of numbers into physical representation
    {
        int row = 1;
        for(Square item : board)
        {
            
            
            if(row != item.row)
            {
                System.out.println();
                System.out.println();
                row++;
            }
            
            System.out.print(' ');
            System.out.print(' ');
            System.out.print(' ');
            
            if(item.piece == 0)
            {
                System.out.print(item.getTileValue());
            }
            else
            {
                System.out.print(item.getCharValue());
            }
            
        }
        System.out.println();
    }
}

