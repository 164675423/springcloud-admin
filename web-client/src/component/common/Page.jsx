import React from 'react';
import PropTypes from 'prop-types';
import {Tag, Icon, Card, Row, Col, Collapse, Divider} from 'antd';
import './page.css';
const Panel = Collapse.Panel;

const EMPTY_FUNCTION = () => {};

export const PAGE_MODE = Object.freeze({
    CARD: 'Card',
    SIMPLE: 'Simple'
});

class Operation extends React.PureComponent {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    handleChange(checked) {
        this.props.onChange(this.props.id, checked, this.props.pageId);
    }

    handleClick(e) {
        e.stopPropagation();
    }

    render() {
        const {pageId, ...other} = this.props;
        return (
            <span className="page-opt" onClick={this.handleClick}>
                <Tag.CheckableTag className="tag-font-big" {...other} onChange={this.handleChange} />
            </span>
        );
    }
}

Operation.propTypes = {
    id: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    checked: PropTypes.bool,
    pageId: PropTypes.string
};
Operation.defaultProps = {
    onChange: EMPTY_FUNCTION
};
export class PageComponent extends React.PureComponent {
    constructor(props) {
        super(props);
        this.handleCardClick = this.handleCardClick.bind(this);
        this.handleSelectAll = this.handleSelectAll.bind(this);
        this.handleCancelAll = this.handleCancelAll.bind(this);
        this.handleLocate = this.handleLocate.bind(this);
    }

    handleCardClick(e) {
        this.props.onCardClick(e.currentTarget.dataset.id, !this.props.page.selected);
        e.stopPropagation();
    }

    handleSelectAll(e) {
        this.props.onSelectAll(e.currentTarget.dataset.id);
        e.stopPropagation();
    }

    handleCancelAll(e) {
        this.props.onCancelAll(e.currentTarget.dataset.id);
        e.stopPropagation();
    }

    handleLocate(e) {
        this.props.onLocate(e.currentTarget.dataset.id);
        e.stopPropagation();
    }

    render() {
        const {page, ...other} = this.props;
        if(this.props.displaySelectedOnly && !page.selected) return null;
        const pageItems = page.items && (this.props.displaySelectedOnly ? page.items.filter(item => item.selected) : page.items);

        if(pageItems && pageItems.length > 0) {
            const leaf = pageItems.filter(item => !item.items || item.items.length === 0);
            const unleaf = pageItems.filter(item => item.items && item.items.length > 0);
            return (
                <div>
                    {leaf.length > 0 &&
                        (this.props.mode === PAGE_MODE.SIMPLE ? (
                            leaf.map(item => <PageComponent key={item.id} page={item} {...other} />)
                        ) : (
                            <Row type="flex" gutter={16}>
                                {leaf.map(item => (
                                    <Col key={item.id} xs={24} sm={24} md={24} lg={24} xl={12} xxl={8}>
                                        <PageComponent page={item} {...other} />
                                    </Col>
                                ))}
                            </Row>
                        ))}
                    {unleaf.length > 0 && (
                        <Collapse bordered={false} defaultActiveKey={unleaf.map(item => item.id)}>
                            {unleaf.map(item => (
                                <Panel
                                    key={item.id}
                                    header={<Row className="hover-display-area">
                                        <Col span={12}>
                                            <h3>
                                                {item.icon && <Icon type={item.icon} />}
                                                {item.name}
                                                {this.props.onLocate && (
                                                    <a data-id={item.id} onClick={this.handleLocate} className="hover-display">
                                                            #
                                                    </a>
                                                )}
                                            </h3>
                                        </Col>
                                        <Col span={12} className="col-align-right">
                                            {this.props.onSelectAll && (
                                                <a data-id={item.id} onClick={this.handleSelectAll}>
                                                    全部选择
                                                </a>
                                            )}
                                            {this.props.onSelectAll && <Divider type="vertical" />}
                                            {this.props.onCancelAll && (
                                                <a data-id={item.id} onClick={this.handleCancelAll}>
                                                    全部取消
                                                </a>
                                            )}
                                        </Col>
                                    </Row>}>
                                    <PageComponent page={item} {...other} />
                                </Panel>
                            ))}
                        </Collapse>
                    )}
                </div>
            );
        }

        const pageOperations = page.operations && (this.props.displaySelectedOnly ? page.operations.filter(opt => opt.selected) : page.operations);

        switch(this.props.mode) {
            case PAGE_MODE.SIMPLE:
                return (
                    <Row gutter={24} className="page-opt">
                        <Col xs={10} sm={10} md={10} lg={8} xl={6} xxl={4}>
                            {page.id && (
                                <Operation key={page.id} id={page.id} checked={page.selected} onChange={this.props.onCardClick}>
                                    {page.name}
                                </Operation>
                            )}
                        </Col>
                        <Col xs={14} sm={14} md={14} lg={16} xl={18} xxl={20}>
                            {pageOperations &&
                                pageOperations.map(opt => (
                                    <Operation key={opt.id} id={opt.id} pageId={page.id} checked={opt.selected} onChange={this.props.onOptChange}>
                                        {opt.name}
                                    </Operation>
                                ))}
                        </Col>
                    </Row>
                );

            default:
                return (
                    <Card
                        data-id={page.id}
                        title={<span>
                            {page.icon && <Icon type={page.icon} />}
                            {page.name}
                        </span>}
                        extra={this.props.onLocate && (
                            <a data-id={page.id} onClick={this.handleLocate} className="hover-display">
                                    #
                            </a>
                        )}
                        onClick={this.handleCardClick}
                        className={`page-opt hover-display-area ${page.selected ? 'selected' : ''}`}>
                        {pageOperations &&
                            pageOperations.map(opt => (
                                <Operation key={opt.id} id={opt.id} pageId={page.id} checked={opt.selected} onChange={this.props.onOptChange}>
                                    {opt.name}
                                </Operation>
                            ))}
                    </Card>
                );
        }
    }
}
/**
 * 层级显示页面/操作，提供页面/操作选中、全选、反选等操作
 */

PageComponent.propTypes = {
    /**
     * 是否只显示选中的 Operation
     * @default false，显示全部 Operation
     */
    displaySelectedOnly: PropTypes.bool.isRequired,

    /**
     * 展示方式
     * 卡片(default)：PAGE_MODE.CARD
     * 文本行: PAGE_MODE.SIMPLE
     */
    mode: PropTypes.oneOf([PAGE_MODE.CARD, PAGE_MODE.SIMPLE]).isRequired,

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
     *      }],
     *      operations: [{
     *          id: required,
     *          name: required,
     *          selected required,  是否选中
     *      }]
     * }
     */
    page: PropTypes.object.isRequired,

    /**
     * "Page点击"后触发
     * @param pageId，所选Page的Id
     * @param selected，Page是否选中
     */
    onCardClick: PropTypes.func.isRequired,

    /**
     * "Page定位"点击后触发
     * @param pageId，所选Page的Id
     */
    onLocate: PropTypes.func,

    /**
     * "Operation点击"后触发
     * @param operationId，所选Operation的Id
     * @param selected，Operation是否选中
     * @param pageId，所选Operation所属的Page的Id;
     */
    onOptChange: PropTypes.func,

    /**
     * "全部选择"点击后触发
     * @param pageId，所选Page的Id
     */
    onSelectAll: PropTypes.func,

    /**
     * "全部取消"点击后触发
     * @param pageId，所选Page的Id
     */
    onCancelAll: PropTypes.func,

    /**
     * "反向选择"点击后触发
     * @param pageId，所选Page的Id
     */
    onReverseSelect: PropTypes.func
};
PageComponent.defaultProps = {
    page: {},
    mode: PAGE_MODE.CARD,
    displaySelectedOnly: false,
    onCardClick: EMPTY_FUNCTION
};
export const Page = PageComponent;
export class PageLink extends React.PureComponent {
    constructor(props) {
        super(props);
        this.handleLocate = this.handleLocate.bind(this);
    }

    handleLocate(e) {
        this.props.onLocate(e.currentTarget.dataset.id);
        e.stopPropagation();
    }

    render() {
        const {page, level} = this.props;

        if(page.items && page.items.length > 0 && level > 0) {
            let leaf = page.items;
            let unleaf = [];

            if(level > 1) {
                leaf = page.items.filter(item => !item.items || item.items.length === 0);
                unleaf = page.items.filter(item => item.items && item.items.length > 0);
            }

            return (
                <div>
                    {leaf.length > 0 && (
                        <Row type="flex" gutter={16}>
                            {leaf.map(item => (
                                <Col key={item.id} xs={24} sm={24} md={24} lg={12} xl={8} xxl={6}>
                                    <PageLink page={item} level={level - 1} onLocate={this.props.onLocate} />
                                </Col>
                            ))}
                        </Row>
                    )}
                    {unleaf.length > 0 && (
                        <Collapse bordered={false} defaultActiveKey={unleaf.map(item => item.id)}>
                            {unleaf.map(item => (
                                <Panel
                                    key={item.id}
                                    header={<a data-id={item.id} onClick={this.handleLocate}>
                                        {item.icon && <Icon type={item.icon} />}
                                        {item.name}
                                    </a>}>
                                    <PageLink page={item} level={level - 1} onLocate={this.props.onLocate} />
                                </Panel>
                            ))}
                        </Collapse>
                    )}
                </div>
            );
        }

        return (
            <Card className="page-opt" data-id={page.id} onClick={this.handleLocate}>
                {
                    <a>
                        {page.icon && <Icon type={page.icon} />}
                        {page.name}
                    </a>
                }
            </Card>
        );
    }
}
/**
 * 层级以Card展示当前页面的子页面，提供页面跳转
 */

PageLink.propTypes = {
    /**
     * 以页面跳转展示的Page子页面层数
     * @default(min): 1
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
     * when level = 1 then 展示 id in ('111')
     * when level = 2 then 展示 id in ('111', '222')
     */
    level: PropTypes.number.isRequired,

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
     * "Page点击"后触发
     * @param id: 所选Page的Id
     */
    onLocate: PropTypes.func.isRequired
};
PageLink.defaultProps = {
    page: {},
    level: 1
};
