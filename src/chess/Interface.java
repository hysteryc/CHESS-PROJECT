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
    
    private void promotion(Board board, Coordinate coordinate) {

    int choice = 0;
    boolean validInput = false;
    
    System.out.println("\nPROMOTION MENU");
    System.out.println("1 - Queen");
    System.out.println("2 - Rook");
    System.out.println("3 - Knight");
    System.out.println("4 - Bishop");
    
    while (!validInput) {
        try {
            System.out.print("Choose promotion (1-4): ");
            String input = scanner.next().trim();
            
            
            if (!input.matches("[1-4]")) {
                throw new IllegalArgumentException("Please enter a number between 1 and 4");
            }
            
            choice = Integer.parseInt(input);
            validInput = true;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            scanner.nextLine(); // Clear the buffer
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            scanner.nextLine();
        }
    }
    board.promotion(choice, coordinate);
    
   
    
}
    
    
    private Board run(Board board, boolean play)
    {
    
        while(play) {
            try {
                // Display board and turn info
                board.drawBoard();
                System.out.println("\n" + (whiteTurn ? "WHITE" : "BLACK") + "'S TURN");

                // Get piece position
                System.out.print("Select Piece: ");
                String position = scanner.next().trim();

                // Get move target
                System.out.print("Move to: ");
                String movement = scanner.next().trim();

                // Validate input format first
                if (!position.matches("[a-h][1-8]") || !movement.matches("[a-h][1-8]"))
                {
                    System.out.println("Invalid input! Use chess notation like 'e2'");
                    continue;
                }

                // Convert to coordinates
                Coordinate oldCoordinate = board.translateInput(position);
                Coordinate newCoordinate = board.translateInput(movement);

                // Verify piece exists and belongs to current player
                Piece piece = board.getPiece(oldCoordinate);
                if (piece == null) 
                {
                    System.out.println("No piece at " + position);
                    continue;
                }

                if ((whiteTurn && piece.pieceType < 0) || (!whiteTurn && piece.pieceType > 0)) 
                {
                    System.out.println("That's not your piece!");
                    continue;
                }

                
                if (!piece.validMove(board, newCoordinate, oldCoordinate))
                {
                    System.out.println("Invalid move for " + piece.getClass().getSimpleName());
                    continue;
                }

                
                Piece capture = board.movePiece(oldCoordinate, newCoordinate);
                if((newCoordinate.row == 8 || newCoordinate.row == 1) && piece instanceof Pawn) promotion(board, newCoordinate);
                whiteTurn = !whiteTurn;

                if (capture != null) System.out.println("Captured " + capture.getClass().getSimpleName() + "!");
                

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            } catch (NullPointerException e) {
                System.out.println("Error: Invalid board position");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
                scanner.nextLine();
            }
        }
        scanner.close();
    return board;
    }
}