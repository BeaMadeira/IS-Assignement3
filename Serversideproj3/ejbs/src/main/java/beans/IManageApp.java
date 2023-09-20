package beans;

import beansDTO.ClientesDTO;
import beansDTO.GestoresDTO;
import beansDTO.MoedasDTO;

import java.util.List;

public interface IManageApp {
    void addGestor(String name);
    void addCliente(int gestor_id, String name);
    void addCurrency(String name, Double rate);
    List<GestoresDTO> ListarGestores();
    List<MoedasDTO> ListarMoedas();
    List<ClientesDTO> ListarClientes();
    List<List<String>> getCreditPerClient();
    List<List<String>> getBill();
    List<List<String>> getBalancePerClient();
    Double getTotalPayments();
    Double getTotalBalance();
    Double getTotalCredit();
    }
