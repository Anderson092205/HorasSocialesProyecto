package sv.gob.cementerios.cementeriosle.config;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SqlServerConnection {

    private static final String CONNECTION_URL =
            "jdbc:sqlserver://ANDERSON:1433;databaseName=CementerioLibertadEste;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

    @PostConstruct
    public void testConnection() {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            System.out.println("✅ Conexión exitosa con SQL Server");
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
    }
}
