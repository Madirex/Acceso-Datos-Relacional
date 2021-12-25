package com.angcar.service;

import com.angcar.dto.FichaDTO;
import com.angcar.repository.CrudRepository;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService<T, ID, R extends CrudRepository<T, ID>, DTO>
        implements IService<DTO, ID>{
    protected final R repository;

    // Operaciones CRUD

    // Obtiene todos
    public Optional<List<T>> findAll() throws SQLException {
        return repository.findAll();
    }

    // Obtiene por ID
    public Optional<T> getById(ID id) throws SQLException {
        return repository.getById(id);
    }

    // Inserta
    public Optional<T> insert(T t) throws SQLException {
        return repository.insert(t);
    }

    // Actualiza
    public Optional<T> update(T t) throws SQLException {
        return repository.update(t);
    }

    // Elimina
    public Optional<T> delete(ID id) throws SQLException {
        return repository.delete(id);
    }
}