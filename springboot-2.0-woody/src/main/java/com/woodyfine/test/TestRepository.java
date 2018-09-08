package com.woodyfine.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class TestRepository implements TestDao{


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
    public User findUserById(Long id) {
        List<User> userResult = jdbcTemplate.query("select id, name, age, sex, addr from users where id = ?", new Object[]{id}, new BeanPropertyRowMapper(User.class));
        if(userResult != null && userResult.size() > 0) {
            return userResult.get(0);
        } else {
            return null ;
        }

    }

    @Override
    public boolean updateUser(User user) {
        int update = jdbcTemplate.update("update users set name = ?, age = ?, sex = ?, addr = ? where id = ?", new Object[]{user.getName(),
                user.getAge(), user.getSex(), user.getAddr(), user.getId()});

        return update == 1 ? true : false;
    }



    @Override
    public List<User> findUser(User user) {

        StringBuilder sql = new StringBuilder();
        sql.append("select id, name, age, sex, addr from users where 1 = 1");
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

        List<User> userList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(User.class));

        return userList;
    }
}
