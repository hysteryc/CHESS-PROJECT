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
    boolean whiteTurn = true;
    
    JFrame frame = new JFrame("Command.chess");
    Color outlineColour = Color.decode("#0b1124");
    Color backgroundColour = Color.decode("#262525");
    Color boardDarkColour = Color.decode("#586180");
    Color boardLightColour = Color.decode("#e1e6f2");
    
    Border selectedBorder = BorderFactory.createLineBorder(outlineColour, 3);
    
    
    // Sets the frame constraints
    public GUI()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 1100);
        frame.setLayout(null);
        frame.getContentPane().setBackground(backgroundColour);
        frame.setResizable(false);
    }
    
    // Returns the isWhite variable for a square
    public boolean isWhite(Coordinate position)
    {
        return squares[position.getFile()][position.getRow()].getPiece().isWhite;
    }
    
    // Returns the coordinate position of a button
    public Coordinate getPosition(JButton button)
    {
        String temp = button.getText();
        int file = Character.getNumericValue(temp.charAt(0));  
        int row = Character.getNumericValue(temp.charAt(1));
        Coordinate position = new Coordinate(file, row);
        
        return position;
    }
    
    // Returns the square position of a black/white king
    public Square getKing(boolean isWhite) 
    {
    
        ArrayList<Square> pieceSquares = isWhite ? whitePieces : blackPieces;
        
        for (Square item : pieceSquares) 
        {
        
            if (item.getPiece() instanceof King) return item; 
           
        }
        
        return null;
    }
    
    // Updates the positions of the the pieces in the whitePieces and blackPieces array
    // Used to save code for both temporary move functions and the move piece function
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
    
    // Makes a temporary move, used in King logic
    public Square tempMove(Coordinate origin, Coordinate destination) {
        
        
        Square originSquare = squares[origin.getFile()][origin.getRow()];
        Square destinationSquare = squares[destination.getFile()][destination.getRow()];
        Piece originPiece = originSquare.getPiece();
        
        
        Square victim = new Square(destinationSquare.getFile(), destinationSquare.getRow(), destinationSquare.getButton());
        
        victim.setPiece(destinationSquare.getPiece());
        
                      
        
        destinationSquare.setPiece(originPiece);
        originSquare.setPiece(null);

       
        updatePiece(originSquare, destinationSquare, originPiece);
        
        return victim;
    }
    
    // Undoes a temporary move, used in King logic
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
    
    // Moves a piece, only called if a user has clicked a square that was marked as a valid move
    public void movePiece(Coordinate origin, Coordinate destination)
    {
       // Moves piece 
        
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
    
    
    
    // This function is desinged to go through every enemy piece and check if they can attack current piece's king
    public int checkAttackers(Square kingPosition) 
    {
        
        
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
    return attackers;
    }
    
    // Uses the check attackers function to see if in check
    public boolean check() 
    {
    
        //This function uses the 'checkAttackers' function to see if we're in check 
        Square king = getKing(whiteTurn);
        
        int attackers = checkAttackers(king);
        
        return attackers > 0;
    }
    
    
    //If in check, checks valid moves for all pieces and if there are none, the game is over
    public boolean checkmate() 
    {
    
        ArrayList<Square> allyPieces = whiteTurn ? whitePieces : blackPieces;
        
                
        for(Square item : allyPieces)
        {
            ArrayList<Coordinate> validMoves = item.getPiece().generateLegalMoves(new Coordinate(item.getFile(), item.getRow()), this, false);
            
            if(!validMoves.isEmpty()) 
            {
                return false;
            }
        }
       
        return true;
    }
    
    
    //Runs back end promotion logic
    private void promotion(int choice, Coordinate position)
    {
    
        
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
    
    
    // Creates a pop up that forces the user to select a piece to promote 
    public void promotionMenu(Coordinate position) {
        
        Square promotionSquare = squares[position.getFile()][position.getRow()]; 
        boolean isWhite = promotionSquare.getPiece().isWhite();
        JDialog promotionDialog = new JDialog(frame, "Choose Promotion", true);        
        promotionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        promotionDialog.setLayout(null); 
        promotionDialog.setUndecorated(true); 
        promotionDialog.setBounds(560, 460, 180, 180); 
        
        promotionDialog.setOpacity(0.85f);
        
       
        
        JButton rook = new JButton("2");
        JButton knight = new JButton("4");
        JButton bishop = new JButton("3");
        JButton queen = new JButton("1");

        // Load icons
        rook.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE ROOK.png" : "chessFiles/chess Pieces/BLACK ROOK.png"));
        knight.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE KNIGHT.png" : "chessFiles/chess Pieces/BLACK KNIGHT.png"));
        bishop.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE BISHOP.png" : "chessFiles/chess Pieces/BLACK BISHOP.png"));
        queen.setIcon(new ImageIcon(isWhite ? "chessFiles/chess Pieces/WHITE QUEEN.png" : "chessFiles/chess Pieces/BLACK QUEEN.png"));

        
        JButton[] promotionOptions = { queen, rook, bishop, knight };
        for (JButton option : promotionOptions) {
            option.setFocusPainted(false);
            option.setContentAreaFilled(false);
            option.setBorder(selectedBorder);
            option.setOpaque(false);
            option.setForeground(new Color(0, 0, 0, 0)); 

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
    
     // Generates the valid moves for a piece and draws a border around the valid squares it can move to
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
    
    //Draws the board
    public void drawboard()
    {
        
        frame.getContentPane().removeAll();
        
        
        
        
        JPanel gamePanel = new JPanel();
        
        
        
        
        gamePanel.setLayout(null); 
        gamePanel.setBounds(0, 0, 1300, 1100); 
        gamePanel.setBackground(backgroundColour);
        
        
        boolean inCheck = check();
        boolean noMoves = checkmate();
        
         
        
        if(inCheck && noMoves) 
        {
            
            displayHeader(gamePanel, new ImageIcon(whiteTurn ? "ChessFiles/background images/checkmate black.png" : "ChessFiles/background images/checkmate white.png")); 
        }
        else if(!inCheck && noMoves)
        {
            displayHeader(gamePanel, new ImageIcon("ChessFiles/background images/stalemate.png"));
        }
        else if(inCheck)
        {
            displayCheck(gamePanel);
        }  
        
        
        
        int x = 350;
        int y = 250;
        
        Boolean isLight = false;
        
        
        for(int row = 7; row >= 0; row--)
        {
            for(int file = 0; file < 8; file++)
            {
                                
                isLight = !isLight;
                
                JButton button = squares[file][row].getButton();
                
                 if(squares[file][row].getPiece() != null) button.setIcon(squares[file][row].getPiece().getImage());
                
                
                button.setBounds(x, y, 75, 75);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setBackground( isLight ? boardLightColour : boardDarkColour);
                button.setForeground(new Color(0, 0, 0, 0));
                
                for (MouseListener ml : button.getMouseListeners()) {
                button.removeMouseListener(ml);
                }
                if(!noMoves)
                {
                button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Coordinate selectedPosition = getPosition(button);
                    Piece tempPiece = squares[selectedPosition.getFile()][selectedPosition.getRow()].getPiece(); 
                    
                    if(selected == null && tempPiece != null && (tempPiece.isWhite() != whiteTurn)) return;
                  
                    
                    
                    // Case 1: Nothing selected yet and clicked a piece
                    if (selected == null && button.getIcon() != null) {
                        selected = button;
                        validMoves = drawValidMoves(button); // highlight valid moves
                        button.setBorder(selectedBorder);
                        button.setBorderPainted(true);
                        
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
                        else if (button.getIcon() != null && 
                                 isWhite(getPosition(selected)) == isWhite(selectedPosition)) {
                            selected = button;
                            drawboard();
                            validMoves = drawValidMoves(button);
                            
                            button.setBorder(selectedBorder);
                            button.setBorderPainted(true);
                            
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
            }
                
                
                
                gamePanel.add(button);
                x = x + 75;
            }
            isLight = !isLight;

            
            
            
            
            y = y + 75;
            x = 350;
        }
        
                 
        ImageIcon capturedSelected = new ImageIcon("ChessFiles/background images/display captured selected.png");
        ImageIcon capturedUnselected = new ImageIcon("ChessFiles/background images/display captured.png");
        
        JButton displayCaptured = new JButton(capturedUnselected);
               
        
        displayCaptured.setBounds(270, 930, 750, 60);
        
        displayCaptured.setBorderPainted(false);
        displayCaptured.setFocusPainted(false);
        displayCaptured.setContentAreaFilled(false);
        displayCaptured.setOpaque(false);
        
        
        
 
         displayCaptured.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               displayCaptured.setIcon(capturedSelected);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                displayCaptured.setIcon(capturedUnselected);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                menu();


            }
        });
        
        
        
        ImageIcon goToMenuSelected = new ImageIcon("ChessFiles/background images/save and leave selected.png");
        ImageIcon goToMenuUnselected = new ImageIcon("ChessFiles/background images/save and leave unselected.png");
        
        JButton goToMenu = new JButton(goToMenuUnselected); 
        goToMenu.setBounds(330, 1000, 630, 40);
        goToMenu.setBorderPainted(false);
        goToMenu.setFocusPainted(false);
        goToMenu.setContentAreaFilled(false);
        goToMenu.setOpaque(false); 
        
        
        goToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                goToMenu.setIcon(goToMenuSelected);
                
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                goToMenu.setIcon(goToMenuUnselected);
                
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                menu();


            }
            
        });
        
        
        gamePanel.add(goToMenu);
        gamePanel.add(displayCaptured);
        //gamePanel.add(displayCapturedBlack);
        
        
        displayFiles(gamePanel, 350, 85);
        displayFiles(gamePanel, 350, 765);
        displayRows(gamePanel, 240, 50);
        displayRows(gamePanel, 905, 50);
        
        displayBackground(gamePanel);
        
        if(!noMoves) displayHeader(gamePanel, new ImageIcon(whiteTurn ? "ChessFiles/background images/whiteMove.png" : "ChessFiles/background images/blackMove.png"));
            
        
        
              
        
        
        
        
        
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.repaint();

    }
    
    //Generates a board from scratch
    public void start()
    {
        frame.getContentPane().removeAll();
        
        // Reset
        selected = null;
        validMoves.clear();
        whitePieces.clear();
        blackPieces.clear();
        whiteTurn = true;
        
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
    
    //Draws the main menu
    public void menu() {
       
        
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
                
        
        JPanel menuPanel = new JPanel();
        
        
        
        
        menuPanel.setLayout(null); 
        menuPanel.setBounds(0, 0, 1300, 1100); 
        menuPanel.setBackground(backgroundColour);
                
        
        
        ImageIcon iconTitle = new ImageIcon("ChessFiles/background images/title.png");
        JLabel titleLabel = new JLabel(iconTitle);
        titleLabel.setBounds(270, 20, 800, 300);

        
        
        ImageIcon newGameSelected = new ImageIcon("ChessFiles/background images/newgame selected.png");
        ImageIcon newGameUnselected = new ImageIcon("ChessFiles/background images/newgame unselected.png");
        
        JButton newGameButton = new JButton(newGameUnselected);
            
        newGameButton.setBounds(70, 340, 800, 125);
        
        newGameButton.setBorderPainted(false);
        newGameButton.setFocusPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setOpaque(false);
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newGameButton.setIcon(newGameSelected);            
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setIcon(newGameUnselected);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                start();


            }
        });
         menuPanel.add(newGameButton);
        
         
        
       
        ImageIcon loadGameSelected = new ImageIcon("ChessFiles/background images/loadgame selected.png");
        ImageIcon loadGameUnselected = new ImageIcon("ChessFiles/background images/loadgame unselected.png");
        
        JButton loadGameButton = new JButton(loadGameUnselected);
            
        loadGameButton.setBounds(-50, 450, 800, 125);
        
        loadGameButton.setBorderPainted(false);
        loadGameButton.setFocusPainted(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setOpaque(false);
        
        
        loadGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadGameButton.setIcon(loadGameSelected);

            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loadGameButton.setIcon(loadGameUnselected);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                start();


            }
        });
        
        menuPanel.add(loadGameButton);
        
        
        
        ImageIcon exitSelected = new ImageIcon("ChessFiles/background images/exit selected.png");
        ImageIcon exitUnselected = new ImageIcon("ChessFiles/background images/exit unselected.png");
        
        JButton exitButton = new JButton(exitUnselected);
        
        exitButton.setBounds(-190, 560, 800, 125);
        exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 2));
        exitButton.setForeground(Color.LIGHT_GRAY);
        
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setOpaque(false);
        
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitSelected);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitUnselected);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();


            }
        });
        menuPanel.add(exitButton);
        
        
        
        
        
        
        
        
        
        
        
        
        
       
        ImageIcon icon = new ImageIcon("ChessFiles/background images/commander.png");
        
        JLabel background = new JLabel(icon);
        background.setBounds(800, 160, 500, 1000);
        
        
        JLabel background2 = new JLabel(icon);
        background2.setBounds(500, 400, 500, 1000);
        
        JLabel background3 = new JLabel(icon);
        background3.setBounds(200, 640, 500, 1000);
        
        JLabel background4 = new JLabel(icon);
        background4.setBounds(-100, 860, 500, 1000);
        
        
        
        menuPanel.add(titleLabel);
        
        
        
        menuPanel.add(background4);
        menuPanel.add(background3);
        menuPanel.add(background2);
        menuPanel.add(background);
        

        
        
        frame.add(menuPanel);
             
        frame.setVisible(true);
    }
    
    
    public void displayHeader(JPanel gamePanel, ImageIcon icon)
    {
        JLabel text = new JLabel(icon);
        text.setBounds(245, -30, 800, 250);
        gamePanel.add(text);
    }
    public void displayCheck(JPanel gamePanel)
    {
        ImageIcon icon = new ImageIcon("ChessFiles/background images/check.png");
        JLabel text = new JLabel(icon);
        text.setBounds(0, -24, 300, 250);
        
        JLabel text2 = new JLabel(icon);
        text2.setBounds(980, -24, 300, 250);
        gamePanel.add(text);
        gamePanel.add(text2);
    }
    
    public void displayRows(JPanel gamePanel, int x, int y)
    {
        ImageIcon icon = new ImageIcon("ChessFiles/background images/rows.png");
        JLabel text = new JLabel(icon);
        
        text.setBounds(x, y, 150, 1000);
        gamePanel.add(text);
    }
    
    public void displayFiles(JPanel gamePanel, int x, int y)
    {
        ImageIcon icon = new ImageIcon("ChessFiles/background images/files.png");
        JLabel text = new JLabel(icon);
        

        text.setBounds(x, y, 600, 250);
        gamePanel.add(text);
    }
    
    public void displayBackground(JPanel gamePanel)
    {
        
        ImageIcon iconLeft = new ImageIcon("ChessFiles/background images/warrior left.png");
        JLabel left = new JLabel(iconLeft);
        
        ImageIcon iconRight = new ImageIcon("ChessFiles/background images/warrior right.png");
        JLabel right = new JLabel(iconRight);

        left.setBounds(-100, 140, 500, 1000);
        right.setBounds(885, 140, 500, 1000);
        
        gamePanel.add(left);
        gamePanel.add(right);
    }
    
    
    
}


/*

Requirements that we don't meet: 
- Code smells, examples: long methods, static ints for positions, Separation of the UI
- Input/output messages for wrong inputs 
- Quiting the program at pop-up points


*/