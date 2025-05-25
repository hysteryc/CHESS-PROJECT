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
    boolean check;
    Piece lastMoved = null;
            
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
        WinCondition win = new WinCondition();
        while(play) {
            try {
                // Display board and turn info
                
                
                
                board.printPieces();
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
                
                if(check) 
                {
                    
                    
                    board.movePiece(oldCoordinate, newCoordinate);
                    
                    check = win.check(board, whiteTurn);
                    
                    if(check) 
                    {
                        System.out.println("You are in check");
                        board.movePiece(newCoordinate, oldCoordinate);
                        continue;
                    }
                    else 
                    {      
                        board.movePiece(newCoordinate, oldCoordinate);
                    }
                }

                
                if (!piece.validMove(board, newCoordinate, oldCoordinate))
                {
                    System.out.println("Invalid move for " + piece.getClass().getSimpleName());
                    continue;
                }
                
                Piece capture = board.movePiece(oldCoordinate, newCoordinate);
                
                lastMoved = piece;
                        
                if((newCoordinate.row == 8 || newCoordinate.row == 1) && piece instanceof Pawn) promotion(board, newCoordinate);
                whiteTurn = !whiteTurn;
                
                check = win.check(board, whiteTurn);
                
                if(check)
                {
                    if(win.checkmate(board, lastMoved)) break;
                }
                
                System.out.println((check ? "YOU ARE IN CHECK" : "YOU ARE NOT IN CHECK"));
                
//                if (win.inCheck(board, whiteTurn)) 
//                {
//                    if (win.isCheckmate(board, whiteTurn)) 
//                    {
//                        board.drawBoard();
//                        System.out.println((whiteTurn ? "WHITE" : "BLACK") + " is in CHECKMATE!");
//                        System.out.println((whiteTurn ? "BLACK" : "WHITE") + " WINS!");
//                        break; 
//                    } 
//                    else 
//                    {
//                        System.out.println((whiteTurn ? "WHITE" : "BLACK") + " is in CHECK!");
//                    }
//                }
                
                if (capture != null) System.out.println("Captured " + capture.getClass().getSimpleName() + "!");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); 
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
        System.out.println("CHECKMATE CHECKMATE CHECKMATE");
    return board;
    }
}