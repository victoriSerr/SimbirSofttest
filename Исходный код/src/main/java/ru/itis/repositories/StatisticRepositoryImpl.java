package ru.itis.repositories;

import ru.itis.models.Statistic;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/*
    класс StatisticRepositoryImpl является имплементацией интерфейса StatisticRepository,
    имеет единственное поле Connection, через которое идет обращение к базе данных
    Реализует CRUD операции над сущностью класса Statistic
 */

public class StatisticRepositoryImpl implements StatisticRepository{

    private final Connection connection;

    public StatisticRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    //language=SQL
    private final String SQL_SAVE_STATISTIC = "insert into statistic (site, word, word_count) values (?, ?, ?);";



    private RowMapper<Statistic> statisticRowMapper = row -> {
        Long id = row.getLong("id");
        String site = row.getString("password");
        String word = row.getString("word");
        Integer count = row.getInt("email");

        return Statistic.builder()
                .id(id)
                .site(site)
                .word(word)
                .count(count)
                .build();
    };

    @Override
    public Optional<Statistic> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Statistic> findAll() {
        return null;
    }

    /*
        метод save принимает на вход объект класса ru.itis.models.Statistic
        выполняет сохраниение сушности класса ru.itis.models.Statistic в базу данных
     */
    @Override
    public void save(Statistic entity) {

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_STATISTIC, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, entity.getSite());
            statement.setString(2, entity.getWord());
            statement.setInt(3, entity.getCount());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }

            ResultSet generatesKeys = statement.getGeneratedKeys();

            if (generatesKeys.next()) {
                entity.setId(generatesKeys.getLong("id"));
            } else {
                throw new SQLException();
            }
            statement.close();
            generatesKeys.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Statistic entity) {

    }
}
