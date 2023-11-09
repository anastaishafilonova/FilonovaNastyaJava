import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EnrichmentService implements UserRepository {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    List<EnrichmentType> availableEnrichmentType;
    public EnrichmentService(List<EnrichmentType> availableEnrichmentType) {
        this.availableEnrichmentType = availableEnrichmentType;
    }
    public synchronized Map<String, String> enrich(Message message) {
        for (EnrichmentType e: availableEnrichmentType) {
            if (e == message.enrichmentType) {
                if (e == EnrichmentType.MSISDN) {
                    if (message.content.containsKey(EnrichmentType.MSISDN.string)) {
                        Map<String, String> input = message.content;
                        User user = findByMsisdn(input.get(EnrichmentType.MSISDN.string));
                        input.put("firstName", user.firstName);
                        input.put("lastName", user.lastName);
                        return input;
                    }
                }
            }
        }
        return message.content;


    }
    @Override
    public User findByMsisdn(String msisdn) {
        return users.get(msisdn);
    }

    @Override
    public void updateUserByMsisdn(String msisdn, User user) {
        users.put(msisdn, user);
    }
}