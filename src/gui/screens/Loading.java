package gui.screens;

import gui.MainScreen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Loading extends JPanel {

    private static final int MainScreenWidth = MainScreen.MainScreenWidth;
    private static final int MainScreenHeight = MainScreen.MainScreenHeight;
    private static final Dimension MainScreenSize = MainScreen.MainScreenSize;
    private static final Color bgColor = MainScreen.bgColor;
    private static final int gifWidth = 200;
    private static final int gifHeight = 200;
    
    private final Image loadingGif = new ImageIcon(getClass().getResource("/image/coffee_pour.gif")).getImage();

    public Loading() {
        setPreferredSize(MainScreenSize);
        setBackground(bgColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (MainScreenWidth - gifWidth) / 2;
        int y = ((MainScreenHeight - gifHeight) / 2) - 30;

        if (loadingGif != null) {
            g.drawImage(loadingGif, x, y, gifWidth, gifHeight, this);
        }
    }
}