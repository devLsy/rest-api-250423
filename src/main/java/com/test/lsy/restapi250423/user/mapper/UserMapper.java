package com.test.lsy.restapi250423.user.mapper;

import com.test.lsy.restapi250423.user.model.FlatUserOrderItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<FlatUserOrderItemDto> selectUserOrderItems();
}
