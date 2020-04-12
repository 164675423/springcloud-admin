import React from 'react';
import {Table, Tag} from 'antd';
import {Link} from 'react-router-dom';
import {TABLE, PAGINATION_OPTIONS, FIXED_COLUMN_WIDTH} from '../../constants';
import {PERMISSION, PAGE_CODE} from './constants';
import {hasPermissions, conventEnumValueToString, hasOptions} from 'util/utils';
import {userRoute} from 'util/route';
import DropdownMenu from 'component/ui/DropdownMenu.jsx';
import WrappedPopconfirm from 'component/ui/WrappedPopconfirm.jsx';
import {abandonUser} from './api';
import PropTypes from 'prop-types';
import styles from './styles.css';
import {getPermissionStorage} from 'util/storege';
import {baseDataStatus} from '../../Enum';
export class TablePanel extends React.PureComponent {
    constructor(props) {
        super(props);
        this.onChangeSort = this.onChangeSort.bind(this);
        this.state = {
            loading: false,
            permission: getPermissionStorage()
        };
    }
onChangeSort = (pagination, filters, sorter) => {
    this.props.onChangeSort({
        pageIndex: pagination.current - 1,
        pageSize: pagination.pageSize,
        sortedColumnKey: sorter.columnKey,
        sortedOrder: sorter.order,
    });
};
onClickAbandonBtn = id => {
    this.setState({loading: true});
    const record = this.props.data.find(d => d.id === id);
    abandonUser(id, {rowVersion: record.rowVersion})
        .then(res => {
            this.setState({loading: false});
            if(res.ok)
                this.props.refreshListData();
        });
}
render() {
    const {permission, loading} = this.state;
    const {pageIndex, pageSize, total} = this.props;
    
    const pagination = {
        total,
        pageSize,
        current: pageIndex + 1,
        ...PAGINATION_OPTIONS
    };

    const columns = [{
        title: '姓名',
        dataIndex: 'name',
    }, {
        title: '用户名',
        dataIndex: 'username',
    }, {
        title: '角色',
        dataIndex: 'roles',
        render: text =>
            text.map(role => (
                <Tag className={styles.tagDisplay} key={role.id}>
                    {role.name}
                </Tag>
            ))
    }, {
        title: '状态',
        dataIndex: 'status',
        render: text => conventEnumValueToString(baseDataStatus, text)
    }, {
        title: '操作',
        dataIndex: 'operate',
        width: FIXED_COLUMN_WIDTH,
        fixed: 'right',
        render: (t, r) => {
            const menus = [{
                id: PERMISSION.update,
                children: <Link key="update" to={`${userRoute.update}/${r.id}`}>编辑</Link>,
                hidden: !(hasPermissions(PAGE_CODE, permission, PERMISSION.update) && hasOptions(r.options, PERMISSION.update)),
            },
            {
                id: PERMISSION.abandon,
                children: (ref =>
                    <WrappedPopconfirm
                        key="abandon"
                        placement="topLeft"
                        onConfirm={this.onClickAbandonBtn}
                        onVisibleChange={ref.setMenuVisible}
                        id={r.id}
                        okText="确认"
                        cancelText="取消"
                        title="是否继续?">
                        <a>作废</a>
                    </WrappedPopconfirm>
                ),
                hidden: !(hasPermissions(PAGE_CODE, permission, PERMISSION.abandon) && hasOptions(r.options, PERMISSION.abandon)),
            }];
            return <DropdownMenu key={r.id} menus={menus} primaryLength={1} id={r.id} />;
        }
    }];
    return (
        <Table
            className={'white-space-nowrap'}
            columns={columns}
            dataSource={this.props.data}
            loading={this.props.loading || loading}
            onChange={this.onChangeSort}
            pagination={pagination}
            rowKey="id"
            {...TABLE}/>
    );
}
}
TablePanel.propTypes = {
    data: PropTypes.array.isRequired,
    loading: PropTypes.bool.isRequired,
    total: PropTypes.number.isRequired,
    onChangeSort: PropTypes.func.isRequired,
    pageIndex: PropTypes.number,
    pageSize: PropTypes.number,
    refreshListData: PropTypes.func,
    sortedColumnKey: PropTypes.string,
    sortedOrder: PropTypes.bool,
};

import {connect} from 'react-redux';
import {tableSearch} from './actions';
const mapStateToProps = state => ({
    data: state.user.list.data,
    loading: state.user.list.isFetching,
    total: state.user.list.total,
    pageIndex: state.user.queryCondition.pageIndex,
    pageSize: state.user.queryCondition.pageSize,
    sortedOrder: state.user.queryCondition.isDesc,
    sortedColumnKey: state.user.queryCondition.sortFiled,
});

const mapDispatchToProps = dispatch => ({
    onChangeSort: option => dispatch(tableSearch(option)),
    refreshListData: () => dispatch(tableSearch()),
});

export default connect(mapStateToProps, mapDispatchToProps)(TablePanel);
