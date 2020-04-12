import {combineReducers} from 'redux';
import * as actions from './actions';
import {state as roleState} from './state';
import {RESET_ALL_STATE} from '../../constants';
import {setNewPages} from './selectors';

const list = (state = roleState.list, action) => {
    switch(action.type) {
        case actions.GET_INIT_DATA_BEGIN:
            return Object.assign({}, state, {
                isFetching: true
            });
        case actions.GET_INIT_DATA_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                data: action.data
            });
        case actions.GET_INIT_DATA_FAIL:
            return Object.assign({}, state, {
                isFetching: false,
                data: [],
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                isFetching: false,
                data: [],
            });
        default:
            return state;
    }
};

const role = (state = roleState.role, action) => {
    switch(action.type) {
        case actions.ROLE_CHANGE:
            return Object.assign({}, state, {
                [action.name]: action.value
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                name: ''
            });
        default:
            return state;
    }
};

const roleDetail = (state = roleState.roleDetail, action) => {
    const pages = state.rolePages;
    const operations = state.roleOperations;
    switch(action.type) {
        case actions.GET_ROLES_BY_ID_BEGIN:
            return Object.assign({}, state, {
                isFetching: true,
            });
        case actions.GET_ROLES_BY_ID_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                data: action.data,
                rolePages: action.data.pages || [],
                roleOperations: action.data.operations || []
            });
        case actions.GET_ROLES_BY_ID_FAIL:
            return Object.assign({}, state, {
                isFetching: false,
                data: {},
                rolePages: [],
                roleOperations: []
            });
        case actions.ROLE_CHANGE:
            if(state.data) { //修改角色名
                state.data.name = action.value;
            }
            return state;
        case actions.PERMISSION_SET:
            if(action.pages && action.pages.add && action.pages.add.length > 0) {
                for(let i = 0; i < action.pages.add.length; i++) {
                    if(pages.indexOf(action.pages.add[i]) === -1)
                        pages.push(action.pages.add[i]);
                }
            }
            if(action.pages && action.pages.remove && action.pages.remove.length > 0) {
                for(let i = 0; i < action.pages.remove.length; i++) {
                    if(pages.indexOf(action.pages.remove[i]) > -1) {
                        pages.splice(pages.indexOf(action.operations.remove[i]), 1);
                    }
                }
            }
            if(action.operations && action.operations.add && action.operations.add.length > 0) {
                for(let i = 0; i < action.operations.add.length; i++) {
                    if(operations.indexOf(action.operations.add[i]) === -1)
                        operations.push(action.operations.add[i]);
                }
            }
            if(action.operations && action.operations.remove && action.operations.remove.length > 0) {
                for(let i = 0; i < action.operations.remove.length; i++) {
                    if(operations.indexOf(action.operations.remove[i]) > -1) {
                        operations.splice(operations.indexOf(action.operations.remove[i]), 1);
                    }
                }
            }
            return Object.assign({}, state, {
                rolePages: pages,
                roleOperations: operations
            });
        case actions.RESRT_ROLE_DETAIL:
            return Object.assign({}, state, {
                isFetching: false,
                data: {},
                rolePages: [],
                roleOperations: []
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                isFetching: false,
                data: {},
                rolePages: [],
                roleOperations: []
            });
        default:
            return state;
    }
};

const pages = (state = roleState.pages, action) => {
    switch(action.type) {
        case actions.GET_PAGES_BEGIN:
            return Object.assign({}, state, {
                isFetching: true,
            });
        case actions.GET_PAGES_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                data: action.data
            });
        case actions.GET_PAGES_FAIL:
            return Object.assign({}, state, {
                isFetching: false,
                data: []
            });
        //查询单个page
        case actions.GET_PAGE_BEGIN:
            return Object.assign({}, state, {
                isFetching: true,
            });
        case actions.GET_PAGE_SUCCESS: {
            setNewPages(action.data.id, state.data, action.data);
            return Object.assign({}, state, {
                isFetching: false,
                data: state.data
            });
        }
        case actions.GET_PAGE_FAIL:
            return Object.assign({}, state, {
                isSubFetching: false,
            });
        case RESET_ALL_STATE:
            return Object.assign({}, state, {
                isFetching: false,
                isSubFetching: false,
                data: []
            });
        default:
            return state;
    }
};

const reducer = combineReducers({
    list,
    role,
    roleDetail,
    pages
});
export default reducer;
