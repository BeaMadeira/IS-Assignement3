package kafka;

public class Pagamento {
    double price=-1;
    Currency currency=null;
    int cliente_id=-1;
    int gestor_id=-1;

    public int getGestor_id() {
        return gestor_id;
    }

    public void setGestor_id(int gestor_id) {
        this.gestor_id = gestor_id;
    }

    public Pagamento() {
    }

    public Pagamento(double price, Currency currency, int cliente_id,int gestor_id) {
        this.price = price;
        this.currency = currency;
        this.cliente_id = cliente_id;
        this.gestor_id=gestor_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }
}
