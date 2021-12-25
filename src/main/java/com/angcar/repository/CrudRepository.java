package com.angcar.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    Optional<List<T>> findAll() throws SQLException;
    Optional<T> getById(ID id) throws SQLException; //read
    Optional<T> insert(T t) throws SQLException; //create
    Optional<T> update(T t) throws SQLException;
    Optional<T> delete(ID t) throws SQLException;
}