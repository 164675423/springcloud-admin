/* eslint-disable react/prop-types */
import React from 'react';
import PropTypes from 'prop-types';
import {Card, Row, Col, Switch, Button, Spin} from 'antd';
import PageTree from './PageTree.jsx';
import PagePanel from './PagePanel.jsx';
import {Page} from 'component/common/Page.jsx';
import {roleRoute} from 'util/route';
import {hasPermissions} from 'util/utils';
import styles from './Roles.css';
import {ROLE_PERMISSION} from './constants';
import TextInput from 'component/ui/TextInput.jsx';
import {getPermissionStorage} from 'util/storege';
import {updateData, addData} from './api';

export class Role extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            selected: '',
            selectedViewVisible: false,
            spinning: true,
            permission: getPermissionStorage()
        };
        this.onPageSelected = this.onPageSelected.bind(this);
        this.onEditClick = this.onEditClick.bind(this);
        this.onSubmitClick = this.onSubmitClick.bind(this);
        this.onClickReturnBtn = this.onClickReturnBtn.bind(this);
    }

    componentDidMount() {
        if(this.props.id) {
            this.props.init().then(() =>
                this.setState({
                    spinning: false
                })
            );
        }else{
            this.props.resetRoleDetail();
            this.setState({
                spinning: false
            });
        }
    }

    onPageSelected(selected) {
        this.setState({
            selected
        });
    }

    onEditClick() {
        this.props.history.push(`${roleRoute.update}/${this.props.role.id}`);
    }

    onSubmitClick() {
        this.setState({
            spinning: true
        });
        const {role, pages, operations} = this.props;
        if(role && role.id) {
            const updateCondition = {
                name: role.name,
                pages: pages,
                operations: operations
            };
            updateData(role.id, updateCondition)
                .then(res => {
                    this.setState({spinning: false});
                    if(res.ok) {
                        this.props.history.push(roleRoute.list);
                        this.props.refreshListData();
                    }
                });
        }else{
            const addCondition = {
                name: role.name,
                pages: pages,
                operations: operations
            };
            addData(addCondition)
                .then(res => {
                    this.setState({spinning: false});
                    if(res.ok) {
                        this.props.history.push(roleRoute.list);

                        this.props.refreshListData();
                    }
                });
        }
    }

    onClickReturnBtn() {
        this.props.history.push(roleRoute.list);
    }

    render() {
        const {permission} = this.state;
        return (
            <div className={styles.detail}>
                <div className="page-toolbar">
                    <Button type="primary" onClick={this.onClickReturnBtn}>
                        返回
                    </Button>
                </div>
                <Spin spinning={this.state.spinning}>
                    <Card>
                        <Row gutter={16}>
                            <Col span={12}>
                                {this.props.readonly ? (
                                    <h3>
                                        角色名:
                                        {this.props.role.name}
                                    </h3>
                                ) : (
                                    <TextInput
                                        className="required"
                                        autoFocus={!this.props.role.name}
                                        placeholder="请输入角色名称"
                                        id="name"
                                        value={this.props.role.name}
                                        onBlur={this.props.onRoleChange}/>
                                )}
                            </Col>
                            <Col span={12} className="col-align-right">
                                {!this.props.readonly && (
                                    <span>
                                        <Button disabled={!this.props.role.name} type="primary" onClick={this.onSubmitClick}>
                                            提交
                                        </Button>
                                    </span>
                                )}
                                {this.props.editable && hasPermissions(PAGE_CODE, permission, ROLE_PERMISSION.update) && <Button onClick={this.onEditClick}>
                                        编辑
                                </Button>}
                            </Col>
                        </Row>
                    </Card>
                    {this.props.readonly ? (
                        <Card styles={{height: 'calc(100vh - 237px)'}}>
                            <Page
                                mode="Card"
                                page={{
                                    items: this.props.role.permissions
                                }}/>
                        </Card>
                    ) : (
                        <Row gutter={4}>
                            <Col md={10} lg={8} xl={6}>
                                <Card style={{height: 'calc(100vh - 237px)'}} className={styles.pageOverflow}>
                                    <PageTree currentKey={this.state.selected} onSelect={this.onPageSelected} />
                                </Card>
                            </Col>
                            <Col md={14} lg={16} xl={18}>
                                <PagePanel
                                    className={styles.pageOverflow}
                                    id={this.state.selected}
                                    maxLevel={this.props.maxLevel}
                                    onSelect={this.onPageSelected}/>
                            </Col>
                        </Row>
                    )}
                </Spin>
            </div>
        );
    }
}
Role.propTypes = {
    editable: PropTypes.bool.isRequired,
    init: PropTypes.func.isRequired,
    readonly: PropTypes.bool.isRequired,
    role: PropTypes.object.isRequired,
    history: PropTypes.object,
    onResetClick: PropTypes.func,
    onRoleChange: PropTypes.func,
};
Role.defaultProps = {
    readonly: true,
    maxLevel: 0,
    editable: false,
    role: {},
    displayMode: 'Card'
};
import {connect} from 'react-redux';
import {getRoleById, roleChange, resetRoleDetail, onSubmit, getInitData} from './actions';
import {PAGE_CODE} from 'page/user/constants.js';

const mapStateToProps = (state, ownProps) => {
    const role = state.role.roleDetail.data;
    return {
        readonly: ownProps.readonly,
        role: role,
        loading: state.role.roleDetail.isFetching,
        editable: ownProps.id && ownProps.readonly && role.options && role.options.some(item => item === ROLE_PERMISSION.update),
        pages: state.role.roleDetail.rolePages,
        operations: state.role.roleDetail.roleOperations,
    };
};

const mapDispatchToProps = (dispatch, ownProps) => ({
    init: () => ownProps.id ? dispatch(getRoleById(ownProps.id)) : Promise.resolve(),
    resetRoleDetail: () => dispatch(resetRoleDetail()),
    onRoleChange: (value, id) => dispatch(roleChange(value, id)),
    onSubmitClick: () => dispatch(onSubmit()),
    refreshListData: () => dispatch(getInitData())
});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Role);
