package com.woodyfine.test;

import com.woodyfine.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class Test {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${test.com.woody}")
    private String testMsg;

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private JDBCTemplateTestRepository JDBCTemplateTestRepository;

    @Autowired
    private TestDao testDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/thymeleaf")
    public ModelAndView getTestMsg() {

        //日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。
        logger.trace("日志输出 trace");
        logger.debug("日志输出 debug");
        logger.info("日志输出 info");
        logger.warn("日志输出 warn");
        logger.error("日志输出 error");

        List<String> learnList = new LinkedList<>();
        learnList.add("12");
        learnList.add("34");
        learnList.add("56");
        learnList.add(testMsg);
        learnList.add(configBean.toString());
        ModelAndView md = new ModelAndView();
        md.addObject("learnList", learnList);
        md.setViewName("index");
        return md;
    }

    @RequestMapping("/jsp")
    public ModelAndView getJspTestMsg() {

        List<String> learnList = new LinkedList<>();
        learnList.add("12");
        learnList.add("34");
        learnList.add("56");
        learnList.add(testMsg);
        learnList.add(configBean.toString());
        ModelAndView md = new ModelAndView();
        md.addObject("learnList", learnList);
        md.setViewName("test");
        return md;
    }

    @RequestMapping("/jdbcTemplate/user")
    @ResponseBody
    public  Boolean saveUser(@RequestBody User user) {
//        return JDBCTemplateTestRepository.insertUser(user);
        return testDao.insertUser(user);
    }

    @RequestMapping("/jdbcTemplate/{id}/delete")
    @ResponseBody
    public  Boolean deleteUser(@PathVariable Long id) {
//        return JDBCTemplateTestRepository.deleteUser(id);
        return testDao.deleteUser(id);
    }

    @RequestMapping("/jdbcTemplate/update")
    @ResponseBody
    public  Boolean updateUser(@RequestBody User user) {
//        return JDBCTemplateTestRepository.updateUser(user);
        return testDao.updateUser(user);
    }

    @RequestMapping("/jdbcTemplate/find")
    @ResponseBody
    public  List<UserBean> findUser(@Nullable @RequestBody User user) {
//        return JDBCTemplateTestRepository.findUser(user);
        return testDao.findUser(user);
    }

    @RequestMapping("/jdbcTemplate/{id}")
    @ResponseBody
    public  UserBean findUserById(@PathVariable Long id) {
//        return JDBCTemplateTestRepository.findUserById(id);
        return testDao.findUserById(id);
    }

    @RequestMapping("/jdbcTemplate/page")
    @ResponseBody
    public Page<UserBean> pageUser(@Nullable @RequestBody UserBean user) {
        user.setStartIndex((user.getPageNum() - 1) * user.getPageSize());
        List<UserBean> userList = testDao.pageUser(user);
        int totalSize = testDao.totalUser(user);

        Page<UserBean> result = new Page<>(user.getStartIndex() * user.getPageSize(), totalSize ,user.getPageSize(),userList);
        return result;
    }


    @RequestMapping("/exception")
    public void json(ModelMap modelMap) {
        System.out.println(modelMap.get("author"));
        int i=5/0;
    }


    @RequestMapping("/redis")
    @ResponseBody
    public String setRedisValue() {
        redisTemplate.opsForValue().set("test","test0011");
        return redisTemplate.opsForValue().get("test");
    }
}
