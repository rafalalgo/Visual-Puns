package kalambury.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Rafał Byczek on 12.06.16.
 */
final public class Database {
    public static final Database instance = new Database();
    private static final String SERVER_ADRES = "localhost";
    private static final String PORT = "5432";
    private static final String DB_NAME = "projektPO";
    private static final String USER_NAME = "rafalbyczek";
    private static final String PASSWORD = "Rafciob.960";

    public final Connection connection;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + SERVER_ADRES + ':' + PORT + '/' + DB_NAME, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private static <T, S> void insertColumn(final TableView<T> table, final Class<S> typeTag, final String name, final int minWidth) {
        final TableColumn<T, S> firstNameCol = new TableColumn<>(name);
        firstNameCol.setMinWidth(minWidth);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(name));
        table.getColumns().add(firstNameCol);
    }

    private ObservableList<Ranking> getRankingList() {
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

    public void deletePoint(final String query2, final String query) {
        try {
            Statement stmts = connection.createStatement();
            stmts.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}