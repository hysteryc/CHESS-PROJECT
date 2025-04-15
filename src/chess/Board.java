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
    
    private coordinates translate_input(String input) // Translates a string input to int coordinates
    {
        int x = Integer.valueOf(Character.toLowerCase(input.charAt(0))) - 96;
        
        int y = Integer.valueOf(String.valueOf(input.charAt(1)));
              
        coordinates pair = new coordinates(x, y);
        
        return pair;
    }
    
    private int getSquareIndex(int x, int y) //Mathematical sequence to determine the index of a square when given coordinates
    {
        return Math.abs(y-8)*8 + x-1;
    }
    
    public void movePiece(String position, String movement) 
    {
     
        coordinates old_coordinates = translate_input(position);
        coordinates new_coordinates = translate_input(movement);
        
        System.out.println(old_coordinates.x + ", " + old_coordinates.y);
        System.out.println(new_coordinates.x + ", " + new_coordinates.y);
        
        int old_index = getSquareIndex(old_coordinates.x, old_coordinates.y);
        int new_index = getSquareIndex(new_coordinates.x, new_coordinates.y);
          
        System.out.println(old_index);
        System.out.println(new_index);
        
        for(Square item : board)
        {
            System.out.println(board.indexOf(item));
        }

        int piece = board.get(old_index).piece;
        
        
        board.get(old_index).changePiece(0);
        board.get(new_index).changePiece(piece);

        
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
           
            if(item.file == 1)
            {
                System.out.print(Math.abs(row-9));
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
        
        System.out.println("\n    a    b    c   d    e    f    g    h");
        
       
    }
}

