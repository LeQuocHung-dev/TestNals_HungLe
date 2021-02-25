package com.test.nals.repository.impl;

import com.test.nals.config.TestNalsConfiguration;
import com.test.nals.domain.WorkRequest;
import com.test.nals.entity.Work;
import com.test.nals.repository.WorkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestNalsConfiguration.class})
public class WorkRepositoryImplTest {

    @Autowired
    WorkRepository workRepository;

    @Test
    @Transactional
    public void isExist() throws Exception {
        //GIVEN
        String workId = "work12";
        WorkRequest work = getWork();
        workRepository.createNewWork(work);
        //WHEN
        boolean isExist = workRepository.isExistWork(workId);
        //THEN
        assertTrue(isExist);
    }

    @Test
    @Transactional
    public void save() throws Exception {
        //GIVEN
        WorkRequest work = getWork();
        boolean isExist = workRepository.isExistWork(work.getId());
        //WHEN
        workRepository.createNewWork(work);
        boolean isExistAfterCreate = workRepository.isExistWork(work.getId());
        //THEN
        assertFalse(isExist);
        assertTrue(isExistAfterCreate);
    }

    @Test
    @Transactional
    public void update() throws Exception {
        //GIVEN
        WorkRequest work = getWork();
        workRepository.createNewWork(work);
        work.setWorkName("TEST");
        //WHEN
        int row = workRepository.updateWork(work);
        //THEN
        assertEquals(1, row);
    }

    @Test
    public void delete() throws Exception {
        //GIVEN
        WorkRequest work = getWork();
        workRepository.createNewWork(work);
        //WHEN
        int row = workRepository.deleteWork(work.getId());
        //THEN
        boolean isExist = workRepository.isExistWork(work.getId());
        assertEquals(1, row);
        assertTrue(!isExist);
    }

    @Test
    public void getListWork() throws Exception {
        //GIVEN
        Pageable pageable = PageRequest.of(1,5, Sort.by("id").ascending());
        //WHEN
        Page<Work> works = workRepository.getWorks(pageable);
        //THEN
        assertTrue(works.getTotalPages() > 0);
    }

    private WorkRequest getWork() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        WorkRequest work = new WorkRequest();
        work.setId("work12");
        work.setWorkName("Lam Toan");
        work.setStartingDate(LocalDateTime.parse("2020-02-02 18:00:00", formatter));
        work.setEndingDate(LocalDateTime.parse("2020-05-05 20:00:00", formatter));
        work.setStatus("Planning");
        return work;
    }

}