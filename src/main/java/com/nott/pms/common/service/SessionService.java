package com.nott.pms.common.service;


import com.nott.pms.exception.BusinessException;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.Objects;

import static com.nott.pms.consts.Consts.CURRENT_USER;

/**
 * @Author zzzwlong
 * @Date 2022/2/26 21:59
 */
@Service
public class SessionService {

    @Autowired
    HttpSession session;
    @Autowired
    private IPermissionService permissionService;

    public User getUser()  {
        User attribute = (User) session.getAttribute(CURRENT_USER);
        if (!Objects.nonNull(attribute)) {
            throw new BusinessException("找不到用户信息");
        }
        return attribute;
    }

//    public void auth(String permission) throws UserNotFoundExeption {
//        User attribute = (User) session.getAttribute(CURRENT_USER);
//        if (!Objects.nonNull(attribute)) {
//            throw new UserNotFoundExeption("找不到用户信息");
//        }
//        if(!permissionService.findUserPerValue(attribute.getRoleId(), permission)){
//            throw new UserNotFoundExeption("无查看权限");
//        }
//    }
}
