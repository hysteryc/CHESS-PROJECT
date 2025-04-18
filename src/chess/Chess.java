/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chess;
/**
 *
 * @author teddy
 */
public class Chess {

    /**
     * @param args the command line arguments
     * C:CHESS-PROJECT\ChessFiles
     */
    public static void main(String[] args) 
    {
        Board board = new Board();
        Progress save = new Progress(board);
        Interface game = new Interface();
        
        board.drawBoard();
        
        game.run(board);
        
        
        
        //save.Save("ChessFiles/BoardData.txt");

        
        
    }
    
}
