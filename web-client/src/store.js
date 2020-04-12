import {createStore, combineReducers, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import {reducer as pageReducer} from './page/index';

const reducer = combineReducers({...pageReducer});

// eslint-disable-next-line no-underscore-dangle
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const middleware = [
    thunk,
];
// create redux store
const store = createStore(reducer, composeEnhancers(
    applyMiddleware(...middleware)
));

export default store;
