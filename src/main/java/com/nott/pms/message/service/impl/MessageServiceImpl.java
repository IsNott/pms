package com.nott.pms.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.message.entity.Message;
import com.nott.pms.message.mapper.MessageMapper;
import com.nott.pms.message.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.message.vo.MessageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-01
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private IEmpService empService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private IMessageService messageService;

    @Override
    public Page<Message> getUserUnreadMsg(Long empNo, Integer type) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Message::getReceiverid, empNo).eq(Message::getStatu, SysConstant.read.UNREAD)
                .eq(Objects.nonNull(type), Message::getType, type);
        Page<Message> page = new Page<>();
        List<Message> messages = this.list(wrapper);
        for (Message message : messages) {
            if (StringUtils.isEmpty(message.getSenderid())) {
                message.setSender("系统发送");
            } else {
                if(null == empService.getById(message.getSenderid())){
                    message.setSender("已离职");
                }else {
                    message.setSender(empService.getById(message.getSenderid()).getName());
                }

            }
            if(null == empService.getById(message.getSenderid())){
                message.setReceiver("已离职");
            }else {
                message.setReceiver(empService.getById(empNo).getName());
            }

        }
        page.setSize(10L);
        page.setRecords(messages);
        return page;
    }

    @Override
    public void repMsg(Long empNo, Message message) {
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.lambda().eq(Message::getReceiverid, empNo);
        List<Message> messages = messageService.list(messageQueryWrapper);
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        if (Objects.isNull(message)) {
            throw new BusinessException("内容不能为空");
        }
        Long receiverid = Long.parseLong(message.getReceiverid());
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Emp::getId, receiverid).eq(Emp::getDeflag, SysConstant.delflag.NORMAL);
        if (null == empService.list(wrapper).get(0)) {
            return;
        }
        message.setSenderid(empNo);
        this.sendMsg(message);
    }

    @Override
    public void sendMsg(Message msg) {
        if (StringUtils.isEmpty(msg.getReceiverid()) || StringUtils.isEmpty(msg.getText())) {
            return;
        }
        if (StringUtils.isEmpty(msg.getSenderid()) || SysConstant.msgType.ANNOUNCE.equals(msg.getType())) {
            msg.setSender("系统发送");
        } else {
            msg.setSender(empService.getById(msg.getSenderid()).getName());
        }
        String receiverid = msg.getReceiverid();
        String[] reId = receiverid.split(",");
        List<String> list = Arrays.asList(reId);
        for (String id : list) {
            msg.setReceiver(empService.getById(Long.parseLong(id)).getName());
            msg.setSendtime(LocalDateTime.now());
            msg.setStatu(Integer.parseInt(SysConstant.read.UNREAD));
            msg.setReceiverid(id);
            save(msg);
        }

    }

    @Override
    public Page<Message> msgPage(Long empNo, Long pageNum, Long row, Integer type) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Message::getReceiverid, empNo).eq(Objects.nonNull(type), Message::getType, type);
        Page<Message> page = new Page<>();
        if (Objects.nonNull(pageNum) && pageNum > 0) {
            page.setCurrent(pageNum);
        }
        if (Objects.nonNull(row) && row > 0) {
            page.setSize(row);
        }
        Page<Message> messagePage = this.page(page, wrapper);
        List<Message> records = messagePage.getRecords();
        List<Message> messageList = new ArrayList<>();
        for (Message message : records) {
            if (null == message.getSender() && null != message.getSenderid()) {
                Emp emp = empService.getById(message.getSenderid());
                if (Objects.nonNull(emp)) {
                    message.setSender(emp.getName());
                }
            }
            if (null == message.getSender() && null == message.getSenderid()) {
                message.setSender("系统发送");
            }
            if (null == message.getReceiver() && null != message.getSenderid()) {
                if(null != empService.getById(message.getSenderid()).getName()){
                    message.setReceiver(empService.getById(message.getSenderid()).getName());
                }else {
                    message.setReceiver("已离职");
                }
            }
            messageList.add(message);
        }
        page.setRecords(messageList);
        List<Message> messages = this.list(wrapper);
        page.setTotal(messages.size());
        return page;
    }

    @Override
    public void setRead(List<String> ids) {
        for (String id : ids) {
            Message message = getById(id);
            if (Objects.nonNull(message)) {
                message.setStatu(Integer.parseInt(SysConstant.read.READ));
                this.updateById(message);
            }
            return;
        }
    }

    @Override
    public void sentAnnou(String text) {
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Emp::getDeflag, SysConstant.delflag.NORMAL);
        List<Emp> empList = empService.list(wrapper);
        Message message = new Message();
        message.setText(text);
        message.setType(Integer.parseInt(SysConstant.msgType.ANNOUNCE));
        ArrayList<String> list = new ArrayList<>();
        for (Emp emp : empList) {
            String string = String.valueOf(emp.getId());
            list.add(string);
        }
        String join = list.stream().collect(Collectors.joining(","));
        message.setReceiverid(join);
        this.sendMsg(message);

    }

    @Override
    public Page<Message> queryAnnouPage() {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Message::getType, SysConstant.msgType.ANNOUNCE);
        Page<Message> page = new Page<>();
        return this.page(page, wrapper);
    }

    @Override
    public Map<String, Integer> getNum(Long empNo) {
        HashMap<String, Integer> map = new HashMap<>();
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverid, empNo).eq(Message::getType, SysConstant.msgType.MSG).eq(Message::getStatu, SysConstant.read.UNREAD);
        List<Message> messages = this.list(wrapper);
        map.put("msg", messages.size());
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverid, empNo).eq(Message::getType, SysConstant.msgType.ANNOUNCE).eq(Message::getStatu, SysConstant.read.UNREAD);
        List<Message> announce = this.list(queryWrapper);
        map.put("announce", announce.size());
        return map;
    }

    @Override
    public Page<MessageVo> adminQueryMsg(Long page, Long rows, Integer type, String keyword) {
        Page<MessageVo> voPage = new Page<>();
        Page<Message> messagePage = new Page<>();
        if (page > 0) {
            messagePage.setCurrent(page);
        }
        if (rows > 0) {
            messagePage.setSize(rows);
        }
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != type && type == 1, Message::getType, SysConstant.msgType.ANNOUNCE)
                .eq(null != type && type == 0, Message::getType, SysConstant.msgType.MSG)
                .like(StringUtils.hasLength(keyword), Message::getText, keyword).orderByDesc(Message::getType,Message::getSendtime);
        List<Message> list = this.list(wrapper);
        Page<Message> msgPage = this.page(messagePage, wrapper);
        List<Message> messageList = msgPage.getRecords();
        ArrayList<MessageVo> vos = new ArrayList<>();
        for (Message message : messageList) {
            MessageVo vo = new MessageVo();
            BeanUtils.copyProperties(message, vo);
            String receiverid = message.getReceiverid();
            if (empService.getById(Long.parseLong(receiverid)) == null) {
                vo.setReceiverName("已离职");
            } else {
                vo.setReceiverName(empService.getById(Long.parseLong(receiverid)).getName());
            }
            if (message.getType() == 0) {
                if (empService.getById(Long.parseLong(receiverid)) == null) {
                    vo.setSenderName("已离职");
                } else {
                    vo.setSenderName(empService.getById(Long.parseLong(receiverid)).getName());
                }
            }
            if (message.getType() == 1) {
                vo.setSenderName("系统发送");
            }
            vos.add(vo);
        }
        voPage.setRecords(vos);
        voPage.setTotal(list.size());
        return voPage;
    }


}
