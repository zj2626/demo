package hello.database.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "person")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "course")
    private String course;
    @Column(name = "score")
    private Integer score;
}
