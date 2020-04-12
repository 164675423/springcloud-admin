
/**
 * 在本地存储内容
 * @param {string} key 键名
 * @param {any} data 值
 */
function setStorage(key, data) {
    const dataType = typeof data;

    if (dataType === 'object')
    // json 对象处理
        localStorage.setItem(key, JSON.stringify(data));
    else if (['number', 'string', 'boolean'].includes(dataType))
    // 基础类型
        localStorage.setItem(key, data);
    else
    // eslint-disable-next-line no-alert
        alert('该类型不能用于本地存储');
}

/**
 * 取出本地存储内容
 * @param {string} key
 */
function getStorage(key) {
    const data = localStorage.getItem(key);
  
    if (data)
        return JSON.parse(data);
    return '';
}

function removeStorage(key) {
    localStorage.removeItem(key);
}

/**
 * 本地存储用户信息
 * @param {object} data 用户信息
 */
function saveUserDataStorage(data) {
    setStorage('user', data);
}

/**
 * 获取本地存储的用户信息
 */
function getUserDataStorage() {
    return getStorage('user');
}

/**
 * 移除本地存储的用户信息
 */
function removeUserDataStorage() {
    removeStorage('user');
}

/**
 * 本地存储按钮权限信息
 * @param {object} data 用户信息
 */
function savePermissionStorage(data) {
    setStorage('permission', data);
}
/**
 * 获取本地存储的权限信息
 */
function getPermissionStorage() {
    return getStorage('permission');
}

/**
 * 移除本地存储的权限信息
 */
function removePermissionStorage() {
    removeStorage('permission');
}

/**
 * 本地存储菜单信息
 * @param {object} data 用户信息
 */
function savePageStorage(data) {
    setStorage('page', data);
}
/**
 * 获取本地存储的菜单信息
 */
function getPageStorage() {
    return getStorage('page');
}

/**
 * 移除本地存储的菜单信息
 */
function removePageStorage() {
    removeStorage('page');
}

export {
    saveUserDataStorage,
    getUserDataStorage,
    removeUserDataStorage,
    savePermissionStorage,
    getPermissionStorage,
    removePermissionStorage,
    savePageStorage,
    getPageStorage,
    removePageStorage
};
