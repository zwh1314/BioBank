package com.example.BioBank.Dao;

import com.example.BioBank.DTO.UserInfoDTO;
import com.example.BioBank.Entity.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoDao {

    @ResultType(UserInfoDTO.class)
    @Select("SELECT user_id as userId, user_name as userName, birthday, priority, major, head_picture as headPicture, " +
            "address, tel, qq, mail_address as mailAddress, gender, introduction, credits FROM user_info WHERE tel = #{tel}")
    UserInfoDTO getUserInfoByTel(@Param("tel")String tel);

    @ResultType(UserInfoDTO.class)
    @Select("SELECT user_id as userId, user_name as userName, birthday, priority, major, head_picture as headPicture, " +
            "address, tel, qq, mail_address as mailAddress, gender, introduction, credits FROM user_info WHERE user_id = #{userId}")
    UserInfoDTO getUserInfoByUserId(@Param("userId") long userId);

    @Insert("INSERT INTO user_info(user_name, priority, tel) VALUES(#{userName}, #{priority}, #{tel})")
    void addUserInfo(UserInfo userInfo);


    @Update("UPDATE user_info SET user_name = #{userName}, birthday = #{birthday}, major = #{major}, " +
            "gender =#{gender}, address = #{address}, qq = #{qq}, mail_address = #{mailAddress}, introduction = #{introduction} " +
            "WHERE user_id = #{userId}")
    void updateUserInfo(UserInfo userInfo);

    @Update("UPDATE user_info SET credits = #{credits} WHERE user_id = #{userId}")
    int updateCredits(@Param("userId") long userId, @Param("credits") long credits);

    @Update("UPDATE user_info SET priority = #{priority} WHERE user_id = #{userId}")
    void updatePriority(@Param("userId") long userId, @Param("priority") String priority);

    /**todo: convert int to void, bind upload file and update database in one transaction*/
    @Update("UPDATE user_info SET head_picture = #{headPicture} WHERE user_id = #{userId}")
    int updateHeadPicture(@Param("userId") long userId, @Param("headPicture") String headPicture);

    @ResultType(UserInfoDTO.class)
    @Select("SELECT credits, user_id as userId FROM user_info WHERE user_id = #{userId}")
    UserInfoDTO getCreditsById(@Param("userId")long userId);


    @Select("SELECT user_name as userName FROM user_info WHERE user_id = #{userId}")
    String getUserNameByUserId(@Param("userId")long userId);
}
