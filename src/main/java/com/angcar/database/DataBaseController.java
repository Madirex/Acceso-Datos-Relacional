package com.angcar.database;

import com.angcar.utils.ApplicationProperties;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Optional;

/**
 * Controlador de Bases de Datos Relacionales
 */
public class DataBaseController {
    private static DataBaseController controller;
    @NonNull
    private String serverUrl;
    @NonNull
    private String serverPort;
    @NonNull
    private String dataBaseName;
    @NonNull
    private String user;
    @NonNull
    private String password;
    @NonNull
    private String jdbcDriver;
    @NonNull
    private Connection connection;
    @NonNull
    private PreparedStatement preparedStatement;

    private DataBaseController() {
        initConfig();
    }

    public static DataBaseController getInstance() {
        if (controller == null) {
            controller = new DataBaseController();
        }
        return controller;
    }

    private void initConfig() {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        serverUrl = properties.readProperty("database.server.url");
        serverPort = properties.readProperty("database.server.port");
        dataBaseName = properties.readProperty("database.name");
        jdbcDriver = properties.readProperty("database.jdbc.driver");
        Dotenv dotenv = Dotenv.load();
        user = dotenv.get("DATABASE_USER");
        password = dotenv.get("DATABASE_PASSWORD");
    }

    public void open() throws SQLException {
        String url = "jdbc:mariadb://" + this.serverUrl + ":" + this.serverPort + "/" + this.dataBaseName;
        connection = DriverManager.getConnection(url, user, password);
    }

    public void close() throws SQLException {
        preparedStatement.close();
        connection.close();
    }

    private ResultSet executeQuery(@NonNull String querySQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(querySQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public Optional<ResultSet> select(@NonNull String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    public Optional<ResultSet> select(@NonNull String querySQL, int limit, int offset, Object... params) throws SQLException {
        String query = querySQL + " LIMIT " + limit + " OFFSET " + offset;
        return Optional.of(executeQuery(query, params));
    }

    public Optional<ResultSet> insert(@NonNull String insertSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }

    public int update(@NonNull String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    public int delete(@NonNull String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    private int updateQuery(@NonNull String genericSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(genericSQL);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    public void initData(@NonNull String sqlFile) throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader(sqlFile));
        sr.runScript(reader);
    }
}
