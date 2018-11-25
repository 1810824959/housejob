package com.liyang.housejob.service.Impl;

import com.liyang.housejob.mapper.RoleMapper;
import com.liyang.housejob.mapper.UserMapper;
import com.liyang.housejob.pojo.Role;
import com.liyang.housejob.pojo.User;
import com.liyang.housejob.pojo.UserExample;
import com.liyang.housejob.service.RoleService;
import com.liyang.housejob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;

    @Override
    public User findByName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);

        if (users.size()==0){
            System.out.println("空空");
            return null;
        }
        System.out.println("还是返回了点东西的");
        User user = users.get(0);
        Role role = roleService.getRoleById(user.getId());
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_"+ role.getName()));

        user.setAuthorityList(list);
        return user;
    }
}
