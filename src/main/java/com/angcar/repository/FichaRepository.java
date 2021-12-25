package com.angcar.repository;

import com.angcar.model.Ficha;
import com.angcar.database.DataBaseController;
import com.angcar.model.HistoricoJefes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FichaRepository implements CrudRepository<Ficha, String>{
    @Override
    public Optional<List<Ficha>> findAll() throws SQLException {
        System.out.println("Obteniendo todas las fichas");
        String query = "SELECT * FROM ficha";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de las fichas."));
        ArrayList<Ficha> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Ficha(
                            resultSet.getString("uuid_ficha"),
                            resultSet.getString("uuid_programador"),
                            resultSet.getString("uuid_proyecto")
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
    public Optional<Ficha> getById(String s) throws SQLException {
        Optional<Ficha> ficha = Optional.empty();
        System.out.println("Obteniendo ficha con id: " + s);
        String query = "SELECT * FROM ficha WHERE uuid_ficha = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "la ficha con ID " + s));
        if (resultSet.first()){
            ficha = Optional.of(new Ficha(
                    resultSet.getString("uuid_ficha"),
                    resultSet.getString("uuid_programador"),
                    resultSet.getString("uuid_proyecto")));
        }
        ficha.ifPresent(System.out::println);
        db.close();
        return ficha;
    }

    @Override
    public Optional<Ficha> insert(Ficha ficha) throws SQLException {
        System.out.println("Insertando ficha");
        String query = "INSERT INTO ficha VALUES (?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,ficha.getUuid_ficha(), ficha.getUuid_programador(),
                ficha.getUuid_proyecto());
        if (res.isPresent()){
            System.out.println(ficha);
        }
        db.close();

        return Optional.of(ficha);
    }

    @Override
    public Optional<Ficha> update(Ficha ficha) throws SQLException {
        System.out.println("Actualizando ficha con id: " + ficha.getUuid_ficha());
        String query = "UPDATE ficha SET uuid_programador = ?, uuid_proyecto = ? WHERE uuid_ficha = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, ficha.getUuid_programador(), ficha.getUuid_proyecto(), ficha.getUuid_ficha());
        db.close();

        if (res > 0)
            System.out.println(ficha);

        return Optional.of(ficha);
    }

    @Override
    public Optional<Ficha> delete(String id) throws SQLException {
        Optional<Ficha> opt = getById(id);
        if (opt.isPresent()){
            Ficha ficha = opt.get();

            System.out.println("Eliminando ficha con id: " + ficha.getUuid_ficha());
            String query = "DELETE FROM ficha WHERE uuid_ficha = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, ficha.getUuid_ficha());
            db.close();
        }

        return opt;
    }

}
