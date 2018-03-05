package com.julyerr.interviews.sql.mybatis;

import com.julyerr.interviews.sql.mybatis.mapper.UserMapper;
import com.julyerr.interviews.sql.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class MybatisCacheTest {
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

    @Test
//    一级缓存测试
//    整个测试结果可以参看查询的日志信息，需要先配置好日志格式
    public void testCache1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();//创建代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //第一次发起请求，查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        //第二次发起请求，查询id为1的用户，进行了缓存，不会进行数据库查询操作
        user1 = userMapper.findUserById(1);
        System.out.println(user1);

//      如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        //更新user1的信息
        user1.setUsername("测试用户22");
        userMapper.updateUser(user1);
        //执行commit操作去清空缓存
        sqlSession.commit();

        //第三次发起请求，查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);

        sqlSession.close();

    }

    @Test
//    二级缓存测试
    public void testCache2() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        // 创建代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);
        //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
        sqlSession1.close();

        //sqlSession3用来清空缓存的，如果要测试二级缓存，需要把该部分注释掉
        //使用sqlSession3执行commit()操作
        UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
        User user = userMapper3.findUserById(1);
        user.setUsername("julyerr");
        userMapper3.updateUser(user);
        //执行提交，清空UserMapper下边的二级缓存
        sqlSession3.commit();
        sqlSession3.close();

        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);

        sqlSession2.close();

    }
}
