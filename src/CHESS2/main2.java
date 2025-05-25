/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

import java.util.Scanner;

/**
 *
 * @author teddy
 */
public class main2 
{
    public static void main(String[] args) 
    {
        Piece lastMoved = null;
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        FileIO.clearCapturedFiles(); 

        System.out.println("Welcome to Barebones Command Line Chess");
        
        System.out.println("Would you like to load a saved game? (yes/no): ");
        String loadInput = scanner.nextLine();
        if (loadInput.equalsIgnoreCase("yes")) 
        {
            FileIO.loadGame(board, board.getCapturedWhite(), board.getCapturedBlack());  
            board.drawBoard();
            board.printPieces();
        } 
        else 
        {
            board.initializeBoard();
            board.drawBoard();
            board.printPieces();
        }
    }
}
