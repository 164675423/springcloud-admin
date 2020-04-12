import {DEFAULT_QUERY_OPTION} from './constants';
export const state = {
    list: {
        isFetching: false,
        total: 0,
        data: []
    },
    queryCondition: DEFAULT_QUERY_OPTION,
    initData: {
        isFetching: false,
        roles: []
    }
};
