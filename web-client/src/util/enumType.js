import {pickBy, pick as lodashPick} from 'lodash';

let languages = {
    'zh-CN': {
        name: {
            'zh-CN': '简体中文',
            'en-US': 'Simplified Chinese'
        },
        flag: 'cn'
    },
    'en-US': {
        name: {
            'zh-CN': '美式英语',
            'en-US': 'U.S. English'
        },
        flag: 'us'
    }
};
let defaultLanguage = 'zh-CN';
let locales = [];

// 枚举原型初始化管理器
class EnumManager {
    setLanguages(lang) {
        languages = lang;
        locales = Object.keys(languages);
    }
    setDefaultLanguage(dLang) {
        defaultLanguage = dLang;
    }
}

export const enumManager = new EnumManager();

// 定义枚举项原型
class EnumItemType {
    getText(lang) {
        return locales.indexOf(lang) > -1 ? this[lang] : this[defaultLanguage];
    }
}

export const EnumItem = new EnumItemType();

// 定义枚举原型
class EnumType {
    has(key) {
        return key !== null && key !== undefined && this.properties && this.properties[key] !== undefined;
    }

    map(callback, lang) {
        return this.toList(lang).map(callback);
    }

    toList(lang) {
        const list = [];
        if(this.properties)
            Object.getOwnPropertyNames(this).forEach(key => {
                if(key !== 'properties')
                    list.push({
                        text: this.properties[this[key]].getText(lang),
                        value: this[key]
                    });
            });
        return list;
    }

    pick(values, textSetter) {
        const predicate = (value, key) => key === 'properties' || values.some(v => v === value.value);

        const props = pickBy(Object.getOwnPropertyDescriptors(this), predicate);
        const properties = lodashPick(Object.getOwnPropertyDescriptors(this.properties), values);

        if(textSetter && typeof textSetter === 'function')
            values.forEach(key => {
                const text = this.properties[key];
                if(!text)
                    return;
                const newValue = textSetter(key, text);
                if(!newValue)
                    return;
                properties[key].value = newValue.__proto__ === EnumItem ? newValue : Object.create(EnumItem, Object.getOwnPropertyDescriptors(newValue));
            });

        props.properties.value = Object.create(Object.prototype, properties);

        return Object.create(Enum, props);
    }
}

export const Enum = new EnumType();
