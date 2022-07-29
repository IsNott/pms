package com.nott.pms.sal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.sal.bo.EmpSalBo;
import com.nott.pms.sal.entity.EmpSal;
import com.nott.pms.sal.mapper.EmpSalMapper;
import com.nott.pms.sal.service.IEmpSalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.utils.ExcelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工薪资表 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-03-31
 */
@Service
public class EmpSalServiceImpl extends ServiceImpl<EmpSalMapper, EmpSal> implements IEmpSalService {
    @Autowired
    private IEmpService empService;


    @Override
    public EmpSal querySalByNo(Long empNo) {
        LambdaQueryWrapper<EmpSal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmpSal::getEmpno, empNo);
        EmpSal sal = this.list(wrapper).get(0);
        Double total = 0.0;
        if(sal.getTotal() !=null && sal.getTotal()>0){
            total = sal.getTotal();
        }

        if (sal.getTotal() == null && sal.getTotalAftax() == null) {
            if (sal.getSubsidy() != null && sal.getSubsidy() > 0) {
                sal.setTotal(sal.getSalary() + sal.getSubsidy());
            } else {
                sal.setSubsidy(0D);
                sal.setTotal(sal.getSalary() + sal.getSubsidy());
            }
            if (sal.getOverTime() != null && sal.getOverTime() > 0) {
                sal.setTotal(total + sal.getSalary() + (sal.getOverTime() * sal.getOtSal()));
            } else {
                sal.setOverTime(0D);
                sal.setTotal(total + sal.getSalary() + (sal.getOverTime() * sal.getOtSal()));
            }

            if (sal.getTotal() <= 5000) {
                sal.setRate(0D);
                sal.setTotalAftax(sal.getTotal());
            }
            if (sal.getTotal() > 5000 && sal.getTotal() <= 8000) {
                sal.setRate(0.03D);
                sal.setTotalAftax(sal.getTotal() - (sal.getTotal() * sal.getRate()));
            }
            if (sal.getTotal() > 8000) {
                sal.setRate(0.05D);
                sal.setTotalAftax(sal.getTotal() - (sal.getTotal() * sal.getRate()));
            }
            updateById(sal);
        }
        return sal;
    }

    @Override
    public String exportExcel(Long empNo, HttpServletResponse response) throws IOException {
        EmpSal empSal = this.querySalByNo(empNo);
        Emp emp = empService.getById(empNo);
        // Excel标题
        String[] title = {"姓名", "基础工资(元)", "员工津贴(元)", "加班费时薪(元)",
                "加班时长(小时)", "税前收入(元)", "税后收入(元)", "税率"};
        // Excel文件名
        String fileName = "薪资明细";
        String[] key = {"name", "salary", "subsidy", "otSal", "overTime", "total", "totalAftax", "rate"};
        // sheet名
        String sheetName = "薪资明细";
        EmpSalBo bo = new EmpSalBo();
        BeanUtils.copyProperties(empSal, bo);
        bo.setName(emp.getName());
        ArrayList<EmpSalBo> empSalBos = new ArrayList<>();
        empSalBos.add(bo);
        List<Map<String, Object>> list = createExcelRecord(empSalBos);
        ExcelUtil.downloadWorkBook(list, key, title, fileName, response);
        return "excel";
    }

    /**
     * 创建Excel表中的记录
     *
     * @param boList
     * @return
     */
    private List<Map<String, Object>> createExcelRecord(List<EmpSalBo> boList) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sheetName", "sheet1");
            listmap.add(map);
            for (int j = 0; j < boList.size(); j++) {
                EmpSalBo bo = boList.get(j);
                Map<String, Object> mapValue = new HashMap<String, Object>();
                mapValue.put("name", bo.getName());
                mapValue.put("salary", bo.getSalary());
                mapValue.put("subsidy", bo.getSubsidy());
                mapValue.put("otSal", bo.getOtSal());
                mapValue.put("overTime", bo.getOverTime());
                mapValue.put("total", bo.getTotal());
                mapValue.put("totalAftax", bo.getTotalAftax());
                mapValue.put("rate", bo.getRate());
                listmap.add(mapValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listmap;
    }
}
