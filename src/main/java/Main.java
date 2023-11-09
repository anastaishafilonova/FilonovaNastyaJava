import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> input = new HashMap<>();
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "88005553535");
        Message message = new Message(input, EnrichmentType.MSISDN);
        EnrichmentService enrichmentService = new EnrichmentService(List.of(EnrichmentType.MSISDN));
        User user1 = new User("Василий", "Смирнов");
        enrichmentService.updateUserByMsisdn("88005553535", user1);
        printMap(enrichmentService.enrich(message));
    }

    public static void printMap(Map<String, String> result) {
        for (String key: result.keySet()) {

            System.out.println(key + " : " + result.get(key));
        }
    }
}
