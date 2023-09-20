package beansDTO;

import Data.Gestores;

import javax.persistence.*;

@Entity
public class ClientesDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCliente;
    @ManyToOne
    private Gestores gestor;

    private String nome;

    public ClientesDTO( int gestor_id, String nome) {
        this.gestor = gestor;
        this.nome = nome;
    }

    public ClientesDTO() {

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
