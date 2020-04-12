
export const menus = [
    {
        url: '/',
        code: 'home',
        name: '首页',
        icon: 'home'
    },
    {
        url: '/product',
        code: 'products',
        name: '商品',
        icon: 'form',
        children: [
            {
                url: '/product/list',
                name: '商品管理',
                code: 'product'
            }
        ]
    },
    {
        url: '/sys',
        name: '系统管理',
        icon: 'form',
        code: 'sys',
        children: [
            {
                url: '/user/list',
                name: '用户管理',
                code: 'user'
            },
            {
                url: '/order/list',
                name: '订单管理',
                code: 'role'
            }
        ]
    }
];
