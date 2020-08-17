package tk.matheuslucena.realidade.Objetos;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Client implements Serializable {

    public Client(int id, String nome){
        this.id = id;
        this.name = nome;
    }
    public Client(){}
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    private String cellPhone;

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
    private int ZipCodeDelivery_idZipCodeDelivery;

    public int getZipCodeDelivery_idZipCodeDelivery() {
        return ZipCodeDelivery_idZipCodeDelivery;
    }

    public void setZipCodeDelivery_idZipCodeDelivery(int ZipCodeDelivery_idZipCodeDelivery) {
        this.ZipCodeDelivery_idZipCodeDelivery = ZipCodeDelivery_idZipCodeDelivery;
    }
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    private String complement;

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
    private boolean statusClient;

    public boolean getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(boolean statusClient) {
        this.statusClient = statusClient;
    }
    private LocalDateTime dataTime;

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

}
