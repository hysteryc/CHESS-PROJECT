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
            
            System.out.println(piece.validMoveWhite(board, newCoordinate));
            board.movePiece(oldCoordinate, newCoordinate);
            board.drawBoard();
        
        }
        return board;
    }
}
