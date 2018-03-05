package com.julyerr.interviews.sql.mybatis;

import com.julyerr.interviews.sql.mybatis.dao.UserDao;
import com.julyerr.interviews.sql.mybatis.dao.UserDaoImpl;
import com.julyerr.interviews.sql.mybatis.mapper.UserMapper;
import com.julyerr.interviews.sql.mybatis.mapper.UserMapperOrders;
import com.julyerr.interviews.sql.mybatis.po.Orders;
import com.julyerr.interviews.sql.mybatis.po.OrdersCustom;
import com.julyerr.interviews.sql.mybatis.po.User;
import com.julyerr.interviews.sql.mybatis.po.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisDemo1Test {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        //创建sqlSessionFactory
        String resource = "MybatisConfiguration.xml"; //mybatis配置文件

        //得到配置文件的流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂SqlSessionFactory,要传入mybaits的配置文件的流
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
//基于dao进行开发
    @Test
//    dao接口查询
    public void testFindUserById() throws Exception {
        //创建UserDao的对象
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        System.out.println(userDao.findUserById(27));
    }



//基于mapper进行开发
    @Test
//    使用mapper.class 进行查询
    public void testFindUserList() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
//        User user = new User();
//        user.setSex("男");
//        user.setUsername("张三1");
//        userQueryVo.setUser(user);
        List<Integer> ids = new ArrayList<>();
        ids.add(27);
        ids.add(28);

//        调用userMapper的方法
        List<User> list = userMapper.findUserList(userQueryVo);
        System.out.println(list);
    }

    @Test
//    使用resultMap进行属性映射
    public void testFindUserByIdResult() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findUserByIdResultMap(27);
        System.out.println(user);
    }

    @Test
//    一对一的查询
    public void testUserMapperOrders() throws Exception {
        //获取sqlSessionFactory的代码省略了
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
//        List<OrdersCustom> list = userMapperOrders.findOrdersUser();
        List<OrdersCustom> list = userMapperOrders.findOrdersUserResultMap();
        System.out.println(list);
    }

    @Test
//    一对多的查询
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<Orders> list = userMapperOrders.findOrdersAndOrderDetailResultMap();
        System.out.println(list);
    }

    @Test
//    多对多的查询
    public void findUserAndItemsResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapperOrders userMapperOrders = sqlSession.getMapper(UserMapperOrders.class);
        List<User> list = userMapperOrders.findUserAndItemsResultMap();
        System.out.println(list);
    }
}
