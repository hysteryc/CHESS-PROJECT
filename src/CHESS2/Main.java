package CHESS2;

import java.util.Scanner;

public class Main {
    
    //Main gameplay Loop
    public static void main(String[] args) 
    {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        FileIO.clearCapturedFiles(); 

        System.out.println("Welcome to Barebones Command Line Chess");
        
        System.out.println("Would you like to load a saved game? (yes/no): ");
        String loadInput = scanner.nextLine();
        if (loadInput.equalsIgnoreCase("yes")) 
        {
            FileIO.loadGame(board.getBoard(), board.getCapturedWhite(), board.getCapturedBlack());  
        } 
        else 
        {
            board.initializeBoard();  
        }

        while (true) 
        {
            System.out.println("");
            board.drawBoard();   
            
            if (board.isKingCaptured(true)) 
            {
                System.out.println("White's king has been captured. Black wins!");
                break;
            } 
            else if (board.isKingCaptured(false)) 
            {
                System.out.println("Black's king has been captured. White wins!");
                break;
            }

            System.out.println("Current turn: " + (board.whiteTurn ? "White" : "Black"));
            System.out.println("Enter your move (e.g., 'e2 e4'):");
            System.out.println("'capturedblack' to display Captured Black Pieces");
            System.out.println("'capturedwhite' to display Captured White Pieces");
            System.out.println("'save' to save the game");
            System.out.println("'quit' to exit the game");
            System.out.println("Enter Input: ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("quit")) 
            {
                System.out.println("Exiting the game.");
                break;
            }
            
            if (input.equalsIgnoreCase("capturedblack")) 
            {
                FileIO.printCapturedBlack();
                continue;
            } 
            else if(input.equalsIgnoreCase("capturedwhite")) 
            {
                FileIO.printCapturedWhite();
                continue;
            } 
            else if(input.equalsIgnoreCase("save")) 
            {
                FileIO.saveGame(board.getBoard(), board.isWhiteTurn(), board.getCapturedWhite(), board.getCapturedBlack());
                System.out.println("Game saved.");
                continue;
            }
            
            if (!input.matches("[a-h][1-8] [a-h][1-8]")) 
            {
                System.out.println("Invalid move format. Please use the format 'e2 e4'.");
                continue;
            }

            String[] move = input.split(" ");
            Coordinate from = parseCoordinate(move[0]);
            Coordinate to = parseCoordinate(move[1]);

            if (!board.movePiece(from, to)) 
            {
                System.out.println("Invalid move. Try again.");
                continue;
            }
        }

        scanner.close();
    }

    // parses the Coordinate from a chess terminology (a2) to position on the boards index
    private static Coordinate parseCoordinate(String notation) 
    {
        int row = 8 - Integer.parseInt(notation.substring(1));  
        int col = notation.charAt(0) - 'a';  
        return new Coordinate(row, col);
    }
}
