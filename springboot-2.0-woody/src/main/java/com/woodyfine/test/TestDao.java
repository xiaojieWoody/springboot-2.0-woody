package com.woodyfine.test;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDao {

    boolean insertUser(User user);

    boolean deleteUser(Long id);

    UserBean findUserById(Long id);

    boolean updateUser(User user);

    List<UserBean> findUser(User user);
}
