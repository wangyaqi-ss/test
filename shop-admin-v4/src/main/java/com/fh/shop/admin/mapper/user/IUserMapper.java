package com.fh.shop.admin.mapper.user;

import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.role.UserRole;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.vo.user.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IUserMapper {

    void addUser(User user);

    void deleteUserById(Long id);

    User findUser(Long id);

    void updateUser(User user);

    Long findUserCount(UserSearchParam userSearchParam);

    List<User> findUserPageList(UserSearchParam userSearchParam);

    void addUserRole(UserRole userRole);

    List<String> findRoleNameList(Long id);

    List<Long> roleIdList(Long id);

    void deleteUserRole(Long id);

    void batchDeleteUserRole(List<Integer> ids);

    void batchDelete(List<Integer> ids);

    User findUserByUserName(String userName);

    void updateLogin(@Param("id") Long id, @Param("date") Date date);

    void updateLoginErrorCount(User userInfo);

    void updateLockUser(Long id);

    void updateLock();

    List<User> findUserExpord(UserSearchParam userSearchParam);

    void updateLoginCount(@Param("id") Long id, @Param("i") int i);
}
