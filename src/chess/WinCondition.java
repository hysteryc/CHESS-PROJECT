/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.util.ArrayList;

/**
 *
 * @author teddy
 */
    public class WinCondition 
{
    //King CANNOT;
    //Move into check
    
    
    public boolean check(Board board, boolean iswhite)
    {
        ArrayList<Piece> side = iswhite ? board.black : board.white;
        
        Piece king = getKing(board, iswhite);
        Coordinate kingPosition = new Coordinate(king.file, king.row);
        
        for(Piece item : side)
        {
            Coordinate attackerPosition = new Coordinate(item.file, item.row);
            
            if(item instanceof Pawn pawn)
            {
                if(pawn.attacking(board, kingPosition, attackerPosition)) 
                {
                    getPath(board, item, king);
                    return true;
                }
            }
            else
            {
                if(item.validMove(board, kingPosition, attackerPosition)) 
                {
                    getPath(board, item, king);
                    return true;
            }
            }
        }
        
        return false;
    }
    
    public boolean checkmate(Board board, Piece attacker)
    {
        
        Piece king = getKing(board, attacker.pieceType < 0);
        
        
        ArrayList<Coordinate> paths = getPath(board, attacker, king);
        
        ArrayList<Piece> blockers = board.white;
        
        if(king.pieceType < 0) blockers = board.black;
        
        Coordinate kingCoordinate = new Coordinate(king.file, king.row);
                
        for(int fileDisplacement = -1; fileDisplacement <= 1; fileDisplacement++)
        {
            for(int rowDisplacement = -1; rowDisplacement <= 1; rowDisplacement++)
            {
                Coordinate kingEscapeSquare = new Coordinate(king.file + fileDisplacement, king.row + rowDisplacement); 
                
                if(!(fileDisplacement == 0 && rowDisplacement == 0) && king.withinBounds(kingEscapeSquare)) 
                {
                    
                    if(king.validMove(board, kingEscapeSquare, kingCoordinate)) 
                    {
                        
                        
                        System.out.println("Escape Square: ");
                        kingEscapeSquare.print();
                        
                        return false;

                    }
                }
            }
        }
        System.out.println("NO ESCAPE SQUARE");
        
        
        for(Coordinate blockCoordinate : paths)
        {
            System.out.print("---------CHECKING PATH---------");
            System.out.print("---------" + blockCoordinate.file + ", " + blockCoordinate.row + "---------");
            for(Piece piece : blockers)
            {
                
                Coordinate blockerPosition = new Coordinate(piece.file, piece.row);
                if(piece.validMove(board, blockCoordinate, blockerPosition)) 
                {
                    System.out.print(piece.pieceType + " Can Do something");
                    return false;
                }
                
            }
        }
        
        
        
        return true;
    }
    
    
    private Piece getKing(Board board, boolean iswhite)
    {
        ArrayList<Piece> side = iswhite ? board.white : board.black;
        
        for(Piece item : side)
        {
            if(Math.abs(item.pieceType) == 16) return item; 
        }
        
        return null;
    }
    
    private ArrayList<Coordinate> getPath(Board board, Piece attacker, Piece king) {
    ArrayList<Coordinate> path = new ArrayList<>();
    
    // Calculate direction vectors
    int fileStep = Integer.compare(king.file, attacker.file);
    int rowStep = Integer.compare(king.row, attacker.row);
    
    // Knight case (returns just attacker's position)
    if (Math.abs(attacker.pieceType) == 13) { // Assuming 13 is knight
        path.add(new Coordinate(attacker.file, attacker.row));
        return path;
    }
    
    // Generate path squares
    Coordinate current = new Coordinate(
        attacker.file + fileStep,
        attacker.row + rowStep
    );
    
    // For orthogonal/diagonal pieces (rook, bishop, queen)
    while (current.file != king.file || current.row != king.row) {
        // Add intermediate squares to path
        path.add(new Coordinate(current.file, current.row));
        
        // Move towards king
        current.file += fileStep;
        current.row += rowStep;
        
        // Safety check to prevent infinite loops
        if (!current.withinBounds()) break;
    }
    
    return path;
}
    
    
    //checkmate
    
    //check
//    public boolean inCheck(Board board, boolean isWhite)
//    {
//        Coordinate kingPosition = findKing(board, isWhite);
//        for(Square square : board.getSquares())
//        {
//            if(square.piece != null)
//            {
//                Piece attacker = square.piece;
//                if((isWhite && attacker.pieceType < 0) || (!isWhite && attacker.pieceType > 0))
//                {
//                    if(attacker.validMove(board, kingPosition, new Coordinate(square.row, square.file)))
//                    {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
    
    //checkmate
//    public boolean isCheckmate(Board board, boolean isWhite) 
//    {
//        if (!inCheck(board, isWhite)) 
//        {
//            return false;
//        }
//
//        for (int row = 1; row <= 8; row++) 
//        {
//            for (int file = 1; file <= 8; file++) 
//            {
//                Square square = board.getSquare(file, row);
//                Piece piece = square.piece;
//
//                if (piece != null && ((isWhite && piece.pieceType > 0) || (!isWhite && piece.pieceType < 0))) 
//                {
//                    Coordinate origin = new Coordinate(row, file);
//
//                    for (int destRow = 1; destRow <= 8; destRow++) 
//                    {
//                        for (int destFile = 1; destFile <= 8; destFile++) 
//                        {
//                            Coordinate destination = new Coordinate(destRow, destFile);
//
//                            if (piece.validMove(board, destination, origin)) 
//                            {
//                                Piece captured = board.getPiece(destination);
//                                board.movePiece(origin, destination);
//                                boolean stillInCheck = inCheck(board, isWhite);
//                                board.movePiece(destination, origin);
//                                if (captured != null) 
//                                {
//                                    board.placePiece(captured, destination);
//                                }
//                                if (!stillInCheck) 
//                                {
//                                return false;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return true; 
//    }
    
    // find king on board.
//    private Coordinate findKing(Board board, boolean isWhite)
//    {
//        for(int row = 1; row <= 8; row++)
//        {
//            for(int file = 1; file <= 8; file++)
//            {
//                Coordinate current = new Coordinate(row, file);
//                Piece piece = board.getPiece(current);
//                if(piece instanceof King && ((isWhite && piece.pieceType > 0) || (!isWhite && piece.pieceType < 0)))
//                {
//                    return current;
//                }
//            }
//        }
//        return null;
//    }
}
