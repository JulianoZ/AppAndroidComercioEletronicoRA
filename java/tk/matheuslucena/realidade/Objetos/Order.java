package tk.matheuslucena.realidade.Objetos;

import java.io.Serializable;

public class Order implements Serializable {

    private int idOrderedFinalized;
    private double ValueTotal;
    private String DateTimeOrdered;
    private int StatusOrdered;
    private String Name;
    private String PaymentMethod;
    private int StatusOrderLocal;
    private String DateTimeOrdered2;


    public void setStatusOrderLocal(int statusOrderLocal) {
        StatusOrderLocal = statusOrderLocal;
    }

    public int getStatusOrderLocal() {
        return StatusOrderLocal;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setStatusOrdered(int statusOrdered) {
        StatusOrdered = statusOrdered;
    }

    public int getStatusOrdered() {
        return StatusOrdered;
    }


    public void setDateTimeOrdered(String dateTimeOrdered) {
        DateTimeOrdered = dateTimeOrdered;
    }
    public String getDateTimeOrdered() {
        return DateTimeOrdered;
    }



    public void setDateTimeOrdered2(String dateTimeOrdered2) {
        DateTimeOrdered2 = dateTimeOrdered2;
    }
    public String getDateTimeOrdered2() {
        return DateTimeOrdered2;
    }

    public void setValueTotal(double valueTotal) {
        ValueTotal = valueTotal;
    }

    public double getValueTotal() {
        return ValueTotal;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }
    public int getIdOrderedFinalized() {
        return idOrderedFinalized;
    }

    public void setIdOrderedFinalized(int idOrderedFinalized) {
        this.idOrderedFinalized = idOrderedFinalized;
    }

}
