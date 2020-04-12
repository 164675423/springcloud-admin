import React from 'react';
import PropTypes from 'prop-types';
import {Switch, Route} from 'react-router-dom';
import Loadable from 'react-loadable';

import PageLoading from 'component/page-loading/index';
import {LocationContext} from 'util/context.js';
import Layout from 'component/layout/index.js';
import ErrorPage from 'page/404/index.js';
import {route as userRoute} from 'page/user/index';
import {route as roleRoute} from 'page/role/index';


const LoadableHome = Loadable({
    loader: () => import('page/home/index.js'),
    loading: PageLoading
});

class PageRoute extends React.Component {
    render() {
        return (
            <LocationContext.Provider value={this.props.location}>
                <Layout>
                    <Switch>
                        <Route exact path="/" component={LoadableHome} />
                        <Route path="/sys/user" component={userRoute} />
                        <Route path="/sys/role" component={roleRoute} />
                        <Route component={ErrorPage} />
                    </Switch>
                </Layout>
            </LocationContext.Provider>
        );
    }
}

PageRoute.propTypes = {
    location: PropTypes.object
};

export default PageRoute;
