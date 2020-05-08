package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

/**
 * create by 2020/5/6
 */

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;


    @GetMapping ("/callback")//处理GET请求
    //@RequestParam（name="code"）里面code是前端传进的code，在后端用String code来接受
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name ="state") String state,
                           HttpServletRequest request
                           ){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.githubUser(accessToken);

        if(user !=null){
            //登录成功

            User userPerson=new User();

            userPerson.setId(null);
            userPerson.setToken(UUID.randomUUID().toString());
            userPerson.setName(user.getName());
            userPerson.setAccountId(String.valueOf(user.getId()));
            userPerson.setGmtCreate(System.currentTimeMillis());
            userPerson.setGmtModified(userPerson.getGmtCreate());
            userMapper.insert(userPerson);




            //serAttribute设置session
            request.getSession().setAttribute("user",user);

            System.out.println("user存在！！！！！");
            return "redirect:/";//加上redirect后,地址会重定向到index
        }else{
            //登录失败
            System.out.println("user不存在！！！！！");
            return "redirect:/";
        }


    }

}

