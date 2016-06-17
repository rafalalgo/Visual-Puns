package kalambury.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by Rafał Byczek on 12.06.16.
 */

final public class Database {
    public static final Database instance = new Database();
    private String SERVER_ADRES;
    private String PORT;
    private String DB_NAME;
    private String USER_NAME;
    private String PASSWORD;

    public void loadParams()  {
        Properties props = new Properties();
        InputStream is = null;

        try {
            File f = new File("config.properties");
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }

        try {
            if(is == null) {
                is = getClass().getResourceAsStream("config.properties");
            }

            props.load(is);
        } catch (Exception e) {

        }

        SERVER_ADRES = props.getProperty("SERVER_ADRES");
        PORT = props.getProperty("PORT");
        DB_NAME = props.getProperty("DB_NAME");
        USER_NAME = props.getProperty("USER_NAME");
        PASSWORD = props.getProperty("PASSWORD");
    }

    public final Connection connection;

    public Database() {
        loadParams();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + SERVER_ADRES + ':' + PORT + '/' + DB_NAME, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public static <T, S> void insertColumn(final TableView<T> table, final Class<S> typeTag, final String name, final int minWidth) {
        final TableColumn<T, S> firstNameCol = new TableColumn<>(name);
        firstNameCol.setMinWidth(minWidth);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(name));
        table.getColumns().add(firstNameCol);
    }

    public ObservableList<Ranking> getRankingList() {
        final Collection<Ranking> data = new LinkedList<>();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nazwa, punkty FROM ranking ORDER BY punkty DESC;")) {

            while(resultSet.next()) {
                data.add(new Ranking(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
        return FXCollections.observableArrayList(data);
    }

    public void getRankingTable(final TableView<Ranking> rankingTable) {
        insertColumn(rankingTable, String.class, "nazwa", 149);
        insertColumn(rankingTable, Integer.class, "punkty", 149);
        rankingTable.setItems(getRankingList());
    }

    private static class DatabaseException extends RuntimeException {
        private static final long serialVersionUID = 4187053082188070490L;

        DatabaseException() {
            super();
        }

        DatabaseException(final String message) {
            super(message);
        }

        DatabaseException(final Throwable cause) {
            super(cause);
        }

        DatabaseException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

    public void addPoint(final String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(final String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getPoint(final String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getInt(1);
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
        throw new IllegalArgumentException("Query did not returned results");
    }

    public String getWord(final String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getString(1);
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
        throw new IllegalArgumentException("Query did not returned results");
    }

    public boolean exist(final String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                    return true;
                else
                    return false;
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void changeWord(final String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeTime(final String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}