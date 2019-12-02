package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.vo.user.UserVo;

import java.util.Date;
import java.util.List;

public interface IUserService {

    void addUser(User user);

    void deleteUserById(Long id);

    UserVo findUser(Long id);

    void updateUser(User user);

    DataTableResult findUserPageList(UserSearchParam userSearchParam);

    void batchDelete(List<Integer> ids);

    User findUserByUserName(String userName);

    void updateLogin(Long id, Date date);

    void updateLoginErrorCount(User userInfo);

    void updateLockUser(Long id);

    void updateLock();

    List<UserVo> findUserExpord(UserSearchParam userSearchParam);

    void updateLoginCount(Long id, int i);
}
