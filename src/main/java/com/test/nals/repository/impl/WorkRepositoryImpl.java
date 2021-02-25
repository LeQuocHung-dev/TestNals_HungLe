package com.test.nals.repository.impl;

import com.test.nals.entity.Work;
import com.test.nals.exception.DaoException;
import com.test.nals.repository.DaoUtils;
import com.test.nals.repository.WorkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class WorkRepositoryImpl implements WorkRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public boolean isExist(String idWork) {
        log.info("Check existance of Work with Id {}", idWork);
        List<SqlParameterValue> params = new ArrayList<>();
        params.add(new SqlParameterValue(Types.VARCHAR, idWork));
        String query = daoUtils.getSQLStatement("SELECT_WORK_BY_KEY");

        List<String> result = null;
        try {
            result = jdbcTemplate.query(query, params.toArray(), this.WORK);
        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        }
        return !result.isEmpty() ? true:false;
    }

    @Override
    public void save(Work work) {
        log.info("Create new work {} ", work);
        if (!isExist(work.getId())) {
            List<SqlParameterValue> params = new ArrayList<>();
            params.add(new SqlParameterValue(Types.VARCHAR, work.getId()));
            params.add(new SqlParameterValue(Types.VARCHAR, work.getWorkName()));
            params.add(new SqlParameterValue(Types.TIMESTAMP, work.getStartingDate()));
            params.add(new SqlParameterValue(Types.TIMESTAMP, work.getEndingDate()));
            params.add(new SqlParameterValue(Types.VARCHAR, work.getStatus()));

            String query = daoUtils.getSQLStatement("SAVE_WORK");

            try {
                jdbcTemplate.update(query, params.toArray());
            } catch (DaoException ex) {
                throw new DaoException(ex.getMessage());
            }
        }
    }

    @Override
    public void update(Work work) {
        log.info("Update work {} ", work);
        if (isExist(work.getId())) {
            List<SqlParameterValue> params = new ArrayList<>();
            params.add(new SqlParameterValue(Types.VARCHAR, work.getId()));
            params.add(new SqlParameterValue(Types.VARCHAR, work.getWorkName()));
            params.add(new SqlParameterValue(Types.TIMESTAMP, work.getStartingDate()));
            params.add(new SqlParameterValue(Types.TIMESTAMP, work.getEndingDate()));
            params.add(new SqlParameterValue(Types.VARCHAR, work.getStatus()));
            params.add(new SqlParameterValue(Types.VARCHAR, work.getId()));

            String query = daoUtils.getSQLStatement("UPDATE_WORK");
            try {
                jdbcTemplate.update(query, params.toArray());
            } catch (DaoException ex) {
                throw new DaoException(ex.getMessage());
            }
            log.info("This work with id {} is updated", work.getId());
        }

    }

    @Override
    public void delete(String workId) {
        log.info("Delete a work with id {} ", workId);
        List<SqlParameterValue> params = new ArrayList<>();
        params.add(0, new SqlParameterValue(Types.VARCHAR, workId));

        String query = daoUtils.getSQLStatement("DELETE_WORK");
        try {
            jdbcTemplate.update(query, params.toArray());
        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public Page<Work> getListWork(org.springframework.data.domain.Pageable pageable) {

        log.info("Get work list in page {} ", pageable.getPageNumber());

        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("id");
        List<Work> works = null;
        try {
            works = jdbcTemplate.query("SELECT * FROM work ORDER BY " + order.getProperty() + " "
                            + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset(),
                    (rs, rowNum) -> mapWorkResult(rs));
        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        }
        return new PageImpl<Work>(works, pageable, count());
    }

    @Override
    public int count() {

        String query = daoUtils.getSQLStatement("COUNT_WORK");
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        }
        return count;
    }

    private final RowMapper<String> WORK = new RowMapper<String>() {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("ID");
        }
    };

    public Work mapWorkResult(ResultSet rs) throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Work work = new Work();
        work.setId(rs.getString("id"));
        work.setWorkName(rs.getString("work_Name"));
        work.setStartingDate(LocalDateTime.parse(rs.getString("starting_Date"), formatter));
        work.setEndingDate(LocalDateTime.parse(rs.getString("ending_Date"), formatter));
        work.setStatus(rs.getString("status"));
        return work;
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DaoUtils getDaoUtils() {
        return daoUtils;
    }

    public void setDaoUtils(DaoUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
