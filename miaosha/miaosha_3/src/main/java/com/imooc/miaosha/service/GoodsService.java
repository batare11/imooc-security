package com.imooc.miaosha.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.LoginVo;

@Service
public class GoodsService {
	@Autowired
	GoodsDao goodsDao;
  public List<GoodsVo> listGoodVo(){
	  return goodsDao.listGoodsVo();
  }
	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
	 return goodsDao.getGoodsVoByGoodsId(goodsId);
	}
	public void reduceStock(GoodsVo goods) {
      MiaoshaGoods g=new MiaoshaGoods();
      g.setGoodsId(goods.getId());
      goodsDao.reduceStock(g);
	}
}
