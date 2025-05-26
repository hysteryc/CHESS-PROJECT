/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CHESS_ASP2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author teddy
 */
public class Database 
{
    // Embedded Database url 
    public static final String url = "jdbc:derby:ChessDB;create=true";
    
    static 
    {
        try 
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        createTables();
    }

    // Creates Tables on First Run
    private static void createTables() {
    try (Connection conn = DriverManager.getConnection(url)) {
        DatabaseMetaData meta = conn.getMetaData();

        if (!tableExists(meta, "BOARDSTATE")) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE TABLE boardstate (row INT, col INT, symbol VARCHAR(10))");
            }
        }
        if (!tableExists(meta, "CAPTUREDWHITE")) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE TABLE capturedwhite (symbol VARCHAR(10))");
            }
        }
        if (!tableExists(meta, "CAPTUREDBLACK")) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE TABLE capturedblack (symbol VARCHAR(10))");
            }
        }
        if (!tableExists(meta, "TURNSTATE")) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE TABLE turnstate (iswhiteturn BOOLEAN)");
            }
        }
    } catch (SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
    }
}
    
    // Checks whether the Table exists or not (for Multiple starts of the same program)
    private static boolean tableExists(DatabaseMetaData meta, String tableName) throws SQLException 
    {
        try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }
    
    // Saves the State of the Game to the Database (Only Accepts one Save at a time)
    public static void Save(Board board, boolean whiteTurn, ArrayList<Piece> capturedWhite, ArrayList<Piece> capturedBlack)
    {
        try(Connection conn = DriverManager.getConnection(url))
        {
            clearPreviousSave(conn);
            
            // Save the state of the game
            String boardSQL = "INSERT INTO boardstate (row, col, symbol) VALUES (?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(boardSQL))
            {
                for(int row = 0; row < 8; row++)
                {
                    for(int col = 0; col < 8; col++)
                    {
                        Piece piece = board.getPieceAt(row, col);
                        if(piece != null)
                        {
                            ps.setInt(1, row);
                            ps.setInt(2, col);
                            ps.setString(3, piece.getSymbol());
                            ps.addBatch();
                        }
                    }
                }
                ps.executeBatch();
            }
            
            saveCapturedPieces(conn, "capturedwhite", capturedWhite);
            saveCapturedPieces(conn, "capturedblack", capturedBlack);
            
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO turnstate (iswhiteturn) VALUES (?)")) 
            {
                ps.setBoolean(1, whiteTurn);
                ps.executeUpdate();
            }
            
            System.out.println("Game saved to database.");
            
        } catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    
    // Need to Discuss what we are doing with these (Refactored from FileIO Class)
    private static void saveCapturedPieces(Connection conn, String tableName, ArrayList<Piece> pieces) throws SQLException 
    {
        String SQL = "INSERT INTO " + tableName + " (symbol) VALUES (?)";
        try(PreparedStatement ps = conn.prepareStatement(SQL))
        {
            for(Piece piece : pieces)
            {
                ps.setString(1, piece.getSymbol());
                ps.addBatch();
            }
            ps.executeBatch();
        }  
    }
    
    // Need to Discuss what we are doing with these (Refactored from FileIO Class)
    private static void clearPreviousSave(Connection conn) throws SQLException 
    {
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM boardstate");
        statement.executeUpdate("DELETE FROM capturedwhite");
        statement.executeUpdate("DELETE FROM capturedblack");
        statement.executeUpdate("DELETE FROM turnstate");
    }
    
    //Load Game From the Embedded Database
    public static void Load(Board board, ArrayList<Piece> capturedWhite, ArrayList<Piece> capturedBlack)
    {
        board.initializeSquares();
        
        try(Connection conn = DriverManager.getConnection(url))
        {
            // Load the Game From the Database
            try(Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM boardstate"))
            {
                while(rs.next())
                {
                    int row = rs.getInt("row");
                    int col = rs.getInt("col");
                    String symbol = rs.getString("symbol");
                    Piece piece = PieceCreator.createFromSymbol(symbol);
                    if (piece != null) 
                    {
                        board.addPieceToSide(piece);
                        board.setPieceAt(row,col,piece);
                    }
                }
            }
            
            // Load captured
            loadCapturedPieces(conn, "capturedwhite", capturedWhite);
            loadCapturedPieces(conn, "capturedblack", capturedBlack);
            
            // Load turn
            try(Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM turnstate"))
            {
                if(rs.next())
                {
                    Board.setWhiteTurn(rs.getBoolean("iswhiteturn"));
                }       
            }
            
            System.out.println("Game loaded from database.");  
            
        }
        catch(SQLException e)
        {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    
    // Need to Discuss what we are doing with these (Refactored from FileIO Class)
    public static void loadCapturedPieces(Connection conn, String tableName, ArrayList<Piece> pieces) throws SQLException
    {
        pieces.clear(); 
        try(Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName))
        {
            while(rs.next())
            {
                String symbol = rs.getString("symbol");
                pieces.add(PieceCreator.createFromSymbol(symbol));
            }
        }
    }
}
