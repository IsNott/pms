package com.nott.pms.sal.service;

import com.nott.pms.sal.entity.EmpSal;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 员工薪资表 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-31
 */
public interface IEmpSalService extends IService<EmpSal> {

    EmpSal querySalByNo(Long empNo);

    String exportExcel(Long empNo, HttpServletResponse response) throws IOException;
}
