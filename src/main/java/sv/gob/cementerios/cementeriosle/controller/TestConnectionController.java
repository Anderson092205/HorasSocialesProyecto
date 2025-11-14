package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class TestConnectionController {

    @GetMapping("/test")
    public String testSqlConnection() {
        String url = "jdbc:sqlserver://ANDERSON:1433;databaseName=CementerioLibertadEste;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

        try (Connection conn = DriverManager.getConnection(url)) {
            return "Conexión exitosa con SQL Server";
        } catch (SQLException e) {
            return " Error de conexión: " + e.getMessage();
        }
    }
}
