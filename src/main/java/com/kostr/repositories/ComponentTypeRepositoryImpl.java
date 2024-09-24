package main.java.com.kostr.repositories;

import main.java.com.kostr.models.Client;
import main.java.com.kostr.models.ComponentType;
import main.java.com.kostr.repositories.interfaces.ComponentTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ComponentTypeRepositoryImpl implements ComponentTypeRepository {

    private final Connection connection;

    public ComponentTypeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private ComponentType getComponentTypeModel(ComponentType componentType, PreparedStatement ps) throws SQLException {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                UUID componentId = (UUID) generatedKeys.getObject(1);

                return new ComponentType(componentId, componentType.getName(), componentType.getType());
            } else {
                return null;
            }
        }
    }

    @Override
    public ComponentType addComponentType(ComponentType componentType) throws SQLException {
        String query = "INSERT INTO ComponentTypes (name, type) VALUES (?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, componentType.getName());
            preparedStatement.setObject(2, componentType.getType(), java.sql.Types.OTHER);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert component type, no rows affected.");
            }

            return getComponentTypeModel(componentType, preparedStatement);
        }
    }

    @Override
    public void removeComponentType(String id) throws SQLException {
        String query = "DELETE FROM ComponentTypes WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public ComponentType updateComponentType(ComponentType componentType) throws SQLException {
        String query = "UPDATE ComponentTypes SET name = ?, type = ? WHERE id = ?::uuid";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, componentType.getName());
            ps.setObject(2, componentType.getType(), java.sql.Types.OTHER);
            ps.setString(3, componentType.getId().toString());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update component type, no rows affected.");
            }

            return getComponentTypeModel(componentType, ps);
        }
    }

    @Override
    public ArrayList<ComponentType> getComponentTypes() throws SQLException {
        String query = "SELECT DISTINCT * FROM ComponentTypes";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            ArrayList<ComponentType> componentTypes = new ArrayList<>();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                main.java.com.kostr.models.enums.ComponentType type = main.java.com.kostr.models.enums.ComponentType.valueOf(rs.getString("type"));
                componentTypes.add(new ComponentType(id, name, type));
            }
            return componentTypes;
        }
    }

    @Override
    public ComponentType getComponentTypeByName(String name) throws SQLException {
        String query = "SELECT * FROM ComponentTypes WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String componentTypeString = rs.getString("type");

                    main.java.com.kostr.models.enums.ComponentType componentTypeEnum = main.java.com.kostr.models.enums.ComponentType.valueOf(componentTypeString);

                    return new ComponentType(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("name"),
                            componentTypeEnum
                    );                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public ComponentType getComponentTypeById(String id) throws SQLException {
        String query = "SELECT * FROM ComponentTypes WHERE id = ?::uuid";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String componentTypeString = rs.getString("type");

                    main.java.com.kostr.models.enums.ComponentType componentTypeEnum = main.java.com.kostr.models.enums.ComponentType.valueOf(componentTypeString);

                    return new ComponentType(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("id"),
                            componentTypeEnum
                    );                } else {
                    return null;
                }
            }
        }
    }
}
