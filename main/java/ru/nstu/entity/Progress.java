package ru.nstu.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "progress")
@Data
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column
    private String classs;

    @Column
    private Integer year;

    @Column
    private String subject;

    @Column
    private Integer quarter;

    @Column
    private Integer half_yearly;

    @Column
    private Integer yearly;

    @Column
    private Integer exam;

    @Column
    private Integer finaly;

    @Override
    public String toString() {

        return classs + " " + subject;
    }

}