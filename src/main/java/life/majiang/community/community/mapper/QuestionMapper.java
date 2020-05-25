package life.majiang.community.community.mapper;

import life.majiang.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Mapper
public interface QuestionMapper{
    @Insert("insert into question(id,title,description,creator,tag,gmt_create,gmt_modified,like_count,comment_count,view_count) " +
            "values(#{id},#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified},#{likeCount},#{commentCount},#{viewCount})")
    void create(Question question);

}


