import {handleAxiosResultNotification, REQUEST_TYPE, get, put, post} from 'util/request';
import stringify from 'util/stringify';
const baseUrl = '/am/api/v1';

// 查询
export const getList = bodyCondition => {
    const queryString = stringify(bodyCondition);
    return get(`${baseUrl}/users?${queryString}`, null)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询用户'));
};

// 作废
export const abandonUser = (id, data) => {
    return put(`${baseUrl}/users/${id}/abandon`, data)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '作废用户'));
};

// 新增
export const addData = data => post(`${baseUrl}/users`, data)
    .then(handleAxiosResultNotification(REQUEST_TYPE.submit, '新增用户'));

// 编辑
export const updateData = (id, data) => put(`${baseUrl}/users/${id}`, data)
    .then(handleAxiosResultNotification(REQUEST_TYPE.submit, '修改用户'));

//初始化数据
export const getInitData = () => {
    return get(`${baseUrl}/ui/user/init?`, null)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询初始化'));
};

//查询详情
export const getDetail = id => get(`${baseUrl}/users/${id}`, null)
    .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询用户详情'));
