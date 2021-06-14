package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Comparator<User> COMPARATOR =
            Comparator.comparing((Function<User, String>) AbstractNamedEntity::getName).thenComparing(User::getEmail);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(1, "Admin", "admin@mail.com", "", Role.ADMIN));
        save(new User(2, "Regular user", "user@mail.com", "", Role.USER));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete user {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            log.info("save {}", user);
            return user;
        }
        log.info("save {}", user);
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get user {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll users");
        return repository.values().stream().sorted(COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("user getByEmail {}", email);
        return getAll().stream().filter(user -> user.getEmail().equals(email))
                .findAny().orElse(null);
    }
}
