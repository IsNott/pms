package com.nott.pms.dto;

import lombok.Data;

import java.util.List;

/**
 * 通用分页对象
 * @Author zzzwlong
 * @Date 2022/1/8 0:05
 */
@Data
public class BasePage<T> {

    private Integer count;

    private Integer page;

    private List<T> objList;

}
