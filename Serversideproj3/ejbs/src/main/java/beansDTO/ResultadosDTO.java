package beansDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResultadosDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public ResultadosDTO(int id) {
        this.id = id;
    }

    public ResultadosDTO() {
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
