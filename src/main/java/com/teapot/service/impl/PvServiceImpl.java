package com.teapot.service.impl;

import com.teapot.dao.TbPvDao;
import com.teapot.pojo.TbPv;
import com.teapot.pojo.TbPvQuery;
import com.teapot.service.PvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class PvServiceImpl implements PvService {
    @Autowired
    private TbPvDao pvDao;
    @Override
    public void plusPv(Date day, String url) {
        TbPvQuery query = new TbPvQuery();
        TbPvQuery.Criteria criteria = query.createCriteria();
        criteria.andDayEqualTo(day);
        criteria.andUrlEqualTo(url);
        List<TbPv> pvs = pvDao.selectByExample(query);
        if (pvs.size() > 0) {
            TbPv tbPv = new TbPv();
            tbPv.setId(pvs.get(0).getId());
            tbPv.setPv(pvs.get(0).getPv() + 1);
            pvDao.updateByPrimaryKeySelective(tbPv);
        } else {
            TbPv tbPv = new TbPv();
            tbPv.setDay(day);
            tbPv.setUrl(url);
            tbPv.setPv(1);
            pvDao.insert(tbPv);
        }
    }
}
