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
    void addAndSelect() throws SQLException, ClassNotFoundException {
        User user1 = new User("1", "juwan", "1234");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User selectedUser = userDao.findById(user1.getId());

        assertEquals(user1.getName(), selectedUser.getName());
        assertEquals(user1.getPassword(), selectedUser.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        User user1 = new User("1", "juwan", "1234");
        User user2 = new User("2", "kissup", "2345");
        User user3 = new User("3", "jeongseok", "3456");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        userDao.add(user2);
        assertEquals(2, userDao.getCount());

        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

}