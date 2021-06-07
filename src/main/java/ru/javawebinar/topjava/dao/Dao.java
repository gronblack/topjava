package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T get(int id);
    T add(T elem);
    T update(T elem);
    void delete(int id);
}
