package com.test.nals;

import com.test.nals.controller.WorkController;
import com.test.nals.service.WorkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({WorkService.class, WorkController.class})
public class DemoApplicationTests {

    @Test
    public void test() {
        System.out.println("Success");
    }
}
