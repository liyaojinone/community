package life.majiang.community.community.mapper;

import life.majiang.community.community.model.RegisterUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Mapper
public interface RegisterUserMapper {
    @Insert("insert into r_user(id,token,name,password,email,gmt_modified,gmt_create) values(#{id},#{token},#{username},#{password},#{email},#{gmtModified},#{gmtCreate})")
    void create(RegisterUser registerUser);

    @Select("SELECT * FROM r_user where name = #{username}")
    RegisterUser findusername(String username);
//    @Select("SELECT * FROM r_user where (name,password) = var#{username}")
}
