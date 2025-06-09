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


public class Queen extends Piece 
{
    public Queen(boolean isWhite) 
    {
        super(isWhite);
    }
    
    //Gets unicode symbol for queen
    
    @Override
    public String getSymbol() 
    {
        return isWhite() ? "q" : "Q";  
    }
    
    //Gets image file path for queen
    
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon queenImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png");
        return queenImage;
    }
    
    //Logic to generate legal moves for a queen
    //Uses a combination of the rook and bishop moves to generate the valid moves list 
    
   @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        legalMoves.addAll(new Rook(isWhite()).generateLegalMoves(origin, gui, flag));
        legalMoves.addAll(new Bishop(isWhite()).generateLegalMoves(origin, gui, flag));
        return legalMoves;
    }
   
}
