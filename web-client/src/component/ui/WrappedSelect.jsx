/* eslint-disable no-unused-vars */
import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {Select} from 'antd';

const Option = Select.Option;

class WrappedSelect extends PureComponent {
    onChange = value => {
        this.props.onChange(value, this.props.name || this.props.id);
    }
    render() {
        const {defaultOption, options, name, onChange, id, ...rest} = this.props;
        const defaultSelectOption = defaultOption
            ? <Option key={defaultOption.value} value={defaultOption.value}>{defaultOption.text}</Option>
            : null;
        const selectOptions = options.map(o => <Option key={o.value} value={o.value}>{o.text}</Option>);
        return (
            <Select {...rest} onChange={this.onChange}>
                {
                    options.length > 0
                        ? selectOptions
                        : defaultSelectOption
                }
            </Select>
        );
    }
}

WrappedSelect.propTypes = {
    defaultOption: PropTypes.object,
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    name: PropTypes.string,
    options: PropTypes.arrayOf(PropTypes.shape({
        text: PropTypes.node,
        value: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
    })),
    value: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number,
        PropTypes.arrayOf(PropTypes.string),
        PropTypes.arrayOf(PropTypes.number)
    ]),
    /**
     * @param value
     * @param name
     */
    onChange: PropTypes.func
};

export default WrappedSelect;
