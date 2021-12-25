package com.angcar.repository;

import com.angcar.model.Repositorio;
import com.angcar.database.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioRepository implements CrudRepository<Repositorio,String> {
    @Override
    public Optional<List<Repositorio>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los repositorios");
        String query = "SELECT * FROM repositorio";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de repositorios"));
        ArrayList<Repositorio> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Repositorio(
                            resultSet.getString("uuid_repositorio"),
                            resultSet.getString("nombre"),
                            resultSet.getDate("fecha_creacion"),
                            resultSet.getString("proyecto")
                    )
            );
        }
        db.close();

        if (!list.isEmpty()) {
            list.forEach(System.out::println);
        }

        return Optional.of(list);
    }

    @Override
    public Optional<Repositorio> getById(String s) throws SQLException {
        Optional<Repositorio> repositorio = Optional.empty();
        System.out.println("Obteniendo repositorio con id: " + s);
        String query = "SELECT * FROM repositorio WHERE uuid_repositorio = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "repositorio con ID " + s));
        if (resultSet.first()){
            repositorio = Optional.of(new Repositorio(
                    resultSet.getString("uuid_repositorio"),
                    resultSet.getString("nombre"),
                    resultSet.getDate("fecha_creacion"),
                    resultSet.getString("proyecto")
            ));
        }
        repositorio.ifPresent(System.out::println);
        db.close();
        return repositorio;
    }

    @Override
    public Optional<Repositorio> insert(Repositorio repositorio) throws SQLException {
        System.out.println("Insertando repositorio");
        String query = "INSERT INTO repositorio VALUES (?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,repositorio.getUuid_repositorio(), repositorio.getNombre(),
                repositorio.getFecha_creacion(), repositorio.getProyecto());
        if (res.isPresent()){
            System.out.println(repositorio);
        }
        db.close();

        return Optional.of(repositorio);
    }

    @Override
    public Optional<Repositorio> update(Repositorio repositorio) throws SQLException {
        System.out.println("Actualizando repositorio con id: " + repositorio.getUuid_repositorio());
        String query = "UPDATE repositorio SET nombre = ?, fecha_creacion = ?, proyecto = ? " +
                "WHERE uuid_repositorio = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, repositorio.getNombre(), repositorio.getFecha_creacion(), repositorio.getProyecto(),
                repositorio.getUuid_repositorio());
        db.close();
        if (res > 0)
            System.out.println(repositorio);

        return Optional.of(repositorio);
    }

    @Override
    public Optional<Repositorio> delete(String id) throws SQLException {
        Optional<Repositorio> opt = getById(id);
        if (opt.isPresent()){
            Repositorio repositorio = opt.get();

            System.out.println("Eliminando repositorio con id: " + repositorio.getUuid_repositorio());
            String query = "DELETE FROM repositorio WHERE uuid_repositorio = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, repositorio.getUuid_repositorio());
            db.close();
        }

        return opt;
    }
}
