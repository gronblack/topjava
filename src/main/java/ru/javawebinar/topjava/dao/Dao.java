package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T, ID> {
    List<T> getAll();
    T get(ID id);
    void add(T elem);
    void delete(ID id);
    void update(ID id, T elem);
}
