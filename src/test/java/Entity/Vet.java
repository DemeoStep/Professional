package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String name;
    @Column(unique = true)
    String phone;
    String clinic;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vet")
    List<Animal> animals = new ArrayList<>();

    public Vet(String name, String phone, String clinic) {
        this.name = name;
        this.phone = phone;
        this.clinic = clinic;
    }

    public void copyOf(Vet vet) {
        this.name = vet.name;
        this.phone = vet.phone;
        this.clinic = vet.clinic;
    }

    @Override
    public String toString() {
        return "Vet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", clinic='" + clinic + '\'' +
                "}";
    }
}
