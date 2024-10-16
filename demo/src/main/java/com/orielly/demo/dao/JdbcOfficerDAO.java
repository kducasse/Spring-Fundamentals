package com.orielly.demo.dao;

import com.orielly.demo.entities.Officer;
import com.orielly.demo.entities.Rank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@Transactional
public class JdbcOfficerDAO implements OfficerDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOfficer;

    private final RowMapper<Officer> officerMapper =
            (resultSet, rowNum) -> new Officer(resultSet.getInt("id"),
                    Rank.valueOf(resultSet.getString("rank")),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")); // This is a lambda

    public JdbcOfficerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertOfficer = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Officer save(Officer officer) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(officer);
        Number newId = insertOfficer.executeAndReturnKey(params);
        return new Officer(newId.intValue(), officer.getRank(), officer.getFirstName(), officer.getLastName());
    }

    @Override
    public Optional<Officer> findById(Integer id) {
        try (Stream<Officer> stream =
                jdbcTemplate.queryForStream("select * from officers where id=?",
                        officerMapper,
                        id)) {
            return stream.findFirst();
        }
    }

    @Override
    public List<Officer> findAll() {
        return jdbcTemplate.query("select * from officers", officerMapper);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from officers", Long.class);
    }

    @Override
    public void delete(Officer officer) {
        jdbcTemplate.update("DELETE FROM officers WHERE id=?", officer.getId());
    }

    @Override
    public boolean existsById(int id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM officers WHERE id=?)",
                Boolean.class, id);
    }
}
