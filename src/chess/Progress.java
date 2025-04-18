/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

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
public class Progress 
{
     private Board board;

    public Progress(Board board) 
    {
        this.board = board;
    }
    
    
    //Writing Existing Board to a file (data as a string) for further to load when implementing...
    public void Save(String filepath) 
    {
        
         

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) 
        {
            for (Square square : board.getBoard()) 
            {
                writer.write(square.file + "," + square.row + "," + square.tileValue + "," + square.piece);
                writer.newLine();
            }
            System.out.println("FILE SAVED");
        } 
        catch (IOException e) 
        {
            System.out.println("DID NOT SAVE");
        }
    }
    
    //Loading the Previous Board Still need to enter the file locations...
    public void Load(String filepath) 
    {
        ArrayList<Square> squares = board.getBoard(); 
        squares.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) 
        {
            String line;
            //Reads while there is a next line in the file...
            while ((line = reader.readLine()) != null) 
            {
                String[] split = line.split(",");
                //parses because it is recieving a string thus transfroming into an int.
                int file = Integer.parseInt(split[0]);
                int row = Integer.parseInt(split[1]);
                int tileValue = Integer.parseInt(split[2]);
                int piece = Integer.parseInt(split[3]);
                Square square = new Square(file, row);
                square.tileValue = tileValue;
                square.piece = piece;
                squares.add(square);
            }
            System.out.println("BOARD LOADED");
        } 
        catch (IOException e) 
        {
            System.out.println("DID NOT LOAD");
        }
    }

}
