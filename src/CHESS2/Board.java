/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    
    // Initilizing the Board at the Beginning of a new Game (Standard Positions for classic Chess
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
    
    
    public void promotion(Square square) {
    Scanner scanner = new Scanner(System.in);
    int choice = 0;
    boolean validInput = false;
    
    System.out.println("\nPROMOTION MENU");
    System.out.println("1 - Queen");
    System.out.println("2 - Rook");
    System.out.println("3 - Knight");
    System.out.println("4 - Bishop");
    
    // Input validation loop
    while (!validInput) {
        try {
            System.out.print("Choose promotion (1-4): ");
            String input = scanner.next().trim();
            
            if (!input.matches("[1-4]")) {
                throw new IllegalArgumentException("Please enter a number between 1 and 4");
            }
            
            choice = Integer.parseInt(input);
            validInput = true;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    // Promotion execution
    Piece pawn = square.getPiece();
    boolean colour = pawn.isWhite();
    ArrayList<Piece> chosenColour = (colour ? whitePieces : blackPieces);
    
    // Remove the pawn first
    chosenColour.remove(pawn);
    
    // Create and set the new promoted piece
    Piece promotedPiece;
    switch (choice) {
        case 1 -> promotedPiece = new Queen(colour);
        case 2 -> promotedPiece = new Rook(colour);
        case 3 -> promotedPiece = new Knight(colour);
        case 4 -> promotedPiece = new Bishop(colour);
        default -> throw new IllegalStateException("Unexpected promotion choice");
    }
    
    square.setPiece(promotedPiece);
    promotedPiece.setCurrentSquare(square);  // Important for tracking position
    chosenColour.add(promotedPiece);
    
    System.out.println("Pawn promoted to " + promotedPiece.getClass().getSimpleName());
    }
    
    // Draw the board in the console
    public void drawBoard() 
    {
        System.out.println("  a  b  c  d e  f  g  h");
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
        System.out.println("  a  b  c  d e  f  g  h");
    }
    
    
    private ArrayList<Coordinate> getPath(Coordinate attackerCoordinate, Piece attacker, Coordinate kingPosition) {
    ArrayList<Coordinate> path = new ArrayList<>();
    
    // Calculate direction vectors
    int colStep = Integer.compare(kingPosition.getCol(), attackerCoordinate.getCol());
    int rowStep = Integer.compare(kingPosition.getRow(), attackerCoordinate.getRow());
    
    // Knight case (returns just attacker's position)
    if (attacker instanceof Knight) {
        path.add(attackerCoordinate);
        return path;
    }
    
    // Generate path squares
    
    int curentCol = attackerCoordinate.getCol();
    int curentRow = attackerCoordinate.getRow();
    
    // For orthogonal/diagonal pieces (rook, bishop, queen)
    while (curentCol != kingPosition.getCol() || curentRow != kingPosition.getRow() ) {
        // Add intermediate squares to path
        path.add(new Coordinate(curentRow, curentCol));
        
        // Move towards king
        curentCol += colStep;
        curentRow += rowStep;
        
        // Safety check to prevent infinite loops
        if ((curentCol < 0 || curentCol > 7) || (curentRow < 0 || curentRow > 7) ) break;
    }
    
    return path;
    }
    
    public boolean check()
    {
        King king = getKing(whiteTurn);
        Square kingSquare = king.getCurrentSquare();
        Coordinate kingPosition = new Coordinate(kingSquare.getRow(), kingSquare.getCol());
        int attackers = checkAttackers(this, kingPosition, (!whiteTurn ? whitePieces : blackPieces));
        if(attackers > 0) 
        {
            System.out.println("===YOU ARE IN CHECK ===");
            return true;
        }
        return false;
    }
    
   
    public boolean checkmate(Piece attacker) {
    King king = getKing(whiteTurn);
    Coordinate kingPos = new Coordinate(king.getCurrentSquare().getRow(), 
                                      king.getCurrentSquare().getCol());

    // 1. Check if piece can block or capturing attacking piece 
    ArrayList<Coordinate> attackPath = getPath(
        new Coordinate(attacker.getCurrentSquare().getRow(), 
        attacker.getCurrentSquare().getCol()),
        attacker,
        kingPos
    );
    
    attackPath.add(new Coordinate(attacker.getCurrentSquare().getRow(),
                                attacker.getCurrentSquare().getCol()));

    ArrayList<Piece> allies = whiteTurn ? whitePieces : blackPieces; //Get all ally pieces
    int pieceCounter = 0;
    boolean foundBlock = false;

    for (Piece ally : allies) {
        pieceCounter++;
        if (pieceCounter <= 16) continue;
        if (ally instanceof King) continue;
        
        Square allySquare = ally.getCurrentSquare();
        if (allySquare == null) continue;

        Coordinate allyPos = new Coordinate(allySquare.getRow(), allySquare.getCol());
        
        for (Coordinate blockSquare : attackPath) { //Check if ally can interupt the attackers path
            if (ally.isValidMove(allyPos.getRow(), allyPos.getCol(),
                               blockSquare.getRow(), blockSquare.getCol(), this)) {
                // Simulate move
                Piece original = board[blockSquare.getRow()][blockSquare.getCol()].getPiece();
                makeTempMove(ally, allyPos, blockSquare);
                
                boolean stillInCheck = checkAttackers(this, kingPos, 
                                                   whiteTurn ? blackPieces : whitePieces) > 0;
                
                undoTempMove(ally, allyPos, blockSquare, original); //Undo move
                
                if (!stillInCheck) {
                    foundBlock = true;
                }
            }
        }
    }

    if (foundBlock) { // If theres a blocker then no checkmate
        return false;
    }

    // 2. Check king can escape by moving by checking if every possible move is valid
    for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
        for (int colDelta = -1; colDelta <= 1; colDelta++) {
            if (rowDelta == 0 && colDelta == 0) continue;
            
            int newRow = kingPos.getRow() + rowDelta;
            int newCol = kingPos.getCol() + colDelta;
            
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                // Check for friendly piece first
                Piece targetPiece = board[newRow][newCol].getPiece();
                if (targetPiece != null && targetPiece.isWhite() == whiteTurn) {
                    continue;
                }
                
                if (king.isValidMove(kingPos.getRow(), kingPos.getCol(),
                                   newRow, newCol, this)) {
                    if (checkAttackers(this, new Coordinate(newRow, newCol),
                                     whiteTurn ? blackPieces : whitePieces) == 0) {
                        return false;
                    }
                }
            }
        }
    }
    
    return true;
}   
    
    private void makeTempMove(Piece piece, Coordinate from, Coordinate to) {
    board[to.getRow()][to.getCol()].setPiece(piece);
    piece.setCurrentSquare(board[to.getRow()][to.getCol()]);
    board[from.getRow()][from.getCol()].setPiece(null);
}

    private void undoTempMove(Piece piece, Coordinate from, Coordinate to, Piece original) {
    board[to.getRow()][to.getCol()].setPiece(original);
    piece.setCurrentSquare(board[from.getRow()][from.getCol()]);
    board[from.getRow()][from.getCol()].setPiece(piece);
}
    
    public int checkAttackers(Board board, Coordinate kingPosition, ArrayList<Piece> enemyList) {
    
    int startRow = kingPosition.getRow();
    int startCol = kingPosition.getCol();
    int attackers = 0;
    
    // Get enemy pieces that are still on the board
    
    int first16 = 0;
    
    for(Piece enemy : enemyList) {
        if(first16 == 16)
        {
            Square enemySquare = enemy.getCurrentSquare();

            // Skip captured pieces (not on board)
            if(enemySquare == null) {
                continue;
            }


            // Debug print - remove in production
           
            if(enemy.isValidMove(
                enemySquare.getRow(),
                enemySquare.getCol(),
                startRow,
                startCol,
                board)) {

                attackers++;
                System.out.println("\nSimulating move...");
                System.out.println(enemy.getSymbol() + " Can Attack Your King\n");
            }
        }
        else
        {
            first16++;
        }
    }
    
    return attackers;
}
    
    // Moving a Piece
    public boolean movePiece(Coordinate from, Coordinate to) {
    int startRow = from.getRow();
    int startCol = from.getCol();
    int endRow = to.getRow();
    int endCol = to.getCol();
    
    Square startSquare = board[startRow][startCol];
    Square endSquare = board[endRow][endCol];
    Piece piece = startSquare.getPiece();
    
    
    
    // Check if there is a piece at start position.
    if (piece == null) {
        System.out.println("No piece at start position.");
        return false;
    }

    // Check for capturing own pieces.
    if (endSquare.getPiece() != null && endSquare.getPiece().isWhite() == piece.isWhite()) {
        System.out.println("Cannot capture your own piece.");
        return false;
    }

    // Check whose turn it is.
    if (piece.isWhite() != whiteTurn) {
        System.out.println("It's " + (whiteTurn ? "White" : "Black") + "'s turn.");
        return false;
    }

    // Check if the move is valid.
    if (!piece.isValidMove(startRow, startCol, endRow, endCol, this)) {
        System.out.println("Invalid move for that piece.");
        return false;
    }

    // Save the original state in case we need to revert
    Piece originalEndPiece = endSquare.getPiece();
    Piece originalStartPiece = startSquare.getPiece();
    
    // Make the move temporarily
    endSquare.setPiece(piece);
    piece.setCurrentSquare(endSquare);
    startSquare.setPiece(null);

    // Handle capturing opponent's pieces (temporarily)
    if (originalEndPiece != null && piece.isOpponent(originalEndPiece)) {
        if (whiteTurn) {
            blackPieces.remove(originalEndPiece);
        } else {
            whitePieces.remove(originalEndPiece);
        }
    }

    // Check if this move leaves our king in check
    King king = getKing(whiteTurn);
    Square kingSquare = king.getCurrentSquare();
    Coordinate kingPosition = new Coordinate(kingSquare.getRow(), kingSquare.getCol());
    int attackers = checkAttackers(this, kingPosition, (!whiteTurn ? whitePieces : blackPieces));
    
    // Revert the move if it leaves king in check
    if (attackers > 0) {
        // Revert the board state
        startSquare.setPiece(originalStartPiece);
        piece.setCurrentSquare(startSquare);
        endSquare.setPiece(originalEndPiece);
        
        // Restore captured piece if there was one
        if (originalEndPiece != null && piece.isOpponent(originalEndPiece)) {
            if (whiteTurn) {
                blackPieces.add(originalEndPiece);
            } else {
                whitePieces.add(originalEndPiece);
            }
        }
        
        System.out.println("Move leaves king in check.");
        return false;
    }

    // Handle pawn promotion (only after we know the move is valid)
    if (piece instanceof Pawn) {
        if ((piece.isWhite() && endRow == 0) || (!piece.isWhite() && endRow == 7)) {
            System.out.println("Pawn reaches promotion rank! Promoting to Queen.");
            promotion(endSquare);
            whiteTurn = !whiteTurn; // Switch turn after promotion
            return true;
        }
    }

    // If we got here, the move is valid and doesn't leave king in check
    // Now handle the capture for real (already done above, but need to save to file if needed)
    if (originalEndPiece != null && piece.isOpponent(originalEndPiece)) {
        System.out.println(piece.getSymbol() + " captures " + originalEndPiece.getSymbol());
        if (whiteTurn) {
            capturedBlack.add(originalEndPiece);
            FileIO.saveCapturedBlack(capturedBlack);
        } else {
            capturedWhite.add(originalEndPiece);
            FileIO.saveCapturedWhite(capturedWhite);
        }
    }

    // Switch turn.
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
    
    
    public ArrayList<Piece> getWhite() 
    {
        return whitePieces;
    }
    
    // Get Captured White Pieces
    public ArrayList<Piece> getBlack() 
    {
        return blackPieces;
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
    public boolean isKingCaptured(boolean isWhite) 
    {
    for (int row = 0; row < 8; row++) 
    {
        for (int col = 0; col < 8; col++) 
        {
            Piece piece = board[row][col].getPiece();
            if (piece != null && piece instanceof King && piece.isWhite() == isWhite) 
            {
                return false; 
            }
        }
    }
    return true; 
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
        return board[row][col].getPiece() == null;  
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
        int first16 = 0;
        for (Piece piece : (whitePlayer ? whitePieces : blackPieces)) 
        {
            if(first16 == 16)
            {
                if(piece instanceof King) 
                {
                    return (King) piece;
                }
            }
            else 
            {
                first16++;
            }
        }
        return null;
    }
    
}
