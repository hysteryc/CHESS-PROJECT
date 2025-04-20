
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;
/**
 *
 * @author mymac
 */
public class Queen extends Piece
{
    
    public Queen(int row, int file, int pieceType)
    {
        super(row, file, pieceType);
        this.material = 9;
    }
    
    private boolean pathClear(Board board, Coordinate destination, Coordinate origin, Coordinate movement)
    {   
        System.out.println("destination: " +  destination.file + ", " + destination.row);
        
        
        if((origin.file == destination.file) && (origin.row == destination.row)) return true;
       
        Coordinate nextSquare = new Coordinate(origin.file + movement.file, origin.row + movement.row);
        int piece = board.checkSquare(nextSquare);
        
        System.out.println("nextSquare: " +  nextSquare.file + ", " + nextSquare.row);
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
        
        
        if((deltaFile != 0 && deltaRow != 0) && (Math.abs(deltaRow) != Math.abs(deltaFile)))
        {
            System.out.println("Need to be on same plain");
            return false; 
        }
               
        int movementFile = 0;
        int movementRow = 0;
        
                
        if(deltaFile < 0) movementFile = -1;
        else movementFile = 1;
        
        if(deltaRow < 0) movementRow = -1;
        else movementRow = 1;
        
        if(deltaFile == 0) movementFile = 0;
        if(deltaRow == 0) movementRow = 0;
        
        System.out.println("Movement: " + movementFile + ", " + movementRow);
        
        
        Coordinate movement = new Coordinate(movementFile, movementRow);
        
        
        return pathClear(board, destination, origin, movement);
        
    }

        

    
}

