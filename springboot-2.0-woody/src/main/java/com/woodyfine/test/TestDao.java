package com.woodyfine.test;

import java.util.List;

public interface TestDao {

    boolean insertUser(User user);

    boolean deleteUser(Long id);

    User findUserById(Long id);

    boolean updateUser(User user);

    List<User> findUser(User user);
}
