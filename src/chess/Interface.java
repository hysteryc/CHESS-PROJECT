/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */

import java.util.Scanner;

public class Interface {
    boolean whiteTurn = true;
    boolean possible = false;
    Scanner scanner = new Scanner(System.in);
    
    public Board run(Board board)
    {
        return run(board, true);
    }
    
    private Board run(Board board, boolean play)
    {
        
        while(play)
        {
            System.out.println("Select Piece:");
            String position = scanner.next();
            
            
                        
            System.out.println("Where do you want to move this piece?");
            String movement = scanner.next();
            

            Coordinate oldCoordinate = board.translateInput(position);
            Coordinate newCoordinate = board.translateInput(movement);
            //board.movePiece(position, movement);

            Piece piece = board.getPiece(oldCoordinate);
            Piece capture = null;
            
            
            if(whiteTurn)
            {
                possible = piece.validMoveWhite(board, newCoordinate, oldCoordinate);
                if(possible) capture = board.movePiece(oldCoordinate, newCoordinate);
                
                
            }
            else
            {
                    possible = piece.validMoveBlack(board, newCoordinate, oldCoordinate);
                    if(possible) capture = board.movePiece(oldCoordinate, newCoordinate);

                
            }
            
            capture = board.getPiece(newCoordinate);
            
            whiteTurn = !whiteTurn;
            
            if(capture != null)
            {
                //remove from play; 
            }
            
            board.drawBoard();
        
        }
        return board;
    }
}
