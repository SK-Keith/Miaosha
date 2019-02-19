package com.keith.miaosha.service;

import com.keith.miaosha.dao.GoodsDao;
import com.keith.miaosha.domain.MiaoshaGoods;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YMX
 * @date 2019/1/28 16:01
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(g);
    }
}
