package com.java4ye.demo.service.impl;

import com.java4ye.demo.model.TbSeckillVoucher;
import com.java4ye.demo.mapper.TbSeckillVoucherMapper;
import com.java4ye.demo.service.ITbSeckillVoucherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀优惠券表，与优惠券是一对一关系 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-12-12
 */
@Service
public class TbSeckillVoucherServiceImpl extends ServiceImpl<TbSeckillVoucherMapper, TbSeckillVoucher> implements ITbSeckillVoucherService {

}
