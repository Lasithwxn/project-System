package com.inventory.interfaces;

import java.util.List;

public interface Manageable<T> {
    void save(T entity);
    void delete(String id);
    T findById(String id);
    List<T> findAll();
}
