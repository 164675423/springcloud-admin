const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
    mode: 'development',
    devtool: 'inline-source-map',
    devServer: {
        contentBase: './build',
        port: 3000,
        historyApiFallback: true,
        // proxy: {
        //   '/api': {
        //     target: 'http://localhost:9000/api',
        //     pathRewrite: {'^/api': ''},
        //     changeOrigin: true
        //   },
        // }
        proxy: [{
            context: ['/am', '/api'],
            target: 'http://localhost:9000',
            changeOrigin: true
        }]
    },
});
