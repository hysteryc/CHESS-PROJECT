/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

/**
 *
 * @author teddy
 */
public class PieceCreator 
{
    // Creating the piece from its unicode Symbol (load game function)
    public static Piece createFromSymbol(String symbol) 
    {
        switch (symbol) 
        {
            case "♙": return new Pawn(true);
            case "♟": return new Pawn(false);
            case "♖": return new Rook(true);
            case "♜": return new Rook(false);
            case "♘": return new Knight(true);
            case "♞": return new Knight(false);
            case "♗": return new Bishop(true);
            case "♝": return new Bishop(false);
            case "♕": return new Queen(true);
            case "♛": return new Queen(false);
            case "♔": return new King(true);
            case "♚": return new King(false);
            default:
                System.out.println("Unrecognized piece symbol: " + symbol);
                return null;
        }
    }
}
