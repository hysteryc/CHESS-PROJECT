/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

/**
 *
 * @author karlo
 */

//Coordinate is used to pass a set of two integers through a function more effectivly  
public class Coordinate 
{
    private final int file;
    private final int row;
    
    public Coordinate(int file, int row)
    {
        
        this.file = file;
        this.row = row;
    }
    
    // Getter for Row
    public int getRow() 
    {
        return row;
    }

    // Getter for File
    public int getFile()
    {
        return file;
    }
    
    //Overrides the inbuild equals function to work according to my logic
    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coordinate other = (Coordinate) obj;
    return this.file == other.file && this.row == other.row;
}

    
}

