/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author karlo
 */


public class Pawn extends Piece 
{
    public Pawn(boolean isWhite) 
    {
        super(isWhite);
    }

    // Get method for unicode symbol for Pawn
    @Override
    public String getSymbol() 
    {
        return isWhite ? "p" : "P";
    }
    
    // Gets pawn image file
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon pawnImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE PAWN.png" : "chessFiles/chess Pieces/BLACK PAWN.png");
        return pawnImage;
    }
    
    //Logic to generate legal moves for a pawn
    //Iterates through every type of move a pawn can make, adds move to legal moves if the move is possible
    
    @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        
        ArrayList<Coordinate> allMoves = new ArrayList<>();
        int direction = isWhite() ? 1 : -1;
        int startRow = origin.getRow();
        int startFile = origin.getFile();

        // One square forward (if empty)
        
       
        
        
        if (withinBounds(startFile, startRow + direction) && gui.squares[startFile][(direction + startRow)].getPiece() == null) 
            {
                allMoves.add(new Coordinate(startFile, (direction + startRow)));
            }
        
        // Two squares forward from the starting position (if empty and the pawn hasn't moved yet)
        
        
        if (withinBounds(startFile, startRow + direction) && (isWhite() && startRow == 1 || !isWhite() && startRow == 6) &&
           gui.squares[startFile][(startRow + direction)].getPiece() == null &&
           gui.squares[startFile][(startRow + direction * 2)].getPiece() == null)
        {
            allMoves.add(new Coordinate(startFile, (direction*2 + startRow)));
        }
        
        
         // Pawn capturing on right
        
        
        if(withinBounds(startFile+1, startRow + direction) && gui.squares[startFile+1][(startRow + direction)].getPiece() != null) 
        {
            if(gui.squares[startFile+1][(startRow + direction)].getPiece().isWhite() != isWhite)
            {
                allMoves.add(new Coordinate(startFile + 1, startRow + direction));
            }
        }
        
         // Pawn capturing on right
        
        if(withinBounds(startFile-1, startRow + direction) && gui.squares[startFile-1][(startRow + direction)].getPiece() != null) 
        {
            if(gui.squares[startFile-1][(startRow + direction)].getPiece().isWhite() != isWhite)
            {
                allMoves.add(new Coordinate(startFile - 1, startRow + direction));
            }
        }
        
         // Check move doesn't leave the king in check
        
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        
        for(Coordinate item: allMoves)
        {
            if (!flag) {
                        if (checkKingSafety(origin, item, gui)) {
                            legalMoves.add(item);
                        }
                    } else {
                        legalMoves.add(item);
                    }
        }

        return legalMoves;
    }
    
   
}

