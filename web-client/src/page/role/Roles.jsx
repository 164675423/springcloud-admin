import React from 'react';
import PropTypes from 'prop-types';
import {Table, Card, Tooltip, Button, Popconfirm, Input, Icon, Spin, Divider} from 'antd';
import {roleRoute} from 'util/route';
import * as actions from './actions';
import {abandonRole, updateInfoData} from './api';
import {hasPermissions, hasOptions} from 'util/utils';
import styles from './Roles.css';
import {Link} from 'react-router-dom';
import {ROLE_PERMISSION} from './constants';
import TextInput from 'component/ui/TextInput.jsx';

class WrapPopconfirmComp extends React.PureComponent {
    constructor(props) {
        super(props);
        this.onConfirm = this.onConfirm.bind(this);
    }

    onConfirm(e) {
        e.stopPropagation();
        this.props.onConfirm(this.props.id);
    }

    render() {
        return (
            <Popconfirm
                title="是否作废"
                onConfirm={this.onConfirm}
                okText="是"
                cancelText="否">
                <a href="#">作废
                </a>
            </Popconfirm>
        );
    }
}

WrapPopconfirmComp.propTypes = {
    id: PropTypes.string.isRequired,
    onConfirm: PropTypes.func.isRequired,
    intl: PropTypes.object
};
const WrapPopconfirm = WrapPopconfirmComp;
export class EditableCellComp extends React.PureComponent {
    static getDerivedStateFromProps(nextProps, prevState) {
        if(prevState.prevValue !== nextProps.value)
            return {
                value: nextProps.value,
                prevValue: nextProps.value
            };
        return null;
    }

    constructor(props) {
        super(props);
        this.state = {
            value: props.value,
            defaultValue: '',
            editable: props.editable || false
        };
        this.handleChange = this.handleChange.bind(this);
        this.check = this.check.bind(this);
        this.edit = this.edit.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    handleChange(e) {
        const value = e.target.value;
        this.setState({
            value
        });
    }

    check() {
        const value = this.state.defaultValue;
        this.setState({
            editable: false,
            value
        });
    }

    edit() {
        const defaultValue = this.state.value;
        this.setState({
            editable: true,
            defaultValue
        });
    }

    onSubmit() {
        if(this.props.id) {
            this.props.onOk(this.props.id, this.state.value);
            this.setState({
                editable: false,
            });
        } else
            this.setState({
                editable: false
            });
    }

    render() {
        const {value, editable} = this.state;
        return (
            <div className={styles.editCell}>
                {editable ? (
                    <div className="editable-cell-input-wrapper">
                        <Input style={{width: '80%'}} value={value} onChange={this.handleChange} className={styles.editInput} onPressEnter={this.onSubmit} />
                        <Popconfirm
                            title="确定编辑?"
                            onConfirm={this.onSubmit}
                            okText="是"
                            cancelText="否">
                            <Icon type="check" style={{marginLeft: '10px'}} className={`${styles.editIcon} editable-cell-icon-check`} />
                        </Popconfirm>
                        <Tooltip
                            title="点击取消编辑">
                            <Icon type="close" style={{marginLeft: '10px'}} onClick={this.check} className={`${styles.editIcon}  editable-cell-icon-check`} />
                        </Tooltip>
                    </div>
                ) : (
                    <Tooltip
                        title="点击角色名可进行编辑"
                        placement="topLeft">
                        <div className="editable-cell-text-wrapper" onClick={this.edit}>
                            {value || ' '}
                            <Icon type="edit" className={`${styles.editIcon} editable-cell-close`} />
                        </div>
                    </Tooltip>
                )}
            </div>
        );
    }
}
EditableCellComp.propTypes = {
    editable: PropTypes.bool,
    intl: PropTypes.object,
    id: PropTypes.string,
    value: PropTypes.string,
    onChange: PropTypes.func,
    onOk: PropTypes.func
};
const EditableCell = EditableCellComp;
import {getPermissionStorage} from 'util/storege';
export class Roles extends React.PureComponent {
    state = {
        loading: false,
        permission: getPermissionStorage()
    };

    onClickAddBtn = () => {
        this.props.history.push(roleRoute.add);
    };
    onClickAbandon = id => {
        this.setState({
            loading: true
        });
        const roles = this.props.roles.data;
        const record = roles.find(d => d.id === id);
        abandonRole(id, {rowVersion: record.rowVersion})
            .then(res => {
                this.setState({loading: false});
                if(res.ok)
                    this.props.refreshListData();
            });
    };
    onModifyRoleInfo = (id, name) => {
        this.setState({
            loading: true
        });
        const roles = this.props.roles.data;
        const record = roles.find(d => d.id === id);
        updateInfoData(id, {rowVersion: record.rowVersion, name: name})
            .then(res => {
                this.setState({loading: false});
                if(res.ok)
                    this.props.refreshListData();
            });
    };

    render() {
        const {data, isFetching} = this.props.roles;
        const {permission} = this.state;
        const columns = [
            {
                title: '角色',
                dataIndex: 'name',
                width: '40%',
                render: (t, r) =>
                    hasPermissions(PAGE_CODE, permission, ROLE_PERMISSION.update) && hasOptions(r.options, ROLE_PERMISSION.update) ? (
                        <EditableCell value={t} id={r.id} onOk={this.onModifyRoleInfo} />
                    ) : (
                        t
                    )
            },
            {
                title: '操作',
                dataIndex: 'action',
                width: '30%',
                render: (t, r) => (
                    <span>
                        <Link to={`${roleRoute.detail}/${r.id}`}>详情</Link>
                        {hasPermissions(PAGE_CODE, permission, ROLE_PERMISSION.update) && hasOptions(r.options, ROLE_PERMISSION.update) && (
                            <span>
                                <Divider type="vertical" />
                                <Link to={`${roleRoute.update}/${r.id}`}>编辑</Link>
                            </span>
                        )}
                        {hasPermissions(PAGE_CODE, permission, ROLE_PERMISSION.abandon) && hasOptions(r.options, ROLE_PERMISSION.abandon) && (
                            <span>
                                <Divider type="vertical" />
                                <WrapPopconfirm onConfirm={this.onClickAbandon} id={r.id} />
                            </span>
                        )}
                    </span>
                )
            }
        ];
        return (
            <div>
                {hasPermissions(PAGE_CODE, permission, ROLE_PERMISSION.add) && (
                    <div className="page-toolbar">
                        <Button type="primary" onClick={this.onClickAddBtn}>
                            新增角色
                        </Button>
                    </div>
                )}
                <Spin spinning={isFetching || this.state.loading}>
                    <Card>
                        <Table className="ellipsis" rowKey="id" pagination={false} bordered columns={columns} dataSource={data} />
                    </Card>
                </Spin>
            </div>
        );
    }
}
Roles.propTypes = {
    history: PropTypes.object
};
import {connect} from 'react-redux';
import {PAGE_CODE} from 'page/user/constants';
import {getInitData} from './actions.js';

const mapStateToProps = state => ({
    roles: state.role.list,
    loading: state.user.list.isFetching,
});
const mapDispatchToProps = dispatch => ({
    refreshListData: () => dispatch(getInitData())
});
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Roles);
