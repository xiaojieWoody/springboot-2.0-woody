package com.woodyfine.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class JDBCTemplateTestRepository implements TestDao{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertUser(User user) {
        int update = jdbcTemplate.update("insert into users(name, age, sex, addr) values (?, ?, ?, ?)", new Object[]{user.getName(),
                user.getAge(), user.getSex(), user.getAddr()});

        return update == 1 ? true : false;
    }

    @Override
    public boolean deleteUser(Long id) {
        int update = jdbcTemplate.update("delete from users where id = ?", id);
        return update == 1 ? true : false;
    }

    @Override
    public UserBean findUserById(Long id) {
        List<UserBean> userResult = jdbcTemplate.query("select id, name, age, sex, addr, create_time as createTime from users where id = ?", new Object[]{id}, new BeanPropertyRowMapper(UserBean.class));
        if(userResult != null && userResult.size() > 0) {
            return userResult.get(0);
        } else {
            return null ;
        }

    }

    @Override
    public boolean updateUser(User user) {

        StringBuilder sql = new StringBuilder();
        sql.append("update users set create_time = NOW()");
        if(user.getName() != null) {
            sql.append(", name = " + "'" +user.getName() + "'");
        }
        if(user.getSex() != null) {
            sql.append(", sex = " + user.getSex());
        }
        if(user.getAge() != null) {
            sql.append(", age = " + user.getAge());
        }
        if(user.getAddr() != null) {
            sql.append(", addr = " + "'" +user.getAddr()+ "'");
        }
        if(user.getId() != null) {
            sql.append(" where id = " +  user.getId());
        }

        int update = jdbcTemplate.update(sql.toString());

        return update == 1 ? true : false;
    }



    @Override
    public List<UserBean> findUser(User user) {

        StringBuilder sql = new StringBuilder();
        sql.append("select id, name, age, sex, addr, create_time as createTime from users where 1 = 1");
        if(user.getId() != null) {
            sql.append(" and id = " + user.getId());
        }
        if(user.getName() != null) {
            sql.append(" and name like '%" + user.getName() + "%'");
        }
        if(user.getSex() != null) {
            sql.append(" and sex = " + user.getSex());
        }
        if(user.getAge() != null) {
            sql.append(" and age = " + user.getAge());
        }
        if(user.getAddr() != null) {
            sql.append(" and addr like '%" + user.getAddr() + "%'");
        }

        List<UserBean> userList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(UserBean.class));

        return userList;
    }
}
