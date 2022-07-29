package com.nott.pms.attend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.attend.entity.Attend;
import com.nott.pms.attend.mapper.AttendMapper;
import com.nott.pms.attend.service.IAttendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-11
 */
@Service
public class AttendServiceImpl extends ServiceImpl<AttendMapper, Attend> implements IAttendService {

    @Override
    public Page<Attend> getEmpAttendList(Long empNo, String date, Long page, Long rows) {
        Page<Attend> attendPage = new Page<>();
        if (Objects.nonNull(page) && page > 0) {
            attendPage.setCurrent(page);
        }
        if (Objects.nonNull(rows) && rows > 0) {
            attendPage.setSize(rows);
        }
        LambdaQueryWrapper<Attend> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attend::getEmpNo, empNo);
        Page<Attend> attends = this.page(attendPage,wrapper);
        return attends;
    }
}
