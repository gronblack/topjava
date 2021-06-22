package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/*.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Meal USER_MEAL_100007 = meal100007;
    private static final Meal ADMIN_MEAL_100013 = meal100013;

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), userMeals);
        assertMatch(service.getAll(ADMIN_ID), adminMeals);
    }

    @Test
    public void getAllNotFound() {
        assertIsEmpty(service.getAll(NOT_FOUND));
    }

    @Test
    public void get() {
        assertMatch(service.get(USER_MEAL_100007.getId(), USER_ID), USER_MEAL_100007);
        assertMatch(service.get(ADMIN_MEAL_100013.getId(), ADMIN_ID), ADMIN_MEAL_100013);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_100007.getId(), ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_100013.getId(), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_100007.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_100007.getId(), USER_ID));

        service.delete(ADMIN_MEAL_100013.getId(), ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_100013.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_100007.getId(), ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_100013.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_END, USER_ID), userMeals);
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_END, ADMIN_ID), adminMeals);
    }

    @Test
    public void getBetweenInclusiveWithNull() {
        assertMatch(service.getBetweenInclusive(DATE_START, null, USER_ID), userMeals);
        assertMatch(service.getBetweenInclusive(null, DATE_END, ADMIN_ID), adminMeals);
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeals);
        assertMatch(service.getBetweenInclusive(null, null, ADMIN_ID), adminMeals);
    }

    @Test
    public void getBetweenInclusiveOneDate() {
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_START, USER_ID), userMealsOneDate);
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_START, ADMIN_ID), adminMealsOneDate);
    }

    @Test
    public void update() {
        Meal updated;

        updated = getUpdated(USER_MEAL_100007);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_100007.getId(), USER_ID), getUpdated(USER_MEAL_100007));

        updated = getUpdated(ADMIN_MEAL_100013);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL_100013.getId(), ADMIN_ID), getUpdated(ADMIN_MEAL_100013));
    }

    @Test
    public void updateNotFound() {
        Meal notFoundMeal = getNew();
        notFoundMeal.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(notFoundMeal, ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.update(notFoundMeal, USER_ID));
    }

    @Test
    public void updateAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(USER_MEAL_100007), ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.update(new Meal(ADMIN_MEAL_100013), USER_ID));
    }

    @Test
    public void create() {
        Meal created, newMeal;
        Integer newId;

        created = service.create(getNew(), USER_ID);
        newId = created.getId();
        newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);

        created = service.create(getNew(), ADMIN_ID);
        newId = created.getId();
        newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal newUserMeal = getNew();
        newUserMeal.setDateTime(USER_MEAL_100007.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newUserMeal, USER_ID));

        Meal newAdminMeal = getNew();
        newAdminMeal.setDateTime(ADMIN_MEAL_100013.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newAdminMeal, ADMIN_ID));
    }
}