package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    public MainWindow() {
    	Image logo = new ImageIcon(getClass().getResource("/image/logo.png")).getImage();
        setIconImage(logo);
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Jayd's Recipes");
        
        MainScreen mainScreen = new MainScreen();
        add(mainScreen);
        pack();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
}