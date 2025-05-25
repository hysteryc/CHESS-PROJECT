/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS;

/**
 *
 * @author teddy
 */
public class Coordinate 
{
    private final int row;
    private final int col;
    
    public Coordinate(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    // Getter for Row
    public int getRow() 
    {
        return row;
    }

    // Getter for Col
    public int getCol() 
    {
        return col;
    }
}
