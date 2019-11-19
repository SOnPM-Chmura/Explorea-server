package com.explorea.repository;

import com.explorea.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM USERS WHERE ID = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM USERS";
    private static final String SQL_INSERT = "INSERT INTO USERS (google_user_id, favorite_routes, created_routes) " +
            "SELECT :google_user_id, :favorite_routes, :created_routes " +
            "WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE google_user_id=:google_user_id)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM USERS WHERE ID = :id";

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public User findById(Integer id) {
        try {
            final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paramSource, ROW_MAPPER);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Iterable<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    public int save(User user) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("google_user_id", user.getGoogleUserId())
                .addValue("favorite_routes", user.getFavoriteRoutes())
                .addValue("created_routes", user.getCreatedRoutes());

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public void deleteById(Integer id) {
        final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }
}
