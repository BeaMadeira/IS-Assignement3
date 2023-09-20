package beansDTO;

import Data.Clientes;

import javax.persistence.*;
import java.util.List;

@Entity
public class GestoresDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idGestor;

    @OneToMany(mappedBy="gestor")
    private List<Clientes> clientes;

    private String nome;

    public GestoresDTO( String nome) {
        this.nome = nome;
    }

    public GestoresDTO() {

    }

    public int getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(int idGestor) {
        this.idGestor = idGestor;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
