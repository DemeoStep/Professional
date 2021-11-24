package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String name;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    AnimalDetails details;
    @ManyToOne
    @JoinColumn(name = "vet_id", referencedColumnName = "id")
    Vet vet;
    @ManyToOne
    @JoinColumn(name = "aviary_id", referencedColumnName = "id")
    Aviary aviary;

    public Animal(String name, AnimalDetails details, Vet vet, Aviary aviary) {
        this.name = name;
        this.details = details;
        this.vet = vet;
        this.aviary = aviary;
    }

    public void copyFrom(Animal animal) {
        this.name = animal.name;
        this.details = animal.details;
        this.vet = animal.vet;
        this.aviary = animal.aviary;
    }
}
