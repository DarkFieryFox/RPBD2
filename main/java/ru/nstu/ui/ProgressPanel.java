package ru.nstu.ui;


import ru.nstu.entity.Progress;
import ru.nstu.entity.Schoolchild;
import ru.nstu.repository.ProgressRepo;
import ru.nstu.repository.SchoolchildRepo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class ProgressPanel extends BaseEntityPanel {

    private final ProgressRepo repo = new ProgressRepo();
    private final SchoolchildRepo schoolchildRepo = new SchoolchildRepo();


    private final Map<Integer, Progress> rowToProgress = new HashMap<>();

    public ProgressPanel() {
        super();
        fillData();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void fillData() {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) tableModel.removeRow(0);
        List<Progress> progresses = repo.findAll();
        List<Object[]> all = progresses.stream()
                .map(progress ->
                        new Object[]{
                                progress.getClasss(),
                                progress.getYear(),
                                progress.getSubject(),
                                progress.getQuarter(),
                                progress.getHalf_yearly(),
                                progress.getYearly(),
                                progress.getExam(),
                                progress.getFinaly(),

                        }
                )
                .collect(Collectors.toList());
        tableModel.setColumnIdentifiers(new Object[]{"Class","year","Subject","Quarter","half_yearly","yearly","exam","finaly"});
        for (int i = 0; i < progresses.size(); i++) {
            tableModel.addRow(all.get(i));
            rowToProgress.put(i, progresses.get(i));
        }
    }

    @Override
    protected void update() {
        fillData();
    }

    @Override
    protected JPanel getMenu() {
        JButton add = new JButton("Add");
        add.addActionListener(this::onAdd);
        add.setPreferredSize(new Dimension(200, 100));

        JButton edit = new JButton("Edit");
        edit.addActionListener(this::onEdit);
        edit.setPreferredSize(new Dimension(200, 100));

        JButton delete = new JButton("Delete");
        delete.addActionListener(this::onDelete);
        delete.setPreferredSize(new Dimension(200, 100));

        JButton searchByClasss = new JButton("Search by classs");
        searchByClasss.addActionListener(this::searchByClasss);
        searchByClasss.setPreferredSize(new Dimension(200, 50));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(searchByClasss);


        return buttonPanel;
    }

    private void searchByClasss(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        classs -> {
                            List<Long> allByClasss = repo.findAllByClasss(classs).stream().map(Progress::getId).collect(Collectors.toList());
                            selectRows(allByClasss);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }

    private void selectRows(List<Long> ids){
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();
        rowToProgress.forEach((row, progress) -> {
            if(ids.contains(progress.getId())){
                selectionModel.addSelectionInterval(row, row);
            }
        });
    }

    private JPanel searchPanel(Consumer<String> onClick) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(200, 40));
        JButton button = new JButton("Ok");
        button.addActionListener(e -> {
            if (!input.getText().isEmpty()) {
                onClick.accept(input.getText());
            }
        });



        panel.add(input);
        panel.add(button);
        return panel;
    }

    private JPanel modifyPanel(Consumer<Progress> onClick, Progress progress) {
        Progress copy = progress == null ? new Progress() : progress;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JTextField classsTextField = new JTextField();
        String classs = copy.getClasss();
        if (classs != null) {
            classsTextField.setText(classs);
        }
        classsTextField.setPreferredSize(new Dimension(100, 20));

        JTextField yearTextField = new JTextField();
        Integer year = copy.getYear();
        if (year != null) {
            yearTextField.setText(year.toString());
        }
        yearTextField.setPreferredSize(new Dimension(100, 20));

        JTextField subjectTextField = new JTextField();
        String subject = copy.getSubject();
        if (subject != null) {
            subjectTextField.setText(subject);
        }
        subjectTextField.setPreferredSize(new Dimension(100, 20));

        JTextField quarterTextField = new JTextField();
        Integer quarter = copy.getQuarter();
        if (quarter != null) {
            quarterTextField.setText(quarter.toString());
        }
        quarterTextField.setPreferredSize(new Dimension(100, 20));

        JTextField half_yearlyTextField = new JTextField();
        Integer half_yearly = copy.getHalf_yearly();
        if (half_yearly != null) {
            half_yearlyTextField.setText(half_yearly.toString());
        }
        half_yearlyTextField.setPreferredSize(new Dimension(100, 20));

        JTextField yearlyTextField = new JTextField();
        Integer yearly = copy.getYearly();
        if (yearly != null) {
            yearlyTextField.setText(yearly.toString());
        }
        yearlyTextField.setPreferredSize(new Dimension(100, 20));

        JTextField examTextField = new JTextField();
        Integer exam = copy.getExam();
        if (exam != null) {
            examTextField.setText(exam.toString());
        }
        examTextField.setPreferredSize(new Dimension(100, 20));

        JTextField finalyTextField = new JTextField();
        Integer finaly = copy.getFinaly();
        if (finaly != null) {
            finalyTextField.setText(finaly.toString());
        }
        finalyTextField.setPreferredSize(new Dimension(100, 20));



        JButton button = new JButton("Ok");
        button.addActionListener(e -> {
            if (!classsTextField.getText().isEmpty()) {
                copy.setClasss(classsTextField.getText());
            }

            if (!subjectTextField.getText().isEmpty()) {
                copy.setSubject(subjectTextField.getText());
            }
            if (!yearTextField.getText().isEmpty()) {
                copy.setYear(Integer.parseInt(yearTextField.getText()));
                }

            if (!quarterTextField.getText().isEmpty()) {
                copy.setQuarter(Integer.parseInt(quarterTextField.getText()));
            }
            if (!half_yearlyTextField.getText().isEmpty()) {
                copy.setHalf_yearly(Integer.parseInt(half_yearlyTextField.getText()));
            }

            if (!yearlyTextField.getText().isEmpty()) {
                copy.setYearly(Integer.parseInt(yearlyTextField.getText()));
            }
            if (!examTextField.getText().isEmpty()) {
                copy.setExam(Integer.parseInt(examTextField.getText()));
            }
            if (!finalyTextField.getText().isEmpty()) {
                copy.setFinaly(Integer.parseInt(finalyTextField.getText()));
            }

            onClick.accept(copy);
        });

        panel.add(classsTextField);
        panel.add(yearTextField);
        panel.add(subjectTextField);
        panel.add(quarterTextField);
        panel.add(half_yearlyTextField);
        panel.add(yearlyTextField);
        panel.add(examTextField);
        panel.add(finalyTextField);
        panel.add(button);
        return panel;
    }

    private void onAdd(ActionEvent e) {
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        progress -> {
                            repo.save(progress);
                            fillData();
                            modifyContainer.removeAll();
                            revalidate();
                        },
                        null
                )
        );
        revalidate();
    }

    private void onEdit(ActionEvent e) {
        Progress f = rowToProgress.get(table.getSelectedRow());
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        progress -> {
                            repo.edit(progress);
                            fillData();
                            modifyContainer.removeAll();
                            revalidate();
                        },
                        f
                )
        );
        revalidate();
    }

    private void onDelete(ActionEvent e) {
        Progress progress = rowToProgress.get(table.getSelectedRow());
        repo.delete(progress);
        fillData();
    }
}
