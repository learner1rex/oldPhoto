package com.oldphoto.mapper;

import com.oldphoto.dto.LoginFormDTO;
import com.oldphoto.dto.UserDTO;
import com.oldphoto.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username,password,nickname) values (#{userName},#{password},#{nickName})")
    public int register(LoginFormDTO loginForm);


    @Select("select * from user where username = #{userName} and password = #{password}")
    public UserDTO getUserDto(LoginFormDTO loginForm);

    @Select("select * from user where username = #{userName} and password = #{password}")
    public User getUser(LoginFormDTO loginForm);
    @Select("select * from user where username = #{username}")
    public User getUserByUserName(@Param("username")String username);

    @Select("select * from user where id = #{userId};")
    public User getUserById(@Param("userId")Long userId);
    @Update("update user set nickname = #{nickName},email = #{email} where id = #{id}")
    public int updateUserInfo(User user);
    @Update("update user set avatar = #{avatar} where id = #{id}")
    public int updateAvatar(User user);

    @Update("update user set password = #{password} where id = #{id}")
    public int updatePwd(User user);
}
