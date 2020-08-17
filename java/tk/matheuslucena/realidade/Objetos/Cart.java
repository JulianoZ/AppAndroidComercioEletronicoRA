package tk.matheuslucena.realidade.Objetos;

public class Cart extends Product{
    int quanty;
    int ordered = 0;
    int sessionId;


    public Product product;


    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    public int getSessionId() {   return sessionId;}


    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getOrdered() {
        return ordered;
    }

    public int GetQuanty(){
        return this.quanty;
    }
    public void SetQuanty(int quanty){
        this.quanty = quanty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    private Cart order;

    public Cart getOrder() {
        return order;
    }

    public void setOrder(Cart order) {
        this.order = order;
    }
}
