package ru.itis.services;

import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    класс, который выполняет подключение к локальной базе данных и инициаизирует репозитории, создавая
    объект класса java.sql.Connection
    Поля:
        username - имя пользователя базы данных
        password - пароль для подключения к базе данных
        url - url базы данных
        driver - драйвер для базы данных
 */

@Getter
public class DataBaseConfiguration {


    private String username;
    private String password;
    private String url;
    private String driver;


    public DataBaseConfiguration(String username, String password, String url, String driver) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driver = driver;
    }

    /*
        метод getConnection() выполняет подключение к базе данных
        возвращает объект класса java.sql.Connection
     */
    public Connection getConnection() {
        Connection connection;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }
}
