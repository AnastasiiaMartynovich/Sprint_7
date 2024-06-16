package Scooter.Data;
import Scooter.Object.Order;
public class OrderData {
    public static Order getDefault(String[] color) {
        return new Order("Anastasia",
                "Martynovich",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2024-06-16",
                "Saske, come back to Konoha",
                color);
    }
}
