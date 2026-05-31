package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import data.AppData;

@SuppressWarnings("serial")
public class MainScreen extends JPanel {

    public static final int MainScreenWidth = 500;
    public static final int MainScreenHeight = 600;
    public static final Dimension MainScreenSize = new Dimension(MainScreenWidth, MainScreenHeight);
    public static final Color bgColor = new Color(0xb8a57d);
    
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    
    public AppData appData;

    public MainScreen() {
        setPreferredSize(MainScreenSize);
        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);

        addScreen("Loading", new gui.screens.Loading());
        showScreen("Loading");
        
        appData = new AppData();
        
        addScreen("Recipe", new gui.screens.Recipe(this));
        showScreen("Recipe");
    }

    public void addScreen(String key, JPanel screen) {
        cards.add(screen, key);
    }

    public void showScreen(String key) {
        cardLayout.show(cards, key);
    }
}