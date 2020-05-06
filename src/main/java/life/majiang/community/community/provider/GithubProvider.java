package life.majiang.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * create by 2020/5/6
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //  MediaType适用于描述 HTTP 请求或响应正文的内容类型。
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            return string;
        } catch (IOException e) {

        }
        return null;


}

    public GithubUser githubUser(String accessToken){
        OkHttpClient client= new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
       try {
           //通过Call 来执行同步或异步请求，调用execute方法同步执行，调用enqueue方法异步执行
           Response response=client.newCall(request).execute();
           String string=response.body().string();
           //JSON.parseObject,是讲Json字符串转化为相应的对象；JSON.toJSONString则是将对象转化为Json字符串，在前后台的传输过程中，Json字符串是相当常用到的
           GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
           return githubUser;
       }catch (IOException e){
    }
       return null;
}
}
