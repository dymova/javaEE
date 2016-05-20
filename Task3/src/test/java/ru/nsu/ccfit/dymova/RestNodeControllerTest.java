package ru.nsu.ccfit.dymova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.entities.Tag;
import ru.nsu.ccfit.dymova.jpa.NodeRepository;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RestNodeControllerTest {
    @Autowired
    private NodeRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Node node1;
    private Node node2;



    @Before
    public void setUp() {
//        Mockito.reset(repository);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        List<Tag> tags1 = new ArrayList<>();
        List<Tag> tags2 = new ArrayList<>();
        tags1.add(new Tag("key1", "value1"));
        tags2.add(new Tag("key2", "value2"));
        node1 = new Node(tags1, new BigInteger("11"), 12d, 13d, "user1", new BigInteger("14"),
                true, new BigInteger("15"), new BigInteger("16"), new Timestamp(System.currentTimeMillis()));

        node2 = new Node(tags2, new BigInteger("21"), 22d, 23d, "user2", new BigInteger("24"),
                true, new BigInteger("25"), new BigInteger("26"), new Timestamp(System.currentTimeMillis()));

    }

    @Ignore
    @Test
    public void testGetNodes() throws Exception {
        repository.deleteAll();
        repository.save(Arrays.asList(node1, node2));

        mockMvc.perform(get("/nodes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$[0].user").value(node1.getUser()))
                .andExpect(jsonPath("$[1].user").value(node2.getUser()));

    }

    @Ignore
    @Test
    public void testCreateNode() throws Exception {
        mockMvc.perform(post("/nodes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(node1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateNode() throws Exception {
        mockMvc.perform(put("/nodes/41")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(node2)))
                .andDo(print())
                .andExpect(status().isOk());

    }



}
