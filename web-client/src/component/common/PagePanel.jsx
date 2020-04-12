import React from 'react';
import PropTypes from 'prop-types';
import {Icon, Card, Divider} from 'antd';
import {Page, PageLink, PAGE_MODE} from './Page.jsx';

class PagePanel extends React.PureComponent {
    constructor(props) {
        super(props);
        this.handleSelectAll = this.handleSelectAll.bind(this);
        this.handleCancelAll = this.handleCancelAll.bind(this);
    }

    handleSelectAll(e) {
        this.props.onSelectAll(e.target.dataset.id);
        e.stopPropagation();
    }

    handleCancelAll(e) {
        this.props.onCancelAll(e.target.dataset.id);
        e.stopPropagation();
    }

    render() {
        const page = this.props.page;
        if(!page.name) return <Card style={{height: 'calc(100vh - 237px)'}} loading={this.props.loading} />;
        const action = {
            onCardClick: this.props.onPageSelected,
            onLocate: this.props.onPageLocate,
            onOptChange: this.props.onOptSelected,
            onSelectAll: this.props.onSelectAll,
            onCancelAll: this.props.onCancelAll,
        };
        return (
            <Card style={{height: 'calc(100vh - 237px)'}}
                title={<h3>
                    {page.icon && <Icon type={page.icon} />}
                    {page.name}
                </h3>}
                loading={this.props.loading}
                // extra={this.props.level <= 0 && (
                //     <span>
                //         {this.props.onSelectAll && (
                //             <a data-id={page.id} onClick={this.handleSelectAll}>
                //                 全部选择
                //             </a>
                //         )}
                //         {this.props.onSelectAll && <Divider type="vertical" />}
                //         {this.props.onCancelAll && (
                //             <a data-id={page.id} onClick={this.handleCancelAll}>
                //                全部取消
                //             </a>
                //         )}
                //     </span>
                // )}
                >
                {this.props.level > 1 ? (
                    <PageLink key={page.id} page={page} level={this.props.level} onLocate={this.props.onPageLocate} />
                ) : (
                    <Page key={page.id} page={page} {...action} displaySelectedOnly={false} mode={this.props.mode} />
                )}
            </Card>
        );
    }
}
/**
 * 层级展示当前页面的子页面，并提供页面跳转，或页面/操作选中、全选、反选等操作
 */

PagePanel.propTypes = {
    /**
     * 正在加载
     * @default false
     */
    loading: PropTypes.bool.isRequired,

    /**
     * 展示方式
     * 卡片(default)：PAGE_MODE.CARD
     * 文本行: PAGE_MODE.SIMPLE
     */
    mode: PropTypes.oneOf([PAGE_MODE.CARD, PAGE_MODE.SIMPLE]),

    /**
     * Card className
     */
    className: PropTypes.string,

    /**
     * 页面数据
     * page: {
     *      id: required,
     *      code,
     *      name: required,
     *      icon,  图标
     *      selected: required,  是否选中
     *      items: [{
     *          ...page,  子页面
     *      }]
     * }
     */
    page: PropTypes.object.isRequired,

    /**
     * 需要以页面跳转展示的Page子页面层数
     * @default(min): 0
     * @eg
     *  props:{
     *      page: {
     *          id: '000',
     *          items: [{
     *              id:'111',
     *              items: [{id: '222'}]
     *          }]
     *      }
     * }
     * when level = 0 then 为所有页面/操作，提供选中、全选、反选等操作
     * when level = 1 then id = '111' 提供页面跳转，为其他页面/操作，提供选中、全选、反选等操作
     * when level = 2 then id in ('111', '222')提供页面跳转，为其他页面/操作，提供选中、全选、反选等操作
     */
    level: PropTypes.number.isRequired,

    /**
     * "Page定位"点击后触发
     * @param pageId，所选Page的Id
     */
    onPageLocate: PropTypes.func.isRequired,

    /**
     * "Page点击"后触发
     * @param pageId，所选Page的Id
     * @param selected，Page是否选中
     */
    onPageSelected: PropTypes.func.isRequired,

    /**
     * "Operation点击"后触发
     * @param operationId，所选Operation的Id
     * @param selected，Operation是否选中
     * @param pageId，所选Operation所属的Page的Id;
     */
    onOptSelected: PropTypes.func.isRequired,

    /**
     * "全部选择"点击后触发
     * @param pageId，所选Page的Id
     */
    intl: PropTypes.object,
    onSelectAll: PropTypes.func,

    /**
     * "全部取消"点击后触发
     * @param pageId，所选Page的Id
     */
    onCancelAll: PropTypes.func,

};

const EMPTY_FUNCTION = () => {};

PagePanel.defaultProps = {
    page: {},
    loading: false,
    level: 0,
    mode: PAGE_MODE.CARD,
    onPageSelected: (selected, unselected) => Promise.resolve(),
    onOptSelected: EMPTY_FUNCTION,
    onPageLocate: EMPTY_FUNCTION
};
export default PagePanel;
