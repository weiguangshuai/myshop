package com.cqupt.project.shop.dao;

import com.cqupt.project.shop.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,
                    @Param("question") String question,
                    @Param("answer") String answer);

    int updateUsername(@Param("username") String username,
                       @Param("newPassword") String newPassword);

    int checkEmailByUserId(@Param("email") String email,
                           @Param("userId") Long userId);
}