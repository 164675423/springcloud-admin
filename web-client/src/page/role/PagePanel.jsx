import PagePanel from 'component/common/PagePanel.jsx';
import {connect} from 'react-redux';
import {isLeafPage} from 'component/common/pageUtils';
import {findPage, selectedMark} from './selectors';
import {changeOpt, selectPage, selectAll, getPageById} from './actions';

const mapStateToProps = (state, ownProps) => {
    const page = findPage(ownProps.id, state.role.pages.data) || {};
    const rolePages = state.role.roleDetail.rolePages || [];
    const roleOpreations = state.role.roleDetail.roleOperations || [];
    const newPage = selectedMark(page, rolePages, roleOpreations);
    const isLeaf = newPage && isLeafPage(newPage);
    return {
        page: newPage,
        level: isLeaf ? 0 : (ownProps.maxLevel || 0) - ((newPage && newPage.level) || 0) + 1,
        loading: state.role.pages.isSubFetching || state.role.pages.isFetching,
        mode: 'Card'
    };
};

const mapDispatchToProps = (dispatch, ownProps) => ({
    onOptSelected: (id, selected, pageId) => dispatch(changeOpt(id, selected, pageId)),
    onPageSelected: (id, selected) => dispatch(selectPage(id, selected)),
    onSelectAll: id => dispatch(selectAll(id, true)),
    onCancelAll: id => dispatch(selectAll(id, false)),
    onPageLocate: id => {
        dispatch(getPageById(id));
        ownProps.onSelect(id);
    }
});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(PagePanel);
