/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */
import java.util.ArrayList;

/**
 *
 * @author karlo
 */
public class Board {
    
    ArrayList<Square> board = new ArrayList<>();
    
    public Board()
    {
        boolean colour = false; 
        for(int row = 1; row <= 8; row++)
        {
            colour = !colour;
            for(int file = 1; file <= 8; file++)
            {
                Square square = new Square(file, row);
                
                if(colour)
                {
                    square.value = '▅';
                    colour = !colour;
                }
                else
                {
                    square.value = '▭';
                    colour = !colour;
                
                }
                
                if(row == 2)
                {
                    square.value = '♟';

                }
                
                if(row == 7)
                {
                    square.value = '♙';

                }
                
                board.add(square);
        
                }
        }
    }
    
    
    public void print()
    {
        int row = 1;
        for(Square item : board)
        {
            
            
            if(row != item.row)
            {
                System.out.println();
                System.out.println();
                row++;
            }
            
            System.out.print(' ');
            System.out.print(' ');
            System.out.print(' ');
            
            System.out.print(item.value);
            
            
        }
        System.out.println();
    }
}

