package com.nott.pms.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.message.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.message.vo.MessageVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-01
 */
public interface IMessageService extends IService<Message> {

    Page<Message> getUserUnreadMsg(Long empNo,Integer type);

    void repMsg(Long empNo,Message msg);

    void sendMsg(Message msg);

    Page<Message> msgPage(Long empNo,Long page,Long row,Integer type);

    void setRead(List<String> asList);

    void sentAnnou(String text);

    Page<Message> queryAnnouPage();

    Map<String,Integer> getNum(Long empNo);

    Page<MessageVo> adminQueryMsg(Long page, Long rows, Integer type,String keyword);
}
