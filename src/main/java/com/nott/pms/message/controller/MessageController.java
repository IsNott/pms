package com.nott.pms.message.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.message.entity.Message;
import com.nott.pms.message.service.IMessageService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pms/msg")
public class MessageController {



    @Autowired
    private IMessageService messageService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IEmpService empService;

    @RequestMapping("/unreadMsg")
    @ApiOperation("分页获取用户未读消息")
    public Result unreadMsg(Integer type) {
        User user = sessionService.getUser();
        return Result.success(messageService.getUserUnreadMsg(user.getEmpNo(),type));
    }

    @GetMapping("/num")
    @ApiOperation("用户通讯信息数量")
    public Result num(){
        User user = sessionService.getUser();
        return Result.success(messageService.getNum(user.getEmpNo()));
    }

    @RequestMapping("/repMsg")
    @ApiOperation("用户回复未读消息")
    public Result resopenMsg(Message msg) {
        User user = sessionService.getUser();
        messageService.repMsg(user.getEmpNo(), msg);
        return Result.ok();
    }

    @RequestMapping("/sendMsg")
    @ApiOperation("用户给用户发送消息")
    public Result sendMsg(Message msg) {
        messageService.sendMsg(msg);
        return Result.ok();
    }

    @RequestMapping("/queryUserMsgPage")
    @ApiOperation("用户消息中心")
    public Result queryUserMsgPage(Long page,Long size,Integer type) {
        Long empNo = sessionService.getUser().getEmpNo();
        return Result.success(messageService.msgPage(empNo,page,size,type));
    }

    @RequestMapping("/removeMsg")
    @ApiOperation("用户删除信息")
    public Result remove(String[] ids) {
        messageService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @PostMapping("/readMsg")
    @ApiOperation("用户已读信息")
    public Result read(String[] ids) {
        messageService.setRead(Arrays.asList(ids));
        return Result.ok();
    }

    @PostMapping("/sendAnnou")
    @ApiOperation("管理员发布公告")
    public Result sendAnnou(String text) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.MSG_PERMISSION);
        messageService.sentAnnou(text);
        return Result.ok();
    }

    @RequestMapping("/annouList")
    @ApiOperation("查看所有公告")
    public Result annouList() {
        sessionService.getUser();
        return Result.success(messageService.queryAnnouPage());
    }

    @RequestMapping("/querySender")
    @ApiOperation("查询发送人")
    public Result querySender(Long id) {
        sessionService.getUser();
        if(Objects.isNull(id)){
            return Result.success("system");
        }
        return Result.success(empService.getById(id).getName());
    }

    @RequestMapping("/queryMsgByAdmin")
    @ApiOperation("管理员查询系统所有信息")
    public Result queryMsgByAdmin(Long page,Long rows,Integer type,String keyword) throws UserNotFoundExeption {
//        sessionService.auth(SysConstant.MSG_PERMISSION);
        return Result.success(messageService.adminQueryMsg(page,rows,type,keyword));
    }

    @RequestMapping("/updateAnnou")
    @ApiOperation("编辑公告")
    public Result updateAnnou(Long id,String text){
        Assert.notNull(text,"文本不能为空");
        Message message = messageService.getById(id);
        message.setText(text);
        messageService.updateById(message);
        return Result.ok();
    }



}
