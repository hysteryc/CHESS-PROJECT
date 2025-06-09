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


public class Knight extends Piece 
{
    public Knight(boolean isWhite) 
    {
        super(isWhite);
    }
    
    //Gets the unicode symbol associated with the Knight
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "h" : "H";  
    }

    // Gets image file for a knight
   @Override
    public ImageIcon getImage() 
    {
        ImageIcon knightImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE KNIGHT.png" : "chessFiles/chess Pieces/BLACK KNIGHT.png");
        return knightImage;
    }
    //Logic to generate legal moves for a knight
    //Iterates through every possible knight move and adds the move if valid  
    @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
    
    
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        int startRow = origin.getRow();
        int startFile = origin.getFile();
        int[][] knightMoves = 
        {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) 
        {
            int endRow = startRow + move[0];
            int endFile = startFile + move[1];
            if (endRow >= 0 && endRow <= 7 && endFile >= 0 && endFile <= 7) 
            {
                Piece targetPiece = gui.squares[endFile][endRow].getPiece();
                
                Coordinate destination = new Coordinate(endFile, endRow);
                
                if(targetPiece == null || targetPiece.isWhite() != isWhite)
                {
                    if (!flag) {
                        if (checkKingSafety(origin, destination, gui)) {
                            legalMoves.add(destination);
                        }
                    } else {
                        legalMoves.add(destination);
                    }
                }
                
                
            }
        }

        return legalMoves;
    }
  
}

