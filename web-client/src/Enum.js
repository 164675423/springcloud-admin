import {EnumItem, Enum} from './util/enumType';
const CN = 'zh-CN';
export const baseDataStatus = Object.freeze({
    __proto__: Enum,
    生效: 1,
    作废: -1,
    properties: Object.freeze({
        '1': Object.freeze({
            __proto__: EnumItem,
            [CN]: '生效',
        }),
        '-1': Object.freeze({
            __proto__: EnumItem,
            [CN]: '作废',
        })
    })
});
