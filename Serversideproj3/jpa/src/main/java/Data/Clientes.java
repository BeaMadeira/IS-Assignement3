package Data;

import javax.persistence.*;

@Entity
public class Clientes {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCliente;

    @ManyToOne
    private Gestores gestor;

    private String nome;

    public Clientes( Gestores gestor, String nome) {
        this.gestor = gestor;
        this.nome = nome;
    }

    public Clientes() {

    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Gestores getGestor() {
        return gestor;
    }

    public void setGestor(Gestores gestor) {
        this.gestor = gestor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
