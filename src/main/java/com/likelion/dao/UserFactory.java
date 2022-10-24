package com.likelion.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;


// spring-boot-start-jdbc 추가 후 사용 가능
@Configuration
public class UserFactory {

    // 조립을 해준다.?
    @Bean
    public UserDao awsUserDao(){
        return new UserDao(awsDataSource());
    }
    @Bean
    public UserDao localUserDao(){
        return new UserDao(localDataSource());
    }

    private DataSource awsDataSource() {
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_NAME"));
        dataSource.setPassword(env.get("DB_PASSWORD"));
        return dataSource;
    }

    private DataSource localDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("localhost");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        return dataSource;
    }

//    @Bean
//    public ConnectionMaker awsConnectionMaker() {
//        return new AwsConnectionMaker();
//    }
//
//    @Bean
//    public ConnectionMaker LocalConnectionMaker() {
//        return new LocalConnectionMaker();
//    }



}