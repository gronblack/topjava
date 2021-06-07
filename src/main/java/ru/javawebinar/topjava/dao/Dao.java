package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T, ID> {
    List<T> getAll();
    T get(ID id);
    T add(T elem);
    T update(T elem);
    void delete(ID id);
}
