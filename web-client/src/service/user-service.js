import {
    get,
    post,
    put,
    handleAxiosResultNotification,
    REQUEST_TYPE
} from 'util/request';
import {saveUserDataStorage, removeUserDataStorage, removePageStorage, removePermissionStorage} from 'util/storege';
import {emitUserDataEvent} from 'util/custom-event';

/**
 * 用户登录
 * @param {string} username 账号
 * @param {string} password 密码
 */
function requestUserLogin(username, password) {
    const url = '/api/v1/login';
    const data = {
        username,
        password
    };

    return post(url, data)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '登录'));
}

/**
 * 用户登出
 */
function userLogout() {
    const url = '/api/v1/logout';
    return post(url).then(() => {
        removeUserDataStorage();
        removePageStorage();
        removePermissionStorage();
        emitUserDataEvent('logout');
        return '退出成功';
    });
}

function requestUserPages() {
    const url = '/am/api/v1/users/me/pages';
    return get(url, null)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '获取菜单'));
}

function requestUserOperations() {
    const url = '/am/api/v1/users/me/operations';
    return get(url, null)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '获取权限'));
}

function changePassword(oldPassword, newPassword) {
    const url = '/am/api/v1/users/me/password';
    const data = {
        oldPassword,
        newPassword
    };
    return put(url, data)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '修改密码'));
}

/**
 * 获取用户列表
 */
function requestUserList(pageSize, pageNum) {
    const url = '/manage/user/list.do';
    return get(url, {pageSize, pageNum});
}

export {requestUserLogin, userLogout, requestUserList, requestUserPages, requestUserOperations, changePassword};
