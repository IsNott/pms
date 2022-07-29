package com.nott.pms.message.vo;

import com.nott.pms.message.entity.Message;
import lombok.Data;

import java.util.Date;
@Data
public class MessageVo extends Message {
    private String senderName;
    private String receiverName;

}
