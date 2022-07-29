package com.nott.pms.attend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.attend.entity.Attend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-11
 */
public interface IAttendService extends IService<Attend> {

    Page<Attend> getEmpAttendList(Long empNo, String date, Long page, Long rows);
}
