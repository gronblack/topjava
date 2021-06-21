package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        int id;
        Meal meal;

        id = getAnyId(userMeals);
        meal = getMealById(userMeals, id);
        assertMatch(service.get(id, USER_ID), meal);

        id = getAnyId(adminMeals);
        meal = getMealById(adminMeals, id);
        assertMatch(service.get(id, ADMIN_ID), meal);
    }

    @Test
    public void getNotFound() {
        // not found meal
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));

        // another user
        assertThrows(NotFoundException.class, () -> service.get(getAnyId(userMeals), ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.get(getAnyId(adminMeals), USER_ID));
    }

    @Test
    public void delete() {
        int userMealId = getAnyId(userMeals);
        service.delete(userMealId, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMealId, USER_ID));

        int adminMealId = getAnyId(adminMeals);
        service.delete(adminMealId, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(adminMealId, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(getAnyId(userMeals), ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.delete(getAnyId(adminMeals), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(DATE_BETWEEN, DATE_BETWEEN, USER_ID), userMealsBetween);
        assertMatch(service.getBetweenInclusive(DATE_BETWEEN, DATE_BETWEEN, ADMIN_ID), adminMealsBetween);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), userMeals);
        assertMatch(service.getAll(ADMIN_ID), adminMeals);
        assertNotMatch(service.getAll(USER_ID), adminMeals);
        assertNotMatch(service.getAll(ADMIN_ID), userMeals);
    }

    @Test
    public void getAllNotFound() {
        assertMatch(service.getAll(NOT_FOUND), Collections.EMPTY_LIST);
    }

    @Test
    public void update() {
        int id;
        Meal updated;

        id = getAnyId(userMeals);
        updated = getUpdated(userMeals, id);
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), getUpdated(userMeals, id));

        id = getAnyId(adminMeals);
        updated = getUpdated(adminMeals, id);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(updated.getId(), ADMIN_ID), getUpdated(adminMeals, id));
    }

    @Test
    public void updateNotFound() {
        Meal userMeal = getMealById(userMeals, getAnyId(userMeals));
        assertThrows(NotFoundException.class, () -> service.update(userMeal, ADMIN_ID));

        Meal adminMeal = getMealById(adminMeals, getAnyId(adminMeals));
        assertThrows(NotFoundException.class, () -> service.update(adminMeal, USER_ID));
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
        Meal userMeal = service.get(getAnyId(userMeals), USER_ID);
        Meal newUserMeal = getNew();
        newUserMeal.setDateTime(userMeal.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newUserMeal, USER_ID));

        Meal adminMeal = service.get(getAnyId(adminMeals), ADMIN_ID);
        Meal newAdminMeal = getNew();
        newAdminMeal.setDateTime(adminMeal.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newAdminMeal, ADMIN_ID));
    }
}