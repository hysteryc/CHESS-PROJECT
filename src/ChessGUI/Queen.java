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

    @Override
    public String getSymbol() 
    {
        return isWhite() ? "♕" : "♛";  
    }
    
    @Override
    public ImageIcon getImage() 
    {
        ImageIcon queenImage = new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png");
        return queenImage;
    }
    
   @Override
    public ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag)
    {
        ArrayList<Coordinate> legalMoves = new ArrayList<>();
        legalMoves.addAll(new Rook(isWhite()).generateLegalMoves(origin, gui, flag));
        legalMoves.addAll(new Bishop(isWhite()).generateLegalMoves(origin, gui, flag));
        return legalMoves;
    }
   
}
