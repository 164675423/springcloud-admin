import React from 'react';
import PropTypes from 'prop-types';
import {Card, Button, Row, Col, Form, Spin, Select} from 'antd';
import TextInput from 'component/ui/TextInput.jsx';
import {userRoute} from 'util/route';
import Return from 'component/ui/Return.jsx';
import {FORM_OPTIONS} from '../../constants';
import {getDetail} from './api';
import {newOrUpdateValidator} from './validator';
import PageWrapper from 'component/page-wrapper';
const Option = Select.Option;
const EMPTY_ARRAY = [];
const FormItem = Form.Item;
export class NewPanel extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            isValidate: false,
            hasOperat: false,
            loading: false,
            data: {},
        };
        this.handelRolesChange = this.handelRolesChange.bind(this);
    }

    componentDidMount() {
        if(this.props.id)
            this.getDetailInfo();
    }

    componentWillUnmount() {
        this.isunmount = true;
    }

    getDetailInfo = () => {
        // eslint-disable-next-line react/prop-types
        const id = this.props.id;
        this.setState({loading: true});
        getDetail(id)
            .then(res => {
                if(res.ok)
                    this.setState({
                        loading: false,
                        data: Object.assign({}, res.data, {
                            roles: res.data.roles.map(role => role.id)
                        }),
                    });
                else
                    this.setState(({
                        loading: false
                    }));
            });
    }
    handelRolesChange(values) {
        const data = Object.assign({}, this.state.data, {
            roles: values
        });
        this.setState({
            data,
            hasOperat: true
        });
    }
    onChange = (value, name) => {
        const data = Object.assign({}, this.state.data, {
            [name]: value
        });
        this.setState({
            data,
            hasOperat: true
        });
    }

    onSubmit = () => {
        this.setState({
            loading: true
        });
        const data = this.state.data;
        const isValid = newOrUpdateValidator(data);
        if(!isValid) {
            this.setState({
                isValidate: true,
                loading: false
            });
            return;
        }
        this.props.onSubmit(data)
            .then(res => {
                if(res.success)
                    this.props.successReturn();
                if(!this.isunmount)
                    this.setState({loading: false});
            });
    }
    onClickReturnBtn = () => {
        this.props.onCancel();
    }
    render() {
        const {data, isValidate, loading, hasOperat} = this.state;
        const {roles} = this.props.initEnumData;
        
        const roleOptions = roles.map(role => <Option key={role.id}>{role.name}</Option>);
        return (
            <div>
                <Spin spinning={loading}>
                    <Form className="form-standard">
                        <Card title="用户信息">
                            <Row>
                                <Col {...FORM_OPTIONS.col}>
                                    <FormItem label="姓名" {...FORM_OPTIONS.item} validateStatus={isValidate && !data.name ? 'error' : null} required >
                                        <TextInput
                                            name="name"
                                            value={data.name}
                                            onBlur={this.onChange}/>
                                    </FormItem>
                                </Col>
                            </Row>
                            <Row>
                                <Col {...FORM_OPTIONS.col}>
                                    <FormItem label="用户名"{...FORM_OPTIONS.item} validateStatus={isValidate && !data.username ? 'error' : null} required >
                                        {data.id ? data.username : <TextInput
                                            name="username"
                                            value={data.username}
                                            onBlur={this.onChange}/>}
                                    </FormItem>
                                </Col>
                            </Row>
                            {!data.id && <Row>
                                <Col {...FORM_OPTIONS.col}>
                                    <FormItem label="密码"{...FORM_OPTIONS.item} validateStatus={isValidate && !data.password ? 'error' : null} required >
                                        <TextInput type="password"
                                            name="password"
                                            value={data.password}
                                            onBlur={this.onChange}/>
                                    </FormItem>
                                </Col>
                            </Row>}
                            {!data.id && <Row>
                                <Col {...FORM_OPTIONS.col}>
                                    <FormItem label="确认密码"{...FORM_OPTIONS.item} validateStatus={isValidate && !data.confirmPassword ? 'error' : null} required >
                                        <TextInput type="password"
                                            name="confirmPassword"
                                            value={data.confirmPassword}
                                            onBlur={this.onChange}/>
                                    </FormItem>
                                </Col>
                            </Row>}
                            <Row>
                                <Col {...FORM_OPTIONS.col}>
                                    <FormItem label="角色"{...FORM_OPTIONS.item} validateStatus={isValidate && !data.confirmPassword ? 'error' : null} required >
                                        <Select mode="multiple" optionFilterProp="children" value={data.roles || EMPTY_ARRAY} onChange={this.handelRolesChange}>
                                            {roleOptions}
                                        </Select>
                                    </FormItem>
                                </Col>
                            </Row>
                        </Card>
                        
                    </Form>
                    <Card>
                        <Row>
                            <Col className="col-align-right">
                                <Button key="audit"
                                    onClick={this.onSubmit}
                                    type="primary"
                                    loading={loading}>
                                    保存</Button>
                            </Col>
                        </Row>
                    </Card>
                </Spin>
                <div className="page-toolbar">
                    <Return
                        onConfirm={this.onClickReturnBtn}
                        disabled={loading}
                        showWarning={hasOperat} />
                </div>
            </div>
        );
    }
}

NewPanel.propTypes = {
    onCancel: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired,
};

import {connect} from 'react-redux';
import {tableSearch, onSubmit} from './actions';

const getinitEnumData = state => {
    const initData = state.user.initData;
    const roles = initData.roles ? initData.roles.map(item => ({
        name: item.name,
        id: item.id
    })) : [];
    return {
        roles
    };
};

const mapStateToProps = state => ({
    initEnumData: getinitEnumData(state)
});

const mapDispatchToProps = (dispatch, props) => ({
    onSubmit: data => dispatch(onSubmit(data)),
    successReturn: () => {
        dispatch(tableSearch());
        props.history.push(userRoute.list);
    },
    onCancel: () => {
        props.history.push(userRoute.list);
    },
});
export default connect(mapStateToProps, mapDispatchToProps)(NewPanel);
