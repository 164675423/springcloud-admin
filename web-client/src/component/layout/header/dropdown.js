import React from 'react';
import {Menu, Dropdown, message, Icon, Modal, Form, Row, Col} from 'antd';
import PropTypes from 'prop-types';
import {getUserDataStorage} from 'util/storege';
import {userLogout, changePassword} from 'service/user-service.js';
import {FORM_OPTIONS} from '../../../constants';
import TextInput from 'component/ui/TextInput.jsx';

class DropdownPresentation extends React.PureComponent {
    state = {
        showModifyPanel: false,
        data: {
            oldPassword: '',
            newPassword: '',
            confirmPassword: ''
        }
    }
    
  /**
   * 退出登录，删除本地存储的用户信息
   * @param {object} e event
   */
  handleLogout = async e => {
      e.preventDefault();
      try {
          const result = await userLogout();
          this.props.onLogout();
          message.success(result);
      } catch(error) {
          message.error(error);
      }
  }

  handleChangePassword = async e => {
      e.preventDefault();
      const{oldPassword, newPassword, confirmPassword} = this.state.data;
      if(oldPassword === '') {
          message.error('原密码必填');
          return;
      }
      if(newPassword === '' || confirmPassword === '' || newPassword !== confirmPassword) {
          message.error('请确认密码是否一致');
          return;
      }
      if(newPassword.length < 6) {
          message.error('密码最低为6位');
          return;
      }
      await changePassword(oldPassword, newPassword).then(res => {
          if(res.ok) {
              this.setState({
                  showModifyPanel: false
              });
              message.success('密码修改成功，请退出重新登录');
              //   userLogout();
              //   this.props.onLogout();
          }
      });
  }

  showPanel = () => {
      this.setState({
          showModifyPanel: true
      });
  }
  closePanel = () => {
      this.setState({
          showModifyPanel: false,
          data: {}
      });
  }
  onChange = (value, name) => {
      const data = Object.assign({}, this.state.data, {
          [name]: value
      });
      this.setState({
          data
      });
  }

  render() {
      const menu = (
          <Menu>
              <Menu.Item>
                  <span><Icon type="logout" /><a target="_blank" onClick={this.handleLogout}>退出登录</a></span>
              </Menu.Item>
              <Menu.Item>
                  <span><Icon type="edit" /><a target="_blank" onClick={this.showPanel}>修改密码</a></span>
              </Menu.Item>
          </Menu>
      );

      const userData = getUserDataStorage();
      const {showModifyPanel, data} = this.state;
      return (
          <div>
              <Dropdown overlay={menu}>
                  <div>
                      <span style={{marginRight: 10}}>
                          <Icon type="github" />
                      </span>
                      { userData.username ? userData.username : '请登录' }
             
                  </div>
              </Dropdown>
              <Modal title="修改密码"
                  visible={showModifyPanel}
                  onOk={this.handleChangePassword}
                  onCancel={this.closePanel}
                  okText="保存"
                  cancelText="取消"
                  maskClosable={false}>
                  <Form className="form-standard">
                      <Row>
                          <Col>
                              <Form.Item label="原密码"{...FORM_OPTIONS.item} required >
                                  <TextInput type="password"
                                      name="oldPassword"
                                      value={data.password}
                                      onBlur={this.onChange}/>
                              </Form.Item>
                          </Col>
                      </Row>
                      <Row>
                          <Col>
                              <Form.Item label="新密码"{...FORM_OPTIONS.item} required >
                                  <TextInput type="password"
                                      name="newPassword"
                                      value={data.newPassword}
                                      onBlur={this.onChange}/>
                              </Form.Item>
                          </Col>
                      </Row>
                      <Row>
                          <Col>
                              <Form.Item label="确认密码"{...FORM_OPTIONS.item} required >
                                  <TextInput type="password"
                                      name="confirmPassword"
                                      value={data.confirmPassword}
                                      onBlur={this.onChange}/>
                              </Form.Item>
                          </Col>
                      </Row>
                  </Form>
              </Modal>
          </div>
         
      );
  }
}
DropdownPresentation.propTypes = {
    onLogout: PropTypes.func.isRequired,
};

import {connect} from 'react-redux';
import {resetStore} from './action';
const mapDispatchToProps = dispatch => ({
    onLogout: () => dispatch(resetStore()),
});
export default connect(null, mapDispatchToProps)(DropdownPresentation);
