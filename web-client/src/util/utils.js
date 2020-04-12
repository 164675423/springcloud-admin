export const hasPermissions = (pageCode, data, name) => {
    if(pageCode && Array.isArray(data) && data.length > 0) {
        const d = data.find(r => r.code === pageCode);
        if(d)
            return Array.isArray(d.operations) && d.operations.length > 0 && d.operations.some(item => item === name);
    }
    return Array.isArray(data) && data.length > 0 && data.some(item => item === name);
};

export const hasOptions = (data, name) => {
    return Array.isArray(data) && data.length > 0 && data.some(item => item === name);
};

/**
 * 根据value获取指定枚举值对应的文本
 * @param {Object} enumData 枚举对象
 * @param {number} value 枚举值
 */
export const conventEnumValueToString = (enumData, value) => {
    if(typeof value === 'undefined' || value === null)
        return null;
    return enumData.has(value) && enumData.properties[value].getText('zh-CN');
};
