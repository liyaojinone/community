package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
public class login {
    @GetMapping ("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public void loginuser(@PathParam("username") String username,
                          @PathParam("password") String password,
                          HttpServletResponse response){



  }
}
