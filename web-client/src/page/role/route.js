import React from 'react';
import {Switch, Route} from 'react-router-dom';
import {Card} from 'antd';
import {roleRoute} from 'util/route';
import ErrorPage from 'page/404/index';
import PropTypes from 'prop-types';
import {PAGE_CODE} from './constants';
import Roles from './Roles.jsx';
import Role from './Role.jsx';
import PageWrapper from 'component/page-wrapper';
class RoleRoute extends React.PureComponent {
    componentDidMount() {
        this.props.init();
        this.props.setCurrentPageCode(PAGE_CODE);
    }
    render() {
        const routeData = [{
            key: '/sys',
            text: '系统管理'
        }, {
            key: roleRoute.list,
            text: '角色管理'
        }];
        const add = Object.assign([], routeData);
        add.push({
            key: roleRoute.add,
            text: '新增'
        });
        const update = Object.assign([], routeData);
        update.push({
            key: roleRoute.update,
            text: '修改'
        });
        const detail = Object.assign([], routeData);
        detail.push({
            key: roleRoute.detail,
            text: '详情'
        });
        return (
            <Switch>
                <Route exact path={roleRoute.list} render={props =>
                    <div>
                        <Card><PageWrapper routeData={routeData}></PageWrapper><Roles history={props.history} /></Card>
                    </div>}/>
                <Route exact path={`${roleRoute.detail}/:id`} render={props =>
                    <div>
                        <Card><PageWrapper routeData={detail}></PageWrapper><Role id={props.match.params.id} history={props.history} readonly={true}/></Card>
                    </div>}/>
                <Route exact path={`${roleRoute.add}`} render={props =>
                    <div>
                        <Card><PageWrapper routeData={add}></PageWrapper><Role history={props.history} readonly={false}/></Card>
                    </div>}/>
                <Route exact path={`${roleRoute.update}/:id`} render={props =>
                    <div>
                        <Card><PageWrapper routeData={update}></PageWrapper><Role id={props.match.params.id} history={props.history} readonly={false}/></Card>
                    </div>}/>
                <Route component={ErrorPage} />
            </Switch>
        );
    }
}

RoleRoute.propTypes = {
    init: PropTypes.func,
    setCurrentPageCode: PropTypes.func,
    history: PropTypes.object,
};


import {connect} from 'react-redux';
import {setCurrentPageCode, getInitData} from './actions.js';

const mapDispatchToProps = dispatch => ({
    setCurrentPageCode: pageCode => dispatch(setCurrentPageCode(pageCode)),
    init: () => dispatch(getInitData()),
});

export default connect(null, mapDispatchToProps)(RoleRoute);
