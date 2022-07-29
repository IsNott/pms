package com.nott.pms.detail.service;

import com.nott.pms.detail.bo.EmpDetailBo;
import com.nott.pms.detail.entity.EmpDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.detail.vo.EmpDetailVo;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.exception.UserNotFoundExeption;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>
 * 员工详细信息表 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-29
 */
public interface IEmpDetailService extends IService<EmpDetail> {

    EmpDetailVo queryEmpDetail(Long id);

    void updateDetail(EmpDetailBo detail);

    void saveNewEmp(Emp emp, EmpDetail detail) throws BadHanyuPinyinOutputFormatCombination;

    void addDetail(EmpDetail empDetail) throws BadHanyuPinyinOutputFormatCombination;
}
