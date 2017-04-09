package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        User removedUser = repository.remove(id);
        if (removedUser==null)
        {
            LOG.info("There is no user with id " + id);
            return false;
        }
        LOG.info("delete user with id " + id);
        return true;
    }

    @Override
    public User save(User user) {
        if (user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(),user);
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        User userById = repository.get(id);
        if (userById!=null)
        {
            LOG.info("get " + id);
            return userById;
        }
        LOG.info("There is no user with id " + id);
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        ArrayList<User> userList = new ArrayList<>(repository.values());
        Collections.sort(userList,new Comparator<User>(){

            @Override
            public int compare(User o1, User o2) {
                if (!o1.getName().equals(o2.getName()))
                    return o1.getName().compareTo(o2.getName());
                else return o1.getEmail().compareTo(o2.getEmail());
            }
        });
        return userList;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {

        for (User user : getAll())
            if (user.getEmail().equals(email)) {
                LOG.info("getByEmail " + email);
                return user;
            }
        LOG.info("There is no user with email " + email);
        return null;
    }
}
