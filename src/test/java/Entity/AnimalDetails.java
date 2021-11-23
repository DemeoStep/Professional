package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AnimalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int age;
    private boolean tail;
    private long weight;
    private String breed;
    @OneToOne(mappedBy = "details")
    private Animal animal;

    public AnimalDetails(int age, boolean tail, long weight, String breed) {
        this.age = age;
        this.tail = tail;
        this.weight = weight;
        this.breed = breed;
    }

    public void copyFrom(AnimalDetails details) {
        this.age = details.age;
        this.tail = details.tail;
        this.weight = details.weight;
        this.breed = details.breed;
    }

    @Override
    public String toString() {
        return "AnimalDetails{" +
                "id=" + id +
                ", age=" + age +
                ", tail=" + tail +
                ", weight=" + weight +
                ", breed='" + breed + '\'' +
                '}';
    }
}
