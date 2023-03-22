package ru.nstu.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.sql.Date;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schoolchild")
@Data
public class Schoolchild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String patronymic;

    @Column
    private String address;

    @Column
    private Date birthday;

    @Column
    private Integer year_admission;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="idd",
            joinColumns = @JoinColumn(name= "schoolchild_id"),
            inverseJoinColumns = @JoinColumn(name= "progress_id")
    )
    private List<Progress> progresses = new ArrayList<>();


    @Override
    public String toString() {

        return name + " " + surname;
    }
}
