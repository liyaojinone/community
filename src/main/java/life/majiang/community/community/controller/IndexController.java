package life.majiang.community.community.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * create on 2020/5/5
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
