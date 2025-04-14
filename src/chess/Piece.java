/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author karlo
 */
public class Piece { //will be used soon
    int row;
    int file;
    int pieceType;
    int material;
    
    public Piece(int row, int file, int pieceType)
    {
        this.row = row;
        this.file = file;
    }
}

