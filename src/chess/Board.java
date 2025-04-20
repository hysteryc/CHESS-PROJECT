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
    
    -11 = black pawn
    -12 = black rook
    -13 = black knight
    -14 = black bishop
    -15 = black queen
    -16 = black king
    */

public class Board {
    
    ArrayList<Square> board = new ArrayList<>();
    ArrayList<Piece> white = new ArrayList<>();

    
     public Board() //Every position is assigned a tile colour (tileValue) and (if applicable) a piece value
    {
        int index = 0;
        boolean colour = false; 
        for(int row = 1; row <= 8; row++)
        {
            colour = !colour;
            for(int file = 1; file <= 8; file++)
            {
                Square square = new Square(file, row, null);
                
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
                    square.pieceType = -11;
                    square.piece = new Pawn(row, file, -11);   
                    
                }
                else if(row == 2)
                {
                    square.pieceType = 11;
                    square.piece = new Pawn(row, file, 11);
                    
                }
                else if(row == 1 && file == 1 || row == 1 && file == 8)
                {

                    square.pieceType = 12;
                   
                }
                else if(row == 1 && file == 2 || row == 1 && file == 7)
                {

                    square.pieceType = 13;
                    
                }
                else if(row == 1 && file == 3 || row == 1 && file == 6)
                {
                    square.pieceType = 14;
                    square.piece = new Bishop(row, file, 14);
                  
                }
                else if(row == 1 && file == 4)
                {

                    square.pieceType = 15;

                }
                else if(row == 1 && file == 5)
                {

                    square.pieceType = 16;

                }
                else if(row == 8 && file == 1 || row == 8 && file == 8)
                {

                    square.pieceType = -12;
                }
                else if(row == 8 && file == 2 || row == 8 && file == 7)
                {
                    square.pieceType = -13;

                }
                else if(row == 8 && file == 3 || row == 8 && file == 6)
                {

                    square.pieceType = -14;
                    square.piece = new Bishop(row, file, -14);

                }
                else if(row == 8 && file == 4)
                {

                    square.pieceType = -15;
                }
                else if(row == 8 && file == 5)
                {
                    square.pieceType = -16;
                }
                
                board.add(square);
                System.out.println("Index: " + index +", File: " + file + ", Row: " + row + ", Piece: " + square.piece);
                index++;
                }
        }
    }
    
    public ArrayList<Square> getBoard() 
    {
        return board;
    }
    
    public Coordinate translateInput(String input) // Translates a string input to int coordinates
    {
        int file = Integer.valueOf(Character.toLowerCase(input.charAt(0))) - 96;
        
        int row = Integer.parseInt(String.valueOf(input.charAt(1)));
        
        
        
        Coordinate pair = new Coordinate(file, row);
        
        
        
        return pair;
    }
    
    public int checkSquare(Coordinate destination)
    {   
        int index = getSquareIndex(destination.file, destination.row);
        Square square = board.get(index);
        

        return square.pieceType;

    }
    
    
    
    private int getSquareIndex(int file, int row) //Mathematical sequence to determine the index of a square when given coordinates
    {
        //x, y
        //1, 1 = 1
        //2, 1 = 2
        //1, 2 = 9
        //1, 3 = 16
        return ((row*8)-8)+(file-1);
    }
    
    public Piece getPiece(Coordinate coordinate)
    {
        int index = getSquareIndex(coordinate.file, coordinate.row);
        Square square = board.get(index);
        return square.piece;
                
    }
    
    public Piece enPassant(Coordinate old_coordinates, Coordinate new_coordinates) 
    {
        
                
        int old_index = getSquareIndex(old_coordinates.file, old_coordinates.row);
        int new_index = getSquareIndex(new_coordinates.file, new_coordinates.row);
       
                
        int pieceType = board.get(old_index).pieceType;
        Piece piece = board.get(old_index).piece;
        
        Piece victim = board.get(new_index).piece;

                
        boolean white = pieceType > 0;
        
        int direction = 1;
        if(!white) direction = -1;

        
        int victimIndex = getSquareIndex(new_coordinates.file, new_coordinates.row + direction);
        
        board.get(old_index).changePiece(0, null);
        board.get(victimIndex).changePiece(0, null);
        board.get(new_index).changePiece(pieceType, piece);
        
        return victim;
 
    }

    public Piece movePiece(Coordinate old_coordinates, Coordinate new_coordinates) 
    {
        
                
        int old_index = getSquareIndex(old_coordinates.file, old_coordinates.row);
        int new_index = getSquareIndex(new_coordinates.file, new_coordinates.row);
          


        int pieceType = board.get(old_index).pieceType;
        Piece piece = board.get(old_index).piece;
        Piece victim = board.get(new_index).piece;
        
        board.get(old_index).changePiece(0, null);
        board.get(new_index).changePiece(pieceType, piece);
        
        return victim;
       
        
    }
    
    
    public void drawBoard() //turns the collection of numbers into physical representation
    {
        System.out.println("     |     |     |     |     |     |     |     |     |");
        System.out.println("     |  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |");
        System.out.println("     |     |     |     |     |     |     |     |     |");
        System.out.println("-----+-----+-----+-----+-----+-----+-----+-----+-----+-----");
        System.out.println("     |     |     |     |     |     |     |     |     |");
         
        for(int i = 8; i >= 1; i--)
        {
            
            
            System.out.print("  ");
            System.out.print(i);
            
           
            for(int ii = 1; ii <= 8; ii++)
            {
                Square square = board.get(((i*8)-8)+(ii-1));
                System.out.print("  |  ");
                
               
                if(square.pieceType == 0)
                {
                    System.out.print(square.getTileValue());
                }
                else
                {
                    System.out.print(square.getCharValue());
                }
            }
            System.out.print("  |  " + i);
            System.out.println();
            System.out.println("     |     |     |     |     |     |     |     |     |");
            System.out.println("-----+-----+-----+-----+-----+-----+-----+-----+-----+-----");
            System.out.println("     |     |     |     |     |     |     |     |     |");
            
        }
            System.out.println("     |  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |");
            System.out.println("     |     |     |     |     |     |     |     |     |");
    }
}