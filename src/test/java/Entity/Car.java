package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long markId;
    @Transient
    private String mark;
    private String model;
    private int price;

    public Car(String mark, String model, int price) {
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                "}";
    }
}
