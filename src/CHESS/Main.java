package CHESS;





import java.util.Scanner;

public class Main 
    {
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);
    
    //Main gameplay Loop
    public static void main(String[] args) 
    {
        Piece lastMoved = null;
        Board board = new Board();
        FileIO fileio = new FileIO();
        Scanner scanner = new Scanner(System.in);
        
        FileIO.clearCapturedFiles(); 

        System.out.println("Welcome to Barebones Command Line Chess");
        
        System.out.println("Would you like to load a saved game? (yes/no): ");
        String loadInput = scanner.nextLine();
        if (loadInput.equalsIgnoreCase("yes")) 
        {
            Database.Load(board, board.getCapturedWhite(), board.getCapturedBlack()); 
            System.out.println();
        } 
        else 
        {
            board.initializeBoard();
        }

        while (true) 
        {
            
            System.out.println("");
            
            board.drawBoard();   
            
            if(board.check())
            {
                if(lastMoved != null) 
                {
                    
                    if(board.checkmate(lastMoved)) 
                    {
                        System.out.println("\n\n\n");
                        board.drawBoard();
                        System.out.println("\n=== NO BLOCKS OR ESCAPES FOUND - CHECKMATE ===");
                        break;
                    }
                }
            }

            System.out.println("\nCurrent turn: " + (board.whiteTurn ? "White" : "Black"));
            System.out.println("Enter your move (e.g., 'e2 e4'):");
            System.out.println("'capturedblack' to display Captured Black Pieces");
            System.out.println("'capturedwhite' to display Captured White Pieces");
            System.out.println("'save' to save the game");
            System.out.println("'exit' to exit the game");
            System.out.println("\nEnter Input: ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) 
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
                Database.Save(board, board.isWhiteTurn(), board.getCapturedWhite(), board.getCapturedBlack());
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
            
            lastMoved = board.getPieceAt(to.getRow(), to.getCol());
            
            
            King whiteKing = board.getKing(board.isWhiteTurn());
            Square white = whiteKing.getCurrentSquare();
            
            
       
                
            
           
 
                    
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
