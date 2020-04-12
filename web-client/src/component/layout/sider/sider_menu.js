import React from 'react';
import PropTypes from 'prop-types';
import {Menu, Icon, Layout as Sider} from 'antd';
import {Link} from 'react-router-dom';

import reducer from 'page/reducer';
import {getPageStorage} from 'util/storege';

const SubMenu = Menu.SubMenu;

class SiderMenu extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            openKey: '/',
            selectedKey: '/',
            page: getPageStorage()
        };
        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick(item, key, keyPath) {
        if(item.key !== this.props.currentPageCode) {
            //切换菜单时，重置store
            // this.props.onResetClick();
        }
    }

  renderMenu = data => {
      return data.map(item => {
          if (item.children && item.children.length > 0) {
              return (
                  <SubMenu
                      key={item.code}
                      title={<span>
                          {item.icon ? <Icon type={item.icon} /> : ''}
                          <span>{item.name}</span>
                      </span>}>
                      {this.renderMenu(item.children)}
                  </SubMenu>
              );
          }
          return (
              <Menu.Item key={item.code}>
                  <Link to={item.url}>
                      {item.icon ? <Icon type={item.icon} /> : ''}
                      <span>{item.name}</span>
                  </Link>
              </Menu.Item>
          );
      });
  }
  render() {
      const {selectedKey, page} = this.state;
      const currentPageCode = this.props.currentPageCode;
      const pages = page.pages || [];
      const reversePages = page.reversePages || [];
      const curent = reversePages.find(item => item.code === currentPageCode) || {};
      const parents = curent.parents || [];
      const openKeys = parents.map(page => page.code) || [];
      if(!pages.some(item => item.code === '/')) {
          const home = {
              code: '/',
              name: '首页',
              url: '/',
              icon: 'home'
          };
          pages.splice(0, 0, home);
      }
      return (
          <Menu
              theme="dark"
              //   openKeys={openKeys}
              selectedKeys={currentPageCode}
              mode="inline"
              onClick={item => this.handleMenuClick(item, currentPageCode)}>
              {this.renderMenu(pages)}
          </Menu>
      );
  }
}

SiderMenu.propTypes = {
    location: PropTypes.object,
    onResetClick: PropTypes.func.isRequired,
    currentPageCode: PropTypes.string,
    resetStore: PropTypes.func
};

import {connect} from 'react-redux';
import {resetStore} from './action';
const mapStateToProps = state => ({
    currentPageCode: state.home.currentPageCode,
});
const mapDispatchToProps = dispatch => ({
    onResetClick: () => dispatch(resetStore()),
});

export default connect(mapStateToProps, mapDispatchToProps)(SiderMenu);
