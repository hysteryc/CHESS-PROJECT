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


public class Rook extends Piece 
{
    public Rook(boolean isWhite) 
    {
        super(isWhite);
    }
    
    // Unicode for Rook
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♖" : "♜"; 
    }
    
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon rookImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE ROOK.png" : "chessFiles/chess Pieces/BLACK ROOK.png");
        return rookImage;
    }
    
    @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        
        int row = origin.getRow();
        int file = origin.getFile();


        int[][] directions = 
        {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        for (int[] dir : directions) 
        {
            int newRow = row + dir[0];
            int newFile = file + dir[1];

            while (newRow >= 0 && newRow < 8 && newFile >= 0 && newFile < 8) 
            {
                Piece targetPiece = gui.squares[newFile][newRow].getPiece();
                Coordinate destination = new Coordinate(newFile, newRow);

                boolean isEnemyPiece = targetPiece != null && targetPiece.isWhite() != isWhite;

                
                if (targetPiece == null || isEnemyPiece) {
                    if (!flag) {
                        if (checkKingSafety(origin, destination, gui)) {
                            legalMoves.add(destination);
                        }
                    } else {
                        legalMoves.add(destination);
                    }

                    
                    if (isEnemyPiece) break;
                } else {
                    
                    break;
                }

                newRow += dir[0];
                newFile += dir[1];
            }
        }
        return legalMoves;
    }
   
}
