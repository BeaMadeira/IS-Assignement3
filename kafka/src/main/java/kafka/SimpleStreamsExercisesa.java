package kafka;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.KTableKTableJoinMerger;

public class SimpleStreamsExercisesa {
    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length != 8) {
            System.err.println("Wrong arguments. Please run the class as follows:");
            System.err.println(SimpleStreamsExercisesa.class.getName() + " input-topic output-topic");
            System.exit(1);
        }


        String topicName = args[0].toString();
        String topicNameCreditos = args[1].toString();
        String out= args[2].toString();
        String outtopicnameAllBalance = args[3].toString();
        String outtopicnameAllCredit = args[4].toString();
        String outtopicnamePayperClient = args[5].toString();
        String outtopicnameCreditperClient = args[6].toString();
        String outtopicnameBalanceperClient = args[7].toString();
        String outtopicnameBillsLastMonth = "lastmonth";

        java.util.Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application-b");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        java.util.Properties propsexb = new Properties();

        propsexb.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application-a");
        propsexb.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        propsexb.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        propsexb.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> linesPagamento = builder.stream(topicName);
        KStream<String,String>  linesCredito   = builder.stream(topicNameCreditos);

        KTable<String, Double> outlinesexb = linesPagamento.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                System.out.println("string pagamento : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Pagamento pagamentos = gson.fromJson(s, Pagamento.class);
                //conversoes
                Double preco=0.0;
                if(pagamentos.getCurrency().getExchangeRate()!=null) {
                    preco = pagamentos.getPrice() * pagamentos.getCurrency().getExchangeRate();
                }
                System.out.println("Pagamento : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((old, newval)-> old + newval);
        outlinesexb.toStream().to(outtopicnamePayperClient, Produced.with(Serdes.String(), Serdes.Double()));

        //Get the credits per client.
        KTable<String, Double> outlinesCredit = linesCredito.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string credito : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Double preco=0.0;
                Credito creditos=new Credito();
                if (s.indexOf("exchangeRate") != -1) {
                    // it contains world
                    creditos = gson.fromJson(s, Credito.class);

                    //conversoes
                    if(creditos.getCurrency().getExchangeRate()!=null) {
                        preco = creditos.getPrice() * creditos.getCurrency().getExchangeRate();
                    }
                }else{
                    System.out.printf("error : " + s);
                }
                System.out.println("Credit : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((old, newval)-> old + newval);

        outlinesCredit.toStream().to(outtopicnameCreditperClient, Produced.with(Serdes.String(), Serdes.Double()));



        //Total Credits
        KTable<String, Double> outlinesTotalCredit = linesCredito.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string credito : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Double preco=0.0;
                Credito creditos=new Credito();
                if (s.indexOf("exchangeRate") != -1) {
                    // it contains world
                    creditos = gson.fromJson(s, Credito.class);

                    //conversoes
                    if(creditos.getCurrency().getExchangeRate()!=null) {
                        preco = creditos.getPrice() * creditos.getCurrency().getExchangeRate();
                    }
                }else{
                    System.out.printf("error : " + s);
                }
                System.out.println("Credito : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).selectKey((oldkey,newkey) -> "totalCredits").groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((oldval, newval) -> oldval + newval);
        outlinesTotalCredit.toStream().to(outtopicnameAllCredit , Produced.with(Serdes.String(), Serdes.Double()));

        //Total Payments
        KTable<String, Double> outlinesTotalPayment = linesPagamento.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string total pagamento : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Double preco=0.0;
                Pagamento pagamento =new Pagamento();
                if (s.indexOf("exchangeRate") != -1) {
                    // it contains world
                    pagamento = gson.fromJson(s, Pagamento.class);

                    //conversoes
                    if(pagamento.getCurrency().getExchangeRate()!=null) {
                        preco = pagamento.getPrice() * pagamento.getCurrency().getExchangeRate();
                    }
                }else{
                    System.out.printf("error : " + s);
                }
                System.out.println("total Pagamento : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).selectKey((oldkey,newkey) -> "totalPayments").groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((oldval, newval) -> oldval + newval);
        outlinesTotalPayment.toStream().to(out, Produced.with(Serdes.String(), Serdes.Double()));


        //total balance

        KTable<String, Double> outlinesTotalPaymentForBal = linesPagamento.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string total pagamento : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Double preco=0.0;
                Pagamento pagamento =new Pagamento();
                if (s.indexOf("exchangeRate") != -1) {
                    // it contains world
                    pagamento = gson.fromJson(s, Pagamento.class);

                    //conversoes
                    if(pagamento.getCurrency().getExchangeRate()!=null) {
                        preco = pagamento.getPrice() * pagamento.getCurrency().getExchangeRate();
                    }
                }else{
                    System.out.printf("error : " + s);
                }
                System.out.println("total Pagamento : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).selectKey((oldkey,newkey) -> "totalBal").groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((oldval, newval) -> oldval + newval);
        outlinesTotalPaymentForBal.toStream().to(out, Produced.with(Serdes.String(), Serdes.Double()));

        KTable<String, Double> outlinesTotalCreditForBal = linesCredito.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string credito : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Double preco=0.0;
                Credito creditos=new Credito();
                if (s.indexOf("exchangeRate") != -1) {
                    // it contains world
                    creditos = gson.fromJson(s, Credito.class);

                    //conversoes
                    if(creditos.getCurrency().getExchangeRate()!=null) {
                        preco = creditos.getPrice() * creditos.getCurrency().getExchangeRate();
                    }
                }else{
                    System.out.printf("error : " + s);
                }
                System.out.println("Credito : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).selectKey((oldkey,newkey) -> "totalBal").groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((oldval, newval) -> oldval + newval);
        outlinesTotalCreditForBal.toStream().to(outtopicnameAllCredit , Produced.with(Serdes.String(), Serdes.Double()));


        KTable<String, Double> outlinesexbTotalPaymentCopia= outlinesTotalPaymentForBal;
        KTable<String, Double> outlinesCreditTotalCreditCopia = outlinesTotalCreditForBal;

        outlinesexbTotalPaymentCopia.join(outlinesCreditTotalCreditCopia,(paymentsValue,creditsValue) -> paymentsValue - creditsValue)
                .toStream().selectKey((oldkey,newkey) -> "totalBal").to(outtopicnameAllBalance, Produced.with(Serdes.String(), Serdes.Double()));




        //Get the current balance of a client.
        KTable<String, Double> outlinesexbCopia = outlinesexb;
        KTable<String, Double> outlinesCreditCopia = outlinesCredit;

        outlinesexbCopia.join(outlinesCreditCopia,(paymentsValue,creditsValue)->paymentsValue - creditsValue)
                .toStream().to(outtopicnameBalanceperClient, Produced.with(Serdes.String(), Serdes.Double()));


        //BILLS LAST MONTH

        KTable<Windowed<String>, Double> outlinesWindowedBills = linesPagamento.mapValues(new ValueMapper<String, Double>() {
            @Override
            public Double apply(String s) {
                //System.out.println("string pagamento : "+ s);
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //s para gson -- transformar em pagamento/credito
                Pagamento pagamentos = gson.fromJson(s, Pagamento.class);
                //conversoes
                Double preco=0.0;
                if(pagamentos.getCurrency().getExchangeRate()!=null) {
                    preco = pagamentos.getPrice() * pagamentos.getCurrency().getExchangeRate();
                }
                System.out.println("Pagamento : "+preco);
                //return pagamento.price e dps fazer reduce c isso
                return preco;
            }
        }).groupByKey(Grouped.with(Serdes.String(),Serdes.Double()))
        .windowedBy(TimeWindows.of(Duration.ofSeconds(20))).reduce((old, newval)-> old + newval);

        outlinesWindowedBills.toStream().to(outtopicnamePayperClient);


        /*KTable<Windowed<String>, Double> outlinesWindowedBills = outlinesCredit
                .toStream().groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(20)))
                .reduce((oldval, newval) -> oldval + newval);
        outlinesWindowedBills.toStream().to(outtopicnameBillsLastMonth);*/

        KafkaStreams streamsexb = new KafkaStreams(builder.build(), propsexb);


        streamsexb.start();
        //streams.start();
    }
}
