package com.shunyue.mall.service.impl;


import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.shunyue.mall.dao.UmsRoleDao;
import com.shunyue.mall.mapper.UmsRoleMapper;
import com.shunyue.mall.model.UmsMenu;
import com.shunyue.mall.model.UmsRole;
import com.shunyue.mall.model.UmsRoleExample;
import com.shunyue.mall.service.UmsAdminCacheService;
import com.shunyue.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 后台角色管理Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRoleDao roleDao;

    @Autowired
    UmsAdminCacheService adminCacheService;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        // 将角色插入 ums_role 数据库里面
        return roleMapper.insert(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        int count = roleMapper.deleteByExample(example);
        // 删除了这些角色，需要清除 角色和用户的关系表相关数据 并清除对应的 redis 缓存
        adminCacheService.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        // 去数据库里面读取
        return roleDao.getMenuList(adminId);
    }

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        // 添加关键字搜索 sql 语句
        if (!StrUtil.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        // 执行sql查询
        return roleMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }
}