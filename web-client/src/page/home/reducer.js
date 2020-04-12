import {GET_STATISTIC} from './actionTypes';
import {RESET_ALL_STATE, SET_CURRENT_PAGE_CODE} from '../../constants';

const initialState = {
    statisticData: {},
    currentPageCode: '/'
};

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case GET_STATISTIC:
            return {
                ...state,
                statisticData: action.statisticData
            };
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                statisticData: {}
            });
        case SET_CURRENT_PAGE_CODE:
            return Object.assign({}, state, {
                currentPageCode: action.pageCode
            });
        default:
            return state;
    }
};

export default reducer;
