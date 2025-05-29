/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

/**
 *
 * @author karlo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class GUI {
   
    Square[][] squares = new Square[8][8];
    JButton selected = null;
    
    ArrayList<Coordinate> validMoves = new ArrayList();
    
    ArrayList<Square> whitePieces = new ArrayList();
    ArrayList<Square> blackPieces = new ArrayList();
    
    
    JFrame frame = new JFrame("Command.chess");
    String dark_grey = "#121111";
    String grey = "#262525";
    String light_grey = "#5c5b5b";
    String promotionColour = "#d7d9a7";
    Border selectedBorder = BorderFactory.createLineBorder(Color.decode(grey), 3);
    boolean whiteTurn = true;
    
    
    public GUI()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 1100);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode(dark_grey));
        frame.setResizable(false);
    }
    
    
    
    public ArrayList<Coordinate> drawValidMoves(JButton button)
    {
 
        
        
        Coordinate origin = getPosition(button);
        int file = origin.getFile();
        int row = origin.getRow();
        
        if(squares[file][row].getPiece() != null)
            {
            Piece selectedPiece = squares[file][row].getPiece();

            ArrayList<Coordinate> legalMoves = selectedPiece.generateLegalMoves(origin, this, false);

            
            for(Coordinate item : legalMoves)
            {
                
                squares[item.getFile()][item.getRow()].getButton().setBorder(selectedBorder);

                squares[item.getFile()][item.getRow()].getButton().setBorderPainted(true);
            }
            
            return legalMoves;
            
        }
        
        return null;
    }
    public Coordinate getPosition(JButton button)
    {
        String temp = button.getText();
        int file = Character.getNumericValue(temp.charAt(0));  
        int row = Character.getNumericValue(temp.charAt(1));
        Coordinate position = new Coordinate(file, row);
        
        return position;
    }
    
    public Square getKing(boolean isWhite) 
    {
    
        ArrayList<Square> pieceSquares = isWhite ? whitePieces : blackPieces;
        
        for (Square item : pieceSquares) 
        {
        
            if (item.getPiece() instanceof King) return item; 
           
        }
        
        return null;
    }
    
    
    public Square tempMove(Coordinate origin, Coordinate destination) {
        Square originSquare = squares[origin.getFile()][origin.getRow()];
        Square destinationSquare = squares[destination.getFile()][destination.getRow()];
        Piece originPiece = originSquare.getPiece();
        
        // Create a deep copy of the destination square (if needed)
        Square victim = new Square(destinationSquare.getFile(), destinationSquare.getRow(), destinationSquare.getButton());
        
        victim.setPiece(destinationSquare.getPiece());
        
                      
        // Execute the move
        destinationSquare.setPiece(originPiece);
        originSquare.setPiece(null);

        // Update piece tracking
        updatePiece(originSquare, destinationSquare, originPiece);

        return victim;
    }

    public void undoTempMove(Coordinate originalPosition, Coordinate currentPosition, Square victim) {
    Square originalSquare = squares[originalPosition.getFile()][originalPosition.getRow()];
    Square currentSquare = squares[currentPosition.getFile()][currentPosition.getRow()];
    Piece movingPiece = currentSquare.getPiece();

    // Restore original position of the moved piece
    originalSquare.setPiece(movingPiece);

    // Replace the destination square with the victim square
    squares[currentPosition.getFile()][currentPosition.getRow()] = victim;

    // Restore victim to piece list if needed
    if (victim.getPiece() != null && victim.getPiece().isWhite() != movingPiece.isWhite()) {
        ArrayList<Square> victimPieces = victim.getPiece().isWhite() ? whitePieces : blackPieces;
        if (!victimPieces.contains(victim)) {
            victimPieces.add(victim);
        }
    }

    // Update piece tracking for the moved piece
    updatePiece(currentSquare, originalSquare, movingPiece);
}

    
    
    public void updatePiece(Square origin, Square destination, Piece piece) 
    {
        
        if (destination.getPiece() != null) {
            ArrayList<Square> pieces = !piece.isWhite ? whitePieces : blackPieces;
            Iterator<Square> iterator = pieces.iterator();
            while (iterator.hasNext()) {
                Square square = iterator.next();
                if (square.equals(destination)) {  
                    iterator.remove();  
                    break;  
                }
            }
    }
        if(piece != null)
        {
        ArrayList<Square> pieces = piece.isWhite ? whitePieces : blackPieces;
        for (int i = 0; i < pieces.size(); i++) {
        
            if (pieces.get(i).equals(origin)) {
            pieces.set(i, destination);
            break; 
        }
        }   
          
    }
}

    
    
    public int checkAttackers(Square kingPosition) 
    {
        // This function is desinged to go through every enemy piece and check if they can attack the king
        
        int attackers = 0;
    Coordinate kingCoordinate = new Coordinate(kingPosition.getFile(), kingPosition.getRow());
    
    ArrayList<Square> enemyList = (!kingPosition.getPiece().isWhite ? whitePieces : blackPieces);
    
    for(Square enemySquare : enemyList) {
        if (enemySquare.getPiece() == null) continue; // Safety check
        
        Piece enemyPiece = enemySquare.getPiece();
        Coordinate enemyCoordinate = new Coordinate(enemySquare.getFile(), enemySquare.getRow());            
        
        ArrayList<Coordinate> enemyMoves = enemyPiece.generateLegalMoves(enemyCoordinate, this, false);
        
        for(Coordinate item : enemyMoves) {
            if(item.equals(kingCoordinate)) {
                attackers++;
                break; // No need to check other moves for this piece
            }
        }
    }
    
    System.out.println("Attackers: " + attackers);
    return attackers;
    }
    
    

    public boolean check() 
    {
    
        //This function uses the 'checkAttackers' function to see if we're in check 
        Square king = getKing(whiteTurn);
        
        int attackers = checkAttackers(king);
        
        return attackers > 0;
    }
    
    public boolean checkmate() 
    {
    
        //This function uses the 'checkAttackers' function to see if we're in check 
       
        
        ArrayList<Square> allyPieces = whiteTurn ? whitePieces : blackPieces;
        
                
        for(Square item : allyPieces)
        {
            ArrayList<Coordinate> validMoves = item.getPiece().generateLegalMoves(new Coordinate(item.getFile(), item.getRow()), this, whiteTurn);
            
            if(!validMoves.isEmpty()) 
            {
                for(Coordinate coordinate : validMoves)
                {
                    System.out.println(coordinate.getFile() + ", " + coordinate.getRow());
                    
                }
                return false;
            }
        }
        return true;
    }
    
    
    
        
        

    
    public void movePiece(Coordinate origin, Coordinate destination)
    {
        
        
        Square originSquare = squares[origin.getFile()][origin.getRow()];
        Square destinationSquare = squares[destination.getFile()][destination.getRow()];
        Piece originPiece = originSquare.getPiece();
        
        
        
        
        destinationSquare.getButton().setIcon(originPiece.getImage());
        originSquare.getButton().setIcon(null);
        
        destinationSquare.setPiece(originPiece);
        originSquare.setPiece(null);
        
        updatePiece(originSquare, destinationSquare, originPiece);
        
        if(originPiece instanceof Pawn && (destination.getRow() == 7 || destination.getRow() == 0))
        {
            promotionMenu(destination);
        }
    }
    
    private void promotion(int choice, Coordinate position)
    {
    //update board
    //update pieces
        
        Square promotionSquare = squares[position.getFile()][position.getRow()]; 
        Piece promotionPiece = promotionSquare.getPiece(); 
                     
        
        
        switch(choice)
        {
            case 1:
            {
                promotionSquare.getButton().setIcon(new ImageIcon(promotionPiece.isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png"));
                promotionSquare.setPiece(new Queen(promotionPiece.isWhite));
                
                break;
            }
            case 2:
            {
                promotionSquare.getButton().setIcon(new ImageIcon(promotionPiece.isWhite ? "chessFiles/chess Pieces/WHITE ROOK.png" : "chessFiles/chess Pieces/BLACK ROOK.png"));
                promotionSquare.setPiece(new Rook(promotionPiece.isWhite));
                break;
            }
            case 3:
            {
                promotionSquare.getButton().setIcon(new ImageIcon(promotionPiece.isWhite ? "chessFiles/chess Pieces/WHITE BISHOP.png" : "chessFiles/chess Pieces/BLACK BISHOP.png"));
                promotionSquare.setPiece(new Bishop(promotionPiece.isWhite));
                break;
            }
            case 4:
            {
                promotionSquare.getButton().setIcon(new ImageIcon(promotionPiece.isWhite ? "chessFiles/chess Pieces/WHITE KNIGHT.png" : "chessFiles/chess Pieces/BLACK KNIGHT.png"));
                promotionSquare.setPiece(new Knight(promotionPiece.isWhite));
                break;
            }
            default:
            {
                promotionSquare.getButton().setIcon(new ImageIcon(promotionPiece.isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png"));
                promotionSquare.setPiece(new Queen(promotionPiece.isWhite));
                break;
            }
            
           
        }
        
        
    }
    
    public void promotionMenu(Coordinate position) {
    
        Square promotionSquare = squares[position.getFile()][position.getRow()]; 
        boolean isWhite = promotionSquare.getPiece().isWhite();
        JDialog promotionDialog = new JDialog(frame, "Choose Promotion", true);        
        promotionDialog.setLayout(null); 
        promotionDialog.setUndecorated(true); 
        promotionDialog.setBounds(560, 460, 180, 180); 
        promotionDialog.setLocationRelativeTo(frame); // center dialog

        JButton rook = new JButton("2");
        JButton knight = new JButton("4");
        JButton bishop = new JButton("3");
        JButton queen = new JButton("1");

        // Load icons
        rook.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE ROOK.png" : "chessFiles/chess Pieces/BLACK ROOK.png"));
        knight.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE KNIGHT.png" : "chessFiles/chess Pieces/BLACK KNIGHT.png"));
        bishop.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE BISHOP.png" : "chessFiles/chess Pieces/BLACK BISHOP.png"));
        queen.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png"));

        // Clean style
        JButton[] promotionOptions = { queen, rook, bishop, knight };
        for (JButton option : promotionOptions) {
            option.setFocusPainted(false);
            option.setContentAreaFilled(false);
            option.setBorder(selectedBorder);
            option.setOpaque(false);
            option.setForeground(new Color(0, 0, 0, 0)); // make text invisible

        }

        rook.setBounds(0, 0, 90, 90);
        knight.setBounds(0, 90, 90, 90);
        bishop.setBounds(90, 0, 90, 90);
        queen.setBounds(90, 90, 90, 90);

        promotionDialog.add(rook);
        promotionDialog.add(knight);
        promotionDialog.add(bishop);
        promotionDialog.add(queen);

        // Add action listeners
        for (JButton option : promotionOptions) {
            for (MouseListener ml : option.getMouseListeners()) {
                option.removeMouseListener(ml);
            }

             option.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    int choice = Integer.parseInt(option.getText());
                    promotion(choice, position);
                    promotionDialog.dispose();
                    
                    }});
        }

        promotionDialog.setVisible(true); // show the dialog after setup
}

    
    public boolean isWhite(Coordinate position)
    {
        return squares[position.getFile()][position.getRow()].getPiece().isWhite;
    }
    
    public void drawboard()
    {
        
        frame.getContentPane().removeAll();
        
        
        
        System.out.println("drawboard() called");
        
        if(check()) {
            if(checkmate()) {
                System.out.println("CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE");
            }
        }
        
        for(Square item : whitePieces)
        {
            System.out.println(item.getPiece().getSymbol() + " - " + item.getFile() + ", " + item.getRow());
        }
        System.out.println();
        for(Square item : blackPieces)
        {
            System.out.println(item.getPiece().getSymbol() + " - " + item.getFile() + ", " + item.getRow());
        }
        
        
        
        JPanel gamePanel = new JPanel();
        
        gamePanel.setLayout(null); 
        gamePanel.setBounds(0, 0, 1300, 1100); 
        gamePanel.setBackground(Color.decode(grey));
        
        int x = 350;
        int y = 250;
        
        Boolean isLight = true;
        
        
        for(int row = 7; row >= 0; row--)
        {
            JLabel row_left = new JLabel(String.valueOf((row+1)));
            row_left.setFont(new Font("Times New Roman", Font.PLAIN, 60));
            row_left.setForeground(Color.decode("#ffffff"));
            row_left.setBounds(x-60, y, 75, 75);
            gamePanel.add(row_left);
            
            
            
            
            
            for(int file = 0; file < 8; file++)
            {
                                
                isLight = !isLight;
                
                JButton temp = squares[file][row].getButton();
                
                 if(squares[file][row].getPiece() != null) temp.setIcon(squares[file][row].getPiece().getImage());
                
                
                temp.setBounds(x, y, 75, 75);
                temp.setBorderPainted(false);
                temp.setFocusPainted(false);
                temp.setBackground(( isLight ? (Color.decode(light_grey)) : (Color.LIGHT_GRAY)));
                temp.setForeground(new Color(0, 0, 0, 0));
                
                for (MouseListener ml : temp.getMouseListeners()) {
                temp.removeMouseListener(ml);
                }
                
                temp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Coordinate selectedPosition = getPosition(temp);
                    Piece tempPiece = squares[selectedPosition.getFile()][selectedPosition.getRow()].getPiece(); 
                    
                    if(selected == null && tempPiece != null && (tempPiece.isWhite() != whiteTurn)) return;
                  
                    
                    
                    // Case 1: Nothing selected yet and clicked a piece
                    if (selected == null && temp.getIcon() != null) {
                        selected = temp;
                        validMoves = drawValidMoves(temp); // highlight valid moves
                        temp.setBorder(selectedBorder);
                        temp.setBorderPainted(true);
                        
                    }

                    // Case 2: A piece is selected
                    else if (selected != null) {
                        boolean moved = false;

                        // Try to move to a valid square
                        for (Coordinate item : validMoves) {
                            if (item.getFile() == selectedPosition.getFile() && item.getRow() == selectedPosition.getRow()) {
                                
                                
                                movePiece(getPosition(selected), item);
                                whiteTurn = !whiteTurn;
                                moved = true;
                                break;
                            }
                        }

                        if (moved) {
                            selected = null;
                            validMoves.clear();
                            drawboard(); // redraw after move
                        }
                        // Case 3: Clicked another piece (same color) -> reselect
                        else if (temp.getIcon() != null && 
                                 isWhite(getPosition(selected)) == isWhite(selectedPosition)) {
                            selected = temp;
                            drawboard();
                            validMoves = drawValidMoves(temp);
                            
                            temp.setBorder(selectedBorder);
                            temp.setBorderPainted(true);
                            
                        }
                        // Case 4: Invalid click (e.g. empty square not a valid move)
                        else {
                            selected = null;
                            validMoves.clear();
                            drawboard(); // clear highlights
                        }
                    }
                }
            });

                
                
                
                gamePanel.add(temp);
                x = x + 75;
            }
            isLight = !isLight;

            JLabel row_right = new JLabel(String.valueOf((row+1)));
            row_right.setFont(new Font("Times New Roman", Font.PLAIN, 60));
            row_right.setForeground(Color.decode("#ffffff"));
            row_right.setBounds(x+15, y, 75, 75);
            gamePanel.add(row_right);
            
            
            
            y = y + 75;
            x = 350;
        }
        JLabel file_top = new JLabel("  a   b   c   d   e   f    g   h");
        file_top.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        file_top.setForeground(Color.decode("#ffffff"));
        file_top.setBounds(x, y-675, 600, 75);
        gamePanel.add(file_top);
        
        JLabel file_bottom = new JLabel("  a   b   c   d   e   f    g   h");
        file_bottom.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        file_bottom.setForeground(Color.decode("#ffffff"));
        file_bottom.setBounds(x, y, 600, 75);
        gamePanel.add(file_bottom);
        
        
        JButton goToMenu = new JButton("Back to menu");
        goToMenu.setBounds(410, 950, 600, 100);
        goToMenu.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        goToMenu.setForeground(Color.LIGHT_GRAY);
        //goToMenu.setBorderPainted(false);
        goToMenu.setFocusPainted(false);
        goToMenu.setContentAreaFilled(false);
        goToMenu.setOpaque(false); 
        
        
        goToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                goToMenu.setForeground(Color.WHITE); 
                goToMenu.setFont(new Font("Times New Roman", Font.PLAIN, 70));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                goToMenu.setForeground(Color.LIGHT_GRAY); 
                goToMenu.setFont(new Font("Times New Roman", Font.PLAIN, 50));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                menu();


            }
        });
        
        
        gamePanel.add(goToMenu);
        
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.repaint();

    }
    
    public void game()
    {
        frame.getContentPane().removeAll();
        
        
        for(int row = 7; row >= 0; row--)
        {
            
            for(int file = 0; file < 8; file++)
            {
                squares[file][row] = new Square(file, row, new JButton(String.valueOf(file) + String.valueOf(row)));
            }
            
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

        for (int file = 0; file < 8; file++) 
        {
            squares[file][6].setPiece(new Pawn(false));
            squares[file][7].setPiece(blackBackRow[file]);
            
            squares[file][1].setPiece(new Pawn(true));
            squares[file][0].setPiece(whiteBackRow[file]);
            
           
            
            blackPieces.add(squares[file][6]);
           
           
            whitePieces.add(squares[file][1]);
            
            
        }
        for (int file = 0; file < 8; file++) 
        {
            blackPieces.add(squares[file][7]);
            whitePieces.add(squares[file][0]);
        }
        
        
        
        
        
        drawboard();
        
        
    }
    
    public void menu() {
        
        
        
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
                
        
        
                
        
        
        
        
        JLabel titleLabel = new JLabel("""
                                       <html><pre>          _____                  _______                  _____                   _____                   _____                   _____                   _____                            _____                   _____                   _____                   _____                   _____                  
                                                /\\    \\                /::\\    \\                /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                          /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 
                                               /::\\    \\              /::::\\    \\              /::\\____\\               /::\\____\\               /::\\    \\               /::\\____\\               /::\\    \\                        /::\\    \\               /::\\____\\               /::\\    \\               /::\\    \\               /::\\    \\                
                                              /::::\\    \\            /::::::\\    \\            /::::|   |              /::::|   |              /::::\\    \\             /::::|   |              /::::\\    \\                      /::::\\    \\             /:::/    /              /::::\\    \\             /::::\\    \\             /::::\\    \\               
                                             /::::::\\    \\          /::::::::\\    \\          /:::::|   |             /:::::|   |             /::::::\\    \\           /:::::|   |             /::::::\\    \\                    /::::::\\    \\           /:::/    /              /::::::\\    \\           /::::::\\    \\           /::::::\\    \\              
                                            /:::/\\:::\\    \\        /:::/~~\\:::\\    \\        /::::::|   |            /::::::|   |            /:::/\\:::\\    \\         /::::::|   |            /:::/\\:::\\    \\                  /:::/\\:::\\    \\         /:::/    /              /:::/\\:::\\    \\         /:::/\\:::\\    \\         /:::/\\:::\\    \\             
                                           /:::/  \\:::\\    \\      /:::/    \\:::\\    \\      /:::/|::|   |           /:::/|::|   |           /:::/__\\:::\\    \\       /:::/|::|   |           /:::/  \\:::\\    \\                /:::/  \\:::\\    \\       /:::/____/              /:::/__\\:::\\    \\       /:::/__\\:::\\    \\       /:::/__\\:::\\    \\            
                                          /:::/    \\:::\\    \\    /:::/    / \\:::\\    \\    /:::/ |::|   |          /:::/ |::|   |          /::::\\   \\:::\\    \\     /:::/ |::|   |          /:::/    \\:::\\    \\              /:::/    \\:::\\    \\     /::::\\    \\             /::::\\   \\:::\\    \\      \\:::\\   \\:::\\    \\      \\:::\\   \\:::\\    \\           
                                         /:::/    / \\:::\\    \\  /:::/____/   \\:::\\____\\  /:::/  |::|___|______   /:::/  |::|___|______   /::::::\\   \\:::\\    \\   /:::/  |::|   | _____   /:::/    / \\:::\\    \\            /:::/    / \\:::\\    \\   /::::::\\    \\   _____   /::::::\\   \\:::\\    \\   ___\\:::\\   \\:::\\    \\   ___\\:::\\   \\:::\\    \\          
                                        /:::/    /   \\:::\\    \\|:::|    |     |:::|    |/:::/   |::::::::\\    \\ /:::/   |::::::::\\    \\ /:::/\\:::\\   \\:::\\    \\ /:::/   |::|   |/\\    \\ /:::/    /   \\:::\\ ___\\          /:::/    /   \\:::\\    \\ /:::/\\:::\\    \\ /\\    \\ /:::/\\:::\\   \\:::\\    \\ /\\   \\:::\\   \\:::\\    \\ /\\   \\:::\\   \\:::\\    \\         
                                       /:::/____/     \\:::\\____|:::|____|     |:::|    /:::/    |:::::::::\\____/:::/    |:::::::::\\____/:::/  \\:::\\   \\:::\\____/:: /    |::|   /::\\____/:::/____/     \\:::|    |        /:::/____/     \\:::\\____/:::/  \\:::\\    /::\\____/:::/__\\:::\\   \\:::\\____/::\\   \\:::\\   \\:::\\____/::\\   \\:::\\   \\:::\\____\\        
                                       \\:::\\    \\      \\::/    /\\:::\\    \\   /:::/    /\\::/    / ~~~~~/:::/    \\::/    / ~~~~~/:::/    \\::/    \\:::\\  /:::/    \\::/    /|::|  /:::/    \\:::\\    \\     /:::|____|        \\:::\\    \\      \\::/    \\::/    \\:::\\  /:::/    \\:::\\   \\:::\\   \\::/    \\:::\\   \\:::\\   \\::/    \\:::\\   \\:::\\   \\::/    /        
                                        \\:::\\    \\      \\/____/  \\:::\\    \\ /:::/    /  \\/____/      /:::/    / \\/____/      /:::/    / \\/____/ \\:::\\/:::/    / \\/____/ |::| /:::/    / \\:::\\    \\   /:::/    /          \\:::\\    \\      \\/____/ \\/____/ \\:::\\/:::/    / \\:::\\   \\:::\\   \\/____/ \\:::\\   \\:::\\   \\/____/ \\:::\\   \\:::\\   \\/____/         
                                         \\:::\\    \\               \\:::\\    /:::/    /               /:::/    /              /:::/    /           \\::::::/    /          |::|/:::/    /   \\:::\\    \\ /:::/    /            \\:::\\    \\                      \\::::::/    /   \\:::\\   \\:::\\    \\      \\:::\\   \\:::\\    \\      \\:::\\   \\:::\\    \\             
                                          \\:::\\    \\               \\:::\\__/:::/    /               /:::/    /              /:::/    /             \\::::/    /           |::::::/    /     \\:::\\    /:::/    /              \\:::\\    \\                      \\::::/    /     \\:::\\   \\:::\\____\\      \\:::\\   \\:::\\____\\      \\:::\\   \\:::\\____\\            
                                           \\:::\\    \\               \\::::::::/    /               /:::/    /              /:::/    /              /:::/    /            |:::::/    /       \\:::\\  /:::/    /                \\:::\\    \\                     /:::/    /       \\:::\\   \\::/    /       \\:::\\  /:::/    /       \\:::\\  /:::/    /            
                                            \\:::\\    \\               \\::::::/    /               /:::/    /              /:::/    /              /:::/    /             |::::/    /         \\:::\\/:::/    /                  \\:::\\    \\                   /:::/    /         \\:::\\   \\/____/         \\:::\\/:::/    /         \\:::\\/:::/    /             
                                             \\:::\\    \\               \\::::/    /               /:::/    /              /:::/    /              /:::/    /              /:::/    /           \\::::::/    /                    \\:::\\    \\                 /:::/    /           \\:::\\    \\              \\::::::/    /           \\::::::/    /              
                                              \\:::\\____\\               \\::/____/               /:::/    /              /:::/    /              /:::/    /              /:::/    /             \\::::/    /                      \\:::\\____\\               /:::/    /             \\:::\\____\\              \\::::/    /             \\::::/    /               
                                               \\::/    /                ~~                     \\::/    /               \\::/    /               \\::/    /               \\::/    /               \\::/____/                        \\::/    /               \\::/    /               \\::/    /               \\::/    /               \\::/    /                
                                                \\/____/                                         \\/____/                 \\/____/                 \\/____/                 \\/____/                 ~~                               \\/____/                 \\/____/                 \\/____/                 \\/____/                 \\/____/                 
                                                                                                                                                                                                                                                                                                                                                         </pre></html>""");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 6));
        titleLabel.setForeground(Color.decode("#ffffff"));
        titleLabel.setBounds(50, 0, 1200, 250);

        
        
        
        
        
        
        
        
        
        JLabel queen = new JLabel("""
                                  <html><pre>                               @@@@@@@@                                                                                                                                                                                                                                                                                                                                                                         
                                                                  @@@@@@                                                                                                                                                                                                                                                                                                                                                                          
                                                                 @@@@@@@                                                                                                                                                                                                                                                                                                                                                                          
                                                            @@@@@@@@@@@@@@@@@@                                                                                                                                                                                                                                                                                                                                                                    
                                                            @@@@@@@@@@@@@@@@@@                                                                                                                                                                                                                                                                                                                                                                    
                                                            @@@@@@@@@@@@@@@@@@                                                                                                                                                                                                                                                                                                                                                                    
                                                            @@@@@@@@@@@@@@@@@@                                                                                                                                                                                                                                                                                                                                                                    
                                                                @@@@@@@@@@                                                              @  @@                                                                                                                                                                                                                                                                                                     
                                                               @@@@@%   @@@@                                                          @@@@# @@@                                                                                                                                                                                                                                                                                                   
                                                             .@@@@@@@@@@@@@@@                                                          @@@@@@@                                                                                                                                                                                                                                                                                                    
                                                    @@@@@@@@@@    *@@@@    :*@@@ =@=                                                    @@@@@                                                                                                                                                                                                                                                                                                     
                                                   @@@@@@@@@@@@@@@      @@   #@@@@@@@@                                      +@     @  @@@@@@@@@      @                                                                                                                                                                                                                                                                                            
                                                  @@@@@@@@@@@@@@@@@@@@  @@@@  @@@@@@@@@                                  @@   *@@@  @@        @@@@  @@@%   @                                                                                                                                                                                                                                                                                      
                                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                @@% @@     @@@          @@@    @@  @@@                                             @@@@@@*                                                                                                                                                                                                                                 
                                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@@@@-   +@@@@@@@@@=  @@@@@@@@@@                                               @@@@@@                                                                                                                                                                                                                                  
                                                   @@@@@@@@@@@@@@@@@@*: @@@@    @@@@@%                                        @@@@@@@*-:++@@@ @@@@@@@@                                                     @@@@@                                                                                                                                                                                                                                  
                                                     @@@@@@@@@@@@@@%#=        *@@@                                         @@+@@@@@@@            *@@@@@@                                                 @@@@@@@@@                                                                                                                                                                                                                                
                                                      @@@@@@@@@@%#.  ..    @@@@@                                            @@@@@@@@*.+            @@@@                                              @@  @       @@@@                                                                                                                                                                                                                             
                                                       @@@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@#@==.         @@@@                                            @@@   @      -@@@@@@                                                       -@@@@%@                                                                                                                                                             
                                                        @@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@:+--.:. *@@@                                            @@@@ @  @       @@@@@@                                                    @@    -@@                                                                                                                                                             
                                                         @@@@@@@@@@@@@@@##.=@@@                                               @@@@@@@@@*=  :-.     @@@                                           @@@@@@@  @@@      @@@@@@                                                 @  @@@@@@@@                                                                                                                                                             
                                                         :@@@@@@@@@@@-       @@                                               @@@@@@@@@@##-     :  @@@                                          @@@@@@@@@ @@@@@@@@@@@@@@@@                                               % @@@@@@@@@@                                                                                                                                                             
                                                          @@@@@@@@@@@@@+     @@@@                                             @@@@@@@@@@@%      *: @@@                                         @@@@@@@@@@  @@@@@@@@@@@@@@@@                                               @@@@@@@@@@@                                                                                                                                                             
                                                          @@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@+        %@@@*                                        @@@@@@@@@@@  @@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@@@@@@@                                                                                                                                                         
                                                         @@@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@+      @@@@@@@                                        @@@@@@@@@@@@ @@@@@@@@@@@@@@@                                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                                                                                                                 
                                                        @@@@@@@@@@@@@@@= #@@@@@@ @@                                            @@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@  @@@@@@@@@@@@@@                                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                                                                                                                
                                                        .@@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@ @@@@@@@@@@@@@@@@@@@@@=@@@@@@                                      @@@@@@@    @@@@@@@@@@@@@                                                                                
                                                  @@@@@@# @@@@@@@@@@@@@@%@@@@@@@@@@@@@@@                                    @@@@@@@@@@@@@@@@*  #@@@*  @@@@                                      @@@@@@@@@@@@@@ *=:+@@@@@@@@                                  @@@@@@@+=  @@+%%*:@@%@@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@    @@@@@@@@@@@@@@                                                                               
                                                    @@@@@@@@@@@@@@@@@@@@@-     @@@@@@                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@..  @@@@@@@                                  @@@@@@@@ +@@@@@@@@@@%#-  -@@@@@@@@@@@@@@@@                             @@@@             @@=          @@@@@@                                                                           
                                                            %@@@@@@@@@@@@@@@@@                                          @@@@@  %@@@@@@@@@@@@-..@@@@@@@@@@@@@@                                    @@@@@@@@@ @@      *:@@@@                                 @@@ .@@@@     @@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@*                           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                                            
                                                         @@@@@@@@@:           @@@                                          @@@@@@@@@@@@@@@        +@@@@@@#                                        @@@@@@-:-        + @@@+                                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                          @@@@@@@@@@@@@@@+@  @ @@@@@@@@@@@@@@                                                                            
                                                         @@@@@@@@@@@@@      @@@@@@+                                                @@@@@@@@@@@@@@@@                                               @@@@@@#-= . .    #=@@@                                 @@@@@@@@@@@@@@@@@@@@@@***##@@@@@#@#@@@@@@@@@@@@@@                         @-@@@@@@@@@@--@*@@ @@@@@@@@@@@@@@@@                                                                            
                                                        @@@@@@@@@@@@@@@@@@#@@@@@@@@                                            @@@@@@@*           *@@@@                                           @@@@@@@*+.. : .  .=@@@                                %@  :@@@@@@@@@@@@#:  =*@@@%#@#*:*. - %@@@@@@@@@@@@@@                       @@@@@@@@@@@@@%-%@  @ @@@@@@@@@@@@@@                                                                            
                                                          @@@@@@@@@@@@@@@@@@@@@@                                               @@@@@@@@@@@@@@#+ @@@@@@@                                           @@@@@@@@@*-     :@@@@@                                @@@@@@@@@@==:**@@@@@@@@+:+%@%%@@@@%@@+:#-  @@@@@@@@@@                      @@@@@@@@@@@@@+%@@@@@:@@@@@@@@@@@@@@                                      @@@@@@@@@@@@@@                        
                                                          @@@@@@@@@@@@        @@                                               @@@@@@@@@@@@@@@@@@@@@@@@                                           @@@@@@@@@@@@=*#@@@@@@@@                               @@@@@@@@@%#%@@@@@##@@@-%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@     .@@@@@@@@@@@@@@@                                  @@         .@@@@@@@                      
                                                          @@@@@@@@@@@@       =@@                                                @@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@@@@@@@@@ @@                            @  +.@@@@@@@@@##@@@@%%.=.:@%@@@@@#*@+=@@#@@@@@@@@@@@@@@@                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @                                @@:          =@@@@@@@                     
                                                           @@@@@@@@@@@-:     @@@                                                @@@@@@@@@%        @@@-                                          @@@@@@@@@@@@@@@+@@@@@@@@@@                             @@@@@@@@@@@@@@@@@@@@@@*#@@%++: +            +*:*@@@@@@@@                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                               @@@@@@@@@@@@  @@@@@@@@@                    
                                                           @@@@@@@@@@@*:  .: @@@                                                @@@@@@@@@%  :+  = @@@                                        @@@@@@@@      #@-     +@@@@@@@@@@                        +@ =@@@@@@@@@@@@@@@##%@@@@%@*..  *@@@@@@@@@@@=        :@@                       @@@@@@@@@@@=@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@@@@@@@@@@@@@@@                    
                                                           @@@@@@@@@@@=  @   @@@                                                @@@@@@@@@@@:@: .#.@@@*                                          @@@@@@@@@@@@@@@@@@@@@@@@@@                            @@@@@@@@@@@@@@@@@@@@@:       %@@@@@@@@@@@@@@@@@  ---:  @@                            %@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@@@@@@@@@@@                   
                                                           @@@@@@@@@@@: -  @@@@@+                                               @@@@@@@@@@#.  .%@ @@@                                                   =@@@@@@@@@@                                   @@@@@@@@@@@@@@@@@    @@@@@@@@-             @@@@@: +@@@@@@                        @@@@                :@@@@@@@                                    @@@@@@@@@@@@@@@@@@@@@@@                    
                                                           @@@@@@@@@@@*-   @@@@@+                                               @@@@@@@@@@:*=-..%@@@@@                                             .@@@@@         +@@@@                               #@+ @#@@@@@@@@@@+      +@@@@@                 @@@@* @@@@                        @@@@@@@@@@@@+:   @@@@@@@@@@@@                                     @@          @@@@@@@@@@                    
                                                           @@@@@@@@@@@#=@@ @@@@@@                                               @@@@@@@@@@%@ . +@@@@@@                                              @@@@@.%.:.:   :@@@@                                @@@@@@@@@@@@@@@@@@@@@*    @                     @@@@                               @@@@@@@@%@@@@@@@@@@@                                           @@@          @@@@@@@                     
                                                           @@@@@@@@@@@@@@@ @@@@@@                                               @@@@@@@@@@%@ .  @@@@@@                                              @@@@@@@@#@@@.:@@@@@                                @  @@@@@@@@@@@@@@@@@@@@@@@@                                                      @@@@            @@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@                      
                                                           @@@@@@@@@@@@@@  @@@@@@                                               @@@@@@@@@@@@#-.#@@@@@@                                               @@@@@@@@@-.=@@@@@@                                @@@@@@@@@@@@@@@@@@@@@@@@@@@                                                       @@@@@ +=:     @@@@@@@@@@                                        @@@@@@@@@@@@@@@@@                        
                                                           @@@@@@@@@@@@@@.@@@@@@@                                               @@@@@@@@@@@@@%-#@@@@@@                                              @@@@@@@@@@@@#@@@@@@                                @@@@@@@@@@@@@@@@@@@@@@@@@@@                                                       @@@:@ :=:   - *@@@@@@@@@                                        @@@@@@@@@@@@@@@@@@                       
                                                           @@@@@@@@@@@@@@@@@@@@@@                                               @@@@@@@@@@@@@@%.@@@@@@                                              #@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@@@@@@@@@@@@@@@@                                                       @@@*@#  :     @@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@@                      
                                                           @@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@%@@@@@@@@@                                             *@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                      @@@%+ : .     @@@@@@@@@                                   @@@   @@@@@@@@@@@@@@@@@@   @@@                 
                                                           @@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@@@@@@@@@@@                                             @@@@@@@@@@@@@@@@@@@                                 @@@@ @@@@@@@@@@@@@@@@@@@@@@.                                                     @@@@+@+ :+    @@@@@@@@@                                      @@@@@@     .   @@@@@@@@@*                   
                                                          @@@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@@@@@@@@@@@                                             @@@@@@@@@@@@@@@@@@@@                                @@@@ @@@@@@@@@@@@@@@@@@@=@@@                                                     @@@@%@@= ::   @@@@@@@@@                                           #@@@@@@@@@@   @                        
                                                          @@@@@@@@@@@@@@@@@@@@@@@@                                              @@@@@@@@@@@@@@@@@@@@@@@                                             @@@@@@@@@@@@@@@@@@@@                                 @@  @@@@@@@@@@@@          @@@   +                                              @@@@@@@%*%    :@@@@@@@@@                                         @@@@     %@@@@@@@                        
                                                          @@@@@@@@@@@@@@@@@@@@@@@@                                             :@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@                                 @   @@@@@@                 =@@@                                                @@@@@@@@@@==: +@@@@@@@@@                                           @@@*.  +@@@@@@                         
                                                          @@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@                                    @@@=@@@                  =@@@@                                             @@@@@@@@@@@##+=@@@@@@@@@@                                          @@@+@. @@@@@@@                         
                                                          @@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@                                    @@@@  @@@@                 @@@@@@                                          @@@@@@@@@@@@@@@@@@@@@@@@@                                          @@@@   @@@@@@@                         
                                                         @@@@@@@@@@@@@@@@@@@@@@@@@@                                            @@@@@@@@@@@@@@@@@@@@@@@@@                                           @@@@@@@@@@@@@@@@@@@@@@                                    @@@@@   =@@@@                 =@@@@@@                                     @@@@@@@@@@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@                         
                                                         @@@@@@@@@@@@@@@@@@@@@@@@@@                                           @@@@@@@@@@@@@@@@@@@@@@@@@@@                                          @@@@@@@@@@@@@@@@@@@@@@@                                    @@@@@  +=:.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@                         
                                                         @@@@@@@@@@@@@@@@@@@@@@@@@@@                                          @@@@@@@@@@@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@@@@@@@@@@                                    %@@@@% ..**%++                   @@@@@                                   @@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@@                        
                                                         @@@@@@@@@@@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@@@@@@@                                    @@@@@@ ::. ++..::-. .. .       @@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       @@@@@@@@@@@@@@@@@                        
                                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       .@@@@@@@@@@@@@@@@@@@@@@@@@                                     @@@@  .::.:..-.::..:::-: .    @@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       @@@@@@@@@@@@@@@@@@                       
                                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                      @@@@@@@@@@@@@@@@@@@@@@@@@@@                                     @@@@@@                      @@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                     @@@@@@@@@@@@@@@@@@@                       
                                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                      @@@@@@@@@@@@@@@@@@@@@@@@@@@                                          @@@@@@.        -+@@@@@@@@@                                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                     @@@@@@@@@@@@@@@@@@@@                      
                                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @ @@@@@@@@@@@@@@@@@@@@@@@@:@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@@@@@@@@@@@@                      
                                                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:                                    @@-@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @@                                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@=                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:                                 @@@@@@@@@@@@@@@@@@@@@@@                     
                                                    @@@@@@@@@@@@@@@@@ *%#.#*  -@@@@@@@@@                                 @@@@@@@@@@@@              @@@@@@@@@@@                                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@@ @@@@@@@@@@@                    
                                                   @@@@@@@@@@@@@@@@@@@@@*-@+@@@@@@@@@@@@@                               @@@@@@@@@@@@@@@@@@@@@@@@@@=@@@@@@@@@@@@                              @@@@      #@@@@@@@@@@@@@ :-@@@@@@@@                                    @@@@@@:                 @@@@@@                               @@@@@       @@@@@*@  *%@@@@@@@@@@@@@@@                              @@@@@@@@@@@@   @@@@@@@@@@@@                  
                                                  @@@@@@@@@@@@@@@@@@@@@@:    . @@@@@@@@@@                              @@@@@@@@@@@@@@@@@@@@@@@@%@ -@@@@@@@@@@@@@                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                             @@@@@@@@@@@@@@@@@@@    =@@@@@@@@@@@@@@@                           @@@@@       *@@@@@@@@@@@@@@@@@                 
                                                 @@@@@@@@@@@@@@@@@@@@@@        .@@@@@@@@@@                            @@@@@@@@@@@@@@@@@@@@@@@       %@@@@@@@@@@@@                           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                @@@@@@@@@@@@@@@@@@+   @@@@@@@@@@@@@                           @@@@@@@@@@@@@@@@@@@@@@*#@@@@@@@@@@@@@@@@@                         @@@@@@@@@@@@@@@@   @@@@@@@@@@@@@                
                                                @@@@@@@@@@@@@@@@@@@@@@@         %@@@@@@@@@@                          @@@@@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@@@@                         @@@@@@@@@@@@@@@@@@@@@@     @@@@@@@@@@@@@                              @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                         @@@@@@@@@@@@@@@@@@@@@@:  @@@@@@@@@@@@@@@@@@                       @@@@@@@@@   @@@@    @@@@@@@@@@@@@@               
                                              @@@@@@@@@@@@@@@@@@@@@@@@@        .*@@@@@@@@@@@                        @@@@@@@@@@@@@@@@@@@@@@@@@       =@@@@@@@@@@@@@@                       @@@@@@@@@@@@@@@@@@@@@       @@@@@@@@@@@@@@                             @@@@@@@@@@@@@@@@@#        @@@@@@@@@@@@                       @@@@@@@@@@@@@@@@@@@@@.     .@@@@@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@              
                                             @@@@@@@@@@@@@@@@@@@@@@@@@@          :@@@@@@@@@@@-                     @@@@@@@@@@@@@@@@@@@@@@@@@@         @@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@@@@+ %         @@@@@@@@@@@@@@                                  :                   .@@@@@@@@@@:++                    @@@@@@@@@@@@@@@@@@@@@@%     .@@@@@@@@@@@@@@@@@@                   @@@@@@@@@@@:%@@@@@    :@@@@@@@@@@@@@@@             
                                            %@@@@@@@@@@@@@@@@@@@@@@@@@@          + #@@@@@@@@@@@                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@%       @@@@@@@@@@@@@@@                   @@@@@@@@@@@@@@@@-               @@@@@@@@@@@@@@#                     @@@@@@%       =@@@@@ @@@@@@@@@@@@@@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@%@@%%-:-       @@@@@@@@@@@@@@@@@@+                 @@@@@@@@@@@*=%@@@@#     @@@@@@@@@@@@@@@@            
                                            @@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@@@@@@@@@                 @@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@:                 @@@@@@@@@@@@@@@@..                @@@@@@@@@@@@@@                    @@@@@@@@@@@                  +@+    @@@@@@@@@@@@@               @@@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@@@@           
                                            @@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  @@@@@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@@@@@@@@@@         @@@@@@@@@@@@@@@@               *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            
                                              @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@. @@@@@-                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.                         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-                  @@@@@@   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    @@@@@#@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@                
                                            @*     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.-:          *@@                   @               .-====.                     #@@@*                   @@                                       @@@@@                     @@@             -.-=::-   +                  @@@@                .@@@=                                        .@@@                @@@@@@                               *@@@@           
                                            @@@@@@@@                                  @@@@@@@@@ %                 @@@@@@@@@@@@@@**              #@@@@@@@@@@@@@@@@@@ -@                 @ @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    @@@@@@@@@@@@@@++.               :@@@@@@@@@@@@@@@@               @@ @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                @@:@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            
                                              @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-                    @@@@@@-    -@@@@@@@@@@@@@@@@@@@@@@@@@%   *@@@@@@@               @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@               @@@@@@@@@@@@*  @@@@@@@@@@@@@@@@@@@@@@@@@@              
                                                  @@@@@@@@@@@@@@@@@@@@@@@@@==-:  :-:     =@@@@@  -@                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@-. +-@@=    -** =  #@               @  @@@@@:%#*@@@@@@%@.    . ..:#.#%% -+==+@@@@@#%@@@                     +%=.##- -..                          =@@@@@@ @              @  @@@@@%      .@@@@@@ +        **--*      %@@@@@                  @@@@@@      --  +@:+:*@@%-##@:@@*-@@@#  *@           
                                           .@@    :#.:=#@@@@@@@@%+*=*=+-:.:          +#%=@%=@@@@@@                @@@          :+%***@%%@@%##=-.::--.:   -*-    @@@@@@                @@@@@@#  . =-=@@@@@@@@@@@@@@@@@@-+:=#+:@@@@@@@@@@@                   @@@@@-  ..:--+***  ##.:-.. :-+ :::=+*=#+  :=@@@@@              @@@@@@   .=-            ==+*++#:   ..-==+-:.    @@@               %@@@@@@@@..*@%%%@.@*@*@@+%#+- @@@@@@@@@@@@@           
                                             @@@@@@@@@@::.@@@@@@@@@@@@*@%* :%@@@@@@@@@@@@@@@@@@                    @@@@@@@@@@@@@#=:==:  =---=%%%###@@@@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  @@@@@@@@@@@@@@%*  =@    .:    :+%@@@@@@@@@@@@@@                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            
                                                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                              @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@@@@@@@@@@@@@@@@@@@@@@@@.                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                            :@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*                           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@</pre></html>""");
        queen.setFont(new Font("Times New Roman", Font.PLAIN, 5));
        queen.setForeground(Color.decode("#ffffff"));
        queen.setBounds(50, 400, 1200, 700);
        
        
        
        
        JButton newGameButton = new JButton("""
                                            <html><pre>
                                                  ___           ___           ___                    ___           ___           ___           ___              
                                                 /\\__\\         /\\  \\         /\\__\\                  /\\  \\         /\\  \\         /\\__\\         /\\  \\             
                                                /::|  |       /::\\  \\       /:/ _/_                /::\\  \\       /::\\  \\       /::|  |       /::\\  \\            
                                               /:|:|  |      /:/\\:\\  \\     /:/ /\\__\\              /:/\\:\\  \\     /:/\\:\\  \\     /:|:|  |      /:/\\:\\  \\           
                                              /:/|:|  |__   /::\\~\\:\\  \\   /:/ /:/ _/_            /:/  \\:\\  \\   /::\\~\\:\\  \\   /:/|:|__|__   /::\\~\\:\\  \\          
                                             /:/ |:| /\\__\\ /:/\\:\\ \\:\\__\\ /:/_/:/ /\\__\\          /:/__/_\\:\\__\\ /:/\\:\\ \\:\\__\\ /:/ |::::\\__\\ /:/\\:\\ \\:\\__\\         
                                             \\/__|:|/:/  / \\:\\~\\:\\ \\/__/ \\:\\/:/ /:/  /          \\:\\  /\\ \\/__/ \\/__\\:\\/:/  / \\/__/~~/:/  / \\:\\~\\:\\ \\/__/         
                                                 |:/:/  /   \\:\\ \\:\\__\\    \\::/_/:/  /            \\:\\ \\:\\__\\        \\::/  /        /:/  /   \\:\\ \\:\\__\\           
                                                 |::/  /     \\:\\ \\/__/     \\:\\/:/  /              \\:\\/:/  /        /:/  /        /:/  /     \\:\\ \\/__/           
                                                 /:/  /       \\:\\__\\        \\::/  /                \\::/  /        /:/  /        /:/  /       \\:\\__\\             
                                                 \\/__/         \\/__/         \\/__/                  \\/__/         \\/__/         \\/__/         \\/__/             </pre></html>""");
        
        newGameButton.setBounds(410, 250, 600, 125);
        newGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 7));
        newGameButton.setForeground(Color.LIGHT_GRAY);
        newGameButton.setBorderPainted(false);
        newGameButton.setFocusPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setOpaque(false);
        
        
        
 
         newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newGameButton.setForeground(Color.WHITE); 
                newGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 8));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setForeground(Color.LIGHT_GRAY); 
                newGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 7));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                game();


            }
        });

       JButton loadGameButton = new JButton("""
                                            <html><pre>      ___   ___          ___          ___                   ___          ___          ___          ___     
                                                 /\\__\\ /\\  \\        /\\  \\        /\\  \\                 /\\  \\        /\\  \\        /\\__\\        /\\  \\    
                                                /:/  //::\\  \\      /::\\  \\      /::\\  \\               /::\\  \\      /::\\  \\      /::|  |      /::\\  \\   
                                               /:/  //:/\\:\\  \\    /:/\\:\\  \\    /:/\\:\\  \\             /:/\\:\\  \\    /:/\\:\\  \\    /:|:|  |     /:/\\:\\  \\  
                                              /:/  //:/  \\:\\  \\  /::\\~\\:\\  \\  /:/  \\:\\__\\           /:/  \\:\\  \\  /::\\~\\:\\  \\  /:/|:|__|__  /::\\~\\:\\  \\ 
                                             /:/__//:/__/ \\:\\__\\/:/\\:\\ \\:\\__\\/:/__/ \\:|__|         /:/__/_\\:\\__\\/:/\\:\\ \\:\\__\\/:/ |::::\\__\\/:/\\:\\ \\:\\__\\
                                             \\:\\  \\\\:\\  \\ /:/  /\\/__\\:\\/:/  /\\:\\  \\ /:/  /         \\:\\  /\\ \\/__/\\/__\\:\\/:/  /\\/__/~~/:/  /\\:\\~\\:\\ \\/__/
                                              \\:\\  \\\\:\\  /:/  /      \\::/  /  \\:\\  /:/  /           \\:\\ \\:\\__\\       \\::/  /       /:/  /  \\:\\ \\:\\__\\  
                                               \\:\\  \\\\:\\/:/  /       /:/  /    \\:\\/:/  /             \\:\\/:/  /       /:/  /       /:/  /    \\:\\ \\/__/  
                                                \\:\\__\\\\::/  /       /:/  /      \\::/__/               \\::/  /       /:/  /       /:/  /      \\:\\__\\    
                                                 \\/__/ \\/__/        \\/__/        ~~                    \\/__/        \\/__/        \\/__/        \\/__/    </pre></html>""");
        
        loadGameButton.setBounds(390, 400, 600, 125);
        loadGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 7));
        loadGameButton.setForeground(Color.LIGHT_GRAY);
        
        loadGameButton.setBorderPainted(false);
        loadGameButton.setFocusPainted(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setOpaque(false);
        
        
        loadGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadGameButton.setForeground(Color.WHITE);
                loadGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 8));

            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loadGameButton.setForeground(Color.LIGHT_GRAY); 
                loadGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 7));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                game();


            }
        });
        
        
        JButton exitButton = new JButton("""
                                          <html><pre>      ___          ___                ___     
                                              /\\  \\        |\\__\\        ___   /\\  \\    
                                             /::\\  \\       |:|  |      /\\  \\  \\:\\  \\   
                                            /:/\\:\\  \\      |:|  |      \\:\\  \\  \\:\\  \\  
                                           /::\\~\\:\\  \\     |:|__|__    /::\\__\\ /::\\  \\ 
                                          /:/\\:\\ \\:\\__\\____/::::\\__\\__/:/\\/__//:/\\:\\__\\
                                          \\:\\~\\:\\ \\/__/\\::::/~~/~  /\\/:/  /  /:/  \\/__/
                                           \\:\\ \\:\\__\\   ~~|:|~~|   \\::/__/  /:/  /     
                                            \\:\\ \\/__/     |:|  |    \\:\\__\\  \\/__/      
                                             \\:\\__\\       |:|  |     \\/__/             
                                              \\/__/        \\|__|                      </pre></html> """);
        
        exitButton.setBounds(650, 550, 500, 125);
        exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 7));
        exitButton.setForeground(Color.LIGHT_GRAY);
        
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setOpaque(false);
        
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setForeground(Color.WHITE); // Color when hovering
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setForeground(Color.LIGHT_GRAY); // Return to default color
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       
        
        
        frame.add(titleLabel);
        frame.add(queen);
        

        frame.add(newGameButton);
        frame.add(loadGameButton);
        //frame.add(exitButton);
        
        
             
        
        
        
        
        
       
        
        
        frame.setVisible(true);
    }
}
