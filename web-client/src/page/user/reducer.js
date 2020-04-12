import {combineReducers} from 'redux';
import * as actions from './actions';
import {state as userState} from './state';
import {DEFAULT_QUERY_OPTION} from './constants';
import {RESET_ALL_STATE} from '../../constants';

const list = (state = userState.list, action) => {
    switch(action.type) {
        case actions.GET_LIST_DATA_BEGIN:
            return Object.assign({}, state, {
                isFetching: true
            });
        case actions.GET_LIST_DATA_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                total: action.data.totalElements,
                data: action.data.content
            });
        case actions.GET_LIST_DATA_FAIL:
            return Object.assign({}, state, {
                isFetching: false,
                total: 0,
                data: [],
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                isFetching: false,
                total: 0,
                data: [],
            });
        default:
            return state;
    }
};

const initData = (state = userState.initData, action) => {
    switch(action.type) {
        case actions.GET_INIT_DATA_BEGIN:
            return Object.assign({}, state, {
                isFetching: true
            });
        case actions.GET_INIT_DATA_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                roles: action.data
            });
        case actions.GET_INIT_DATA_FAIL:
            return Object.assign({}, state, {
                isFetching: false,
                roles: [],
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                isFetching: false,
                roles: [],
            });
        default:
            return state;
    }
};

const queryCondition = (state = userState.queryCondition, action) => {
    switch(action.type) {
        case actions.SAVE_QUERY_CONDITION:
            return Object.assign({}, state, {
                [action.name]: action.value
            });
        case actions.RESET_QUERY_PANEL:
            return DEFAULT_QUERY_OPTION;
        case actions.GET_LIST_DATA_SUCCESS:
            return Object.assign({}, state, {
                pageSize: action.condition.pageSize,
                sortField: action.condition.sortField,
                isDesc: action.condition.isDesc
            });
        case RESET_ALL_STATE:
            return DEFAULT_QUERY_OPTION;
        default:
            return state;
    }
};

const reducer = combineReducers({
    list,
    queryCondition,
    initData
});
export default reducer;
