import {PAGE} from '../../constants';
import {baseDataStatus} from '../../Enum';

export const PAGE_CODE = 'sys-user';

export const PERMISSION = {
    add: 'add',
    update: 'update',
    abandon: 'abandon',
};

export const DEFAULT_QUERY_OPTION = {
    roleId: '',
    name: '',
    status: baseDataStatus.生效,
    pageIndex: PAGE.index,
    pageSize: PAGE.size,
    sortField: 'username',
    isDesc: false,
};
