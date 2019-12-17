package com.explorea.repository;

import com.explorea.model.Rating;
import com.explorea.model.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RatingRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM RATINGS WHERE ID = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM RATINGS";
    private static final String SQL_INSERT = "INSERT INTO RATINGS (USER_ID, ROUTE_ID, RATING) " +
            "values((SELECT id FROM USERS WHERE google_user_id=:user_id), :route_id, :rating) " +
            "ON CONFLICT (user_id, route_id) DO " +
            "UPDATE SET rating = :rating";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM RATINGS WHERE ID = :id";
    private static final String SQL_FIND_BY_ROUTE_AND_USER = "SELECT route_id, rating FROM RATINGS " +
            "WHERE user_id = (SELECT id FROM USERS WHERE google_user_id=:google_user_id) " +
            "AND route_id = :route_id";
    private static final String SQL_UPDATE_AVG_RATING =
            "UPDATE routes SET avg_rating = (SELECT AVG(rating) FILTER (WHERE route_id = :id) FROM ratings) " +
                    "WHERE ID = :id";

    private static final BeanPropertyRowMapper<Rating> ROW_MAPPER = new BeanPropertyRowMapper<>(Rating.class);
    private static final BeanPropertyRowMapper<RatingDTO> ROW_MAPPER_DTO = new BeanPropertyRowMapper<>(RatingDTO.class);


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

    public int save(RatingDTO rating, String googleId) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("user_id", googleId)
                .addValue("route_id", rating.getRouteId())
                .addValue("rating", rating.getRating());

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public void deleteById(Integer id) {
        final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }

    public RatingDTO findByUserAndRoute(Integer routeId, String googleId) {
        try {
            final SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("route_id", routeId)
                    .addValue("google_user_id", googleId);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ROUTE_AND_USER, paramSource, ROW_MAPPER_DTO);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Transactional
    public int saveAndUpdateAvgRating(RatingDTO rating, String googleId){

        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("user_id", googleId)
                .addValue("route_id", rating.getRouteId())
                .addValue("rating", rating.getRating());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        if(jdbcTemplate.update(SQL_INSERT, paramSource, keyHolder)<=0){
            return -1;
        }

        final SqlParameterSource paramSource2 = new MapSqlParameterSource()
                .addValue("id", rating.getRouteId());
        jdbcTemplate.update(SQL_UPDATE_AVG_RATING, paramSource2);

        return (int) keyHolder.getKeys().get("id");
    }
}
