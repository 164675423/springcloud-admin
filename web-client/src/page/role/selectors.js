const EMPTY_OBJECT = {};
/* eslint-disable */

export const findPage = (id, pages, level = 0) => {
    level++;
    if (!id) return;

    for (let i = 0; i < pages.length; i++) {
        pages[i].level = level;
        if (pages[i].id === id) return pages[i];

        if (pages[i].items) {
            const page = findPage(id, pages[i].items, level);
            if (page) return page;
        }
    }
};

export const setNewPages = (id, pages, data, level = 0) => {
    level++;
    let changed = false;
    if (!id) return;

    for (let i = 0; i < pages.length; i++) {
        pages[i].level = level;
        if (pages[i].id === id){
            changed = true;
            pages[i] = data;
        };
        if(changed === true)  return;
        const pageItems = pages[i].items;
        if (pageItems) {
            setNewPages(id, pageItems, data, level);
        }
    }
};

export const selectedMark = (page, rolePages, roleOpreations) => {
    let changed = false;
    const newPage = {};

    if (page.items && page.items.length > 0) {
        newPage.items = page.items
            .map(item => {
                const sub = selectedMark(item, rolePages, roleOpreations);
                changed = changed || sub !== item;
                return sub;
            })
            .filter(item => item);
        return changed
            ? Object.assign({}, page, newPage, {
                  selected: newPage.items.some(item => item.selected)
              })
            : page;
    } else if (page.itemCount && page.itemCount > 0) return page;

    if(rolePages.indexOf(page.id) > -1)
        newPage.selected = true;

    if (page.operations && page.operations.length > 0 && roleOpreations.length >0)
        newPage.operations = page.operations.map(opt => {
            const selected = roleOpreations.findIndex(optId => optId === opt.id) > -1;
            if (opt.selected === selected) return opt;
            changed = true;
            return Object.assign({}, opt, {
                selected
            });
        });
    return Object.assign({}, page, newPage);
};

const pageSelectors = {};
export const pageSelector = id => {
    const selector = pageSelectors[id];
    if (selector) return selector;
    pageSelectors[id] = createSelector(
        state => state.getIn(['page', 'domainData', 'pages', 'data']),
        data => {
            if (!data) return EMPTY_OBJECT;
            const page = findPage(id, data.toJS());
            if (!page) return EMPTY_OBJECT;
            return page;
        }
    );
    return pageSelectors[id];
};
const pageWithPermissionSelectors = {};
export const pageWithPermissionSelector = id => {
    const selector = pageWithPermissionSelectors[id];
    if (selector) return selector;
    pageWithPermissionSelectors[id] = createSelector(
        pageSelector(id),
        state => state.getIn(['page', 'appData', 'rolePermission']),
        (page, permissions) => {
            if (!page) return EMPTY_OBJECT;
            return permissions ? selectedMark(page, permissions.toJS()) : page;
        }
    );
    return pageWithPermissionSelectors[id];
};
import {pageCached, isLeafPage} from 'component/common/pageUtils';

const mergeTreeItems = (source, target) => {
    if (target)
        target.forEach(tItem => {
            const sItem = source.find(s => s.id === tItem.id);

            if (sItem) {
                if (pageCached(sItem) && isLeafPage(sItem)) return;
                if (!sItem.items) sItem.items = [];
                return mergeTreeItems(sItem.items, tItem.items);
            }

            source.push(tItem);
        });
    return source;
};

export const pageSelect = (id, pages) => {
    const obj = findPage(id, pages);
    if(!obj) return;
    return  {
            page: obj.id,
            operations: obj.operations ? obj.operations.map(item => item.id) : []
        };
};