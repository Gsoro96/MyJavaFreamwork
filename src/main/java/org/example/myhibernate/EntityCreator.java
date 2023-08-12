package org.example.myhibernate;

import org.example.annotations.MyId;
import org.example.exceptions.EntityMisuseException;
import org.example.myhibernate.container.EntitiesContainer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.MyBeanUtils.fieldHasAnnotationOfType;

public class EntityCreator {

    private final EntitiesContainer entitiesContainer;

    public EntityCreator(EntitiesContainer entitiesContainer) {
        this.entitiesContainer = entitiesContainer;
    }

    public void createEntities() {



        entitiesContainer.getEntities().forEach(entity -> {
            String sql = "CREATE TABLE IF NOT EXISTS " + entity.getSimpleName();
            String open_parenthesis = " (";
            String close_parenthesis = " );";

            sql = sql + open_parenthesis;

            Field[] declaredFields = entity.getDeclaredFields();
            List<Field> idFields = Arrays.stream(declaredFields)
                    .filter(field -> fieldHasAnnotationOfType(field, MyId.class))
                    .collect(Collectors.toList());
            validateFields(idFields);

            String name = idFields.get(0).getName();
            name = name.toLowerCase();
            String columnSql = name + " VARCHAR(255) PRIMARY KEY";
            sql = sql + columnSql;

            sql = sql + close_parenthesis;
            try {
                createDB(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void validateFields(List<Field> fields) {
        if (fields.isEmpty()) {
            throw new EntityMisuseException("@MyId is missing");
        }
        if (fields.size() != 1) {
            throw new EntityMisuseException("@MyId should not be used multiple times");
        }

    }

    private void createDB(String sql) throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_java_framework_db",
                    "my_java_framework", "demo");

            stmt = c.createStatement();
            System.out.println("SQL to be executed " + sql);
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            if(stmt != null){
                stmt.close();
                c.close();
            }
        }
    }
}
