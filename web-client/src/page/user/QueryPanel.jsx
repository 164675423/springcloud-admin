// eslint-disable-next-line react/prop-types
import React from 'react';
import {Button, Form, Row, Col} from 'antd';
import TextInput from 'component/ui/TextInput.jsx';
import WrappedSelect from 'component/ui/WrappedSelect.jsx';
import {PERMISSION, PAGE_CODE} from './constants';
import {FORM_OPTIONS} from '../../constants';
import {baseDataStatus} from '../../Enum';
import {userRoute} from 'util/route';
import PropTypes from 'prop-types';
import PageWrapper from 'component/page-wrapper';
import {Link} from 'react-router-dom';
import {getPermissionStorage} from 'util/storege';
import {hasPermissions} from 'util/utils';

const baseDataStatusList = baseDataStatus.toList();
const FormItem = Form.Item;
class QueryPanel extends React.PureComponent {
    state = {
        permission: getPermissionStorage()
    }
  handleFilterChange = (value, name) => {
      this.props.onConditionsChange(name, value);
  }

handleFilterPressEnter = (value, name) => {
    this.props.onConditionsChange(name, value);
    this.props.querySearch();
}

render() {
    const {permission} = this.state;
    const addable = hasPermissions(PAGE_CODE, permission, PERMISSION.update);
    const {roles} = this.props.initEnumData;
    return (
        <div>
            <Form className="form-standard">
                <Row>
                    <Col {...FORM_OPTIONS.col}>
                        <FormItem label={'姓名'} {...FORM_OPTIONS.item}>
                            <TextInput
                                name="name"
                                value={this.props.conditions.name}
                                onPressEnter={this.handleFilterPressEnter}
                                onChange={this.handleFilterChange}/>
                        </FormItem>
                    </Col>
                    <Col {...FORM_OPTIONS.col}>
                        <FormItem label="角色" {...FORM_OPTIONS.item}>
                            <WrappedSelect
                                allowClear
                                name="roleId"
                                value={this.props.conditions.roleId}
                                options={roles}
                                onChange={this.handleFilterChange}/>
                        </FormItem>
                    </Col>
                    <Col {...FORM_OPTIONS.col}>
                        <FormItem label={'状态'} {...FORM_OPTIONS.item}>
                            <WrappedSelect
                                id="status"
                                allowClear={true}
                                value={this.props.conditions.status}
                                onChange={this.handleFilterChange}
                                options={baseDataStatusList} />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span={6}>
                        <Button key="query" type="primary" onClick={this.props.querySearch} loading={this.props.loading}>查询</Button>
                        <Button key="clear" onClick={this.props.onResetClick}>重置</Button>
                    </Col>
                    <Col span={18} className="col-align-right">
                        {addable && <Link to={`${userRoute.add}`}>
                            <Button key="add" type="primary">新增</Button>
                        </Link>}
                    </Col>
                </Row>
            </Form>
        </div>
    );
}
}

QueryPanel.propTypes = {
    conditions: PropTypes.object.isRequired,
    loading: PropTypes.bool.isRequired,
    querySearch: PropTypes.func.isRequired,
    onConditionsChange: PropTypes.func.isRequired,
    onResetClick: PropTypes.func.isRequired,
};

import {connect} from 'react-redux';
import {saveQueryCondition, querySearch, resetQueryPanel} from './actions';

const getinitEnumData = state => {
    const initData = state.user.initData;
    const roles = initData.roles ? initData.roles.map(item => ({
        text: item.name,
        value: item.id
    })) : [];
    return {
        roles
    };
};

const mapStateToProps = state => ({
    conditions: state.user.queryCondition,
    loading: state.user.list.isFetching,
    initEnumData: getinitEnumData(state)
});

const mapDispatchToProps = dispatch => ({
    onConditionsChange: (name, value) => dispatch(saveQueryCondition(name, value)),
    querySearch: () => dispatch(querySearch()),
    onResetClick: () => dispatch(resetQueryPanel()),
});
export default connect(mapStateToProps, mapDispatchToProps)(QueryPanel);
