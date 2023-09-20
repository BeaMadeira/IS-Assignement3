package kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import com.fasterxml.jackson.databind.DeserializationContext;
//import jdk.swing.interop.SwingInterOpUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.spec.PSource;


public class SimpleConsumer {
    public static void main(String[] args) throws Exception{
//Assign topicName to string variable

        //String outtopicnameAllPay = args[0].toString();

        String out = args[0].toString();
        String outtopicnameAllBalance = args[1].toString();
        String outtopicnameAllCredit = args[2].toString();
        String outtopicnamePayperClient = args[3].toString();
        String outtopicnameCreditperClient = args[4].toString();
        String outtopicnameBalanceperClient = args[5].toString();
        String outtopicnameBillsLastMonth = "lastmonth";

       /* String outtopicnameAllCredit = args[1].toString();
        String outtopicnameAllBalance = args[2].toString();
        String outtopicnameCreditperClient = args[4].toString();
        String outtopicnameBalanceperClient = args[5].toString();*/

        // create instance for properties to access producer configs
        //envio
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
        props2.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props2.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");



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
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.DoubleDeserializer");

        /*--------------outtopicnameAllBalance = args[2].toString();
        String  = args[3].toString();
        String  = args[4].toString();
        String outtopicnameBalanceperClient = args[5].toString();*/

        Consumer<String, Double> consumerout = new KafkaConsumer<>(props);
        consumerout.subscribe(Collections.singletonList(out));

        Consumer<String, Double> consumerouttopicnameAllCredit = new KafkaConsumer<>(props);
        consumerouttopicnameAllCredit.subscribe(Collections.singletonList(outtopicnameAllCredit));

        Consumer<String, Double> consumerouttopicnameAllBalance = new KafkaConsumer<>(props);
        consumerouttopicnameAllBalance.subscribe(Collections.singletonList(outtopicnameAllBalance));

        Consumer<String, Double> consumerouttopicnamePayperClient = new KafkaConsumer<>(props);
        consumerouttopicnamePayperClient.subscribe(Collections.singletonList(outtopicnamePayperClient));

        Consumer<String, Double> consumerouttopicnameCreditperClient = new KafkaConsumer<>(props);
        consumerouttopicnameCreditperClient.subscribe(Collections.singletonList(outtopicnameCreditperClient));

        Consumer<String, Double> consumerouttopicnameBalanceperClient = new KafkaConsumer<>(props);
        consumerouttopicnameBalanceperClient.subscribe(Collections.singletonList(outtopicnameBalanceperClient));

        Consumer<String, Double> consumerouttopicnameBillLastMonth = new KafkaConsumer<>(props);
        consumerouttopicnameBillLastMonth.subscribe(Collections.singletonList(outtopicnameBillsLastMonth));


        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        ObjectMapper om = new ObjectMapper();
        String allbalance="";
        String allcredit="";
        String allpay="";
        String estat = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"idestatistica\"},{\"type\":\"double\",\"optional\":true,\"field\":\"balaco\"},{\"type\":\"double\",\"optional\":true,\"field\":\"credito\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"idmanager\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"idpessoa\"},{\"type\":\"double\",\"optional\":true,\"field\":\"pagamentos\"}],\"optional\":false},\"payload\":{\"";
        Producer<String, String> producer = new KafkaProducer<>(props2);
        /*{"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"idresultado"},{"type":"double","optional":true,"field":"balance"},{"type":"double","optional":true,"field":"bill"},{"type":"double","optional":true,"field":"credito"},{"type":"int32","optional":false,"field":"idcliente"},{"type":"double","optional":true,"field":"pagamento"}],"optional":false},
                "payload":{"idresultado":1,"balance":99999.8,"bill":234.4,"credito":234.4,"idcliente":1,"pagamento":234.4}}*/
                /*{"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"idestatistica"},{"type":"double","optional":true,"field":"balaco"},{"type":"double","optional":true,"field":"credito"},{"type":"int32","optional":false,"field":"idmanager"},{"type":"int32","optional":false,"field":"idpessoa"},{"type":"double","optional":true,"field":"pagamentos"}],"optional":false},
                "payload":{"idestatistica":1,"balaco":123.3,"credito":123.3,"idmanager":1,"idpessoa":1,"pagamentos":1456.3}}*/
        while (true) {

            ConsumerRecords<String,Double> records = consumerout.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : records) {
                System.out.println(record.key() + " => " + record.value());

                //all payments

                allpay="{\"schema\":{\"type\":\"double\",\"optional\":true,\"field\":\"pagamentos\"}],\"optional\":true},\"payload\":{\"idestatistica\":1,\"pagamentos\":"+ record.value()+"}}";
                allpay="{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":true,\"field\":\"idestatistica\"},{\"type\":\"double\",\"optional\":true,\"field\":\"pagamentos\"}],\"optional\":true},\"payload\":{\"idestatistica\":1,\"pagamentos\":"+ record.value()+"}}";
                producer.send(new ProducerRecord<String, String>("estatisticas", null, allpay));
                System.out.println("allpay "+allpay);
                //


            }
            ConsumerRecords<String,Double> recordsouttopicnameAllCredit = consumerouttopicnameAllCredit.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsouttopicnameAllCredit) {
                System.out.println(record.key() + " => " + record.value());
                allcredit="{\"schema\":{\"type\":\"double\",\"optional\":true,\"field\":\"credito\"}],\"optional\":true},\"payload\":{\"idestatistica\":1,\"credito\":"+ record.value()+"}}";
                allcredit="{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":true,\"field\":\"idestatistica\"},{\"type\":\"double\",\"optional\":true,\"field\":\"credito\"}],\"optional\":true},\"payload\":{\"idestatistica\":1,\"credito\":"+ record.value()+"}}";

                producer.send(new ProducerRecord<String, String>("estatisticas", null, allcredit));
                System.out.println("allcredit "+allcredit);
            }


            ConsumerRecords<String,Double> recordsouttopicnameAllBalance = consumerouttopicnameAllBalance.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsouttopicnameAllBalance) {
                System.out.println(record.key() + " => " + record.value());
                allbalance = "\"balaco\": "+record.value().toString()+",";
                System.out.println("-------------------------------------------------------all balance: "+allbalance);
                //{"schema":{"type":"struct","fields":[{"type":"int32","optional":true,"field":"idestatistica"},
                allbalance="{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":true,\"field\":\"idestatistica\"},{\"type\":\"double\",\"optional\":true,\"field\":\"balaco\"}],\"optional\":true},\"payload\":{\"idestatistica\":1,\"balaco\":"+ record.value()+"}}";

                producer.send(new ProducerRecord<String, String>("estatisticas", null, allbalance));
                System.out.println("allbal "+allbalance);
            }



            ConsumerRecords<String,Double> recordsouttopicnamePayperClient = consumerouttopicnamePayperClient.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsouttopicnamePayperClient) {
                System.out.println("PAY PER CLIENT");
                System.out.println(record.key() + " => " + record.value());
                System.out.println("PAY PER CLIENT fim");
                String pay = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"idcliente\"},{\"type\":\"double\",\"optional\":true,\"field\":\"pagamento\"}],\"optional\":false},\"payload\":{\"idcliente\":"+record.key()+",\"pagamento\":"+ record.value()+"}}";
                producer.send(new ProducerRecord<String, String>("dados", null, pay));
                System.out.println("pay per client "+pay);
            }


            ConsumerRecords<String,Double> recordsconsumerouttopicnameCreditperClient = consumerouttopicnameCreditperClient.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsconsumerouttopicnameCreditperClient) {
                System.out.println("CREDIT PER CLIENT");
                System.out.println(record.key() + " => " + record.value());
                System.out.println("CREDIT PER CLIENT FIM");
                String credit = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"idcliente\"},{\"type\":\"double\",\"optional\":true,\"field\":\"credito\"}],\"optional\":false},\"payload\":{\"idcliente\":"+record.key()+",\"credito\":"+ record.value()+"}}";
                producer.send(new ProducerRecord<String, String>("dados", null, credit));
                System.out.println("credit per client "+credit);

            }

            ConsumerRecords<String,Double> recordsconsumerouttopicnameBillLastMonth = consumerouttopicnameBillLastMonth.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsconsumerouttopicnameBillLastMonth) {
                System.out.println("BILL LAST MONTH");
                System.out.println(record.key() + " => " + record.value());
                System.out.println("BILL LAST MONTH FIM");

                String bill = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"idcliente\"},{\"type\":\"double\",\"optional\":true,\"field\":\"bill\"}],\"optional\":false},\"payload\":{\"idcliente\":"+record.key()+",\"bill\":"+record.value()+"}}";
                producer.send(new ProducerRecord<String, String>("dados", null, bill));
                System.out.println("bill last month per client "+bill);

            }

            ConsumerRecords<String,Double> recordsconsumerouttopicnameBalanceperClient = consumerouttopicnameBalanceperClient.poll(Duration.ofMillis(300));
            for (ConsumerRecord<String, Double> record : recordsconsumerouttopicnameBalanceperClient) {
                System.out.println("BALANCE PER CLIENT");
                System.out.println(record.key() + " => " + record.value());
                System.out.println("BALANCE PER CLIENT FIM");
                //{"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"idcliente"},{"type":"double","optional":true,"field":"balance"}],"optional":false},"payload":{"idcliente":5,"balance":45.5}}
                String balance = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"idcliente\"},{\"type\":\"double\",\"optional\":true,\"field\":\"balance\"}],\"optional\":false},\"payload\":{\"idcliente\":"+record.key()+",\"balance\":"+ record.value()+"}}";
                producer.send(new ProducerRecord<String, String>("dados", null, balance));
                System.out.println("balance per client "+balance);
            }



            Thread.sleep(10000);

        }
//consumer.close();
    }
}