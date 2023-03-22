package ru.nstu.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class BaseEntityPanel extends JPanel {


    protected final DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new Object[]{});
    protected final JTable table = new JTable(tableModel);

    protected JPanel modifyContainer = new JPanel();

    public BaseEntityPanel() {
        setLayout(new BorderLayout());
        add(modifyContainer, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(getMenu(), BorderLayout.EAST);
    }

    protected abstract JPanel getMenu();
    protected abstract void update();
}
