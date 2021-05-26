package com.hello.security;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.security.model.User;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HelloSecurityApplicationTests {

	@Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Before(value = "")
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
        		.apply(springSecurity())
        		.build();
    }
    
    /**
     * 
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private String obtainAccessToken() throws Exception {
    	 
        ResultActions result 
          = mockMvc.perform(post("/v1/authenticate")
        		  .contentType(MediaType.APPLICATION_JSON)
                  .content("{ \"email\":\"mark.juber@test.com\",\n"
                  		+ "	\"password\":\"Happy@123\" }") 
                  .accept(MediaType.APPLICATION_JSON))
             	  .andExpect(status().isOk())
             	  .andExpect(content().contentType(MediaType.APPLICATION_JSON));
            
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
        
    }
    
    @Test
    public void read() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/{id}",1)
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andDo(print())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(jsonPath("$.email", is("mark.juber@test.com")));
    }
    
    
    @Test
    public void readAll() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/")
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
          .andDo(print())
          .andExpect(jsonPath("$").exists())
          .andExpect(jsonPath("$[0].id", is(1)))
          .andExpect(jsonPath("$[1].id", is(2)));
    }
    
    
    @Test
    public void create() throws Exception {
        String accessToken = obtainAccessToken();

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test.com");
        user.setPhone("9876566600");
        user.setPassword("Happy@123");
            
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
          .header("Authorization", "Bearer " + accessToken)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(asJsonString(user))
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk()); 
    }
    
    @Test
    public void createWithValidation() throws Exception {
        String accessToken = obtainAccessToken();

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test");
        user.setPhone("98765666");
        user.setPassword("Happy@123");
            
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
          .header("Authorization", "Bearer " + accessToken)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(asJsonString(user))
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest()); 
    }

    
    @Test
    public void update() throws Exception {
        String accessToken = obtainAccessToken();
        User user = new User();
        user.setFirstName("Hello");
        user.setLastName("Hello");
        user.setPhone("98765666000");
        
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/2")
          .header("Authorization", "Bearer " + accessToken)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(asJsonString(user))
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName", is("Hello")));
    }
    
    
    @Test
    public void delete() throws Exception {
        String accessToken = obtainAccessToken();
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/2")
          .header("Authorization", "Bearer " + accessToken)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk());
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}