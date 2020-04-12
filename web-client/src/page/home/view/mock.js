//柱状图
export const Histogram = [
    {
        name: 'London',
        'Jan.': 18.9,
        'Feb.': 28.8,
        'Mar.': 39.3,
        'Apr.': 81.4,
        May: 47,
        'Jun.': 20.3,
        'Jul.': 24,
        'Aug.': 35.6,
        'Sep.': 48.5,
        'Oct.': 38.5,
        'Nov.': 18.5,
        'Dec.': 58.5,
    },
    {
        name: 'Berlin',
        'Jan.': 12.4,
        'Feb.': 23.2,
        'Mar.': 34.5,
        'Apr.': 99.7,
        May: 52.6,
        'Jun.': 35.5,
        'Jul.': 37.4,
        'Aug.': 42.4,
        'Sep.': 12.5,
        'Oct.': 34.5,
        'Nov.': 51.5,
        'Dec.': 42.5,
    }
];

const lineChartData = [
    {
        year: '1991',
        value: 3
    },
    {
        year: '1992',
        value: 4
    },
    {
        year: '1993',
        value: 3.5
    },
    {
        year: '1994',
        value: 5
    },
    {
        year: '1995',
        value: 4.9
    },
    {
        year: '1996',
        value: 6
    },
    {
        year: '1997',
        value: 7
    },
    {
        year: '1998',
        value: 9
    },
    {
        year: '1999',
        value: 13
    }
];
const lineChartCols = {
    value: {
        min: 0
    },
    year: {
        range: [0, 1]
    }
};

export const lineChart = {
    data: lineChartData,
    cols: lineChartCols
};

const lineData2 = [
    {
        month: '2019-01-01',
        acc: 84.0
    },
    {
        month: '2019-02-01',
        acc: 14.9
    },
    {
        month: '2019-03-01',
        acc: 17.0
    },
    {
        month: '2019-04-01',
        acc: 20.2
    },
    {
        month: '2019-05-01',
        acc: 55.6
    },
    {
        month: '2019-06-01',
        acc: 56.7
    },
    {
        month: '2019-07-01',
        acc: 30.6
    },
    {
        month: '2019-08-01',
        acc: 63.2
    },
    {
        month: '2019-09-01',
        acc: 24.6
    },
    {
        month: '2019-10-01',
        acc: 14.0
    },
    {
        month: '2019-11-01',
        acc: 9.4
    },
    {
        month: '2019-12-01',
        acc: 6.3
    }
];
const lineCols2 = {
    month: {
        alias: '月份'
    },
    acc: {
        alias: '积累量'
    }
};

export const lineChart2 = {
    data2: lineData2,
    cols2: lineCols2
};

const HistogramData2 = [
    {genre: 'Sports', sold: 275},
    {genre: 'Strategy', sold: 115},
    {genre: 'Action', sold: 120},
    {genre: 'Shooter', sold: 350},
    {genre: 'Other', sold: 150},
];

const HistogramScale2 = {
    sold: {alias: '销售量'},
    genre: {alias: '游戏种类'},
};

export const Histogram2 = {
    HistogramData2,
    HistogramScale2
};
