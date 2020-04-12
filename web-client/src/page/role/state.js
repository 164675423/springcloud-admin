export const state = {
    list: {
        isFetching: false,
        data: []
    },
    role: {
        name: '',
    },
    roleDetail: {
        isFetching: false,
        data: {},
        rolePages: [],
        roleOperations: []
    },
    pages: {
        isFetching: false,
        isSubFetching: false,
        data: []
    },
};
