package com.example.BioBank.Dao;
import com.example.BioBank.DTO.UserDTO;
import com.example.BioBank.Entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public  interface UserDao {
    @Insert("INSERT INTO user(password,tel,priority) VALUES (#{password},#{tel},#{priority});")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    void insertUser(User user);

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
    void updatePassword(@Param("tel") String tel, @Param("newPassword") String newPassword);

    @Update("UPDATE user SET priority = #{newPriority} WHERE userId = #{userid}")
    void updatePriority(@Param("userid") long userid, @Param("newPriority") int newPriority);

    @Delete("Delete From user WHERE user_id=#{userid}")
    void deleteByUserId(@Param("userid")long userid);
}
