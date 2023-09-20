package book;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import Data.Gestores;
import Data.Student;
import beans.IManageApp;
import beans.IManageStudents;
import beansDTO.ClientesDTO;
import beansDTO.GestoresDTO;
import beansDTO.MoedasDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@RequestScoped
@Path("/myservice")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {


    @EJB
    private IManageApp manageApp;
    @GET
    @Path("/test")
    public String method1() {
        return "";
    }
    @GET
    @Path("/listarclientes")
    public List<ClientesDTO> method2() {
        List<ClientesDTO> lista = manageApp.ListarClientes();
        return lista;
    }

    @GET
    @Path("/listarmoedas")
    public List<MoedasDTO> method3() {
        List<MoedasDTO> list = manageApp.ListarMoedas();
        return list;
    }

    //List<GestoresDTO> ListarGestores();
    @GET
    @Path("/listargestores")
    public List<GestoresDTO> listargestores() {
        List<GestoresDTO> list = manageApp.ListarGestores();
        return list;
    }

    //esta correto?
    @GET
    @Path("/person3/{name}/{exrate}")
    public MoedasDTO method3(@PathParam("name") String value,@PathParam("exrate") Double rate) {
        manageApp.addCurrency(value,rate);
        return new MoedasDTO(value, rate);
    }

    @GET
    @Path("/addgestor/{name}")
    public GestoresDTO method3(@PathParam("name") String value) {
        manageApp.addGestor(value);
        return new GestoresDTO(value);
    }
    @GET
    @Path("/addcliente/{name}/{idgestor}")
    public ClientesDTO method3(@PathParam("name") String value, @PathParam("idgestor") int id) {
        manageApp.addCliente(id,value);
        return new ClientesDTO(id,value);
    }

    // Double getTotalPayments();
    @GET
    @Path("/pagamentosTotal")
    public String getPagamentosTotal() {
        return manageApp.getTotalPayments().toString();
    }

    // getTotalBalance();
    @GET
    @Path("/balanceTotal")
    public String getBalanceTotal() {
        return manageApp.getTotalBalance().toString();
    }
    //getTotalCredit();
    @GET
    @Path("/creditoTotal")
    public String getCreditoTotal() {
        return manageApp.getTotalCredit().toString();
    }

    // List<List<String>> getCreditPerClient();
    @GET
    @Path("/listarcreditoporcliente")
    public List<List<String>> listarcreditos() {
        List<List<String>> list = manageApp.getCreditPerClient();
        return list;
    }

    // List<List<String>> getBill();
    @GET
    @Path("/listarbillsporcliente")
    public List<List<String>> listarbills() {
        List<List<String>> list = manageApp.getBill();
        return list;
    }

    //List<List<String>> getBalancePerClient();
    @GET
    @Path("/listarbalancesporcliente")
    public List<List<String>> listarbalance() {
        List<List<String>> list = manageApp.getBalancePerClient();
        return list;
    }
}