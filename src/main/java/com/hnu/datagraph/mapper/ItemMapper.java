package com.hnu.datagraph.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnu.datagraph.entity.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}