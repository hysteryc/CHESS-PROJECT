/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teddy
 */
public class Pawn extends Piece
{
    public Pawn(int row, int file, boolean isWhite)
    {
        super(row, file, isWhite);
        this.material = 1;
        this.pieceType = 1;
    }
    
    @Override 
    public char getSymbol()
    {
        return isWhite ? '♙' : '♟';
    }
    
    //Displays all the legal moves a pawn can achieve at its current position...
    @Override 
    public List<coordinates> getLegalMoves(Board board)
    {
        List<coordinates> possibleMoves = new ArrayList<>();
        int direction =  isWhite ? -1 : 1;
        
        //Moving foward one space on the board
        int newRow = row + direction;
        if(board.isEmptySquare(file, newRow))
        {
            possibleMoves.add(new coordinates(file, newRow));
            
            if((isWhite && row == 2) || (!isWhite && row == 7))
            {
                int twoSpaces = row + 2 * direction;
                if(board.isEmptySquare(file, twoSpaces))
                {
                    possibleMoves.add(new coordinates(file, twoSpaces));
                }
            }
        }
        
        //capture piece to the left of the pawn.
        if(file > 1)
        {
            int leftCol = file - 1;
            int leftRow = row + direction;
            Piece pieceOnLeft = board.getPieceAt(leftCol, leftRow);
            if (pieceOnLeft != null && pieceOnLeft.isWhite() != isWhite) 
            { 
                possibleMoves.add(new coordinates(leftCol, leftRow));
            } 
        }
        
        //capture piece to the right of pawn.
        if (file < 8) 
        {
            int rightCol = file + 1;
            int rightRow = row + direction;
            Piece pieceOnRight = board.getPieceAt(rightCol, rightRow);
            if (pieceOnRight != null && pieceOnRight.isWhite() != isWhite) 
            {
                possibleMoves.add(new coordinates(rightCol, rightRow));
            }
        }   
        return possibleMoves;
    }
}
