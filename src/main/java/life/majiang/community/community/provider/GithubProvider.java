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
    //接受accessToken，并返回一个body().String()
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //  MediaType适用于描述 HTTP 请求或响应正文的内容类型。
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String a=accessTokenDTO.toString();
        System.out.println("accesstoken:"+a);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        System.out.println(body);
        //Request即我们构建的每一个HTTP请求。通过配置请求的 地址、http方法、请求头等信息
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String response_string = response.body().string();
            System.out.println("response_string:"+response_string);
            return response_string;
        } catch (IOException e) {

        }
        return null;


}

    public GithubUser githubUser(String accessToken){
        OkHttpClient client= new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?"+accessToken)
                .build();
       try {
           //通过Call 来执行同步或异步请求，调用execute方法同步执行，调用enqueue方法异步执行
           Response response = client.newCall(request).execute();
           String string = response.body().string();
           System.out.println("response.body().string():"+string);

           //JSON.parseObject,是讲Json字符串转化为相应的对象；JSON.toJSONString则是将对象转化为Json字符串，在前后台的传输过程中，Json字符串是相当常用到的
           //System.out.println("User.toString():"+User.toString());
           return JSON.parseObject(string, GithubUser.class);
       }catch (Exception e){
    }
       return null;
}
}
