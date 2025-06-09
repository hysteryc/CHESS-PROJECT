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



public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
        
    }
    
    // Gets the unicode symbol associated with the Bishop
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "b" : "B"; 
    }
    
    // Gets the imagePath associated with the Bishop
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon bishopImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE BISHOP.png" : "chessFiles/chess Pieces/BLACK BISHOP.png");
        return bishopImage;
    }
   
    //Logic to generate legal moves for a bishop
    //Iterates through every diagonal square until reaching the edge of the board or another piece.  
    @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        int startRow = origin.getRow();
        int startFile = origin.getFile();
        int[][] directions = 
        {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) 
        {
            int row = startRow;
            int file = startFile;
            while (true) 
            {
                row += direction[0];
                file += direction[1];
                
                
                
                
                if (row >= 0 && row <= 7 && file >= 0 && file <= 7) 
                {
                    Piece targetPiece = gui.squares[file][row].getPiece();
                    boolean isEnemyPiece = targetPiece != null && targetPiece.isWhite() != isWhite;
                    
                    Coordinate destination = new Coordinate(file, row);
                    
                    
                    if(targetPiece == null || isEnemyPiece) 
                    {
                        if (!flag) 
                        {
                            if (checkKingSafety(origin, destination, gui)) 
                            {
                                legalMoves.add(destination);
                            }
                        } 
                        else 
                        {
                            legalMoves.add(destination);
                        }
                        
                        if(isEnemyPiece) break;
                    }
                    else break;
                    
                     
                } 
                else break;  
                
            }
        }
        return legalMoves;
    }
    
}
