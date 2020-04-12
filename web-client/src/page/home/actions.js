import {message} from 'antd';

import {GET_STATISTIC} from './actionTypes.js';
import {requestHomeStatistic} from 'service/statistic.js';
import {RESET_ALL_STATE, SET_CURRENT_PAGE_CODE} from '../../constants';

const saveData = data => ({
    type: GET_STATISTIC,
    statisticData: data
});

function getStatisticData() {
    return async dispatch => {
        try {
            const {data} = await requestHomeStatistic();
            const {orderCount, productCount, userCount} = data;
            dispatch(saveData({
                orderCount,
                productCount,
                userCount
            }));
        } catch(error) {
            message.error(error);
        }
    };
}

const setCurrentPageCode = () => ({
    type: SET_CURRENT_PAGE_CODE,
    pageCode: '/'
});

export {
    saveData,
    getStatisticData,
    setCurrentPageCode
};
