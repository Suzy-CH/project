package com.sky.service.impl;

import com.sky.controller.admin.CommonController;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;


@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
//        插入一条数据
        Dish dish= new Dish();
//        对象属性拷贝
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setImage(CommonController.path);
        dishMapper.insert(dish);
        Long dishId=dish.getId();
//        插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0)
        {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
