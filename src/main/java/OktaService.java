import com.okta.sdk.resource.log.LogEventList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.impl.resource.log.DefaultLogEventList;
import com.okta.sdk.resource.log.LogEventList;


public class OktaService {

    Client client = null;

    public LogEventList getSystemLogs(String since, String until,
                                      String filter, String q) {
        return client.getLogs(until, since, filter, q, "ASCENDING");
    }

    public JsonNode getSystemLogsASJSON(String since, String until,
                                        String filter, String q) {
        LogEventList list =  client.getLogs(until, since, filter, q, "ASCENDING");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(list, JsonNode.class);
    }

    public int getsizeOfCurrentPage(String since, String until,
                                    String filter, String q) {

        DefaultLogEventList list = (DefaultLogEventList) client.getLogs(until, since, filter, q, "ASCENDING");
        String nextpage = (String)list.get("nextPage");
        System.out.println("nextpageLink:"+nextpage);

        //https://dev-337840.okta.com/api/v1/logs?sortOrder=ASCENDING&since=2020-04-01T00:00:00.000Z

        return list.getCurrentPage().getItems().size();
    }

    public Client getClient(String domain, String apiKey){

        Client client = Clients.builder()
                .setClientCredentials(new TokenClientCredentials(apiKey))
                .setOrgUrl(domain)
                .build();
        return client;

    }

    public int setDomainWithAdmin(String since, String until,
                                  String filter, String q) throws IllegalArgumentException{
        DefaultLogEventList list = (DefaultLogEventList) client.getLogs(until, since, filter, q, "ASCENDING");
        return list.getCurrentPage().getItems().size();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static void main(String[] args) {

        try {
            OktaService service = new OktaService();
            Client client = service.getClient("","");
            service.setClient(client);


            LogEventList list =  service.getSystemLogs("2020-04-01T00:00:00.000Z", null, null, null);
            System.out.println("href:"+list.getResourceHref());
            int pageSize = service.getsizeOfCurrentPage(null,null,null,null);
            System.out.println("size of the current page:"+pageSize);
        }
        catch(Exception exp){
            System.out.println("message:"+exp.getMessage());
            throw exp;
        }

    }


}

