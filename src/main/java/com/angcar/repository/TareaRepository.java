package com.angcar.repository;

import com.angcar.model.Tarea;
import com.angcar.database.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TareaRepository implements CrudRepository<Tarea, String> {
    @Override
    public Optional<List<Tarea>> findAll() throws SQLException {
        System.out.println("Obteniendo todas las tareas");
        String query = "SELECT * FROM tarea";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de tareas"));
        ArrayList<Tarea> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Tarea(
                            resultSet.getString("uuid_tarea"),
                            resultSet.getString("uuid_programador"),
                            resultSet.getString("uuid_issue")
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
    public Optional<Tarea> getById(String s) throws SQLException {
        Optional<Tarea> tarea = Optional.empty();
        System.out.println("Obteniendo tarea con id: " + s);
        String query = "SELECT * FROM tarea WHERE uuid_tarea = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "commit con ID " + s));
        if (resultSet.first()){
            tarea = Optional.of(new Tarea(
                    resultSet.getString("uuid_tarea"),
                    resultSet.getString("uuid_programador"),
                    resultSet.getString("uuid_issue")
            ));
        }
        tarea.ifPresent(System.out::println);
        db.close();
        return tarea;
    }

    @Override
    public Optional<Tarea> insert(Tarea tarea) throws SQLException {
        System.out.println("Insertando tarea");
        String query = "INSERT INTO tarea VALUES (?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,tarea.getUuid_tarea(),tarea.getUuid_programador(),
                tarea.getUuid_issue());
        if (res.isPresent()){
            System.out.println(tarea);
        }
        db.close();

        return Optional.of(tarea);
    }

    @Override
    public Optional<Tarea> update(Tarea tarea) throws SQLException {
        System.out.println("Actualizando tarea con id: " + tarea.getUuid_tarea());
        String query = "UPDATE tarea SET uuid_programador = ?, uuid_issue = ? WHERE uuid_tarea = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, tarea.getUuid_programador(), tarea.getUuid_issue(), tarea.getUuid_tarea());
        db.close();

        return Optional.of(tarea);
    }

    @Override
    public Optional<Tarea> delete(String id) throws SQLException {
        Optional<Tarea> opt = getById(id);
        if (opt.isPresent()){
            Tarea tarea = opt.get();

            System.out.println("Eliminando tarea con id: " + tarea.getUuid_tarea());
            String query = "DELETE FROM tarea WHERE uuid_tarea = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, tarea.getUuid_tarea());
            db.close();
        }

        return opt;
    }
}