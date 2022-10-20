package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
//    @Autowired
//    ApplicationContext context;
//
//    @Test
//    void addAndSelect() throws SQLException {
//        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
//        String id = "2";
//
//        User user = new User(id,"juwan", "110055qwe");
//        userDao.add(user);
//
//        User userSelecte = userDao.findById(id);
//        assertEquals("juwan",userSelecte.getName());
//    }


    @Test
    void addAndSelect() throws SQLException {
        UserDao userDao = new UserDao();
        String id = "23";

        User user = new User(id, "juwan","qweqwrqwr22");
        userDao.add(user);

        User selectedUser = userDao.findById(id);

        Assertions.assertEquals("juwan",selectedUser.getName());
    }
}