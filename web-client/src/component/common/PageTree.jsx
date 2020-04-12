import React from 'react';
import PropTypes from 'prop-types';
import {Tree, Icon, Spin} from 'antd';
const TreeNode = Tree.TreeNode;
import {union} from 'lodash';

const calcLink = (id, pages, paths) => {
    for(let i = 0; i < pages.length; i++)
        if(pages[i].id === id || (pages[i].items && calcLink(id, pages[i].items, paths))) {
            paths.push(pages[i].id);
            return true;
        }
};

class PageTree extends React.PureComponent {
    static getDerivedStateFromProps(nextProps, prevState) {
        if(nextProps.currentKey && prevState.prevCurrentKey !== nextProps.currentKey) {
            const link = [];
            calcLink(nextProps.currentKey, nextProps.pages, link);
            if(link.length > 0)
                return {
                    selectedKeys: [nextProps.currentKey],
                    expandedKeys: union(prevState.expandedKeys, link.slice(1)),
                    prevCurrentKey: nextProps.currentKey
                };
        }

        return null;
    }

    constructor(props) {
        super(props);
        const link = [];
        this.state =
            props.currentKey && calcLink(props.currentKey, props.pages, link)
                ? {
                    selectedKeys: [props.currentKey],
                    expandedKeys: link.slice(1)
                }
                : {
                    selectedKeys: [],
                    expandedKeys: []
                };
        this.onExpand = this.onExpand.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.onLoadData = this.onLoadData.bind(this);
        this.renderTreeNodes = this.renderTreeNodes.bind(this);
    }

    componentDidMount() {
        this.props.init();
    }

    renderTreeNodes(data) {
        return data.map(item => (
            <TreeNode
                key={item.id}
                title={<span>
                    {item.icon && <Icon type={item.icon} />}
                    {item.name}
                </span>}
                dataRef={item}
                isLeaf={!item.itemCount && (!item.items || item.items.length === 0)}>
                {item.items && item.items.length > 0 && this.renderTreeNodes(item.items)}
            </TreeNode>
        ));
    }

    onExpand(expandedKeys, node) {
        this.setState({
            expandedKeys
        });
        if(typeof this.props.onExpand === 'function') this.props.onExpand(expandedKeys[0], node.node.props.pos, node.expanded);
    }

    onSelect(selectedKeys, e) {
        if(e.selected)
            this.setState({
                selectedKeys
            });
        if(typeof this.props.onSelect === 'function') this.props.onSelect(e.selected ? selectedKeys[0] : this.state.selectedKeys[0]);
    }

    onLoadData(node) {
        const data = node.props.dataRef;
        return data.items && data.items.length ? Promise.resolve() : this.props.onLoadData(data.id);
    }

    render() {
        return (
            <Spin spinning={this.props.loading}>
                <Tree
                    loadData={this.onLoadData}
                    onExpand={this.onExpand}
                    onSelect={this.onSelect}
                    expandedKeys={this.state.expandedKeys}
                    selectedKeys={this.state.selectedKeys}
                    autoExpandParent={false}>
                    {this.renderTreeNodes(this.props.pages)}
                </Tree>
            </Spin>
        );
    }
}
/**
 * 树形结构展示Page
 */

PageTree.propTypes = {
    /**
     *
     */
    pages: PropTypes.array.isRequired,

    /**
     * 正在加载
     * @default false
     */
    loading: PropTypes.bool,

    /**
     * 当前选中项
     */
    currentKey: PropTypes.string,

    /**
     * 初始化
     */
    init: PropTypes.func.isRequired,

    /**
     * 异步加载数据
     * @param pageId 节点Page的Id
     */
    onLoadData: PropTypes.func.isRequired,

    /**
     * 展开/收起节点时触发
     * @param pageId 节点Page的Id
     * @param pos 节点Page在树上的位置
     * @param expanded 节点Page是否展开
     */
    onExpand: PropTypes.func,

    /**
     * 点击树节点触发
     * @param pageId 节点Page的Id
     */
    onSelect: PropTypes.func
};
PageTree.defaultProps = {
    pages: [],
    init: () => Promise.resolve(),
    onLoadData: () => Promise.resolve()
};
export default PageTree;
