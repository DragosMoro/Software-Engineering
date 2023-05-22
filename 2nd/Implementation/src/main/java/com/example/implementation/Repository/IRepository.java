package com.example.implementation.Repository;


import com.example.implementation.Model.Entity;

public interface IRepository<E extends Entity<Integer>> {
    E getById(Integer id);
    void add(E entity);
    void delete(Integer id);
    void update(E entity);
    Iterable<E> getAll();


}
