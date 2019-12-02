package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.role.UserRole;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.Md5Util;
import com.fh.shop.admin.vo.user.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    //新增用户
    public void addUser(User user) {
        //生成一个32位字符串
        String uuid = UUID.randomUUID().toString();
        user.setPassword(Md5Util.md5(Md5Util.md5(user.getPassword())+uuid));
        user.setSalt(uuid);
        userMapper.addUser(user);
        addUserRole(user);
        //throw new RuntimeException("111111");
    }


    //新增用户角色中间表
    private void addUserRole(User user) {
        //添加用户角色中间表
        if (StringUtils.isNotEmpty(user.getIds())){
            String[] roleIdArr=user.getIds().split(",");
            for (String s : roleIdArr) {
                UserRole userRole=new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.parseLong(s));
                userMapper.addUserRole(userRole);
            }
        }
    }

    @Override
    public void deleteUserById(Long id) {
        //先删除用户角色中间表数据
        userMapper.deleteUserRole(id);
        //删除用户
        userMapper.deleteUserById(id);
    }

    @Override
    public UserVo findUser(Long id) {
        User user = userMapper.findUser(id);
        UserVo userVo = getUserVo(user);
        //获取相关角色信息
        List<Long> roleIdList=userMapper.roleIdList(id);
        userVo.setRoleIdList(roleIdList);
        return userVo;
    }

    private UserVo getUserVo(User user) {
        UserVo userVo=new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setPassword(user.getPassword());
        userVo.setRealName(user.getRealName());
        userVo.setSex(user.getSex());
        userVo.setAge(user.getAge());
        userVo.setPhone(user.getPhone());
        userVo.setEmail(user.getEmail());
        userVo.setPay(user.getPay());
        userVo.setPhoto(user.getPhoto());
        userVo.setEntryTime(DateUtil.date2str(user.getEntryTime(),DateUtil.Y_M_D));
        userVo.setLoginCount(user.getLoginCount());
        userVo.setLoginTime(DateUtil.date2str(user.getLoginTime(),DateUtil.FUFF_YEAR));
        return userVo;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
        //先删除 之前的用户角色中间表数据
        userMapper.deleteUserRole(user.getId());
        //再添加
        addUserRole(user);
    }

    @Override
    public DataTableResult findUserPageList(UserSearchParam userSearchParam) {
        //获取角色查询条件
        buildRoleArray(userSearchParam);
        //查询总条数
        Long totalCount=userMapper.findUserCount(userSearchParam);
        //获取分页列表
        List<User> userList = userMapper.findUserPageList(userSearchParam);
        //po转vo
        List<UserVo> userVoList = new ArrayList<UserVo>();
        for (User userInfo : userList) {
            UserVo userVo=getUserVo(userInfo);
            //查询相关角色集合
            List<String> roleName=userMapper.findRoleNameList(userInfo.getId());
            if (roleName != null && roleName.size()>0 ){
                String roleNames=StringUtils.join(roleName,",");
                userVo.setRoleNames(roleNames);
            }
            userVoList.add(userVo);
        }
        DataTableResult dataTableResult = new DataTableResult(userSearchParam.getDraw(), totalCount, totalCount, userVoList);
        return dataTableResult;
    }

    //批量删除
    @Override
    public void batchDelete(List<Integer> ids) {
        //先删除 用户角色中间表数据
        userMapper.batchDeleteUserRole(ids);
        //删除用户
        userMapper.batchDelete(ids);
    }

    //通过用户名查询用户
    @Override
    public User findUserByUserName(String userName) {
        User userByUserName = userMapper.findUserByUserName(userName);
        return userByUserName;
    }

    //修改 登录时间
    @Override
    public void updateLogin(Long id,Date date) {
        userMapper.updateLogin(id,date);
    }


    //记录密码错误次数
    @Override
    public void updateLoginErrorCount(User userInfo) {
        userInfo.setLoginErrorCount(userInfo.getLoginErrorCount()+1);
        userMapper.updateLoginErrorCount(userInfo);
    }

    //锁定用户
    @Override
    public void updateLockUser(Long id) {
        userMapper.updateLockUser(id);
    }

    //解锁全部用户
    @Override
    public void updateLock() {
        userMapper.updateLock();
    }

    //按条件查询要导出的数据
    @Override
    public List<UserVo> findUserExpord(UserSearchParam userSearchParam) {
        List<User> userExpordList = userMapper.findUserExpord(userSearchParam);
        //po转vo
        List<UserVo> userVoList=new ArrayList<UserVo>();
        for (User userInfo : userExpordList) {
            UserVo userVo=getUserVo(userInfo);
            //查询相关角色集合
            List<String> roleName=userMapper.findRoleNameList(userInfo.getId());
            if (roleName != null && roleName.size()>0 ){
                String roleNames=StringUtils.join(roleName,",");
                userVo.setRoleNames(roleNames);
            }
            userVoList.add(userVo);
        }
        return userVoList;
    }

    //修改 登陆次数
    @Override
    public void updateLoginCount(Long id, int i) {
        userMapper.updateLoginCount(id,i);
    }

    private void buildRoleArray(UserSearchParam userSearchParam) {
        if (StringUtils.isNotEmpty(userSearchParam.getRoleIds())){
            String[] roleIdArr=userSearchParam.getRoleIds().split(",");
            userSearchParam.setRoleIdArr(roleIdArr);
            userSearchParam.setRoleIdLength(roleIdArr.length);
        }
    }

}
