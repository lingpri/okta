import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.log.LogEvent;
import com.okta.sdk.resource.log.LogEventList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OktaSyslog {

    private static final Logger LOG = LoggerFactory.getLogger(OktaSyslog.class);

    private static void println(String message) {
        System.out.println(message);
        System.out.flush();
    }

    private static void printSpliteratorCharacteristicsForTraversal(LogEventList events) {

        System.out.println("*************************************************************");
        Spliterator<LogEvent> events1 = events.spliterator();
        if(events1.hasCharacteristics(Spliterator.ORDERED)) {
            /**
             *Person 1: What is this huge number?
             Person 2 (Master Coder): It is the 64 bit integer limit. That number is over nine quintillion.
             Person 3 (Master Coder): 9,223,372,036,854,775,807, to be exact.
             */
            System.out.println("estimateSize:"+events1.estimateSize());

        }
        if(events1.hasCharacteristics(Spliterator.SIZED)){
            System.out.println("sized");
        }
        if(events1.hasCharacteristics(Spliterator.IMMUTABLE)){
            System.out.println("IMMUTABLE");
        }
        if(events1.hasCharacteristics(Spliterator.SORTED)){
            System.out.println("SORTED");
        }
        if(events1.hasCharacteristics(Spliterator.CONCURRENT)){
            System.out.println("CONCURRENT");
        }
        if(events1.hasCharacteristics(Spliterator.IMMUTABLE)){
            System.out.println("IMMUTABLE");
        }
        if(events1.hasCharacteristics(Spliterator.NONNULL)){
            System.out.println("NONNULL");
        }
        if(events1.hasCharacteristics(Spliterator.DISTINCT)){
            System.out.println("DISTINCT");
        }
        if(events1.hasCharacteristics(Spliterator.IMMUTABLE)){
            System.out.println("IMMUTABLE");
        }
        System.out.println("*************************************************************");
    }

    private static void accept(LogEvent logEvent) {
        System.out.printf("Time=%s,Actor=%s,Event Info=%s%n",
                logEvent.getPublished(),
                logEvent.getActor().getDisplayName(),
                logEvent.getDisplayMessage()
                );
    }

    /**
     * How to implement your client
     */
    private static void p_getlogs() {
      //Example using parallel streams and using your own Fork join pool
      long time = System.currentTimeMillis();
      Client client = Clients.builder().build();
       //get all the logs
      LogEventList logEvents = client.getLogs();
      logEvents.stream().parallel().forEach(OktaSyslog::accept);
      time = System.currentTimeMillis() - time;
      System.out.println("elapsed time in ms ="+time);
      //printSpliteratorCharacteristicsForTraversal(logEvents);
    }

    private static void s_getlogs() {
        //Example using parallel streams and using your own Fork join pool
        long time = System.currentTimeMillis();
        Client client = Clients.builder().build();
        //get all the logs
        LogEventList logEvents = client.getLogs();
        logEvents.stream().forEach(OktaSyslog::accept);
        time = System.currentTimeMillis() - time;
        System.out.println("elapsed time in ms ="+time);
        //printSpliteratorCharacteristicsForTraversal(logEvents);
    }

    private static void benchMark() {
       // p_getlogs();
        s_getlogs();
    }

    public static void main(String[] args){

        benchMark();

       // LogEventList logEvents2 = client.getLogs(null, null, "eventType eq \"system.api_token.create\"", null , null);
       // print(logEvents2);

        //filter query example
        /*get logs based on these filters
        actor who caused the action
        eventType eq "system.api_token.create"
        outcome of the event
        severity eq "INFO"
        target*/
        //LogEventList logEvents2 = client.getLogs(null, null, "eventType eq \"system.api_token.create\"", null , null);
       // System.out.println("the events with eventType equals system.api_token.create------------------------------------------------------------------"+logEvents);
        //logEvents(logEvents2);

        //LogEventList logEvents1 = client.getLogs(null, null, null, "INFO" , null);
        //System.out.println("----------------------------severity WARN");
        //logEvents(logEvents1);

    }


}