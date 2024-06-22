package Scooter.data;
import Scooter.object.Courier;


public class CourierData {
       public static Courier getDefault() {
        return new Courier("av.mart", "123456", "Anastasia");
    }

    public static Courier getDefaultWithoutPassword() {
        return new Courier("av.mart", "", "Anastasia");
    }

    public static Courier getRegisteredCourier() {
        return new Courier("Sasha17", "345678", "Sasha");
    }

    public static Courier getWrong() {
        return new Courier("12345Vasya", "12345", "Vasya");
    }
}
