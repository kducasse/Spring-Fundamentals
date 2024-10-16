package com.orielly.demo.dao;

import com.orielly.demo.entities.Officer;
import com.orielly.demo.entities.Rank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcOfficerDAOTest {
    @Autowired
    private JdbcOfficerDAO dao;

    @Autowired
    private JdbcTemplate template;

    private List<Integer> ids() {
        return template.query("select id from officers",
                (rs, rowNum) -> rs.getInt("id"));
    }

    @Test
    void save() {
        Officer officer = new Officer(null, Rank.ENSIGN,
                "Wesley", "Crusher");
        officer = dao.save(officer);
        assertNotNull(officer.getId());
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        List<String> dbNames = dao.findAll().stream()
                .map(Officer::getLastName)
                .toList();
        assertThat(dbNames).contains("Wesley", "Crusher");
    }

    @Test
    void count() {
        assertEquals(ids().size(), dao.count());
    }

    @Test
    void delete() {
        ids().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            dao.delete(officer.get());
        });
        assertEquals(0, dao.count());
    }

    @Test
    void existsById() {
        ids().forEach(id -> assertTrue(dao.existsById(id)));
    }

    @Test
    void findByIdThatExists() {
        ids().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            assertEquals(id, officer.get().getId());
        });
    }

    @Test
    public void findByIdThatDoesNotExist() {
        assertThat(ids()).doesNotContain(999);
        Optional<Officer> officer = dao.findById(999);
        assertFalse(officer.isPresent());
    }
}