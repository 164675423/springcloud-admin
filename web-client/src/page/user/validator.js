import {message} from 'antd';

export const newOrUpdateValidator = data => {
    const requiredFields = [];
    if(!data || !data.name)
        requiredFields.push('姓名');
    if(!data || !data.username)
        requiredFields.push('用户名');
    if(!data || !data.id) {
        if(!data || !data.password)
            requiredFields.push('密码');
        if(!data || !data.confirmPassword)
            requiredFields.push('确认密码');
        if(!data || data.password !== data.confirmPassword) {
            message.warning('请确认密码是否一致');
            return false;
        }
        if(data && data.password && data.password.length < 6) {
            message.warning('密码最低为6位');
            return false;
        }
    }
    
    if(requiredFields.length > 0) {
        message.warning(`${requiredFields.join('，')}必填`);
        return false;
    }
    return true;
};
