import {reducer as homeReducer} from './home/index';
import {reducer as userReducer} from './user/index';
import {reducer as roleReducer} from './role/index';
import {reducer as productReducer} from './product/index';

const reducer = {
    home: homeReducer,
    user: userReducer,
    role: roleReducer
};

export default reducer;
