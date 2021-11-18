package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return markId == car.markId && price == car.price && Objects.equals(mark, car.mark) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markId, mark, model, price);
    }
}
