package com.java4ye.demo.mapper;

import com.java4ye.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Java4ye
 * @since 2021-01-05
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 增加用户
     * @param record
     * @return
     */
    @Override
    int insert(User record);
}
