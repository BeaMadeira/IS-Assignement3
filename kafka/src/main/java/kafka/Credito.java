package kafka;

public class Credito {
        double price;
        Currency currency;
        int cliente_id;
        int gestor_id;

        public int getGestor_id() {
            return gestor_id;
        }

        public void setGestor_id(int gestor_id) {
            this.gestor_id = gestor_id;
        }
        public Credito() {
        }

        public Credito(double price, Currency currency, int cliente_id,int gestor_id) {
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
