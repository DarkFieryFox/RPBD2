package ru.nstu.ui;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() throws HeadlessException {
        super("LAB 2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel first = new JPanel();
        first.add(new JTextField("First"));

        JTabbedPane tabbedPane = new JTabbedPane();
       tabbedPane.addTab("Parent", new ParentPanel());
        tabbedPane.addTab("Schoolchild", new SchoolchildPanel());
        tabbedPane.addTab("Progress", new ProgressPanel());
        tabbedPane.addChangeListener(l -> ((BaseEntityPanel) tabbedPane.getSelectedComponent()).update());

        getContentPane().add(tabbedPane);

        setPreferredSize(new Dimension(1200, 600));
        pack();
        setVisible(true);
    }
}
