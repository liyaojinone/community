package life.majiang.community.community.controller;


import jdk.nashorn.internal.ir.FunctionNode;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.RegisterUserMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.RegisterUser;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PulishController {
    @GetMapping("/publish")
    public String pulish(){
        return "publish";
    }
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisterUserMapper registerUserMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @PostMapping("/publish")
    public String postpulish(@RequestParam ("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("tag") String tag ,
                             HttpServletRequest request,
                             Model model)

    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
    }
        User user=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                System.out.println("token:"+token);

                user=userMapper.findByToken(token);
                if( StringUtils.isEmpty(user)){


                    request.getSession().setAttribute("user",user);
                }
                break;
            }


        }

        if (user==null){
        model.addAttribute("error","用户未登录");
        return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
