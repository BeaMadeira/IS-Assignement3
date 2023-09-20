package Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dados {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idcliente;

    private Double pagamento=0.0;
    private Double credito=0.0;
    private Double balance=0.0;
    private Double bill=0.0;


    /*
    *
    * pagamentos por cliente
    * 1-4.5
    * 2-0.5
    * 3-5.6
    *
    * id resultado     pagamento         credito      BALANCE       bill    data
    * 1                 1           363                 4534        43543
    * 2                 2           r4r4r4              34543       45345
    * 3                 3           werew               3543543     45435
    * 4                 1           544                 436543      43543
    * 5                 2           454354              342343      43353
    * 6
    *
    * estatisticas    pagamentos   balanco     credito     id pessoa  idmanager  data
    * 1                435           345         3454
    * 2
    * 3
    * 4
    *
    *
    *
    * */

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public Dados( Double pagamento, Double credito, Double balance, Double bill) {
        this.pagamento = pagamento;
        this.credito = credito;
        this.balance = balance;
        this.bill = bill;
    }

    public Dados() {
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }
}
