/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author mymac
 */
public class Coordinate {
    
    public int file;
    public int row;

    public Coordinate(int file, int row) {
        this.file = file;
        this.row = row;
    }
    
    public void print()
    {
        System.out.println(file + ", " + row);
    }
    
    public boolean withinBounds()
    {
        return (file <= 8 && file >= 1) && (row <= 8 && row >= 1);
    }
}

