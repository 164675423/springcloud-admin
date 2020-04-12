import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {Popconfirm} from 'antd';

class WrappedPopconfirm extends PureComponent {
    constructor(props) {
        super(props);
        this.onConfirm = this.onConfirm.bind(this);
    }

    onConfirm(e) {
        e.stopPropagation();
        this.props.onConfirm(this.props.id);
    }

    render() {
        const {id, children, onConfirm, ...rest} = this.props;
        return (
            <Popconfirm onConfirm={this.onConfirm} {...rest}>
                {children}
            </Popconfirm>
        );
    }
}
WrappedPopconfirm.propTypes = {
    id: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number
    ]).isRequired,
    onConfirm: PropTypes.func.isRequired,
    children: PropTypes.node
};

export default WrappedPopconfirm;
