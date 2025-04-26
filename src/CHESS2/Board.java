/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teddy
 */
public class Board 
{
    public static boolean whiteTurn;
    private Square[][] board;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> capturedBlack;
    private ArrayList<Piece> capturedWhite;
    
    // Constructor For the Board.
    public Board()
    {
        board = new Square[8][8];
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        capturedWhite = new ArrayList<>();
        capturedBlack = new ArrayList<>();
        whiteTurn = true;
        initializeBoard();
    }
    
    // Initilizing the Board at the Beginning of a new Game (STandard Positions for classic Chess
    public void initializeBoard() 
    {
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                board[row][col] = new Square(row, col);
            }
        }
        
        for (int col = 0; col < 8; col++) 
        {
            Pawn whitePawn = new Pawn(true);
            Pawn blackPawn = new Pawn(false);
            board[6][col].setPiece(whitePawn);
            board[1][col].setPiece(blackPawn);
            whitePieces.add(whitePawn);
            blackPieces.add(blackPawn);
            whitePawn.setCurrentSquare(board[6][col]);
            blackPawn.setCurrentSquare(board[1][col]);
        }
        
        Piece[] whiteBackRow = 
        {
            new Rook(true),
            new Knight(true),
            new Bishop(true),
            new Queen(true),
            new King(true),
            new Bishop(true),
            new Knight(true),
            new Rook(true)
        };
        
        Piece[] blackBackRow = 
        {
            new Rook(false),
            new Knight(false),
            new Bishop(false),
            new Queen(false),
            new King(false),
            new Bishop(false),
            new Knight(false),
            new Rook(false)
        };

        for (int col = 0; col < 8; col++) 
        {
            board[7][col].setPiece(whiteBackRow[col]);
            whitePieces.add(whiteBackRow[col]);
            board[0][col].setPiece(blackBackRow[col]);
            blackPieces.add(blackBackRow[col]);

            whiteBackRow[col].setCurrentSquare(board[7][col]);
            blackBackRow[col].setCurrentSquare(board[0][col]);
        }
    }
    
    // Get Method for Board
    public Square[][] getBoard() 
    {
        return board;
    }
    
    // Draw the board in the console
    public void drawBoard() 
    {
        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) 
        {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) 
            {
                Square square = board[row][col];
                System.out.print(square + " ");
            }
            System.out.println((8 - row));
        }
        System.out.println("  a b c d e f g h");
    }
    
    // Moving a Piece
    public boolean movePiece(Coordinate from, Coordinate to) 
    {
        int startRow = from.getRow();
        int startCol = from.getCol();
        int endRow = to.getRow();
        int endCol = to.getCol();

        Square startSquare = board[startRow][startCol];
        Square endSquare = board[endRow][endCol];
        Piece piece = startSquare.getPiece();

        // Check if there is a piece at start position.
        if (piece == null) 
        {
            System.out.println("No piece at start position.");
            return false;
        }

        // Check for capturing own pieces.
        if (endSquare.getPiece() != null && endSquare.getPiece().isWhite() == piece.isWhite()) 
        {
            System.out.println("Cannot capture your own piece.");
            return false;
        }

        // Check Which Players Turn
        if (piece.isWhite() != whiteTurn) 
        {
            System.out.println("It's " + (whiteTurn ? "White" : "Black") + "'s turn.");
            return false;
        }

        // Check whether Move is valid.
        if (!piece.isValidMove(startRow, startCol, endRow, endCol, this)) 
        {
            System.out.println("Invalid move for that piece.");
            return false;
        }

        // Capturing of Opponents Pieces
        if (endSquare.getPiece() != null) 
        {
            Piece targetPiece = endSquare.getPiece();
            if (piece.isOpponent(targetPiece)) 
            {
                System.out.println(piece.getSymbol() + " captures " + targetPiece.getSymbol());
                targetPiece.setCurrentSquare(null);
                if (whiteTurn) 
                {
                    blackPieces.remove(targetPiece);
                    capturedBlack.add(targetPiece);
                    FileIO.saveCapturedBlack(capturedBlack);  // Autosave after capture
                } 
                else 
                {
                    whitePieces.remove(targetPiece);
                    capturedWhite.add(targetPiece);
                    FileIO.saveCapturedWhite(capturedWhite);  // Autosave after capture
                }
            }
        }
        //Promotion
        if (piece instanceof Pawn) 
        {
            if ((piece.isWhite() && endRow == 0) || (!piece.isWhite() && endRow == 7)) 
            {
                // Promote to Queen (or other piece if you want to allow that)
                System.out.println("Pawn reaches promotion rank! Promoting to Queen.");
                endSquare.setPiece(new Queen(piece.isWhite()));
                startSquare.setPiece(null);
                return true;
            }
        }
        endSquare.setPiece(piece);
        startSquare.setPiece(null);
        // Switches the Turn once the move is made.
        whiteTurn = !whiteTurn;

        return true;
    }
    
    // Get piece at a given square
    public Piece getPieceAt(int row, int col) 
    {
        return board[row][col].getPiece();
    }
    
    // Get Captured White Pieces
    public ArrayList<Piece> getCapturedWhite() 
    {
        return capturedWhite;
    }
    
    // Get Captured White Pieces
    public ArrayList<Piece> getCapturedBlack() 
    {
        return capturedBlack;
    }
    
    // Set piece at a given square
    public void setPieceAt(int row, int col, Piece piece) 
    {
        board[row][col].setPiece(piece);
    }
    
    // Get all legal moves for a given piece
    public List<Coordinate> getLegalMovesForPiece(Piece piece, Coordinate from) 
    {
        return piece.generateLegalMoves(this, from);
    }
    
    // Check if king has been captures (Substitute for Check and Checkmate)
    public boolean isKingCaptured(boolean whitePlayer) 
    {
        King king = getKing(whitePlayer);
        return king == null || king.getCurrentSquare() == null;
    }
    
    // Win Condition or End Game Condition
    public void checkVictory() 
    {
        if (isKingCaptured(true)) 
        {
            System.out.println("Black wins!");
        } 
        else if (isKingCaptured(false)) 
        {
            System.out.println("White wins!");
        }
    }
    
    // Sets the Current Turn (Mainly for Loading a Game)
    public static void setWhiteTurn(boolean whiteTurn) 
    {
        Board.whiteTurn = whiteTurn;
    }
    
    // Check Turn Logic
    public boolean isWhiteTurn() 
    {
        return whiteTurn;
    }
    
    // Check if a Square on the Board is Empty
    public boolean isSquareEmpty(int row, int col) 
    {
    // Adjust for 1-indexed rows and columns
        return board[row][col].getPiece() == null;  // Returns true if no piece is present at the given square
    }
    
    // Check whether a piece is on opposing team
    public boolean isOpponentPiece(int row, int col, boolean isWhite) 
    {
        Square square = board[row][col];  
        Piece targetPiece = square.getPiece();  
        return targetPiece != null && targetPiece.isOpponent(isWhite ? targetPiece : null); 
    }
    
    // Gets the position of the King on the Board
    public King getKing(boolean whitePlayer) 
    {
        for (Piece piece : (whitePlayer ? whitePieces : blackPieces)) 
        {
            if (piece instanceof King) 
            {
                return (King) piece;
            }
        }
        return null;
    }
    
}
