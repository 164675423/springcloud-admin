import {RESET_ALL_STATE, SET_CURRENT_PAGE_CODE} from '../../constants';
import * as api from './api';
import {pageSelect} from './selectors';

import store from '../../store';

//更新当前选中的菜单
export const setCurrentPageCode = pageCode => ({
    type: SET_CURRENT_PAGE_CODE,
    pageCode: pageCode
});

//初始化
export const GET_INIT_DATA_BEGIN = 'GET_INIT_DATA_BEGIN';
export const GET_INIT_DATA_SUCCESS = 'GET_INIT_DATA_SUCCESS';
export const GET_INIT_DATA_FAIL = 'GET_INIT_DATA_FAIL';

const getInitDataBegin = () => ({
    type: GET_INIT_DATA_BEGIN
});

const getInitDataSuccess = data => ({
    type: GET_INIT_DATA_SUCCESS,
    data
});

const getInitDataFail = () => ({
    type: GET_INIT_DATA_FAIL
});
//获取初始化数据
export const getInitData = () => dispatch => {
    dispatch(getInitDataBegin());
    return api.getList().then(res => {
        if(res.ok)
            dispatch(getInitDataSuccess(res.data));
        else
            dispatch(getInitDataFail());
    });
};

export const ROLE_CHANGE = 'ROLE_CHANGE';
export const roleChange = (value, name) => ({
    type: ROLE_CHANGE,
    value,
    name
});

//获取指定ID的角色信息
export const GET_ROLES_BY_ID_BEGIN = 'GET_ROLES_BY_ID_BEGIN';
export const GET_ROLES_BY_ID_SUCCESS = 'GET_ROLES_BY_ID_SUCCESS';
export const GET_ROLES_BY_ID_FAIL = 'GET_ROLES_BY_ID_FAIL';
export const getRolesByIdBegin = () => ({
    type: GET_ROLES_BY_ID_BEGIN
});
export const getRolesByIdSuccess = data => ({
    type: GET_ROLES_BY_ID_SUCCESS,
    data
});
export const getRolesByIdFail = () => ({
    type: GET_ROLES_BY_ID_FAIL,
});

export const getRoleById = id => dispatch => {
    dispatch(getRolesByIdBegin());
    return api.getDetail(id).then(res => {
        if(res.ok)
            dispatch(getRolesByIdSuccess(res.data));
        else
            dispatch(getRolesByIdFail());
    });
};

export const GET_PAGES_BEGIN = 'GET_PAGES_BEGIN';
export const GET_PAGES_SUCCESS = 'GET_PAGES_SUCCESS';
export const GET_PAGES_FAIL = 'GET_PAGES_FAIL';

export const getPagesBegin = () => ({
    type: GET_PAGES_BEGIN
});

export const getPagesSuccess = data => ({
    type: GET_PAGES_SUCCESS,
    data
});

export const getPagesFail = () => ({
    type: GET_PAGES_FAIL,
});
export const getPagesByMaxLevel = () => dispatch => {
    dispatch(getPagesBegin());
    return api.getPages().then(res => {
        if(res.ok)
            dispatch(getPagesSuccess(res.data));
        else
            dispatch(getPagesFail());
    });
};

export const GET_PAGE_BEGIN = 'GET_PAGE_BEGIN';
export const GET_PAGE_SUCCESS = 'GET_PAGE_SUCCESS';
export const GET_PAGE_FAIL = 'GET_PAGE_FAIL';

export const getPageBegin = () => ({
    type: GET_PAGE_BEGIN
});

export const getPageSuccess = (data, id) => ({
    type: GET_PAGE_SUCCESS,
    id,
    data
});

export const getPageFail = () => ({
    type: GET_PAGES_FAIL,
});
export const getPageById = id => dispatch => {
    dispatch(getPageBegin());
    return api.getPageDetail(id).then(res => {
        if(res.ok)
            dispatch(getPageSuccess(res.data));
        else
            dispatch(getPageFail());
    });
};

export const changeOpt = (id, selected, pageId) => dispatch => {
    const props = selected ? 'add' : 'remove';
    const pages = store.getState().role.roleDetail.rolePages;
    const hasPage = Boolean(pageId && !pages.includes(pageId));
    return dispatch(
        permissionSet(
            selected && hasPage
                ? {
                    add: [pageId]
                }
                : null,
            {
                [props]: [id]
            }
        )
    );
};

export const PERMISSION_SET = 'PERMISSION_SET';
export const permissionSet = (pages, operations) => {
    const action = {
        type: PERMISSION_SET
    };
    if(pages)
        action.pages = {
            add: pages.add || [],
            remove: pages.remove || []
        };
    if(operations)
        action.operations = {
            add: operations.add || [],
            remove: operations.remove || []
        };
    return action;
};

//全部选择或者全部取消时
export const selectAll = (id, selected) => dispatch => {
    const permission = [];
    const prop = selected ? 'add' : 'remove';
    dispatch(
        permissionSet(
            {
                [prop]: permission.pages
            },
            {
                [prop]: permission.operations
            }
        )
    );
};

export const selectPage = (id, selected) => dispatch => {
    const permission = pageSelect(id, store.getState().role.pages.data);
    const prop = selected ? 'add' : 'remove';
    dispatch(
        permissionSet(
            {
                [prop]: [permission.page]
            },
            {
                [prop]: permission.operations
            }
        )
    );
};

//清除role detail
export const RESRT_ROLE_DETAIL = 'RESRT_ROLE_DETAIL';
export const resetRoleDetail = () => ({
    type: RESRT_ROLE_DETAIL
});

//提交
export const onSubmit = () => {
    const roleDetail = store.getState().role.roleDetail;
    const {data, rolePages, roleOperations} = roleDetail;
    if(data && data.id) {
        const updateCondition = {
            name: data.name,
            pages: rolePages,
            operations: roleOperations
        };
        return api.updateData(data.id, updateCondition).then(res => ({success: res.ok}));
    }else{
        const addCondition = {
            name: data.name,
            pages: rolePages,
            operations: roleOperations
        };
        return api.addData(addCondition).then(res => ({success: res.ok}));
    }
};

