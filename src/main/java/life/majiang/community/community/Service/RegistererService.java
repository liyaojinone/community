package life.majiang.community.community.Service;

import life.majiang.community.community.mapper.RegisterUserMapper;
import life.majiang.community.community.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistererService {
    @Autowired
    RegisterUserMapper userMapper;

    public RegisterUser finduser (String name){
        return userMapper.findusername(name);
    }
    public void registeruser (RegisterUser user){
        userMapper.create(user);
    }
}
