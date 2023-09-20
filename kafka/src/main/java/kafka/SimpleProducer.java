package kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        /******************************** topico enviar *********/
//Assign topicName to string variable
        String topicName = args[0].toString();
        String topicNamecreditos = args[1].toString();
// create instance for properties to access producer configs
        Properties props = new Properties();
//Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
//Set acknowledgements for producer requests.
        props.put("acks", "all");
//If the request fails, the producer can automatically retry,
        props.put("retries", 0);
//Specify buffer size in config
        props.put("batch.size", 16384);
//Reduce the no of requests less than 0
        props.put("linger.ms", 1);
//The buffer.memory controls the total amount of memory available to the producer forbuffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");


        Properties props2 = new Properties();
        //Assign localhost id
        props2.put("bootstrap.servers", "localhost:9092");
//Set acknowledgements for producer requests.
        props2.put("acks", "all");
//If the request fails, the producer can automatically retry,
        props2.put("retries", 0);
//Specify buffer size in config
        props2.put("batch.size", 16384);
//Reduce the no of requests less than 0
        props2.put("linger.ms", 1);
//The buffer.memory controls the total amount of memory available to the producer forbuffering.
        props2.put("buffer.memory", 33554432);
        props2.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props2.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props2.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        /*props2.put("session.timeout.ms", 30000);
        props2.put("heartbeat.interval.ms", 500);
        props2.put("metadata.max.age.ms", 500);
        props2.put("request.timeout.ms", 27000);*/

        String jsonString;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        ObjectMapper om = new ObjectMapper();

        Consumer<String, String> consumermoedas = new KafkaConsumer<>(props2);
        Consumer<String, String> consumergestores = new KafkaConsumer<>(props2);
        Consumer<String, String> consumerclientes = new KafkaConsumer<>(props2);

        consumermoedas.subscribe(Collections.singletonList("moedas"));
        consumergestores.subscribe(Collections.singletonList("gestores"));
        consumerclientes.subscribe(Collections.singletonList("clientes"));


        ArrayList<Currency> moedas =new ArrayList<>();
        ArrayList<ArrayList<Integer>> idcliente = new ArrayList<>();
        Random random = new Random();
        int i= random.nextInt(50 - 1) + 1;
        int flag =0;

        while (true) {
            ConsumerRecords<String, String> recordsgestores = consumergestores.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, String> record : recordsgestores) {
                //null => {"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"idgestor"},{"type":"string","optional":true,"field":"nome"}],"optional":false},"payload":{"idgestor":2,"nome":"avd"}}
                if (record.key() != null) {
                    Map<String, Object> map = om.readValue(record.value(), Map.class);
                    jsonObject = new JSONObject(map);
                    //System.out.println("------------------------" + jsonObject.get("payload"));
                    String[] as = jsonObject.get("payload").toString().split(",");
                    String[] keyValue1 = as[0].replace("{", "").split("=");
                    String[] keyValue2 = as[1].split("=");
                    String[] keyValue3 = as[2].replace("}", "").split("=");

                    //System.out.println("--------------- key: " + keyValue1[0] + " value: " + keyValue1[1]);
                    //System.out.println("--------------- key: " + keyValue2[0] + " value: " + keyValue2[1]);
                    //System.out.println("--------------- key: " + keyValue3[0] + " value: " + keyValue3[1]);

                }
            }
            //System.out.println("a");
            ConsumerRecords<String, String> records = consumermoedas.poll(Duration.ofMillis(300));
            //System.out.println("b");
            //System.out.println("dngfbjdikosfjhguijkodfjgiok------------------------------------------");
            for (ConsumerRecord<String, String> record : records) {
                //null => {"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"idmoeda"},{"type":"double","optional":true,"field":"exchangerate"},{"type":"string","optional":true,"field":"nome"}],"optional":false},"payload":{"idmoeda":4,"exchangerate":0.5,"nome":"ola"}}
                System.out.println(record.key() + " => " + record.value());


                Map<String, Object> map = om.readValue(record.value(), Map.class);
                jsonObject = new JSONObject(map);
                //System.out.println("------------------------" + jsonObject.get("payload"));
                String[] as = jsonObject.get("payload").toString().split(",");
                String[] keyValue1 = as[0].replace("{", "").split("=");
                String[] keyValue2 = as[1].split("=");
                String[] keyValue3 = as[2].replace("}", "").split("=");

                System.out.println("--------------- key: " + keyValue1[0] + " value: " + keyValue1[1]);
                System.out.println("--------------- key: " + keyValue2[0] + " value: " + keyValue2[1]);
                System.out.println("--------------- key: " + keyValue3[0] + " value: " + keyValue3[1]);

                //if(keyValue1[1]) nao existir no array
                Currency moeda = new Currency();
                moeda.setIdMoeda(Integer.parseInt(keyValue1[1]));
                moeda.setExchangeRate(Double.parseDouble(keyValue2[1]));
                moeda.setNome(keyValue3[1]);
                moedas.add(moeda);

                //record.value();idmoeda":4,"exchangerate":0.5,"nome":"ola"}
            }
            System.out.println("b");

            i = random.nextInt(50 - 1) + 1;
            ConsumerRecords<String, String> recordsclientes = consumerclientes.poll(Duration.ofMillis(200));
            for (ConsumerRecord<String, String> record : recordsclientes) {

                //null => {"schema":{"type":"struct","fields":[{"type":"int64","optional":false,"field":"idcliente"},{"type":"string","optional":true,"field":"nome"},{"type":"int32","optional":true,"field":"gestor_idgestor"}],"optional":false},"payload":{"idcliente":3,"nome":"a","gestor_idgestor":null}}
                //System.out.println(record.key() + " => " + record.value());

                //String value = (String) jsonObject.get("payload");
                Map<String, Object> map = om.readValue(record.value(), Map.class);
                jsonObject = new JSONObject(map);
                String[] as = jsonObject.get("payload").toString().split(",");
                String[] keyValue1 = as[0].replace("{", "").split("=");
                String[] keyValue2 = as[1].split("=");
                String[] keyValue3 = as[2].replace("}", "").split("=");
                //keyValue1/2/3[0] -> key ////////////////////////////////////
                //keyValue1/2/3[1] -> value /////////////////////////////////
                System.out.println("--------------- key: " + keyValue1[0] + " value: " + keyValue1[1]);
                System.out.println("--------------- key: " + keyValue2[0] + " value: " + keyValue2[1]);
                System.out.println("--------------- key: " + keyValue3[0] + " value: " + keyValue3[1]);
                //idcliente":3,"nome":"a","gestor_idgestor":null}
                if (keyValue3[1].equals(null))
                    System.out.println("error : " + keyValue3[1]);
                ArrayList aux = new ArrayList();
                aux.add(Integer.parseInt(keyValue1[1]));
                aux.add(Integer.parseInt(keyValue3[1]));
                idcliente.add(aux);
            }


            Pagamento pagamento = new Pagamento();
            Credito credito = new Credito();
            String jsonStringpagamento="";
            String jsonStringcredito="";
            if(moedas.isEmpty()==false && idcliente.isEmpty()==false) {
                random = new Random();
                int index = random.nextInt(moedas.size() - 0) + 0;
                pagamento.setCurrency(moedas.get(index));
                index = random.nextInt(moedas.size() - 0) + 0;
                credito.setCurrency(moedas.get(index));

                Random randomprice = new Random();
                int preco = randomprice.nextInt(200 - 1) + 1;
                pagamento.setPrice(preco);
                randomprice = new Random();
                preco = randomprice.nextInt(200 - 1) + 1;
                credito.setPrice(preco);

                index = random.nextInt(idcliente.size() - 0) + 0;
                pagamento.setCliente_id(idcliente.get(index).get(0));
                pagamento.setGestor_id(idcliente.get(index).get(1));
                index = random.nextInt(idcliente.size() - 0) + 0;
                credito.setCliente_id(idcliente.get(index).get(0));
                credito.setGestor_id(idcliente.get(index).get(1));


                /*Pagamento pagamento = new Pagamento();
                Credito credito = new Credito();
                Currency curr = new Currency();
                String jsonStringpagamento="";
                String jsonStringcredito="";

                curr.setIdMoeda(1);
                curr.setNome("euro");
                curr.setExchangeRate(1.0);

                credito.setCliente_id(1);
                credito.setGestor_id(1);
                credito.setPrice(5);
                credito.setCurrency(curr);

                pagamento.setCliente_id(1);
                pagamento.setGestor_id(1);
                pagamento.setPrice(10);
                pagamento.setCurrency(curr);*/

                jsonStringpagamento = gson.toJson(pagamento);
                jsonStringcredito = gson.toJson(credito);
            }

            Producer<String, String> producer = new KafkaProducer<>(props);
            System.out.println("----------------josn pagamento----------------");
            System.out.println(jsonStringpagamento);
            System.out.println("----------------josn pagamento----------------");

            if (pagamento.getCliente_id()!=0 && pagamento.getCliente_id() != -1 && pagamento.getCurrency().getExchangeRate() != null && pagamento.getPrice() != -1 && pagamento.getGestor_id() != -1){
                producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(pagamento.getCliente_id()), jsonStringpagamento));
                System.out.println("Sending message pagamento " + jsonStringpagamento + " to topic " + topicName);
                pagamento.setCliente_id(-1) ;
                pagamento.setCurrency(null);
                pagamento.setPrice( -1)  ;
                pagamento.setGestor_id(-1);

                }else{
                System.out.println("-----------------dados em falta pagamentos--------------------------------");
            }
            System.out.println("----------------josn credito----------------");
            System.out.println(jsonStringcredito);
            System.out.println("----------------josn credito----------------");

            if (credito.getCliente_id() != -1 && credito.getCurrency() != null && credito.getPrice() != -1 && credito.getGestor_id() != -1) {
                producer.send(new ProducerRecord<String, String>(topicNamecreditos, Integer.toString(credito.getCliente_id()), jsonStringcredito));
                System.out.println("Sending message credito " + jsonStringcredito + " to topic " + topicName);
                credito.setCliente_id(-1) ;
                credito.setCurrency(null);
                credito.setPrice( -1)  ;
                credito.setGestor_id(-1);
            }else{
                System.out.println("-----------------dados em falta creditos------------------------");
            }
                Thread.sleep(5000);
            }
        }

    }

