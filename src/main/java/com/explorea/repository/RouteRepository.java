package com.explorea.repository;

import com.explorea.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM ROUTES WHERE ID = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM ROUTES";
    private static final String SQL_INSERT = "INSERT INTO ROUTES (PLACES, LENGTH, AVG_RATING) values(:places, :length, :avg_rating)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM ROUTES WHERE ID = :id";

    private static final BeanPropertyRowMapper<Route> ROW_MAPPER = new BeanPropertyRowMapper<>(Route.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Route findById(Integer id) {
        try {
            final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paramSource, ROW_MAPPER);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Iterable<Route> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    public int save(Route route) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("places", route.getPlacesList())
                .addValue("length", route.getLength())
                .addValue("avg_rating", route.getAverageRating());

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public void deleteById(Integer id) {
        final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }
}
