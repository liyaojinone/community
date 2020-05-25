package life.majiang.community.community.controller;

import life.majiang.community.community.Service.RegistererService;
import life.majiang.community.community.mapper.RegisterUserMapper;
import life.majiang.community.community.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Validated
@Controller
public class RegistererController {
   @Autowired
   RegistererService registererService;
   @Autowired
   RegisterUserMapper registerUserMapper;

   @GetMapping("/register")
   public String registerpage() {
      return "register";
   }

   @PostMapping("/register")
   public String registerUser(@NotBlank(message = "用户名不能为空") @RequestParam("username")  String username,
                              @NotBlank(message = "密码不能为空") @RequestParam("password") String password,
                              @NotBlank(message = "邮箱不能为空") @RequestParam("email") String email,
                              HttpServletResponse response,
                              Model model) {
//      if(username==null|| Objects.equals(username, "")){
//         model.addAttribute("error","用户名不能为空");
//      }
//      if (password==null|| Objects.equals(password, "")){
//         model.addAttribute("error","密码不能为空");
//      }
//      if (email==null|| Objects.equals(email, "")){
//         model.addAttribute("error","邮箱不能为空");
//      }

      RegisterUser registerUser1 = registererService.finduser(username);
      if (registerUser1 != null) {
         model.addAttribute("error", "用户名已存在");
         System.out.println("用户名已存在");
         return "register";
      }else {

         RegisterUser registerUser = new RegisterUser();
         registerUser.setId(null);
         registerUser.setUsername(username);
         registerUser.setPassword(password);
         registerUser.setEmail(email);
         String token = UUID.randomUUID().toString();
         registerUser.setToken(token);
         registerUser.setGmtCreate(System.currentTimeMillis());
         registerUser.setGmtModified(System.currentTimeMillis());
         registerUserMapper.create(registerUser);
         response.addCookie(new Cookie("token",token));
         return "redirect:/";
      }
   }
}