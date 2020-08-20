import axios from 'axios';
import {message, Modal} from 'antd';
import {removeUserDataStorage} from './storege';
import {emitUserDataEvent} from 'util/custom-event';

//axios 全局配置
axios.defaults.withCredentials = true;
axios.defaults.crossDomain = true;
axios.defaults.baseURL = 'http://172.26.11.197/';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axios.defaults.headers.put['Content-Type'] = 'application/json;charset=UTF-8';

const request = config => {
    const {method, url, data, params} = config;

    const axioConfig = {
        method,
        url: `${url}`,
        data,
        params
    };

    return axios(axioConfig)
        .then(res => {
            if (res.status !== 200)
                return {
                    ok: false,
                    status: res.status,
                    message: res.data.message
                };
            return {
                ok: true,
                status: res.status,
                message: res.data.message,
                data: res.data.payload || {}
            };
        })
        .catch(error => {
            if (error.response.status === 401) {
                removeUserDataStorage();
                emitUserDataEvent('logout');
            }

            return {
                ok: false,
                status: error.response.status,
                data: error.response.data.payload || {},
                message: error.response.data.message
            };
        });
};

/**
 * get 请求
 * @param {string} url
 * @param {object} params 查询参数
 */
const get = (url, params) =>
    request({
        method: 'GET',
        url,
        params
    });

/**
 * put 请求
 * @param {string} url
 * @param {object} data 数据
 */
const put = (url, data) =>
    request({
        method: 'PUT',
        url,
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    });

/**
 * post 请求
 * @param {string} url
 * @param {object} data 数据
 */
const post = (url, data) =>
    request({
        method: 'POST',
        url,
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    });

/**
 * 成功/失败后的消息提示类型定义
 */
const REQUEST_TYPE = {
    query: {
        success: null,
        failure: 'message'
    },
    initQuery: {
        success: null,
        failure: 'modal'
    },
    submit: {
        success: 'message',
        failure: 'modal'
    }
};
const initQueryDefaultInfo = Object.freeze({
    title: '界面初始化'
});
/**
 * 消息格式化
 * @param status        http status
 * @param description   操作描述，eg. 提交销售订单 / 查询用户信息
 * @param message       服务端返回的，具体消息内容
 * @returns {title: string, content: string}    格式化后的消息
 */
export const messageFormat = (status, description, message) => {
    const desc = description || '';
    switch (status) {
        case 200:
        case 201:
            return {title: `${desc}成功`};
        case 401:
            return {
                title: `${desc}失败：登录超时，请重新登录 (401)`
            };
        case 403:
            return {
                title: `${desc}失败：没有权限执行操作，请联系管理员 (403)`
            };
        case 404:
            return {
                title: message ? `${desc}失败 (404)` : `${desc}失败：Not Found (404)`,
                content: message
            };
        case 500:
            return {
                title: `${desc}失败：内部服务器错误 (500)`,
                content: message
            };
        case 502:
            return {
                title: `${desc}失败，请稍后重试 (502)`
            };
        default:
            return {
                title: `${desc}失败 (${status})`,
                content: message
            };
    }
};
const handleAxiosResultNotification = (
    requestType = REQUEST_TYPE.query,
    description
) => res => {
    if (requestType === REQUEST_TYPE.initQuery && !description)
        description = initQueryDefaultInfo.title;

    const errorInfo = messageFormat(res.status, description, res.message);
    const successInfo = messageFormat(res.status, description, res.message);

    // 当服务端返回的 message 反序列化为 object 时，序列化为字符串做展示
    // 考虑目前 message 出现 object，一般为代码实现错误，content 展示暂时不考虑优化，仅以stringify文本展示
    if (errorInfo.content && typeof errorInfo.content === 'object')
        errorInfo.content = JSON.stringify(errorInfo.content);

    if (res.ok && requestType.success)
        switch (requestType.success) {
            case 'message':
                message.success(successInfo.title);
                break;
            default:
                message.success(successInfo.title);
        }

    if (!res.ok && requestType.failure)
        switch (requestType.failure) {
            case 'message':
                message.error(
                    errorInfo.content
                        ? `${errorInfo.title}：${errorInfo.content}`
                        : errorInfo.title
                );
                break;
            case 'modal':
                Modal.error({
                    title: errorInfo.title,
                    content: errorInfo.content
                });
                break;
            default:
                message.error(
                    errorInfo.content
                        ? `${errorInfo.title}：${errorInfo.content}`
                        : errorInfo.title
                );
        }
    return res;
};

export {get, post, put, handleAxiosResultNotification, REQUEST_TYPE};
