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
                .setOrgUrl("https://graylog.okta.com")
                .setClientCredentials(new TokenClientCredentials("{include the generated token}"))
                .build();

        UserList users = client.listUsers();

        // get the first user in the collection
        println("First user in collection: " + users.iterator().next().getProfile().getEmail());

        //User user = client.getUser("Priya Rammohan");

    }

}
