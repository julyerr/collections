package com.julyerr.interviews.sql.mybatis.mapper;

import com.julyerr.interviews.sql.mybatis.po.User;
import com.julyerr.interviews.sql.mybatis.po.UserQueryVo;

import java.util.List;

public interface UserMapper {
    //用户信息综合查询
    public List<User> findUserList(UserQueryVo userQueryVo) throws Exception;

    //根据id查询用户信息，使用resultMap输出
    public User findUserByIdResultMap(int id) throws Exception;
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;
    //根据用户名模糊查询
    public List<User> findUserByName(String name) throws Exception;
    //添加用户信息
    public void insertUser(User user) throws Exception;
    //删除用户信息
    public void deleteUser(int id) throws Exception;
    //更新用户信息
    public void updateUser(User user) throws Exception;
}