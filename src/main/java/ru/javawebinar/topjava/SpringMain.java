package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.print("\nUsers out:\n");
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.getAll().forEach(System.out::println);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            // get all
            System.out.print("\nMeals out:\n");
            mealRestController.getAll().forEach(System.out::println);

            // create
            mealRestController.create(new Meal(LocalDateTime.now(), "Новая еда", 1500));

            // update
            Meal updMeal = new Meal(7, LocalDateTime.parse("2020-01-31T20:00"), "Ужин10", 10);
            mealRestController.update(updMeal, 7);
            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
