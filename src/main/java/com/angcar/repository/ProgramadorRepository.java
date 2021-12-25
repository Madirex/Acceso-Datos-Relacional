package com.angcar.repository;

import com.angcar.model.Programador;
import com.angcar.database.DataBaseController;
import com.angcar.model.Proyecto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramadorRepository implements CrudRepository<Programador, String>{

    @Override
    public Optional<List<Programador>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los programadores");
        String query = "SELECT * FROM programador";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de Programadores"));
        ArrayList<Programador> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Programador(
                            resultSet.getString("uuid_programador"),
                            resultSet.getString("nombre"),
                            resultSet.getDate("fecha_alta"),
                            resultSet.getString("departamento"),
                            resultSet.getString("tecnologias_dominadas"),
                            resultSet.getDouble("salario"),
                            resultSet.getBoolean("es_jefe_departamento"),
                            resultSet.getBoolean("es_jefe_proyecto"),
                            resultSet.getBoolean("es_jefe_activo"),
                            resultSet.getString("password")
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
    public Optional<Programador> getById(String s) throws SQLException {
        Optional<Programador> programador = Optional.empty();
        System.out.println("Obteniendo programador con id: " + s);
        String query = "SELECT * FROM programador WHERE uuid_programador = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "programador con ID " + s));
        if (resultSet.first()){
            programador = Optional.of(new Programador(
                    resultSet.getString("uuid_programador"),
                    resultSet.getString("nombre"),
                    resultSet.getDate("fecha_alta"),
                    resultSet.getString("departamento"),
                    resultSet.getString("tecnologias_dominadas"),
                    resultSet.getDouble("salario"),
                    resultSet.getBoolean("es_jefe_departamento"),
                    resultSet.getBoolean("es_jefe_proyecto"),
                    resultSet.getBoolean("es_jefe_activo"),
                    resultSet.getString("password")
            ));
        }
        programador.ifPresent(System.out::println);
        db.close();
        return programador;
    }

    @Override
    public Optional<Programador> insert(Programador programador) throws SQLException {
        System.out.println("Insertando programador");
        String query = "INSERT INTO programador VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,programador.getUuid_programador(), programador.getNombre(), programador.getFecha_alta(),
                programador.getDepartamento(), programador.getTecnologias_dominadas(), programador.getSalario(),
                programador.isEs_jefe_departamento(), programador.isEs_jefe_proyecto(), programador.isEs_jefe_activo(),
                programador.getPassword());
        if (res.isPresent()){
            System.out.println(programador);
        }
        db.close();

        return Optional.of(programador);
    }

    @Override
    public Optional<Programador> update(Programador programador) throws SQLException {
        System.out.println("Actualizando programador con id: " + programador.getUuid_programador());
        String query = "UPDATE programador SET nombre = ?, fecha_alta = ?, departamento = ?, " +
               "tecnologias_dominadas = ?, salario = ?, " +
                "es_jefe_departamento = ?, es_jefe_proyecto = ?, es_jefe_activo = ?, password = ? " +
                "WHERE uuid_programador = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, programador.getNombre(), programador.getFecha_alta(),
                programador.getDepartamento(), programador.getTecnologias_dominadas(), programador.getSalario(),
                programador.isEs_jefe_departamento(), programador.isEs_jefe_proyecto(), programador.isEs_jefe_activo(),
                programador.getPassword(), programador.getUuid_programador());
        db.close();

        return Optional.of(programador);
    }

    @Override
    public Optional<Programador> delete(String id) throws SQLException {
        Optional<Programador> opt = getById(id);
        if (opt.isPresent()){
            Programador programador = opt.get();

            System.out.println("Eliminando programador con id: " + programador.getUuid_programador());
            String query = "DELETE FROM programador WHERE uuid_programador = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, programador.getUuid_programador());
            db.close();
        }

        return opt;
    }
}