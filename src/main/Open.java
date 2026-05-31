package main;

import gui.MainWindow;
import javax.swing.SwingUtilities;

public class Open {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}