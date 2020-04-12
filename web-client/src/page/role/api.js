import {handleAxiosResultNotification, REQUEST_TYPE, get, put, post} from 'util/request';
const baseUrl = '/am/api/v1';

// 查询
export const getList = () => {
    return get(`${baseUrl}/roles?`, null)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询角色'));
};

// 作废
export const abandonRole = (id, data) => {
    return put(`${baseUrl}/roles/${id}/abandon`, data)
        .then(handleAxiosResultNotification(REQUEST_TYPE.query, '作废角色'));
};

// 新增
export const addData = data => post(`${baseUrl}/roles`, data)
    .then(handleAxiosResultNotification(REQUEST_TYPE.submit, '新增角色'));

// 编辑
export const updateData = (id, data) => put(`${baseUrl}/roles/${id}`, data)
    .then(handleAxiosResultNotification(REQUEST_TYPE.submit, '修改角色'));
    
// 编辑基础信息
export const updateInfoData = (id, data) => put(`${baseUrl}/roles/${id}/info`, data)
    .then(handleAxiosResultNotification(REQUEST_TYPE.submit, '修改角色基础信息'));

//查询详情
export const getDetail = id => get(`${baseUrl}/roles/${id}`, null)
    .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询角色详情'));

//查询pages
export const getPages = () => get(`${baseUrl}/pages`, null)
    .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询pages'));

//查询page详情
export const getPageDetail = id => get(`${baseUrl}/pages/${id}`, null)
    .then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询page详情'));
