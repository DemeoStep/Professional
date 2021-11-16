package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private boolean tail;
    private double weight;
    private Date createOn;

    public Animal(String name, int age, boolean tail, double weight, Date createOn) {
        this.name = name;
        this.age = age;
        this.tail = tail;
        this.weight = weight;
        this.createOn = createOn;
    }

    public void copyFrom(Animal animal) {
        this.age = animal.age;
        this.createOn = animal.createOn;
        this.name = animal.name;
        this.tail = animal.tail;
        this.weight = animal.weight;
    }
}
