package tk.matheuslucena.realidade.Objetos;


public class OrderV2 extends Product{

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int sessionId;
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    public int getSessionId() {   return sessionId;}

    public Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    private float ValueUnit;

    public float getValueUnit() {
        return ValueUnit;
    }

    public void setValueUnit(float ValueUnit) {
        this.ValueUnit = ValueUnit;
    }
    private int Quantity;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    private float ValueTotal;

    public float getValueTotal() {
        return ValueTotal;
    }

    public void setValueTotal(float ValueTotal) {
        this.ValueTotal = ValueTotal;
    }
    private int SectionClient;

    public int getSectionClient() {
        return SectionClient;
    }

    public void setSectionClient(int SectionClient) {
        this.SectionClient = SectionClient;
    }
    private String OrderFinalized;

    public String getOrderFinalized() {
        return OrderFinalized;
    }


    private boolean StatusFinalized;

    public boolean getStatusFinalized() {
        return StatusFinalized;
    }

    public void setStatusFinalized(boolean StatusFinalized) {
        this.StatusFinalized = StatusFinalized;
    }
    private int OrderFinalized_idOrderFinalized;

    public int getOrderFinalized_idOrderFinalized() {
        return OrderFinalized_idOrderFinalized;
    }

    public void setOrderFinalized_idOrderFinalized(int OrderFinalized_idOrderFinalized) {
        this.OrderFinalized_idOrderFinalized = OrderFinalized_idOrderFinalized;
    }
    private boolean ProductDelivered;

    public boolean getProductDelivered() {
        return ProductDelivered;
    }

    public void setProductDelivered(boolean ProductDelivered) {
        this.ProductDelivered = ProductDelivered;
    }


    int ordered = 0;

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getOrdered() {
        return ordered;
    }


    private int StatusOrdered;
    public void setStatusOrdered(int statusOrdered) {
        StatusOrdered = statusOrdered;
    }

    public int getStatusOrdered() {
        return StatusOrdered;
    }


    public Client client;

    public void setClient(Client client) {
        client = client;
    }

    public Client getClient() {
        return client;
    }

}
