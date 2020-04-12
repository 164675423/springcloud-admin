import React from 'react';
import {Switch, Route} from 'react-router-dom';
import {Card} from 'antd';
import {userRoute} from 'util/route';
import ErrorPage from 'page/404/index';
import TablePanel from './TablePanel.jsx';
import QueryPanel from './QueryPanel.jsx';
import NewPanel from './NewPanel.jsx';
import PropTypes from 'prop-types';
import {PAGE_CODE} from './constants';
import PageWrapper from 'component/page-wrapper';

class AppRoute extends React.PureComponent {
    componentDidMount() {
        this.props.init();
        this.props.setCurrentPageCode(PAGE_CODE);
    }
    render() {
        const routeData = [{
            key: '/sys',
            text: '系统管理'
        }, {
            key: userRoute.list,
            text: '用户管理'
        }];
        const add = Object.assign([], routeData);
        add.push({
            key: userRoute.add,
            text: '新增'
        });
        const update = Object.assign([], routeData);
        update.push({
            key: userRoute.update,
            text: '修改'
        });
        return (
            <Switch>
                <Route exact path={userRoute.list} render={() =>
                    <div>
                        <Card><PageWrapper routeData={routeData}></PageWrapper><QueryPanel /></Card>
                        <Card> <TablePanel /></Card>
                    </div>}/>
                <Route exact path={userRoute.add} render={props =>
                    <div>
                        <Card><PageWrapper routeData={add}></PageWrapper><NewPanel history={props.history}/></Card>
                    </div>}/>
                <Route exact path={`${userRoute.update}/:id`} render={props =>
                    <div>
                        <Card><PageWrapper routeData={update}></PageWrapper><NewPanel id={props.match.params.id} history={props.history}/></Card>
                    </div>}/>
                <Route component={ErrorPage} />
            </Switch>
        );
    }
}

AppRoute.propTypes = {
    init: PropTypes.func,
    setCurrentPageCode: PropTypes.func,
    history: PropTypes.object,
};


import {connect} from 'react-redux';
import {getInitData, setCurrentPageCode} from './actions.js';

const mapDispatchToProps = dispatch => ({
    init: () => dispatch(getInitData()),
    setCurrentPageCode: pageCode => dispatch(setCurrentPageCode(pageCode))
});

export default connect(null, mapDispatchToProps)(AppRoute);
