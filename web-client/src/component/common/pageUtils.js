import intersection from 'lodash/intersection';
import difference from 'lodash/difference'; // TODO: v4-rc 支持 Immutable.isImmutable

const isImmutable = maybeImmutable =>
    (maybeImmutable && maybeImmutable['@@__IMMUTABLE_ITERABLE__@@']) || (maybeImmutable && maybeImmutable['@@__IMMUTABLE_RECORD__@@']);
/**
 * 在树形结构 pages 中，查找指定（id）的 page
 * @param id
 * @param pages
 * @returns {*}
 */

export const findPage = (id, pages) => {
    if(!id) return;
    if(isImmutable(pages))
        for(let i = 0; i < pages.size; i++) {
            if(pages.getIn([i, 'id']) === id) return pages.get(i);

            if(pages.hasIn([i, 'items'])) {
                const page = findPage(id, pages.getIn([i, 'items']));
                if(page) return page;
            }
        }
    else
        for(let i = 0; i < pages.length; i++) {
            if(pages[i].id === id) return pages[i];

            if(pages[i].items) {
                const page = findPage(id, pages[i].items);
                if(page) return page;
            }
        }
};
/**
 * 判断指定page是否已获取完整数据
 * @param page
 */

export const pageCached = page =>
    isImmutable(page)
        ? (page.has('items') && page.get('items').size > 0) ||
          (page.has('itemCount') && page.get('itemCount') === 0) ||
          (page.has('operations') && page.get('operations').size > 0) ||
          (page.has('operationCount') && page.get('operationCount') === 0) ||
          (!page.has('operations') && !page.has('items') && !page.has('itemCount') && !page.has('operationCount'))
        : (page.items && page.items.length > 0) ||
          page.itemCount === 0 ||
          (page.operations && page.operations.length > 0) ||
          page.operationCount === 0 ||
          (page.items === undefined && page.itemCount === undefined && page.operations === undefined && page.operationCount === undefined);
/**
 * 判断page是否为叶子结点
 * @param page
 */

export const isLeafPage = page =>
    (!page.items || page.items.length === 0) && !page.itemCount;
/**
 * 在树形结构 pages 中，查找指定（id）的 page，并返回对于 pageId 及 operationIds
 * @param id
 * @param pages
 * @returns {{page: string, operations: Array[string]}}
 */

export const pageSelect = (id, pages) => {
    const obj = findPage(id, pages);
    if(!obj) return;
    return {
        page: obj.id,
        operations: obj.operations ? obj.operations.map(item => item.id) : []
    };
};
export const expandPermissions = (paramPages = [], paramScope = []) => {
    const pages = paramPages;
    const scope = paramScope;
    const initData = {
        pages: [],
        operations: []
    };
    return Array.isArray(pages) ? pages.reduce(
        (result, page) => {
            if(!page.items || page.items.length === 0) {
                if(scope.length === 0 || scope.findIndex(item => item === page.id) > -1) {
                    result.pages.push(page.id);
                    if(page.operations) page.operations.forEach(opt => result.operations.push(opt.id));
                }

                return result;
            }

            const {pages, operations} = expandPermissions(page.items, scope);
            return {
                pages: result.pages.concat(pages),
                operations: result.operations.concat(operations)
            };
        }, initData) : initData;
};
/**
 * 在树形结构 pages 中，查找指定（id）的 page，返回本身及其下属全部 page 对于 pageId 及 operationIds
 * @param pageId
 * @param pages
 * @returns {{pages: Array[string], operations: Array[string]}}
 */

export const pageSelectAll = (pageId, pages) => {
    const obj = findPage(pageId, pages);
    if(!obj) return;
    return expandPermissions([isImmutable(obj) ? obj.toJS() : obj]);
};
/**
 * 在树形结构 pages 中，查找指定（id）的 page，返回本身及其下属全部 page，反选的 operationIds 结果
 * @param pageId
 * @param pages
 * @param permission
 * @returns {{add, remove}}
 */

export const pageReverseSelect = (pageId, pages, permission) => {
    const obj = findPage(pageId, pages);
    if(!obj) return;
    const scope = permission.toJS();
    const expand = expandPermissions(obj, scope.pages);
    if(!expand) return;
    const remove = intersection(expand.operations, scope.operations);
    return {
        add: difference(expand.operations, remove),
        remove
    };
};
