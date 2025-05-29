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
        return isWhite ? "♙" : "♟";
    }
    
    // Checking whether Move is Valid based on pieces movement rules.
    
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon pawnImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE PAWN.png" : "chessFiles/chess Pieces/BLACK PAWN.png");
        return pawnImage;
    }
    
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
        
        
        if(withinBounds(startFile+1, startRow + direction) && gui.squares[startFile+1][(startRow + direction)].getPiece() != null) 
        {
            if(gui.squares[startFile+1][(startRow + direction)].getPiece().isWhite() != isWhite)
            {
                allMoves.add(new Coordinate(startFile + 1, startRow + direction));
            }
        }
        
        
        if(withinBounds(startFile-1, startRow + direction) && gui.squares[startFile-1][(startRow + direction)].getPiece() != null) 
        {
            if(gui.squares[startFile-1][(startRow + direction)].getPiece().isWhite() != isWhite)
            {
                allMoves.add(new Coordinate(startFile - 1, startRow + direction));
            }
        }
        
        
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

