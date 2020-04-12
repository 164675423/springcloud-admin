import {getPagesByMaxLevel, getPageById} from './actions';
import PageTree from 'component/common/PageTree.jsx';
import {connect} from 'react-redux';

const mapStateToProps = state => ({
    pages: state.role.pages.data,
    loading: state.role.pages.isFetching
});

const mapDispatchToProps = (dispatch, ownProps) => ({
    init: () => dispatch(getPagesByMaxLevel()),
    onLoadData: id => dispatch(getPageById(id)),
    onSelect: id => {
        dispatch(getPageById(id));
        if(typeof ownProps.onSelect === 'function') ownProps.onSelect(id);
    }
});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(PageTree);
