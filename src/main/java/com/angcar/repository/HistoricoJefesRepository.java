package com.angcar.repository;

import com.angcar.database.DataBaseController;
import com.angcar.model.HistoricoJefes;
import com.angcar.model.Issue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoricoJefesRepository implements CrudRepository<HistoricoJefes,String> {
    @Override
    public Optional<List<HistoricoJefes>> findAll() throws SQLException {
        System.out.println("Obteniendo todos el histórico de jefes");
        String query = "SELECT * FROM historico_jefes";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de historico_jefes"));
        ArrayList<HistoricoJefes> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new HistoricoJefes(
                            resultSet.getString("uuid_historico"),
                            resultSet.getString("uuid_programador"),
                            resultSet.getString("uuid_departamento"),
                            resultSet.getDate("fecha_alta"),
                            resultSet.getDate("fecha_baja")
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
    public Optional<HistoricoJefes> getById(String s) throws SQLException {
        Optional<HistoricoJefes> historicoJefes = Optional.empty();
        System.out.println("Obteniendo historico_jefes con id: " + s);
        String query = "SELECT * FROM historico_jefes WHERE uuid_historico = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "historico_jefes con ID " + s));
        if (resultSet.first()){
            historicoJefes = Optional.of(new HistoricoJefes(
                    resultSet.getString("uuid_historico"),
                    resultSet.getString("uuid_programador"),
                    resultSet.getString("uuid_departamento"),
                    resultSet.getDate("fecha_alta"),
                    resultSet.getDate("fecha_baja")
            ));
        }
        historicoJefes.ifPresent(System.out::println);
        db.close();
        return historicoJefes;
    }

    @Override
    public Optional<HistoricoJefes> insert(HistoricoJefes historicoJefes) throws SQLException {
        System.out.println("Insertando historico_jefes");
        String query = "INSERT INTO historico_jefes VALUES (?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,historicoJefes.getUuid_historico(),
                historicoJefes.getUuid_programador(), historicoJefes.getUuid_departamento(),
                historicoJefes.getFecha_alta(), historicoJefes.getFecha_baja());
        if (res.isPresent()){
            System.out.println(historicoJefes);
        }
        db.close();

        return Optional.of(historicoJefes);
    }

    @Override
    public Optional<HistoricoJefes> update(HistoricoJefes historicoJefes) throws SQLException {
        System.out.println("Actualizando historico_jefes con id: " + historicoJefes.getUuid_historico());
        String query = "UPDATE historico_jefes SET uuid_programador = ?, uuid_departamento = ?, fecha_alta = ?, " +
                "fecha_baja = ? WHERE uuid_historico = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, historicoJefes.getUuid_programador(), historicoJefes.getUuid_departamento(),
                historicoJefes.getFecha_alta(), historicoJefes.getFecha_baja(), historicoJefes.getUuid_historico());
        db.close();
        if (res > 0)
            System.out.println(historicoJefes);

        return Optional.of(historicoJefes);
    }

    @Override
    public Optional<HistoricoJefes> delete(String id) throws SQLException {
        Optional<HistoricoJefes> opt = getById(id);
        if (opt.isPresent()){
            HistoricoJefes historicoJefes = opt.get();

            System.out.println("Eliminando historico_jefes con id: " + historicoJefes.getUuid_historico());
            String query = "DELETE FROM historico_jefes WHERE uuid_historico = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, historicoJefes.getUuid_historico());
            db.close();
        }

        return opt;
    }

}
