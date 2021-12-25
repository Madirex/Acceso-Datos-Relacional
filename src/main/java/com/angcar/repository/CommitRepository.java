package com.angcar.repository;

import com.angcar.model.Commit;
import com.angcar.database.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommitRepository implements CrudRepository<Commit, String>{

    @Override
    public Optional<List<Commit>> findAll() throws SQLException {
        System.out.println("Obteniendo todos los commits");
        String query = "SELECT * FROM commit";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query).orElseThrow(() -> new SQLException("Error al consultar " +
                "los registros de Commits"));
        ArrayList<Commit> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Commit(
                            resultSet.getString("uuid_commit"),
                            resultSet.getString("titulo"),
                            resultSet.getString("texto"),
                            resultSet.getDate("fecha"),
                            resultSet.getString("repositorio"),
                            resultSet.getString("proyecto"),
                            resultSet.getString("autor_commit"),
                            resultSet.getString("issue_procedente")
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
    public Optional<Commit> getById(String id) throws SQLException {
        Optional<Commit> commit = Optional.empty();
        System.out.println("Obteniendo commit con id: " + id);
        String query = "SELECT * FROM commit WHERE uuid_commit = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        ResultSet resultSet = db.select(query, id).orElseThrow(() -> new SQLException("Error al consultar " +
                "commit con ID " + id));
        if (resultSet.first()){
            commit = Optional.of(new Commit(
                    resultSet.getString("uuid_commit"),
                    resultSet.getString("titulo"),
                    resultSet.getString("texto"),
                    resultSet.getDate("fecha"),
                    resultSet.getString("repositorio"),
                    resultSet.getString("proyecto"),
                    resultSet.getString("autor_commit"),
                    resultSet.getString("issue_procedente")
            ));
        }
        commit.ifPresent(System.out::println);
        db.close();
        return commit;
    }

    @Override
    public Optional<Commit> insert(Commit commit) throws SQLException {
        System.out.println("Insertando commit");
        String query = "INSERT INTO commit VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        Optional<ResultSet> res = db.insert(query,commit.getUuid_commit(),commit.getTitulo(),
                commit.getTexto(), commit.getFecha(), commit.getRepositorio(), commit.getProyecto(),
                commit.getAutor_commit(), commit.getIssue_procedente());
        if (res.isPresent()){
            System.out.println(commit);
        }
        db.close();

        return Optional.of(commit);
    }

    @Override
    public Optional<Commit> update(Commit commit) throws SQLException {
        System.out.println("Actualizando commit con id: " + commit.getUuid_commit());
        String query = "UPDATE commit SET titulo = ?, texto = ?, fecha = ?, repositorio = ?, proyecto = ?," +
                " autor_commit = ?, issue_procedente = ? WHERE uuid_commit = ?";
        DataBaseController db = DataBaseController.getInstance();

        db.open();
        int res = db.update(query, commit.getTitulo(), commit.getTexto(),commit.getFecha(),
                commit.getRepositorio(), commit.getProyecto(), commit.getAutor_commit(),
                commit.getIssue_procedente(), commit.getUuid_commit());
        db.close();
        if (res > 0)
            System.out.println(commit);

        return Optional.of(commit);
    }

    @Override
    public Optional<Commit> delete(String id) throws SQLException {
        Optional<Commit> opt = getById(id);
        if (opt.isPresent()){
            Commit commit = opt.get();

            System.out.println("Eliminando commit con id: " + commit.getUuid_commit());
            String query = "DELETE FROM commit WHERE uuid_commit = ?";
            DataBaseController db = DataBaseController.getInstance();

            db.open();
            int res = db.delete(query, commit.getUuid_commit());
            db.close();
        }

        return opt;
    }

}
