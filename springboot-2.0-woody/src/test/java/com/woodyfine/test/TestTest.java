package com.woodyfine.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestTest {

    @Autowired
    private TestDao testDao;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUpMvc() {
        //使用MockMvc的时候需要先用MockMvcBuilders使用构建MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        UserBean user = new UserBean();
        user.setName("Test");
//        User user =new User("root","root");
        session.setAttribute("user", user); //拦截器那边会判断用户是否登录，所以这里注入一个用
    }

    @Test
    public void findUserById() throws Exception {

        System.out.println(session.getAttribute("user").toString());

        UserBean result = testDao.findUserById(15L);
        System.out.println(result);

        mvc.perform(MockMvcRequestBuilders.get("/test/jdbcTemplate/15")
                //代表发送端发送的数据格式是application/json;charset=UTF-8
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //代表客户端希望接受的数据类型为application/json;charset=UTF-8
                .accept(MediaType.APPLICATION_JSON_UTF8)
                //注入一个session，这样拦截器才可以通过
                .session(session)
        )
                //ResultActions.andExpect添加执行完成后的断言
                //看请求的状态响应码是否为200如果不是则抛异常，测试不通过
                .andExpect(MockMvcResultMatchers.status().isOk())
                //jsonPath用来获取author字段比对是否为嘟嘟MD独立博客,不是就测试不通过
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("李琪"))
                //添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void insertUserTest() throws Exception {
        String json = "{\n" +
                "\"name\":\"小花花\",\n" +
                "\"sex\":0,\n" +
                "\"age\":18,\n" +
                "\"addr\":\"上海市普陀区\"\n" +
                "\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.get("/test/jdbcTemplate/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //代表客户端希望接受的数据类型为application/json;charset=UTF-8
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString())
                //注入一个session，这样拦截器才可以通过
                .session(session))
                //ResultActions.andExpect添加执行完成后的断言
                //看请求的状态响应码是否为200如果不是则抛异常，测试不通过
                .andExpect(MockMvcResultMatchers.status().isOk())
                //添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }
}