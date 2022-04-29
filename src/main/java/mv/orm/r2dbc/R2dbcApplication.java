package mv.orm.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class R2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(R2dbcApplication.class, args);

		String url = "jdbc:postgres://localhost:5432/student";
		String username = "postgres";
		String password = "postgres";

		try(Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from student");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
