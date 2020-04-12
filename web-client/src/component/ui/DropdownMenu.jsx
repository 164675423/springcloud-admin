import React from 'react';
import PropTypes from 'prop-types';
import {Menu, Dropdown, Icon, Divider} from 'antd';
import './DropdownMenu.css';

const mockRef = {setMenuVisible: () => {}};

class DropdownMenu extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {visible: false};
        this.handleClick = this.handleClick.bind(this);
        this.handlePrimaryMenu = this.handlePrimaryMenu.bind(this);
        this.handleVisibleChange = this.handleVisibleChange.bind(this);
        this.publicRef = {setMenuVisible: this.handleVisibleChange};
    }

    componentWillUnmount() {
        this.unmount = true;
    }

    handleClick(event) {
        const item = this.props.menus.find(item => item.id === event.key);
        if(item && typeof item.onClick === 'function') {
            const {menus, hiddenIfEmpty, dropdown, ...other} = this.props;
            item.onClick(event.key, other);
        }
    }

    handlePrimaryMenu(event) {
        const item = this.props.menus.find(item => item.id === event.target.dataset.id);
        if(item && typeof item.onClick === 'function') {
            const {className, menus, hiddenIfEmpty, dropdown, ...other} = this.props;
            item.onClick(event.target.dataset.id, other);
        }
    }

    handleVisibleChange(visible) {
        if(!this.unmount)
            this.setState({ visible });
    }

    render() {
        const items = this.props.menus.filter(item => !item.hidden);
        const showAll = this.props.primaryLength + 1 >= items.length;
        const primary = [];
        const menu = [];
        items.forEach((item, index) => {
            if(showAll || this.props.primaryLength > index) {
                if(primary.length > 0)
                    primary.push(<Divider key={index} type="vertical"/>);

                if(typeof item.children === 'string')
                    primary.push(<a key={item.id} data-id={item.id} className="ant-dropdown-link" disabled={item.disabled} onClick={this.handlePrimaryMenu}>{item.children}</a>);
                else if(typeof item.children === 'function')
                    primary.push(item.children(mockRef));
                else
                    primary.push(item.children);
            } else {
                menu.push(<Menu.Item key={item.id} disabled={item.disabled}>
                    {typeof item.children === 'function' ? item.children(this.publicRef) : item.children}
                </Menu.Item>);
            }
        });

        const {overlay, children, onVisibleChange, visible, ...dropdown} = this.props.dropdown;
        const menuTitle = children ||
            (menu.length > 0 && <a key="down">更多<Icon type="down"/></a>) ||
            (!this.props.hiddenIfEmpty && <a key="down" disabled>更多<Icon type="down"/></a>);

        return (
            <span className={this.props.className}>
                {primary}
                {primary.length > 0 && (menu.length > 0 || !this.props.hiddenIfEmpty) && <Divider key="divider" type="vertical"/>}
                {menu.length > 0
                    ? <Dropdown key="menu" {...dropdown}
                        overlay={<Menu onClick={this.handleClick}>{menu}</Menu>}
                        onVisibleChange={onVisibleChange || this.handleVisibleChange}
                        visible={visible || this.state.visible}>
                        {menuTitle}
                    </Dropdown>
                    : menuTitle}
            </span>
        );
    }
}

DropdownMenu.propTypes = {
    /** 需要展示的menu */
    menus: PropTypes.arrayOf(PropTypes.shape({
        /** 唯一性标识，会在`onClick()`回调函数中使用。 */
        id: PropTypes.string.isRequired,
        /** 按钮展示信息，string 或 node */
        children: PropTypes.oneOfType([PropTypes.string, PropTypes.node, PropTypes.func]).isRequired,
        /** 设置为隐藏 */
        hidden: PropTypes.bool,
        /** 设置为不可用 */
        disabled: PropTypes.bool,
        /**
         * 点击时会调用该方法。
         * 参数：
         * `id` menu标识
         * `other` DropdownMenu中传入的所有自定义参数
         */
        onClick: PropTypes.func
    })).isRequired,
    /** 需要显示展示的操作数 */
    primaryLength: PropTypes.number.isRequired,
    /** Dropdown 相关配置，参见 antd*/
    dropdown: PropTypes.object,
    /** 不存在隐藏菜单时，图标是否隐藏，default：true*/
    hiddenIfEmpty: PropTypes.bool,
    className: PropTypes.string
};

DropdownMenu.defaultProps = {
    primaryLength: 1,
    hiddenIfEmpty: true,
    dropdown: {
        trigger: ['click'],
        placement: 'bottomRight'
    }
};

export default DropdownMenu;
