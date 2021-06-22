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
    }

    @Test
    public void getAllNotFound() {
        assertIsEmpty(service.getAll(NOT_FOUND));
    }

    @Test
    public void get() {
        assertMatch(service.get(adminMeal100011.getId(), ADMIN_ID), adminMeal100011);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal100007.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal100007.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal100007.getId(), USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void deleteAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(adminMeal100011.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_END, USER_ID), userMealsBetween);
    }

    @Test
    public void getBetweenInclusiveWithNull() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeals);
    }

    @Test
    public void getBetweenInclusiveOneDate() {
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_START, USER_ID), userMealsOneDate);
    }

    @Test
    public void update() {
        service.update(getUpdated(userMeal100007), USER_ID);
        assertMatch(service.get(userMeal100007.getId(), USER_ID), getUpdated(userMeal100007));
    }

    @Test
    public void updateNotFound() {
        Meal notFoundMeal = getNew();
        notFoundMeal.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(notFoundMeal, ADMIN_ID));
    }

    @Test
    public void updateAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(adminMeal100011), USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal newUserMeal = getNew();
        newUserMeal.setDateTime(userMeal100007.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newUserMeal, USER_ID));
    }
}