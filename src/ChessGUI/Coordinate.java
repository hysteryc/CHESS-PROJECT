/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

/**
 *
 * @author karlo
 */
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

    // Getter for Col
    public int getFile()
    {
        return file;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coordinate other = (Coordinate) obj;
    return this.file == other.file && this.row == other.row;
}

    
}

