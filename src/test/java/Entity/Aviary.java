package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Aviary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String biome;
    private double size;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aviary")
    List<Animal> animals = new ArrayList<>();

    public Aviary(String biome, double size) {
        this.biome = biome;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Aviary{" +
                "id=" + id +
                ", biome='" + biome + '\'' +
                ", size=" + size +
                "}";
    }
}
