// 登录页面
import React from 'react';
import PropTypes from 'prop-types';
import {Form, Icon, Input, Button} from 'antd';
import style from './style.scss';
import {requestUserLogin, requestUserPages, requestUserOperations} from 'service/user-service.js';
import {getUserDataStorage, saveUserDataStorage, savePageStorage, savePermissionStorage} from 'util/storege';
import {emitUserDataEvent} from 'util/custom-event';
const FormItem = Form.Item;
const userData = getUserDataStorage();
import axios from 'axios';

class LoginPage extends React.PureComponent {
    constructor(props) {
        super(props);
        this.usernameInputRef = React.createRef();
        this.passwordInputRef = React.createRef();
    }
  state = {
      loginTxt: '登录',
      cantLogin: false
  }
  componentDidMount() {
      document.title = '管理系统';
      this.focusInput();
  }

  focusInput = () => {
      if (userData) {
          const passwordInputNode = this.passwordInputRef.current;
          passwordInputNode.focus();
      } else {
          const userNameInputNode = this.usernameInputRef.current;
          userNameInputNode.focus();
      }
  }

  handleSubmit = e => {
      e.preventDefault();

      this.props.form.validateFields((err, values) => {
          if (!err) {
              this.setState({
                  loginTxt: 'login, please wait…',
                  cantLogin: true
              });
              const {userName, password} = values;
              requestUserLogin(userName, password).then(res => {
                  if (res.ok) {
                      axios.all([requestUserPages(), requestUserOperations()])
                          .then(axios.spread((res1, res2) => {
                              // Both requests are now complete
                              if(res1.ok && res2.ok) {
                                  this.setState({
                                      loginTxt: '登录',
                                      cantLogin: false
                                  });
                                  saveUserDataStorage(res.data);
                                  savePageStorage(res1.data);
                                  savePermissionStorage(res2.data);
                                  emitUserDataEvent('login');
                              }else{
                                  this.setState({
                                      loginTxt: '登录',
                                      cantLogin: false
                                  });
                              }
                          }));
                  }else{
                      this.setState({
                          loginTxt: '登录',
                          cantLogin: false
                      });
                  }
              });
          }
      });
  }

  render() {
      const {getFieldDecorator} = this.props.form;
      const {loginTxt, cantLogin} = this.state;
      return (
          <div className={style.container}>

              <Form onSubmit={this.handleSubmit} className="form-container">

                  <FormItem>
                      {getFieldDecorator('userName', {
                          rules: [{required: true, message: '请输入账号!'}],
                          initialValue: userData ? userData.username : ''
                      })(
                          <Input
                              className="form-input"
                              prefix={<Icon type="user" />}
                              placeholder="请输入账号"
                              onChange={this.userNameChange}
                              ref={this.usernameInputRef}/>
                      )}
                  </FormItem>

                  <FormItem>
                      {getFieldDecorator('password', {
                          rules: [{required: true, message: '请输入密码!'}],
                      })(
                          <Input
                              className="form-input"
                              prefix={<Icon type="lock" />}
                              type="password"
                              placeholder="请输入密码"
                              ref={this.passwordInputRef}/>
                      )}
                  </FormItem>

                  <Button
                      disabled={cantLogin}
                      type="primary"
                      htmlType="submit"
                      className="form-button">
                      {loginTxt}
                  </Button>
              </Form>
          </div>
      );
  }
}

LoginPage.propTypes = {
    form: PropTypes.object.isRequired,
};

export default Form.create()(LoginPage);
