package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        String id = "10";

        User user = new User(id,"juwan", "110055qwe");
        userDao.add(user);
        assertEquals(1, userDao.getCount());


        User userSelecte = userDao.findById(id);
        assertEquals("110055qwe",userSelecte.getPassword());
    }


//    @Test
//    void addAndSelect() throws SQLException {
//        UserDao userDao = new UserDao();
//        String id = "23";
//
//        User user = new User(id, "juwan","qweqwrqwr22");
//        userDao.add(user);
//
//        User selectedUser = userDao.findById(id);
//
//        Assertions.assertEquals("juwan",selectedUser.getName());
//    }
}