import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OktaSyslog {

    private static final Logger LOG = LoggerFactory.getLogger(OktaSyslog.class);

    private static void println(String message) {
        System.out.println(message);
        System.out.flush();
    }


    public static void main(String[] args){

        Client client = Clients.builder()
                .setOrgUrl("https://graylog-dev-337840.okta.com")
                .setClientCredentials(new TokenClientCredentials("00JJ3b6EcWUOcc1SEXBqHRwfodQIRpMaz5W3of2UVG"))
                .build();

        UserList users = client.listUsers();

        // get the first user in the collection
        println("First user in collection: " + users.iterator().next().getProfile().getEmail());

        //User user = client.getUser("Priya Rammohan");

    }

}