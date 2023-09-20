package beans;

import Data.*;
import beans.IManageApp;
import beansDTO.ClientesDTO;
import beansDTO.GestoresDTO;
import beansDTO.MoedasDTO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ManageApp implements IManageApp {
    @PersistenceContext(unitName = "playAula")
    EntityManager em;

    public void addGestor(String name) {

       Gestores s = new Gestores(name);
       em.persist(s);

    }
    public void addCliente(int gestor_id, String name) {
        //adicionar ja com manager
        // User u=em.find(User.class,iduser);
            Gestores g = em.find(Gestores.class, gestor_id);

        Clientes s = new Clientes(g,name);
        em.persist(s);

    }
    public void addCurrency (String name, Double rate){
        Moedas s = new Moedas(name,rate);
        em.persist(s);
    }

    public  List<GestoresDTO> ListarGestores(){

        TypedQuery<Gestores> q = em.createQuery("from Gestores  ", Gestores.class);;

        List<Gestores> list = q.getResultList();

        List<GestoresDTO> td = new ArrayList<>();

        for (Gestores p : list)
            td.add(new GestoresDTO(p.getNome()));

        return td;
    }
    public List<MoedasDTO> ListarMoedas(){

        TypedQuery<Moedas> q = em.createQuery("from Moedas  ", Moedas.class);;

        List<Moedas> list = q.getResultList();

        List<MoedasDTO> td = new ArrayList<>();

        for (Moedas p : list)
            td.add(new MoedasDTO(p.getNome(),p.getExchangeRate()));

        return td;

    }
    public List<ClientesDTO> ListarClientes(){
        TypedQuery<Clientes> q = em.createQuery("from Clientes  ", Clientes.class);;

        List<Clientes> list = q.getResultList();

        List<ClientesDTO> td = new ArrayList<>();

        for (Clientes p : list)
            td.add(new ClientesDTO(p.getGestor().getIdGestor(),p.getNome()));

        return td;
    }

    public List<List<String>> getCreditPerClient(){
        //tabela dados
        //lista todos os dados
        TypedQuery<Dados> q = em.createQuery("from Dados  ", Dados.class);
        List<Dados> list = q.getResultList();
        List<List<String>> td = new ArrayList<List<String>>();

        List<String>aux=new ArrayList<>();
        for (Dados p : list) {
            Clientes cliente = em.find(Clientes.class, p.getIdcliente());
            aux.add(cliente.getNome());
            aux.add(p.getCredito().toString());
            td.add(aux);
        }

        return td;
    }

    public  List<List<String>> getBill(){
        //tabela dados
        //lista todos os dados
        TypedQuery<Dados> q = em.createQuery("from Dados  ", Dados.class);
        List<Dados> list = q.getResultList();
        List<List<String>> td = new ArrayList<List<String>>();

        List<String>aux=new ArrayList<>();
        for (Dados p : list) {
            Clientes cliente = em.find(Clientes.class, p.getIdcliente());
            aux.add(cliente.getNome());
            aux.add(p.getBill().toString());
            td.add(aux);
        }
        return td;
    }
    public List<List<String>> getBalancePerClient(){
        //tabela dados
        //lista todos os dados
        TypedQuery<Dados> q = em.createQuery("from Dados  ", Dados.class);
        List<Dados> list = q.getResultList();
        List<List<String>> td = new ArrayList<List<String>>();

        List<String>aux=new ArrayList<>();
        for (Dados p : list) {
            Clientes cliente = em.find(Clientes.class, p.getIdcliente());
            aux.add(cliente.getNome());
            aux.add(p.getCredito().toString());
            td.add(aux);
        }
        return td;
    }

    public Double getTotalPayments(){
        //tabela estatistica
        //pagamentos
        Double b=0.0;
        Estatisticas a = em.find(Estatisticas.class, 1);
        if(a==null){
            b= 0.0;
        }else{
            b=a.getPagamentos();
        }
        return b;
    }
    public Double getTotalBalance(){
        //tabela estatistica
        //pagamentos
        Double b=0.0;
        Estatisticas a = em.find(Estatisticas.class, 1);
        if(a==null){
            b= 0.0;
        }else{
            b=a.getBalaco();
        }
        return b;
    }
    public Double getTotalCredit() {

        //tabela estatistica
        //pagamentos
        Double b = 0.0;
        Estatisticas a = em.find(Estatisticas.class, 1);
        if (a == null) {
            b = 0.0;
        } else {
            b = a.getCredito();
        }
        return b;
    }
}



