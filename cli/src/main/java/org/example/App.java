package org.example;

import java.util.List;
import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class App {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target =
                client.target("http://localhost:8080/rest/services/myservice/test");
        Response response /*= target.request().get()*/;
        String value /*= response.readEntity(String.class)*/;
        //System.out.println("RESPONSE1: " + value);
        //response.close();
        String valor;
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("MENU\n" +
                    "1-Adicionar Moeda\n" +
                    "2-Adicionar Gestor\n" +
                    "3-Adicionar Cliente\n" +
                    "4-Visualizar Clientes\n" +
                    "5-Visualizar Moedas\n" +
                    "6-Visualizar Total de Pagamentos\n" +
                    "7-Visualizar Balanco Total\n" +
                    "8-Visualizar Total de Creditos\n" +
                    "9-Visualizar Gestores\n" +
                    "10-Visualizar Credito por Cliente\n" +
                    "11-Visualizar Bill por Cliente(window)\n" +
                    "12-Visualizar Balanco por Cliente\n");

            System.out.println("Seleccione a sua opcao: \n");

            int opt = sc.nextInt();

            switch (opt) {
                case 1:
                    System.out.println("Insira o nome da moeda: ");
                    String nomeMoeda = sc.nextLine();
                    System.out.println("Insira o exchange rate: ");
                    Double exRate = sc.nextDouble();
                    target = client.target("http://localhost:8080/rest/services/myservice/person3/" + nomeMoeda + "/" + exRate);
                    response = target.request().get();
                    value = response.readEntity(String.class);
                    System.out.println("Moeda inserida: " + value);
                    response.close();
                    break;
                case 2:
                    //adicionar gestor
                    System.out.println("Insira o nome do gestor: ");
                    String nomeGestor = sc.nextLine();
                    target = client.target("http://localhost:8080/rest/services/myservice/addgestor/" + nomeGestor);
                    response = target.request().get();
                    value = response.readEntity(String.class);
                    System.out.println("Gestor Inserido: " + value);
                    response.close();
                    break;
                case 3:
                    //adicionar cliente
                    System.out.println("Insira o nome do cliente: ");
                    String nomeCliente = sc.nextLine();
                    System.out.println("Insira o id do seu gestor: ");
                    int idGestor = sc.nextInt();
                    target = client.target("http://localhost:8080/rest/services/myservice/addcliente/" + nomeCliente + "/" + idGestor);
                    response = target.request().get();
                    value = response.readEntity(String.class);
                    System.out.println("Cliente Inserido: " + value);
                    response.close();
                    break;
                case 4:
                    //listar clientes /listarclientes
                    target = client.target("http://localhost:8080/rest/services/myservice/listarclientes");
                    response = target.request().get();
                    System.out.println(response);
                    List<Cliente> ListaCliente = response.readEntity(new GenericType<List<Cliente>>() {
                    });
                    System.out.println("Clientes: " + ListaCliente);
                    response.close();
                    break;
                case 5:
                    //listar moedas
                    target = client.target("http://localhost:8080/restws/rest/myservice/listarmoedas");
                    response = target.request().get();
                    List<MoedasDTO> moedaList = response.readEntity(new GenericType<List<MoedasDTO>>() {
                    });

                    System.out.println("Moedas: " + response);
                    response.close();
                    break;
                case 6:
                    ///pagamentosTotal
                    target = client.target("http://localhost:8080/rest/services/myservice/pagamentosTotal");
                    response = target.request().get();
                    valor = response.readEntity(String.class);
                    System.out.println("Total de Pagamentos: " + valor);
                    response.close();
                    break;
                case 7:
                    //balanceTotal
                    target = client.target("http://localhost:8080/rest/services/myservice/balanceTotal");
                    response = target.request().get();
                    valor = response.readEntity(String.class);
                    System.out.println("Balanco Total: " + valor);
                    response.close();
                    break;
                case 8:
                    //CreditosTotal
                    target = client.target("http://localhost:8080/rest/services/myservice/creditoTotal");
                    response = target.request().get();
                    valor = response.readEntity(String.class);
                    System.out.println("Total Creditos: " + valor);
                    response.close();
                    break;
                case 9:
                    //listar gestores
                    target = client.target("http://localhost:8080/rest/services/myservice/listargestores");
                    response = target.request().get();
                    List<Gestores> listagestores = response.readEntity(new GenericType<List<Gestores>>(){});
                    System.out.println("Gestores: " + listagestores);
                    response.close();
                    break;
                case 10:
                    //listarcreditoporcliente
                    target = client.target("http://localhost:8080/rest/services/myservice/listarcreditoporcliente");
                    response = target.request().get();
                    List<List<String>> listacreditos = response.readEntity(new GenericType<List<List<String>>>(){});
                    System.out.println("Credito Por Cliente: " + listacreditos);
                    response.close();
                    break;
                case 11:
                    //listarbillsporcliente
                    target = client.target("http://localhost:8080/rest/services/myservice/listarbillsporcliente");
                    response = target.request().get();
                    List<List<String>> listabills = response.readEntity(new GenericType<List<List<String>>>(){});
                    System.out.println("Bill Por Cliente: " + listabills);
                    response.close();
                    break;
                case 12:
                    //listarbalancesporcliente
                    target = client.target("http://localhost:8080/rest/services/myservice/listarbalancesporcliente");
                    response = target.request().get();
                    List<List<String>> listabalance = response.readEntity(new GenericType<List<List<String>>>(){});
                    System.out.println("Balance Por Cliente: " + listabalance);
                    response.close();
                    break;
                default:
                    break;
            }
        }

    }

}