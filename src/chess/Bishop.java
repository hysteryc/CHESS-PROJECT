/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author mymac
 */
public class Bishop extends Piece
{
    
    public Bishop(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 3;
    }
    
    private boolean pathClear(Board board, Coordinate destination, Coordinate origin, Coordinate movement)
    {
        System.out.println("Current: " + origin.file + ", " + origin.row);
        System.out.println("Destination: " + destination.file + ", " + destination.row);
        
        //if(pieceType) > 0;
        
        
        if((origin.file == destination.file) && (origin.row == destination.row)) return true;
       
        Coordinate nextSquare = new Coordinate(origin.file + movement.file, origin.row + movement.row);
        int piece = board.checkSquare(nextSquare);
        System.out.println("piece: " + piece);
        
       
        if((pieceType > 0 && piece < 0) && ((nextSquare.file == destination.file) && (nextSquare.row == destination.row))) return true;
        if((pieceType < 0 && piece > 0) && ((nextSquare.file == destination.file) && (nextSquare.row == destination.row))) return true;
        
        if(piece == 0) 
        {
            
            return pathClear(board, destination, nextSquare, movement);
        }
            
        return false;
        
        
    }
    
    public boolean validMoveWhite(Board board, Coordinate destination, Coordinate origin)
    {
        
        if (!withinBounds(destination))  return false;


        if (origin.row == destination.row && origin.file == destination.file) return false;


        int deltaFile = destination.file - origin.file;
        int deltaRow = destination.row - origin.row;

        System.out.println("deltaFile:" + deltaFile);
        System.out.println("deltaRow: " + deltaRow);
        
        if(Math.abs(deltaRow) != Math.abs(deltaFile)) 
        {
            System.out.println("Bishops need to be same displacement");
            return false; //Bishop should be the same displacement
        }
        int movement_file = 1;
        int movement_row = 1;
        
                
        if(deltaFile < 0) movement_file = -1;
        
        if(movement_row < 0) movement_row = -1;
                
        Coordinate movement = new Coordinate(movement_file, movement_row);
        
        
        return pathClear(board, destination, origin, movement);
        
    }

        

    
}
