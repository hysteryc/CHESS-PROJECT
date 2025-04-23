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

public class Board 
{
    
    ArrayList<Square> board = new ArrayList<>();
    ArrayList<Piece> white = new ArrayList<>();
    ArrayList<Piece> black = new ArrayList<>();
    WinCondition winner = new WinCondition();
            
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
                    black.add(square.piece); 
                    
                }
                else if(row == 2)
                {
                    square.pieceType = 11;
                    square.piece = new Pawn(row, file, 11);
                    white.add(square.piece); 
                    
                }
                else if(row == 1 && file == 1 || row == 1 && file == 8)
                {

                    square.pieceType = 12;
                    square.piece = new Rook(row, file, 12);
                    white.add(square.piece); 
                   
                }
                else if(row == 1 && file == 2 || row == 1 && file == 7)
                {

                    square.pieceType = 13;
                    square.piece = new Knight(row, file, 13);
                    white.add(square.piece); 
                }
                else if(row == 1 && file == 3 || row == 1 && file == 6)
                {
                    square.pieceType = 14;
                    square.piece = new Bishop(row, file, 14);
                    white.add(square.piece); 
                  
                }
                else if(row == 1 && file == 4)
                {

                    square.pieceType = 15;
                    square.piece = new Queen(row, file, 15);
                    white.add(square.piece); 
                }
                else if(row == 1 && file == 5)
                {
                    square.piece = new King(row, file, 16);
                    square.pieceType = 16;
                    white.add(square.piece); 

                }
                else if(row == 8 && file == 1 || row == 8 && file == 8)
                {

                    square.pieceType = -12;
                    square.piece = new Rook(row, file, -12);
                    black.add(square.piece);
                }
                else if(row == 8 && file == 2 || row == 8 && file == 7)
                {
                    square.pieceType = -13;
                    square.piece = new Knight(row, file, -13);
                    black.add(square.piece);

                }
                else if(row == 8 && file == 3 || row == 8 && file == 6)
                {

                    square.pieceType = -14;
                    square.piece = new Bishop(row, file, -14);
                    black.add(square.piece);

                }
                else if(row == 8 && file == 4)
                {

                    square.pieceType = -15;
                    square.piece = new Queen(row, file, -15);
                    black.add(square.piece);
                }
                else if(row == 8 && file == 5)
                {
                    square.pieceType = -16;
                    square.piece = new King(row, file, -16);
                    black.add(square.piece);
                }
                
                board.add(square);
                System.out.println("Index: " + index +", File: " + file + ", Row: " + row + ", Piece: " + square.piece);
                index++;
                }
        }
    }
    
    // Could use this or Maybe when we update to GUI use an iterator (iterable)
    public ArrayList<Square> getSquares() 
    {
        return board;
    }
    
    public void printPieces() 
    {
        System.out.println("WHITE:");
        for(Piece item : white)
        {
            System.out.println("Piece type: " + item.pieceType + " | Material: "+ item.material + " | Position: " + item.file + ", " + item.row);
        }
        
        System.out.println("\n\nBlack:");
        for(Piece item : black)
        {
            System.out.println("Piece type: " + item.pieceType + " | Material: "+ item.material + " | Position: " + item.file + ", " + item.row);
        }
    }
    
    public Coordinate translateInput(String input) 
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
    
    public void promotion(int promotionType, Coordinate coordinate)
    {
        int index = getSquareIndex(coordinate.file, coordinate.row);
        Square square = board.get(index);
        
        int colourFlipper = 1;
        if(square.pieceType < 0) colourFlipper = -1;
        
        
        switch (promotionType) {
        case 1 -> {
            square.pieceType = 15*colourFlipper;
            square.piece = new Queen(coordinate.file, coordinate.row, square.pieceType);
            }
        case 2 -> {
            square.pieceType = 12*colourFlipper;
            square.piece = new Rook(coordinate.file, coordinate.row, square.pieceType);
            }
        case 3 -> {
            square.pieceType = 13*colourFlipper;
            square.piece = new Knight(coordinate.file, coordinate.row, square.pieceType);
            }
        case 4 -> {
            square.pieceType = 14*colourFlipper;
            square.piece = new Bishop(coordinate.file, coordinate.row, square.pieceType);
            }
        default -> // This should never happen due to input validation
            System.out.println("Invalid promotion choice");
    }
        // Handle rook promotion
        // Handle knight promotion
        // Handle bishop promotion
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
    
    public Square getSquare(int file, int row) 
    {
        return board.get(getSquareIndex(file, row));
    }
    
    public void placePiece(Piece piece, Coordinate coord) 
    {
        getSquare(coord.file, coord.row).piece = piece;
    }

    public Piece movePiece(Coordinate oldCoord, Coordinate newCoord) {
    // Validate indices first
    int oldIndex = getSquareIndex(oldCoord.file, oldCoord.row);
    int newIndex = getSquareIndex(newCoord.file, newCoord.row);
    
    // Store move state for potential undo
    Piece movingPiece = board.get(oldIndex).piece;
    Piece victim = board.get(newIndex).piece;
    
    // Execute move
    if (victim != null) {
        removePiece(victim);  // Remove victim from active pieces list
    }
    board.get(oldIndex).changePiece(0, null);
    board.get(newIndex).changePiece(movingPiece.pieceType, movingPiece);
    
    updatePiece(oldCoord, newCoord, movingPiece.pieceType > 0);
    
    return victim;
}

public void undoMovePiece(Coordinate prevCoord, Coordinate currCoord, Piece victim) {
    int prevIndex = getSquareIndex(prevCoord.file, prevCoord.row);
    int currIndex = getSquareIndex(currCoord.file, currCoord.row);
    
    Piece movingPiece = board.get(currIndex).piece;
    
    // Restore original state
    board.get(prevIndex).changePiece(movingPiece.pieceType, movingPiece);
    
    if (victim != null) {
        board.get(currIndex).changePiece(victim.pieceType, victim);
        (victim.pieceType > 0 ? white : black).add(victim);  // Revive victim
    } else {
        board.get(currIndex).changePiece(0, null);
    }
    
    updatePiece(currCoord, prevCoord, movingPiece.pieceType > 0);
}

    
    
    public void removePiece(Piece piece) {
   
    ArrayList<Piece> list = piece.pieceType > 0 ? white : black;
    if (list.remove(piece)) {
        System.out.println("REMOVED: " + piece.pieceType);
    }

    
    }
    
    public void updatePiece(Coordinate pieceCoordinate, Coordinate newCoordinate, boolean isWhite)
    {
        for(Piece item : (isWhite ? white : black))
        {
        if(pieceCoordinate.file == item.file && pieceCoordinate.row == item.row) 
        {
            item.file = newCoordinate.file;
            item.row = newCoordinate.row;
            System.out.println("UPDATED");
        }
        }
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