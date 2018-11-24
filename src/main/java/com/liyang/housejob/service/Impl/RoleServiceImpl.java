package com.liyang.housejob.service.Impl;

import com.liyang.housejob.mapper.RoleMapper;
import com.liyang.housejob.pojo.Role;
import com.liyang.housejob.pojo.RoleExample;
import com.liyang.housejob.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;


    @Override
    public Role getRoleById(int id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }
}
