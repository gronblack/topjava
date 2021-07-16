package ru.javawebinar.topjava.service.jdbc;

import org.junit.Ignore;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
//    @Autowired
//    private Environment environment;

    @Override
    @Ignore
    public void createWithException() throws Exception {
//        Assume.assumeFalse(Arrays.asList(environment.getActiveProfiles()).contains(JDBC));
//        super.createWithException();
    }
}