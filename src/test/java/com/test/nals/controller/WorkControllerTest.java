package com.test.nals.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.nals.domain.WorkRequest;
import com.test.nals.entity.Work;
import com.test.nals.service.WorkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WorkController.class)
public class WorkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkService workService;

    @Test
    public void createNewWork() throws Exception {
        //GIVEN
        String url = "/works";
            //create a work request argument
        WorkRequest request = new WorkRequest();
        request.setId("work12");
        request.setWorkName("Lam Toan");
        request.setStartingDate(null);
        request.setEndingDate(null);
        request.setStatus("Planning");
            //Create inputJson from workRequest object
        String inputJson = new ObjectMapper().writeValueAsString(request);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        //THEN
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void updateWork() throws Exception {
        //GIVEN
        String url = "/works/work12";
        //create a work request argument
        WorkRequest request = new WorkRequest();
        request.setId("work12");
        request.setWorkName("Lam Toan Van");
        request.setStartingDate(null);
        request.setEndingDate(null);
        request.setStatus("Planning");
        //Create inputJson from workRequest object
        String inputJson = new ObjectMapper().writeValueAsString(request);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        //THEN
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void removeWork() throws Exception {
        //GIVEN
        String url = "/works/work12";
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();
        //THEN
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    public void getWorks() throws Exception {
        //GIVEN
        String url = "/works";
        List<Work> works = new ArrayList<>();
        prepareWorkList(works);
        Page<Work> workPage = new PageImpl<Work>(works);
        when(workService.getListWork(0, 5, "ASC","id")).thenReturn(workPage);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*
    * Preparing a list of work
    *
    */
    private List<Work> prepareWorkList(List<Work> works) {

        //Create a new work
        Work work;
        for (int i = 0; i < 5; i++) {
            work = new Work();
            work.setId("work" + i);
            work.setWorkName("workName" + i);
            work.setStartingDate(null);
            work.setEndingDate(null);
            work.setStatus("Plainning");
            works.add(work);
        }
        return works;
    }

}