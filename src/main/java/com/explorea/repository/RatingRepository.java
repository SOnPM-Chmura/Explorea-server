package com.explorea.repository;

import com.explorea.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM RATINGS WHERE ID = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM RATINGS";
    private static final String SQL_INSERT = "INSERT INTO RATINGS (USER_ID, ROUTE_ID, RATING) values(:user_id, :route_id, :rating)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM RATINGS WHERE ID = :id";

    private static final BeanPropertyRowMapper<Rating> ROW_MAPPER = new BeanPropertyRowMapper<>(Rating.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Rating findById(Integer id) {
        try {
            final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paramSource, ROW_MAPPER);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Iterable<Rating> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    public int save(Rating rating) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("user_id", rating.getUserId())
                .addValue("route_id", rating.getRouteId())
                .addValue("rating", rating.getRating());

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public void deleteById(Integer id) {
        final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }

}
