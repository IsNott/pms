package com.nott.pms.emp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.emp.bo.EmpBo;
import com.nott.pms.emp.entity.Emp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.emp.vo.EmpInfoVo;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-13
 */
public interface IEmpService extends IService<Emp> {

    Page<EmpInfoVo> findByPage(Integer page, Integer size, String name);


    Page<Emp> queryEmpByPage(Long page, Long rows, String name);

    void saveBascie(EmpBo info);

    List<Emp> queryNomemp();
}
