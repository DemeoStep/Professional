package Entity;

import java.util.Objects;

public class Car {
    private long id;
    private String mark;
    private String model;
    private int price;

    public Car() {
    }

    public Car(String mark, String model, int price) {
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    public Car(long id, String mark, String model, int price) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
        return price == car.price && mark.equals(car.mark) && model.equals(car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, model, price);
    }
}
