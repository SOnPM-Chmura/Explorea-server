package com.explorea.repository;

import com.explorea.model.RouteDTO;
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

    private static final String SQL_FIND_BY_ID = "SELECT id, coded_route, avg_rating, length_by_foot, length_by_bike, time_by_foot, time_by_bike, city FROM ROUTES WHERE ID = :id";
    private static final String SQL_FIND_BY_CITY = "SELECT * FROM ROUTES WHERE LOWER(CITY) = LOWER(:city)";
    private static final String SQL_FIND_ALL = "SELECT id, coded_route, avg_rating, length_by_foot, length_by_bike, time_by_foot, time_by_bike, city FROM ROUTES";
    private static final String SQL_INSERT = "INSERT INTO ROUTES " +
            "(coded_route, avg_rating, length_by_foot, length_by_bike, time_by_foot, time_by_bike, city, creator_id) " +
            "values(:coded_route, :avg_rating, :length_by_foot, :length_by_bike, :time_by_foot, :time_by_bike, :city, " +
            "(SELECT id FROM USERS WHERE google_user_id=:google_user_id))";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM ROUTES WHERE ID = :id " +
            "AND creator_id = (SELECT id FROM USERS WHERE google_user_id=:google_user_id)";
    private static final String SQL_FIND_CREATED_BY = "SELECT id, coded_route, avg_rating, length_by_foot, length_by_bike, time_by_foot, time_by_bike, city FROM ROUTES " +
            "WHERE creator_id = (SELECT id FROM USERS WHERE google_user_id=:google_user_id)";
    private static final String SQL_FIND_FAVORITE = "SELECT routes.id, routes.coded_route, routes.avg_rating, routes.length_by_foot, routes.length_by_bike, routes.time_by_foot, routes.time_by_bike, routes.city " +
            "FROM ROUTES RIGHT OUTER JOIN RATINGS ON (routes.id = ratings.route_id)" +
            "WHERE ratings.rating >= 5 AND ratings.user_id = (SELECT id FROM USERS WHERE google_user_id=:google_user_id)";
    private static final String SQL_UPDATE_AVG_RATING =
            "UPDATE routes SET avg_rating = (SELECT AVG(rating) FILTER (WHERE route_id = :id) FROM ratings) " +
            "WHERE ID = :id";

    private static final BeanPropertyRowMapper<Route> ROW_MAPPER = new BeanPropertyRowMapper<>(Route.class);
    private static final BeanPropertyRowMapper<RouteDTO> ROW_MAPPER_DTO = new BeanPropertyRowMapper<>(RouteDTO.class);


    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public RouteDTO findById(Integer id) {
        try {
            final SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paramSource, ROW_MAPPER_DTO);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Iterable<RouteDTO> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER_DTO);
    }

    public int save(Route route, String googleId) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("coded_route", route.getCodedRoute())
                .addValue("avg_rating", route.getAvgRating())
                .addValue("length_by_foot", route.getLengthByFoot())
                .addValue("length_by_bike", route.getLengthByBike())
                .addValue("time_by_foot", route.getTimeByFoot())
                .addValue("time_by_bike", route.getTimeByBike())
                .addValue("city", route.getCity())
                .addValue("google_user_id", googleId);

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public int deleteById(Integer id, String googleId) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("google_user_id", googleId);
        return jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }

    public Iterable<RouteDTO> findRoutesCreatedByUser(String googleId) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("google_user_id", googleId);
        return jdbcTemplate.query(SQL_FIND_CREATED_BY, paramSource, ROW_MAPPER_DTO);
    }

    public Iterable<RouteDTO> findUsersFavorite(String googleId) {
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("google_user_id", googleId);
        return jdbcTemplate.query(SQL_FIND_FAVORITE, paramSource, ROW_MAPPER_DTO);
    }

    public int updateAvgRating(Integer id){
        final SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(SQL_UPDATE_AVG_RATING, paramSource);
    }
}
