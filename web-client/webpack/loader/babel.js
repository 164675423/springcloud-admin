module.exports = {
  test: /\.jsx|.js$/,
  exclude: /(node_modules|bower_components)/,
  use: {
    loader: 'babel-loader',
  }
};
