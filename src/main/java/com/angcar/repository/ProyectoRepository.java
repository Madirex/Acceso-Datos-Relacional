package com.angcar.repository;

import com.angcar.model.Proyecto;
import com.angcar.database.DataBaseController;
import com.angcar.model.Repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProyectoRepository implements CrudRepository<Proyecto, String> {

    @Override
    public Optional<List<Proyecto>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los proyectos");
        String query = "SELECT * FROM proyecto";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de proyectos"));
        ArrayList<Proyecto> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Proyecto(
                            resultSet.getString("uuid_proyecto"),
                            resultSet.getString("jefe_proyecto"),
                            resultSet.getString("nombre"),
                            resultSet.getDouble("presupuesto"),
                            resultSet.getDate("fecha_inicio"),
                            resultSet.getDate("fecha_fin"),
                            resultSet.getString("tecnologias_usadas"),
                            resultSet.getString("repositorio"),
                            resultSet.getBoolean("es_acabado"),
                            resultSet.getString("uuid_departamento")
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
    public Optional<Proyecto> getById(String s) throws SQLException {
        Optional<Proyecto> proyecto = Optional.empty();
        System.out.println("Obteniendo proyecto con id: " + s);
        String query = "SELECT * FROM proyecto WHERE uuid_proyecto = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "proyecto con ID " + s));
        if (resultSet.first()){
            proyecto = Optional.of(new Proyecto(
                    resultSet.getString("uuid_proyecto"),
                    resultSet.getString("jefe_proyecto"),
                    resultSet.getString("nombre"),
                    resultSet.getDouble("presupuesto"),
                    resultSet.getDate("fecha_inicio"),
                    resultSet.getDate("fecha_fin"),
                    resultSet.getString("tecnologias_usadas"),
                    resultSet.getString("repositorio"),
                    resultSet.getBoolean("es_acabado"),
                    resultSet.getString("uuid_departamento")
            ));
        }
        proyecto.ifPresent(System.out::println);
        db.close();
        return proyecto;
    }

    @Override
    public Optional<Proyecto> insert(Proyecto proyecto) throws SQLException {
        System.out.println("Insertando proyecto");
        String query = "INSERT INTO proyecto VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,proyecto.getUuid_proyecto(), proyecto.getJefe_proyecto(),
                proyecto.getNombre(), proyecto.getPresupuesto(), proyecto.getFecha_inicio(), proyecto.getFecha_fin(),
                proyecto.getTecnologias_usadas(), proyecto.getRepositorio(), proyecto.isEs_acabado(),
                proyecto.getUuid_departamento());
        if (res.isPresent()){
            System.out.println(proyecto);
        }
        db.close();

        return Optional.of(proyecto);
    }

    @Override
    public Optional<Proyecto> update(Proyecto proyecto) throws SQLException {
        System.out.println("Actualizando proyecto con id: " + proyecto.getUuid_proyecto());
        String query = "UPDATE proyecto SET jefe_proyecto = ?, nombre = ?, presupuesto = ?, " +
                "fecha_inicio = ?, fecha_fin = ?, tecnologias_usadas = ?, repositorio = ?, " +
                "es_acabado = ?, uuid_departamento = ? WHERE uuid_proyecto = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, proyecto.getJefe_proyecto(),
                proyecto.getNombre(), proyecto.getPresupuesto(),
                proyecto.getFecha_inicio(), proyecto.getFecha_fin(), proyecto.getTecnologias_usadas(),
                proyecto.getRepositorio(), proyecto.isEs_acabado(), proyecto.getUuid_departamento(), proyecto.getUuid_proyecto());
        db.close();
        if (res > 0)
            System.out.println(proyecto);

        return Optional.of(proyecto);
    }

    @Override
    public Optional<Proyecto> delete(String id) throws SQLException {
        Optional<Proyecto> opt = getById(id);
        if (opt.isPresent()){
            Proyecto proyecto = opt.get();

            System.out.println("Eliminando proyecto con id: " + proyecto.getUuid_proyecto());
            String query = "DELETE FROM proyecto WHERE uuid_proyecto = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, proyecto.getUuid_proyecto());
            db.close();
        }

        return opt;
    }
}
