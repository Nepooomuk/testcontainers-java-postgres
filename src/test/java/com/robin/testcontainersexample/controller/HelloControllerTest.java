package com.robin.testcontainersexample.controller;

import com.robin.testcontainersexample.model.Hello;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class HelloControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
    }

    @Test
    public void testSpringBootContext() throws Exception {
        Hello hello = new Hello();
        hello.setName("{\"name\": \"robin\"}");

        System.out.print(hello.toString());

        this.mockMvc.perform(
                post("/hello")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(hello.toString()))
                .andExpect(status().isOk()).andExpect(content().string("Nice"));

        this.mockMvc.perform(
                get("/hello")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

}