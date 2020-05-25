package life.majiang.community.community.mapper;


import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * creat by 2020/5/8
 */



@ComponentScan
@Mapper
public interface UserMapper {
    @Insert("insert into user (id,name,account_id,token,gmt_create,gmt_modified) values(#{id},#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user,r_user where token =#{token}")
    User findByToken(String token);



}

