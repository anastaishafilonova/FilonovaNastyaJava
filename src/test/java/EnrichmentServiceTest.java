import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrichmentServiceTest {
    EnrichmentService enrichmentService = new EnrichmentService(List.of(EnrichmentType.MSISDN));
    User user = new User("NS", "F");
    @Test
    public void updateUserByMsisdnTest() {
        enrichmentService.updateUserByMsisdn("7303553377", user);
        Map<String, User> testMapOfUsers = new HashMap<>();
        testMapOfUsers.put("7303553377", user);
        enrichmentService.updateUserByMsisdn("7303553377", user);
        Map<String, User> result = enrichmentService.users;
        boolean flag = testMapOfUsers.size() == result.size();
        if (flag) {
            for (String s : testMapOfUsers.keySet()) {
                if (!(result.containsKey(s) && result.get(s).equals(testMapOfUsers.get(s)))) {
                    flag = false;
                }
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    public void findByMsisdnTest() {
        enrichmentService.updateUserByMsisdn("7303553377", user);
        Assertions.assertTrue(enrichmentService.findByMsisdn("7303553377").equals(user));
    }

    @Test
    public void enrichTest() {
        enrichmentService.updateUserByMsisdn("88005553535", user);
        Map<String, String> input = new HashMap<>();
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "88005553535");
        Message message1 = new Message(input, EnrichmentType.MSISDN);
        input.remove("msisdn");
        Message message2 = new Message(input, EnrichmentType.MSISDN);
        Assertions.assertEquals(enrichmentService.enrich(message2), input);
        input.put("msisdn", "88005553535");
        input.put("firstName", user.firstName);
        input.put("lastName", user.lastName);
        Map<String, String> result = enrichmentService.enrich(message1);
        boolean flag = input.size() == result.size();
        if (flag) {
            for (String s : input.keySet()) {
                if (!(result.containsKey(s) && result.get(s).equals(input.get(s)))) {
                    flag = false;
                }
            }
        }
        Assertions.assertTrue(flag);
    }
}
