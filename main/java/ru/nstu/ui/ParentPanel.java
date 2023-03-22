package ru.nstu.ui;


import ru.nstu.entity.Schoolchild;
import ru.nstu.entity.Parent;
import ru.nstu.repository.SchoolchildRepo;
import ru.nstu.repository.ParentRepo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import java.util.stream.Collectors;

public class ParentPanel extends BaseEntityPanel {

    private final ParentRepo repo = new ParentRepo();
    private final SchoolchildRepo schoolchildRepo = new SchoolchildRepo();

    private final Map<Integer, Parent> rowToParent = new HashMap<>();

    public ParentPanel() {
        super();
        fillData();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void fillData() {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) tableModel.removeRow(0);
        List<Parent> parents = repo.findAll();
        List<Object[]> all = parents.stream()
                .map(parent ->
                        new Object[]{
                                parent.getName(),
                                parent.getSurname(),
                                parent.getPatronymic(),
                                parent.getDegree(),
                                parent.getAddress(),
                                parent.getSchoolchildren().stream().map(Schoolchild::getName).collect(Collectors.joining(", ")),

                        }
                )
                .collect(Collectors.toList());
        tableModel.setColumnIdentifiers(new Object[]{"Name","surname","patronymic","degree","address","schoolchild"});
        for (int i = 0; i < parents.size(); i++) {
            tableModel.addRow(all.get(i));
            rowToParent.put(i, parents.get(i));
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
        add.setPreferredSize(new Dimension(100, 50));

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

        JButton searchByDegree = new JButton("Search by degree");
        searchByDegree.addActionListener(this::searchByDegree);
        searchByDegree.setPreferredSize(new Dimension(200, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);

        buttonPanel.add(searchByName);
        buttonPanel.add(searchBySurname);
        buttonPanel.add(searchByPatronymic);
        buttonPanel.add(searchByDegree);
        buttonPanel.add(searchByAddress);
        return buttonPanel;
    }


    private void searchByName(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        name -> {
                            List<Long> allByName = repo.findAllByName(name).stream().map(Parent::getId).collect(Collectors.toList());
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
                            List<Long> allBySurname = repo.findAllBySurname(surname).stream().map(Parent::getId).collect(Collectors.toList());
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
                            List<Long> allByPatronymic = repo.findAllByPatronymic(patronymic).stream().map(Parent::getId).collect(Collectors.toList());
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
                            List<Long> allByAddress = repo.findAllByAddress(address).stream().map(Parent::getId).collect(Collectors.toList());
                            selectRows(allByAddress);
                            modifyContainer.removeAll();
                            revalidate();
                        }
                )
        );
        revalidate();
    }

    private void searchByDegree(ActionEvent actionEvent) {
        modifyContainer.removeAll();
        modifyContainer.add(
                searchPanel(
                        degree -> {
                            List<Long> allByDegree = repo.findAllByDegree(degree).stream().map(Parent::getId).collect(Collectors.toList());
                            selectRows(allByDegree);
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
        rowToParent.forEach((row, parent) -> {
            if(ids.contains(parent.getId())){
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

    private JPanel modifyPanel(Consumer<Parent> onClick, Parent parent) {
        Parent copy = parent == null ? new Parent() : parent;

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

        JTextField degreeTextField = new JTextField();
        String degree = copy.getDegree();
        if (degree != null) {
            degreeTextField.setText(degree);
        }
        degreeTextField.setPreferredSize(new Dimension(100, 20));

        JTextField addressTextField = new JTextField();
        String address = copy.getAddress();
        if (address != null) {
            addressTextField.setText(address);
        }
        addressTextField.setPreferredSize(new Dimension(100, 20));

        Schoolchild[] schoolchildren = schoolchildRepo.findAll().toArray(new Schoolchild[0]);
        JList<Schoolchild> schoolchildList = new JList<>(schoolchildren);
        schoolchildList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        List<Integer> schoolchildIndexes = copy.getSchoolchildren().stream()
                .map(List.of(schoolchildren)::indexOf).collect(Collectors.toList());
        int[] schoolchildIndexesArray = new int[schoolchildIndexes.size()];
        int i = 0;
        for (Integer schoolchildIndex : schoolchildIndexes) {
            schoolchildIndexesArray[i++] = schoolchildIndex;
        }
        schoolchildList.setSelectedIndices(schoolchildIndexesArray);



        JButton button = new JButton("Ok");
        button.addActionListener(e -> {
            if (!nameTextField.getText().isEmpty()) {
                copy.setName(nameTextField.getText());
            }
            if (!surnameTextField.getText().isEmpty()) {
                copy.setSurname(surnameTextField.getText());
            }
            if (!patronymicTextField.getText().isEmpty()) {
                copy.setPatronymic(patronymicTextField.getText());
            }
            if (!degreeTextField.getText().isEmpty()) {
                copy.setDegree(degreeTextField.getText());
            }
            if (!addressTextField.getText().isEmpty()) {
                copy.setAddress(addressTextField.getText());
            }
            copy.setSchoolchildren(Arrays.stream(schoolchildList.getSelectedIndices()).mapToObj(index -> schoolchildren[index]).collect(Collectors.toList()));

            onClick.accept(copy);
        });

        panel.add(nameTextField);
        panel.add(surnameTextField);
        panel.add(patronymicTextField);
        panel.add(degreeTextField);
        panel.add(addressTextField);
        panel.add(new JScrollPane(schoolchildList));
        panel.add(button);
        return panel;
    }

    private void onAdd(ActionEvent e) {
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        parent -> {
                            repo.save(parent);
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
        Parent f = rowToParent.get(table.getSelectedRow());
        modifyContainer.removeAll();
        modifyContainer.add(
                modifyPanel(
                        parent -> {
                            repo.edit(parent);
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
        Parent parent = rowToParent.get(table.getSelectedRow());
        repo.delete(parent);
        fillData();
    }
}
