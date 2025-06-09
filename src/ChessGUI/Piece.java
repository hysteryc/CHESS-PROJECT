/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author karlo
 */



/**
 *
 * @author teddy
 */

// Piece is a parent for all of the piece types and stores communal functions that are used by every piece
public abstract class Piece 
{
    protected boolean isWhite;
   
    private ImageIcon pieceImage;
    
    
    
    public Piece(boolean isWhite) 
    {
        this.isWhite = isWhite;
    }
    
    public boolean isWhite() 
    {
        return isWhite;
    }

    public boolean isOpponent(Piece other) 
    {
        return other != null && this.isWhite != other.isWhite();
    }
    
    
    //Method used in EVERY generateLegalMoves function 
    //Temporarily makes a move, checks if the king is in check after the move is made
    //Undoes the move and returns whether the king was in check by any of the opponent moves
    public boolean checkKingSafety(Coordinate origin, Coordinate destination, GUI gui)
    {
                
        Square victim = gui.tempMove(origin, destination);
        
        boolean isWhite = gui.squares[destination.getFile()][destination.getRow()].getPiece().isWhite();
        
        Square kingPosition = gui.getKing(isWhite);
        
        Coordinate kingCoordinate = new Coordinate(kingPosition.getFile(), kingPosition.getRow());
    
        ArrayList<Square> enemyList = (!kingPosition.getPiece().isWhite() ? gui.whitePieces : gui.blackPieces);
    
        for(Square enemySquare : enemyList) {
        if (enemySquare.getPiece() == null) continue;
        
        Piece enemyPiece = enemySquare.getPiece();
        Coordinate enemyCoordinate = new Coordinate(enemySquare.getFile(), enemySquare.getRow());            
        
        ArrayList<Coordinate> enemyMoves = enemyPiece.generateLegalMoves(enemyCoordinate, gui, true);
        
        for(Coordinate item : enemyMoves) {
            if(item.equals(kingCoordinate)) {
                gui.undoTempMove(origin, destination, victim);
                return false;
            }
        }
        
        
       
        }
        
        gui.undoTempMove(origin, destination, victim);
        return true;
    }
    
    //Method each piece uses in move logic to check that a move is within the bounds of the board;
    public boolean withinBounds(int file, int row)
    {
        return (file >= 0 && file <= 7) && (row >= 0 && row <= 7);
    }
    
    // Each piece will return their own image file 
    public abstract ImageIcon getImage();
    

    // Each piece will return its own Unicode symbol
    public abstract String getSymbol();

    // Validate movement logic (based on board state) and piece movement rules
    // gui and flag are required to pass through because of the king safety 
    // gui is required for making and undoing temporary moves
    // flag is used to prevent infinite loops 
    // eg. generateLegalMoves > checkKingSafety (runs generateLegalMoves for enemyPieces) > generateLegalMoves...
    public abstract ArrayList<Coordinate> generateLegalMoves(Coordinate origin, GUI gui, boolean flag);
}