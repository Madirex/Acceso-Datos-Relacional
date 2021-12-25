package com.angcar.repository;

import com.angcar.model.Issue;
import com.angcar.database.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IssueRepository implements CrudRepository<Issue, String>{
    @Override
    public Optional<List<Issue>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los issues");
        String query = "SELECT * FROM issue";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de Issues"));
        ArrayList<Issue> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Issue(
                            resultSet.getString("uuid_issue"),
                            resultSet.getString("titulo"),
                            resultSet.getString("texto"),
                            resultSet.getDate("fecha"),
                            resultSet.getString("proyecto"),
                            resultSet.getString("repositorio_asignado"),
                            resultSet.getBoolean("es_acabado")
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
    public Optional<Issue> getById(String s) throws SQLException {
        Optional<Issue> issue = Optional.empty();
        System.out.println("Obteniendo issue con id: " + s);
        String query = "SELECT * FROM issue WHERE uuid_issue = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, s).orElseThrow(() -> new SQLException("Error al consultar " +
                "issue con ID " + s));
        if (resultSet.first()){
            issue = Optional.of(new Issue(
                    resultSet.getString("uuid_issue"),
                    resultSet.getString("titulo"),
                    resultSet.getString("texto"),
                    resultSet.getDate("fecha"),
                    resultSet.getString("proyecto"),
                    resultSet.getString("repositorio_asignado"),
                    resultSet.getBoolean("es_acabado")
            ));
        }
        issue.ifPresent(System.out::println);
        db.close();
        return issue;
    }

    @Override
    public Optional<Issue> insert(Issue issue) throws SQLException {
        System.out.println("Insertando issue");
        String query = "INSERT INTO issue VALUES (?, ?, ?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,issue.getUuid_issue(), issue.getTitulo(), issue.getTexto(),
                issue.getFecha(), issue.getProyecto(),
                issue.getRepositorio_asignado(), issue.isEsAcabado());
        if (res.isPresent()){
            System.out.println(issue);
        }
        db.close();

        return Optional.of(issue);
    }

    @Override
    public Optional<Issue> update(Issue issue) throws SQLException {
        System.out.println("Actualizando issue con id: " + issue.getUuid_issue());
        String query = "UPDATE issue SET titulo = ?, texto = ?, fecha = ?, proyecto = ?," +
                " repositorio_asignado = ?, es_acabado = ? WHERE uuid_issue = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, issue.getTitulo(), issue.getTexto(),issue.getFecha(),
                issue.getProyecto(), issue.getRepositorio_asignado(),
                issue.isEsAcabado(), issue.getUuid_issue());
        db.close();
        if (res > 0)
            System.out.println(issue);

        return Optional.of(issue);
    }

    @Override
    public Optional<Issue> delete(String id) throws SQLException {
        Optional<Issue> opt = getById(id);
        if (opt.isPresent()){
            Issue issue = opt.get();

            System.out.println("Eliminando issue con id: " + issue.getUuid_issue());
            String query = "DELETE FROM issue WHERE uuid_issue = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, issue.getUuid_issue());
            db.close();
        }

        return opt;
    }
}