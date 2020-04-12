import * as api from './api';
import store from '../../store';
import {RESET_ALL_STATE, SET_CURRENT_PAGE_CODE} from '../../constants';

//重置store
export const resetStore = () => ({
    type: RESET_ALL_STATE
});

//更新当前选中的菜单
export const setCurrentPageCode = pageCode => ({
    type: SET_CURRENT_PAGE_CODE,
    pageCode: pageCode
});
/*==============================查询action================================= */
//修改查询条件
export const SAVE_QUERY_CONDITION = 'SAVE_QUERY_CONDITION';
export const saveQueryCondition = (name, value) => ({
    type: SAVE_QUERY_CONDITION,
    name,
    value
});

/*==============================查询列表================================= */
export const GET_LIST_DATA_BEGIN = 'GET_LIST_DATA_BEGIN';
export const GET_LIST_DATA_SUCCESS = 'GET_LIST_DATA_SUCCESS';
export const GET_LIST_DATA_FAIL = 'GET_LIST_DATA_FAIL';

const getListDataBegin = () => ({
    type: GET_LIST_DATA_BEGIN
});

const getListDataSuccess = (condition, data) => ({
    type: GET_LIST_DATA_SUCCESS,
    condition,
    data
});

const getListDataFail = () => ({
    type: GET_LIST_DATA_FAIL
});

//获取查询列表
export const getList = condition => dispatch => {
    dispatch(getListDataBegin());
    return api.getList(condition).then(res => {
        if(res.ok)
            dispatch(getListDataSuccess(condition, res.data));
        else
            dispatch(getListDataFail());
    });
};

/*=============================Table数据刷新================================== */
export const tableSearch = page => dispatch => {
    const query = store.getState().user.queryCondition;
    if(!query.pageIndex || !page)
        query.pageIndex = 0;
    const condition = Object.assign({}, query, page);
    dispatch(getList(condition));
};

/*==============================初始化或清理数据================================= */
//清除数据
export const CLEAR_DATA = 'CLEAR_DATA';
export const clearData = () => ({
    type: CLEAR_DATA,
});
//重置查询条件
export const RESET_QUERY_PANEL = 'RESET_QUERY_PANEL';
export const resetQueryPanel = () => ({
    type: RESET_QUERY_PANEL
});

//按钮搜索
export const querySearch = () => dispatch => {
    const condition = store.getState().user.queryCondition;
    condition.pageIndex = 0;
    dispatch(getList(condition));
};

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
    return api.getInitData().then(res => {
        if(res.ok)
            dispatch(getInitDataSuccess(res.data));
        else
            dispatch(getInitDataFail());
    });
};

//提交
export const onSubmit = data => () => {
    const newCondition = {
        username: data.username,
        name: data.name,
        password: data.password,
        roles: data.roles
    };
    const updateCondition = {
        name: data.name,
        roles: data.roles,
        rowVersion: data.rowVersion
    };
    return data.id ? api.updateData(data.id, updateCondition)
        .then(res => ({success: res.ok})) : api.addData(newCondition).then(res => ({success: res.ok}));
};
