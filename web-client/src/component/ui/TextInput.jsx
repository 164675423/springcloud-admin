/* eslint-disable no-unused-vars */
/* eslint-disable no-return-assign */
/* eslint-disable react/prop-types */
import React from 'react';
import PropTypes from 'prop-types';
import {Input} from 'antd';

//基于antd的文本输入框
export default class TextInput extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            value: this.props.defaultValue || this.props.value || ''
        };
        this.onChange = this.onChange.bind(this);
        this.onBlur = this.onBlur.bind(this);
        this.onPressEnter = this.onPressEnter.bind(this);
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        const prevValue = prevState.prevValue;
        if (prevValue !== nextProps.value) {
            return {
                value: nextProps.value,
                prevValue: nextProps.value
            };
        }
        return null;
    }

    focus() {
        this.input.focus();
    }
    onPressEnter(e) {
        const value = this.state.value && this.state.value.trim();
        if (typeof this.props.onPressEnter === 'function')
            this.props.onPressEnter(value, this.props.id || this.props.name, e);
    }

    onChange(e) {
        this.setState({
            value: e.target.value
        });
        if (typeof this.props.onChange === 'function')
            this.props.onChange(e.target.value, this.props.id || this.props.name, e);
    }
    onBlur(e) {
        const value = this.state.value && this.state.value.trim();
        if (typeof this.props.onBlur === 'function')
            this.props.onBlur(value, this.props.id || this.props.name, e);
    }
    render() {
        const {onChange, onBlur, onPressEnter, defaultValue, value, type, ...other} = this.props;
        let Component = null;
        if (type === 'textarea')
            Component = Input.TextArea;
        else {
            Component = Input;
            other.type = type;
        }
        return (
            <Component {...other}
                value={this.state.value}
                onChange={this.onChange}
                onBlur={this.onBlur}
                onPressEnter={this.onPressEnter}
                ref={input => this.input = input} />
        );
    }
}

TextInput.propTypes = {
    /** 控件的唯一性标识，会在`onChange()`回调函数中使用。 */
    id: PropTypes.string,
    /** 控件的表单名称，如果没有指定`id`属性，那么该值会在`onChange()`回调函数中使用。 */
    name: PropTypes.string,
    /** 控件的初始值。 uncontrolled component used */
    defaultValue: PropTypes.string,
    /** 控件的初始值。 controlled component used  */
    value: PropTypes.string,
    /**
     * 在文本框失去焦点时会调用该方法。
     * 参数：
     * `value (string)`：文本框中当前的值。
     * `id (string)`：控件的`id`或者`name`属性的值。
     * `event`：事件
     */
    onBlur: PropTypes.func,
    /**
     * 在文本框值有变化时会调用该方法。
     * 参数：
     * `value (string)`：文本框中当前的值。
     * `id (string)`：控件的`id`或者`name`属性的值。
     * `event`：事件
     */
    onChange: PropTypes.func,
    /**
     * 在文本框输入Enter时会调用该方法。
     * 参数：
     * `value (string)`：文本框中当前的值。
     * `id (string)`：控件的`id`或者`name`属性的值。
     * `event`：事件
     */
    onPressEnter: PropTypes.func
};
