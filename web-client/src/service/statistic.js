import {get, handleAxiosResultNotification, REQUEST_TYPE} from 'util/request';

function requestHomeStatistic() {
    const url = '/am/api/v1/statistics';
    return get(url).then(handleAxiosResultNotification(REQUEST_TYPE.query, '查询首页统计'));
}

export {
    requestHomeStatistic
};
