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


public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }
    
    // Gets the unicode symbol associated with the King
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "k" : "K";  
    }
    
    //Gets image file for the king
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon kingImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE KING.png" : "chessFiles/chess Pieces/BLACK KING.png");
        return kingImage;
    }
    
    //Logic to generate legal moves for a King
    //Simulates every surrounding square and checks if a move is valid.  
    @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        int startRow = origin.getRow();
        int startFile = origin.getFile();
        int[][] kingMoves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} 
        };

        for (int[] move : kingMoves) 
        {
            int endRow = startRow + move[0];
            int endFile = startFile + move[1];
            if (endRow >= 0 && endRow <= 7 && endFile >= 0 && endFile <= 7) {
                Piece targetPiece = gui.squares[endFile][endRow].getPiece();
                
                Coordinate destination = new Coordinate(endFile, endRow);
                
                
                
                if (targetPiece == null || targetPiece.isWhite() != isWhite) 
                {
                    if(flag == false)
                    {
                        if(checkKingSafety(origin, destination, gui)) legalMoves.add(destination); 
                    }
                    else
                    {
                        legalMoves.add(destination);
                    }
                    
                } 
                
            }
        }
        return legalMoves;
    }
}

