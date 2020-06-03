package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan("ru.geekbrains.server")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/network_chat");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }

//    @Bean
//    public UserRepository userRepository(DataSource dataSource) throws SQLException {
//        return new UserRepository(dataSource);
//    }
//
//    @Bean
//    public AuthService authService(UserRepository userRepository) throws SQLException {
//        return new AuthServiceJdbcImpl(userRepository);
//    }
//
//    @Bean
//    public ChatServer chatServer(AuthService authService) throws SQLException {
//        return new ChatServer(authService);
//    }
}
