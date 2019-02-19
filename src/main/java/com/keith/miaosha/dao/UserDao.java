package com.keith.miaosha.dao;

import com.keith.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author YMX
 * @date 2018/12/29 11:47
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getUserById(@Param("id") int id);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    public int insert(User user);
}
