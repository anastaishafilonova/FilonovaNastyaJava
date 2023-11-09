import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationTest {
    @Test
    public void applicationTest() throws InterruptedException {
        EnrichmentService enrichmentService = new EnrichmentService(List.of(EnrichmentType.MSISDN));
        CopyOnWriteArrayList<Map<String, String>> inputTest = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<User> userTest = new CopyOnWriteArrayList<>();
        User user1 = new User("NS", "F");
        userTest.add(user1);
        enrichmentService.updateUserByMsisdn("88005553535", user1);
        Map<String, String> input = new HashMap<>();
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "88005553535");
        inputTest.add(input);
        User user2 = new User("A", "F");
        userTest.add(user2);
        enrichmentService.updateUserByMsisdn("8800555353", user2);
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "8800555353");
        inputTest.add(input);
        User user3 = new User("B", "F");
        userTest.add(user3);
        enrichmentService.updateUserByMsisdn("880055535", user3);
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "880055535");
        inputTest.add(input);
        User user4 = new User("S", "F");
        userTest.add(user4);
        enrichmentService.updateUserByMsisdn("88005553", user4);
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "88005553");
        inputTest.add(input);
        User user5 = new User("C", "F");
        userTest.add(user5);
        enrichmentService.updateUserByMsisdn("8800555", user5);
        input.put("action", "button_click");
        input.put("page", "book_card");
        input.put("msisdn", "8800555");
        inputTest.add(input);
        List<Map<String, String>> enrichmentResults = new CopyOnWriteArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executorService.submit(() -> {
                Message message = new Message(inputTest.get(finalI), EnrichmentType.MSISDN);
                enrichmentResults.add(enrichmentService.enrich(message));
                latch.countDown();
            });
        }
        latch.await();
        CopyOnWriteArrayList<Map<String, String>> perfectResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 5; i++) {
            inputTest.get(i).put("firstName", userTest.get(i).firstName);
            perfectResults.add(inputTest.get(i));
        }
        for (int i = 0; i < 5; i++) {
            boolean flag = enrichmentResults.get(i).size() == perfectResults.get(i).size();
            if (flag) {
                for (String s : enrichmentResults.get(i).keySet()) {
                    if (!(perfectResults.get(i).containsKey(s) && perfectResults.get(i).get(s).equals(enrichmentResults.get(i).get(s)))) {
                        flag = false;
                    }
                }
            }
            Assertions.assertTrue(flag);
            Assertions.assertEquals(enrichmentResults, perfectResults);
        }
    }
}
