package Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Estatisticas {
    private static final long serialVersionUID = 1L;

    //    * estatisticas    pagamentos   balanco     credito     id pessoa  idmanager  data


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEstatistica;


    private Double pagamentos=0.0;
    private Double balaco=0.0;
    private Double credito=0.0;
    private int idpessoa=0;
    private int idmanager=0;

    public Estatisticas( Double pagamentos, Double balaco, Double credito, int idpessoa, int idmanager) {
        this.pagamentos = pagamentos;
        this.balaco = balaco;
        this.credito = credito;
        this.idpessoa = idpessoa;
        this.idmanager = idmanager;
    }

    public Estatisticas() {
    }

    public int getIdEstatistica() {
        return idEstatistica;
    }

    public void setIdEstatistica(int idEstatistica) {
        this.idEstatistica = idEstatistica;
    }

    public Double getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Double pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Double getBalaco() {
        return balaco;
    }

    public void setBalaco(Double balaco) {
        this.balaco = balaco;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public int getIdmanager() {
        return idmanager;
    }

    public void setIdmanager(int idmanager) {
        this.idmanager = idmanager;
    }

}
