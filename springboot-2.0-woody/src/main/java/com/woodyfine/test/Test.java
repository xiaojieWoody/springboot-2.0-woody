package com.woodyfine.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
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
    private TestRepository testRepository;

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
        return testRepository.insertUser(user);
    }

    @RequestMapping("/jdbcTemplate/{id}/delete")
    @ResponseBody
    public  Boolean deleteUser(@PathVariable Long id) {
        return testRepository.deleteUser(id);
    }

    @RequestMapping("/jdbcTemplate/update")
    @ResponseBody
    public  Boolean updateUser(@RequestBody User user) {
        return testRepository.updateUser(user);
    }

    @RequestMapping("/jdbcTemplate/find")
    @ResponseBody
    public  List<User> findUser(@RequestBody User user) {
        return testRepository.findUser(user);
    }

    @RequestMapping("/jdbcTemplate/{id}")
    @ResponseBody
    public  User findUserById(@PathVariable Long id) {
        return testRepository.findUserById(id);
    }

}
