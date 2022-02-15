package com.example.BioBank.Dao;
import com.example.BioBank.DTO.UserDTO;
import com.example.BioBank.Entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public  interface UserDao {
    @Insert("INSERT INTO user(password,tel,priority) VALUES (#{password},#{tel},#{priority});")
    int insertUser(User user);

    @ResultType(UserDTO.class)
    @Select("SELECT user_id as userId,tel,priority FROM user WHERE user_id = #{userId}")
    UserDTO getUserByUserId(@Param("userId") long userId);

    @ResultType(UserDTO.class)
    @Select("SELECT user_id as userId,tel,priority FROM user WHERE tel = #{tel} and password = #{password}")
    UserDTO getUserByTelAndPassword(@Param("tel") String tel,@Param("password") String password);

    @ResultType(UserDTO.class)
    @Select("SELECT user_id as userId,tel,priority FROM user WHERE tel = #{tel}")
    UserDTO getUserByTel(@Param("tel") String tel);

    @Update("UPDATE user SET password = #{newPassword} WHERE tel = #{tel}")
    int updatePassword(@Param("tel") String tel, @Param("newPassword") String newPassword);

    @Delete("Delete From user WHERE user_id=#{userid}")
    int deleteByUserId(@Param("userid")long userid);
}
