package ssf.d13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ssf.d13.model.Contact;

@Service
public class ContactsRedis implements ContactRepo {

    private static final String CONTACT_ENTITY = "addressBook";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact contact) {
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, contact.getId());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", contact.getId(), contact);
    }

    @Override
    public Contact findById(final String contactId) {
        Contact result = (Contact) redisTemplate.opsForHash()
                .get(CONTACT_ENTITY + "_Map", contactId);
        return result;
    }

    @Override
    public Optional<List<Contact>> getAddressBook() {
        List<Object> fromContactList = redisTemplate.opsForList()
                .range(CONTACT_ENTITY, 0, -1);
            
        List<Contact> addressBook = (List<Contact>) redisTemplate.opsForHash()
                .multiGet(CONTACT_ENTITY + "_Map", fromContactList)
                .stream()
                .filter(Contact.class::isInstance)
                .map(Contact.class::cast)
                .toList();
        return Optional.of(addressBook);
    }

}