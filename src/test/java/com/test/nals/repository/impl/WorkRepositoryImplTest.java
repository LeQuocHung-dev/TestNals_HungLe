package com.test.nals.repository.impl;

import com.test.nals.config.TestNalsConfiguration;
import com.test.nals.entity.Work;
import com.test.nals.repository.WorkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        Work work = getWork();
        workRepository.save(work);
        //WHEN
        boolean isExist = workRepository.isExist(workId);
        //THEN
        assertTrue(isExist);
    }

    @Test
    @Transactional
    public void save() throws Exception {
        //GIVEN
        Work work = getWork();
        boolean isExistBeforeSave = workRepository.isExist(work.getId());
        //WHEN
        workRepository.save(work);
        boolean isExistAfterSave = workRepository.isExist(work.getId());
        //THEN
        assertFalse(isExistBeforeSave);
        assertTrue(isExistAfterSave);
    }

    @Test
    @Transactional
    public void update() throws Exception {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    public void delete() throws Exception {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    public void getListWork() throws Exception {
        //GIVEN
        //WHEN
        //THEN
    }

    private Work getWork() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Work work = new Work();
        work.setId("work12");
        work.setWorkName("Lam Toan");
        work.setStartingDate(LocalDateTime.parse("2020-02-02 18:00:00", formatter));
        work.setEndingDate(LocalDateTime.parse("2020-05-05 20:00:00", formatter));
        work.setStatus("Planning");
        return work;
    }

}