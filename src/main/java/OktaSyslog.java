import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.log.LogEvent;
import com.okta.sdk.resource.log.LogEventList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class OktaSyslog {

    private static final Logger LOG = LoggerFactory.getLogger(OktaSyslog.class);

    private static void println(String message) {
        System.out.println(message);
        System.out.flush();
    }

    private static void logEvents(LogEventList logEvents) {

        logEvents.stream().forEach(new Consumer<LogEvent>() {
            public void accept(LogEvent event) {
                System.out.println(event.toString());
            }
        });


    }


    public static void main(String[] args){

        Client client = Clients.builder()
                .setOrgUrl("graylogdomain")
                .setClientCredentials(new TokenClientCredentials("<secret>"))
                .build();

        UserList users = client.listUsers();

        // get the first user in the collection
        println("First user in collection: " + users.iterator().next().getProfile().getEmail());

        //get all the logs
        LogEventList logEvents = client.getLogs();
        logEvents(client.getLogs());

        //filter query example
        /*get logs based on these filters
        actor who caused the action
        eventType eq "system.api_token.create"
        outcome of the event
        severity eq "INFO"
        target*/
        LogEventList logEvents2 = client.getLogs(null, null, "eventType eq \"system.api_token.create\"", null , null);
        System.out.println("the events with eventType equals system.api_token.create------------------------------------------------------------------"+logEvents);
        logEvents(logEvents2);

        LogEventList logEvents1 = client.getLogs(null, null, null, "INFO" , null);
        System.out.println("----------------------------severity WARN");
        logEvents(logEvents1);

    }

}