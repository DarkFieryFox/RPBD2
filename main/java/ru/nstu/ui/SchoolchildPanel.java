package ru.nstu.ui;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.sql.Date;

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


public class SchoolchildPanel extends BaseEntityPanel {

    private final SchoolchildRepo repo = new SchoolchildRepo();
    private final ProgressRepo progressRepo = new ProgressRepo();

    private final Map<Integer, Schoolchild> rowToSchoolchild = new HashMap<>();

    public SchoolchildPanel() {
        super();
        fillData();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void fillData() {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) tableModel.removeRow(0);
        List<Schoolchild> schoolchildren = repo.findAll();
        List<Object[]> all = schoolchildren.stream()
                .map(schoolchild ->
                        new Object[]{
                                schoolchild.getName(),
                                schoolchild.getProgresses().stream().map(Progress::getClasss).collect(Collectors.joining(", ")),
                                schoolchild.getSurname(),
                                schoolchild.getPatronymic(),
                                schoolchild.getAddress(),
                                schoolchild.getBirthday(),
                                schoolchild.getYear_admission(),

                        }
                )
                .collect(Collectors.toList());
        tableModel.setColumnIdentifiers(new Object[]{"Name", "class","surname","patronymic","address","birthday","year_admission"});
        for (int i = 0; i < schoolchildren.size(); i++) {
            tableModel.addRow(all.get(i));
            rowToSchoolchild.put(i, schoolchildren.get(i));
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
        add.setPreferredSize(new Dimension(300, 50));

        JButton edit = new JButton("Edit");
        edit.addActionListener(this::onEdit);
        edit.setPreferredSize(new Dimension(100, 50));

        JButton delete = new JButton("Delete");
        delete.addActionListener(this::onDelete);
        delete.setPreferredSize(new Dimension(100, 50));

        JButton searchByName = new JButton("Search by name");
        searchByName.addActionListener(this::searchByName);
        searchByName.setPreferredSize(new Dimension(200, 50));

        JButton searchBySurname = new JButton("Search by surname");
        searchBySurname.addActionListener(this::searchBySurname);
        searchBySurname.setPreferredSize(new Dimension(200, 50));

        JButton searchByPatronymic = new JButton("Search by patronymic");
        searchByPatronymic.addActionListener(this::searchByPatronymic);
        searchByPatronymic.setPreferredSize(new Dimension(200, 50));

        JButton searchByAddress = new JButton("Search by address");
        searchByAddress.addActionListener(this::searchByAddress);
        searchByAddress.setPreferredSize(new Dimension(200, 50));

        JButton searchByYear_admission = new JButton("Search by Year_admission");
        searchByYear_admission.addActionListener(this::searchByYear_admission);
        searchByYear_admission.setPreferredSize(new Dimension(200, 50));

        JButton searchByBirthday = new JButton("Search by Birthday");
        searchByBirthday.addActionListener(this::searchByBirthday);
        searchByBirthday.setPreferredSize(new Dimension(200, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
     
        buttonPanel.add(searchByName);
        buttonPanel.add(searchBySurname);
        buttonPanel.add(searchByPatronymic);
        buttonPanel.add(searchByAddress);
        buttonPanel.add(searchByYear_admission);
        buttonPanel.add(searchByBirthday);

        return buttonPanel;
    }



    private void searchByName(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        name -> {
                            List<Long> allByName = repo.findAllByName(name).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allByName);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }
    private void searchBySurname(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        surname -> {
                            List<Long> allBySurname = repo.findAllBySurname(surname).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allBySurname);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }

    private void searchByPatronymic(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        patronymic -> {
                            List<Long> allByPatronymic = repo.findAllByPatronymic(patronymic).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allByPatronymic);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }

    private void searchByAddress(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        address -> {
                            List<Long> allByAddress = repo.findAllByAddress(address).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allByAddress);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }

    private void searchByYear_admission(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(

                        r -> {
                            int year_admission;
                            try{
                                year_admission = Integer.parseInt(r);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                            List<Long> allByRating = repo.findAllByYear_admission(year_admission).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allByRating);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }
    private void searchByBirthday(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        birthday -> {
                            List<Long> allByBirthday = repo.findAllByBirthday(Date.valueOf(birthday)).stream().map(Schoolchild::getId).collect(Collectors.toList());
                            selectRows(allByBirthday);
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
        rowToSchoolchild.forEach((row, schoolchild) -> {
            if(ids.contains(schoolchild.getId())){
                selectionModel.addSelectionInterval(row, row);
            }
        });
    }

    private JPanel searchPanel(Consumer<String> onClick) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(100, 40));
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

    private JPanel modifyPanel(Consumer<Schoolchild> onClick, Schoolchild schoolchild) {
        Schoolchild copy = schoolchild == null ? new Schoolchild() : schoolchild;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JTextField nameTextField = new JTextField();
        String name = copy.getName();
        if (name != null) {
            nameTextField.setText(name);
        }
        nameTextField.setPreferredSize(new Dimension(100, 20));

        JTextField surnameTextField = new JTextField();
        String surname = copy.getSurname();
        if (surname != null) {
            surnameTextField.setText(surname);
        }
        surnameTextField.setPreferredSize(new Dimension(100, 20));

        JTextField patronymicTextField = new JTextField();
        String patronymic = copy.getPatronymic();
        if (patronymic != null) {
            patronymicTextField.setText(patronymic);
        }
        patronymicTextField.setPreferredSize(new Dimension(100, 20));

        JTextField year_admissionTextField = new JTextField();
        Integer year_admission = copy.getYear_admission();
        if (year_admission != null) {
            year_admissionTextField.setText(year_admission.toString());
        }
        year_admissionTextField.setPreferredSize(new Dimension(100, 20));

        JTextField birthdayTextField = new JTextField();
        Date birthday = copy.getBirthday();
        if (birthday != null) {
            birthdayTextField.setText(birthday.toString());
        }
        birthdayTextField.setPreferredSize(new Dimension(100, 20));

        JTextField addressTextField = new JTextField();
        String address = copy.getAddress();
        if (address != null) {
            addressTextField.setText(address);
        }
        addressTextField.setPreferredSize(new Dimension(100, 20));

        Progress[] progresses = progressRepo.findAll().toArray(new Progress[0]);
        JList<Progress> progressList = new JList<>(progresses);
        progressList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        List<Integer> progressIndexes = copy.getProgresses().stream()
                .map(List.of(progresses)::indexOf).collect(Collectors.toList());
        int[] progressIndexesArray = new int[progressIndexes.size()];
        int i = 0;
        for (Integer progressIndex : progressIndexes) {
            progressIndexesArray[i++] = progressIndex;
        }
        progressList.setSelectedIndices(progressIndexesArray);


        JButton button = new JButton("Ok");
        button.addActionListener(e -> {
            if (!nameTextField.getText().isEmpty()) {
                copy.setName(nameTextField.getText());
            }
            copy.setProgresses(Arrays.stream(progressList.getSelectedIndices()).mapToObj(index -> progresses[index]).collect(Collectors.toList()));

            if (!surnameTextField.getText().isEmpty()) {
                copy.setSurname(surnameTextField.getText());
            }
            if (!patronymicTextField.getText().isEmpty()) {
                copy.setPatronymic(patronymicTextField.getText());
            }
            if (!addressTextField.getText().isEmpty()) {
                copy.setAddress(addressTextField.getText());
            }
            if (!birthdayTextField.getText().isEmpty()) {
                copy.setBirthday(Date.valueOf((birthdayTextField.getText())));
            }

            if (!year_admissionTextField.getText().isEmpty()) {
                copy.setYear_admission(Integer.parseInt(year_admissionTextField.getText()));
            }

            onClick.accept(copy);
        });

        panel.add(nameTextField);
        panel.add(new JScrollPane(progressList));
        panel.add(surnameTextField);
        panel.add(patronymicTextField);
        panel.add(addressTextField);
        panel.add(birthdayTextField);
        panel.add(year_admissionTextField);

        panel.add(button);
        return panel;
    }

    private void onAdd(ActionEvent e) {
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        schoolchild -> {
                            repo.save(schoolchild);
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
        Schoolchild f = rowToSchoolchild.get(table.getSelectedRow());
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        schoolchild -> {
                            repo.edit(schoolchild);
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
        Schoolchild schoolchild = rowToSchoolchild.get(table.getSelectedRow());
        repo.delete(schoolchild);
        fillData();
    }
}
