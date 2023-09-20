package kafka;

public class Currency {
    private int idMoeda;


    private String nome;
    private Double exchangeRate;

    public Currency(String nome, Double exchangeRate) {
        this.nome = nome;
        this.exchangeRate = exchangeRate;
    }

    public Currency() {

    }

    public int getIdMoeda() {
        return idMoeda;
    }

    public void setIdMoeda(int idMoeda) {
        this.idMoeda = idMoeda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
