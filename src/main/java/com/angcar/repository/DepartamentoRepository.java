package com.angcar.repository;

import com.angcar.model.Departamento;
import com.angcar.database.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoRepository implements CrudRepository<Departamento, String>{
    @Override
    public Optional<List<Departamento>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los departamentos...");
        String query = "SELECT * FROM departamento";
        DataBaseController db = DataBaseController.getInstance();
        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de repositorios."));
        List<Departamento> departamentoList = new ArrayList<>();
        while (resultSet.next()) {
            departamentoList.add(
                    new Departamento(
                            resultSet.getString("uuid_departamento"),
                            resultSet.getString("nombre"),
                            resultSet.getString("jefe_departamento"),
                            resultSet.getDouble("presupuesto"),
                            resultSet.getDouble("presupuesto_anual")
                    )
            );
        }

        if (!departamentoList.isEmpty()){
            departamentoList.forEach(System.out::println);
        }
        db.close();
        return Optional.of(departamentoList);
    }

    @Override
    public Optional<Departamento> getById(String s) throws SQLException {
        Optional<Departamento> departamentoOpt = Optional.empty();
        System.out.println("Obteniendo departamento con id: " + s);
        String query = "SELECT * FROM departamento WHERE uuid_departamento = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "departamento con ID " + s));
        if (resultSet.first()){
            departamentoOpt = Optional.of(new Departamento(
                    resultSet.getString("uuid_departamento"),
                    resultSet.getString("nombre"),
                    resultSet.getString("jefe_departamento"),
                    resultSet.getDouble("presupuesto"),
                    resultSet.getDouble("presupuesto_anual")
            ));
        }
        departamentoOpt.ifPresent(System.out::println);
        db.close();
        return departamentoOpt;
    }

    @Override
    public Optional<Departamento> insert(Departamento departamento) throws SQLException {
        System.out.println("Insertando departamento");
        String query = "INSERT INTO departamento VALUES (?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,departamento.getUuid_departamento(), departamento.getNombre(),
                departamento.getJefe_departamento(), departamento.getPresupuesto(), departamento.getPresupuesto_anual());
        if (res.isPresent()){
            System.out.println(departamento);
        }
        db.close();

        return Optional.of(departamento);
    }

    @Override
    public Optional<Departamento> update(Departamento departamento) throws SQLException {
        System.out.println("Actualizando departamento con id: " + departamento.getUuid_departamento());
        String query = "UPDATE departamento SET nombre = ?, jefe_departamento = ?, presupuesto = ?, " +
                "presupuesto_anual = ? WHERE uuid_departamento = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, departamento.getNombre(), departamento.getJefe_departamento(),
                departamento.getPresupuesto(), departamento.getPresupuesto_anual(), departamento.getUuid_departamento());
        db.close();
        if (res > 0)
            System.out.println(departamento);

        return Optional.of(departamento);
    }

    @Override
    public Optional<Departamento> delete(String id) throws SQLException {
        Optional<Departamento> opt = getById(id);
        if (opt.isPresent()){
            Departamento departamento = opt.get();

            System.out.println("Eliminando departamento con id: " + departamento.getUuid_departamento());
            String query = "DELETE FROM departamento WHERE uuid_departamento = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, departamento.getUuid_departamento());
            db.close();
        }

        return opt;
    }

}
