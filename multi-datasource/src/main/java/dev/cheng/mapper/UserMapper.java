package dev.cheng.mapper;

import dev.cheng.controller.vo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toVo(dev.cheng.entity.User user);
}
