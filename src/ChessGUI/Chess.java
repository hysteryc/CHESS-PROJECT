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
import java.util.ArrayList;

public class Chess extends JPanel {
    
    JFrame frame;
    String grey = "#262525";
    String light_grey = "#3b3a39";
    boolean exit = false;
    
    
    public Chess(JFrame frame)
    {
        this.frame = frame;
        board();
    }
    
    public void board()
    {
    JPanel gamePanel = new JPanel();
        
    ArrayList<JButton> squares = new ArrayList();
        gamePanel.setLayout(null); // Important since frame uses null layout
        gamePanel.setBounds(0, 0, 1300, 1100); // Match frame size
        gamePanel.setBackground(Color.decode(grey));
        
        
        JButton goToMenu = new JButton("Back to menu");
        goToMenu.setBounds(410, 250, 600, 125);
        goToMenu.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        goToMenu.setForeground(Color.LIGHT_GRAY);
        goToMenu.setBorderPainted(false);
        goToMenu.setFocusPainted(false);
        goToMenu.setContentAreaFilled(false);
        goToMenu.setOpaque(false);        
        
        
        
        int index;
        
        
        int x = 100;
        int y = 100;
        
        
        
        for(int i = 0; i < 8; i++)
        {
            y = y + 75;
            for(int ii = 0; ii < 8; ii++)
            {
                x = x + 75;
                squares.add(new JButton());
                index = (i * 8 + ii);
                
                Boolean isLight = (i + ii)%2 != 0;
                
                JButton temp = squares.get(index);
                temp.setBounds(x, y, 75, 75);
                temp.setBorderPainted(false);

                temp.setBackground(( isLight ? (Color.decode(light_grey)) : (Color.LIGHT_GRAY)));
                gamePanel.add(temp);
            }
            
            x = 100;
        }
        
        
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
                exit = true;


            }
        });
        
        
        gamePanel.add(goToMenu);
        
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.repaint();
    
    
}
}


