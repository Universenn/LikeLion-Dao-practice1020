package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class UserDao {

    private final DataSource dataSource;

    private final JdbcContext jdbcContext;

//    private ConnectionMaker connectionMaker;
//
//    public UserDao(ConnectionMaker connectionMaker) {
//        this.connectionMaker = connectionMaker;
//    }


    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }

//    public void jdbcContextWithStatementStaegy(StatementStrategy stmt) throws SQLException{
//        Connection c = null;
//        PreparedStatement ps = null;
//        // connection, PreparedStatement할때 에러가 나도 ps.close(), c.close()를 하기 위한 처리
//        try {
//            c = connectionMaker.makeConnection();
////            ps = new DeleteAllStrategy().makePreparedStatement(c);
//            ps = stmt.makePreparedStatement(c);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally { // error 가 나도 실행되는 블럭
//            if (ps != null){
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }
//    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
//        jdbcContextWithStatementStaegy(new DeleteAllStrategy());
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from users");
            }
        });
    }


    public void add(User user) throws SQLException, ClassNotFoundException {
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());

                return pstmt;
            }
        });
//        AddStrategy st = new AddStrategy(user);
//        jdbcContextWithStatementStaegy(st);
//
//        Map<String, String> env = System.getenv();
//        // DB 실행
//        Connection c = connectionMaker.makeConnection();
//
//        // Query 문 작성
//        PreparedStatement ps = c.prepareStatement("INSERT INTO users (id,name,password) values (?,?,?);");
//        ps.setString(1,user.getId());
//        ps.setString(2,user.getName());
//        ps.setString(3,user.getPassword());
//
//        ps.executeUpdate();
//        ps.close();
//        c.close();

    }
    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if(rs.next()){user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));}

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("SELECT count(*) FROM users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally { // error 가 나도 실행되는 블럭
            if (rs!= null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}