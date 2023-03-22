package ru.nstu.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parents")
@Data
public class Parent {

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
    private String degree;

    @Column
    private String address;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="kinship",
            joinColumns = @JoinColumn(name= "parent_id"),
            inverseJoinColumns = @JoinColumn(name= "schoolchild_id")
    )
    private List<Schoolchild> schoolchildren = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
