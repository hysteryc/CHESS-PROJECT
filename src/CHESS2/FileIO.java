/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author teddy
 */
public class FileIO 
{
    Board board = new Board();
    
    // Clears the Captured Files at the Beginning of New Game
    public static void clearCapturedFiles() 
    {
        try(BufferedWriter ww = new BufferedWriter(new FileWriter("./Resources/capturedWhite.txt"));
            BufferedWriter wb = new BufferedWriter(new FileWriter("./Resources/capturedBlack.txt"))) 
        {
            System.out.println("Captured files cleared successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error clearing capture files: ");
        }
    }
    
    // Saving the Captured White Pieces to a txt file
    public static void saveCapturedWhite(ArrayList<Piece> capturedWhite) 
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Resources/capturedWhite.txt"))) 
        {
            for (Piece piece : capturedWhite) 
            {
                bw.write(piece.getClass().getSimpleName());
                bw.newLine();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving captured white pieces ");
        }
    }

    // Saving the captured black pieces to a txt file
    public static void saveCapturedBlack(ArrayList<Piece> capturedBlack) 
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Resources/capturedBlack.txt"))) 
        {
            for (Piece piece : capturedBlack) 
            {
                bw.write(piece.getClass().getSimpleName());
                bw.newLine();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving captured black pieces");
        }
    }
    
    //Printing the captured White pieces from a file to the command line
    public static void printCapturedWhite() 
    {
        System.out.println("Captured White Pieces:");
        try (BufferedReader reader = new BufferedReader(new FileReader("./Resources/capturedWhite.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println("- " + line);
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading captured white pieces: ");
        }
    }

    //Printing the captured black pieces from a file to the command line
    public static void printCapturedBlack() 
    {
        System.out.println("Captured Black Pieces:");
        try (BufferedReader reader = new BufferedReader(new FileReader("./Resources/capturedBlack.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println("- " + line);
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading captured black pieces: ");
        }
    }
    
    //Saving the current state of the board to a txt file
    public static void saveGame(Board board, boolean whiteTurn, ArrayList<Piece> capturedWhite, ArrayList<Piece> capturedBlack) 
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Resources/boardState.txt"))) 
        {
            bw.write("Turn: " + (whiteTurn ? "White" : "Black"));
            bw.newLine();
            bw.write("Board:");
            bw.newLine();

            for (int row = 0; row < 8; row++) 
            {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < 8; col++) 
                {
                    Piece piece = board.getPieceAt(row, col);
                    if (piece == null) 
                    {
                        line.append(".,");
                    } 
                    else 
                    {
                        line.append(piece.getSymbol()).append(",");
                    }
                }
                bw.write(line.toString());
                bw.newLine();
            }

            bw.write("CapturedWhite: ");
            for (Piece piece : capturedWhite) 
            {
                bw.write(piece.getSymbol() + ",");
            }
            bw.newLine();

            bw.write("CapturedBlack: ");
            for (Piece piece : capturedBlack) 
            {
                bw.write(piece.getSymbol() + ",");
            }
            bw.newLine();
            System.out.println("Game successfully saved.");
        } 
        catch (IOException e) 
        {
            System.out.println("IO Exception");;
        }
    }
    
    // Loading the Saved Board State from a txt file
    public static void loadGame(Board board, ArrayList<Piece> capturedWhite, ArrayList<Piece> capturedBlack)
    {
        try(BufferedReader br = new BufferedReader(new FileReader("./Resources/boardState.txt")))
        {
            String line = br.readLine();
            if (line.contains("White")) 
            {
                Board.setWhiteTurn(true);
            } 
            else if(line.contains("Black")) 
            {
                Board.setWhiteTurn(false);
            }
            br.readLine();
            for (int row = 0; row < 8; row++) 
            {
                line = br.readLine();
                String[] symbols = line.split(",");
                for (int col = 0; col < 8; col++) 
                {
                    String s = symbols[col];
                    if (s.equals(".")) 
                    {
                        board.setPieceAt(row, col, null);
                    } 
                    else 
                    {
                        Piece piece = PieceCreator.createFromSymbol(s); 
                        board.setPieceAt(row, col, piece);
                        
                    }
                }
            }
            
            capturedWhite.clear();
            line = br.readLine();
            String[] whiteSymbols = line.substring("CapturedWhite: ".length()).split(",");
            for (String s : whiteSymbols) 
            {
                if (!s.isEmpty()) 
                {
                    capturedWhite.add(PieceCreator.createFromSymbol(s));
                }
            }

            capturedBlack.clear();
            line = br.readLine(); 
            String[] blackSymbols = line.substring("CapturedBlack: ".length()).split(",");
            for (String s : blackSymbols) 
            {
                if (!s.isEmpty()) 
                {
                    capturedBlack.add(PieceCreator.createFromSymbol(s));
                }
            }
            
            System.out.println("Game successfully loaded. It's " + (Board.whiteTurn ? "White" : "Black") + "'s turn.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error loading game: ");
        }
    }
}
