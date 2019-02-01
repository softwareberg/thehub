const webpack = require('webpack');
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const StyleLintPlugin = require('stylelint-webpack-plugin');

const port = process.env.PORT || 3000;
const prod = process.env.NODE_ENV === 'production';

module.exports = {
  devtool: prod ? '' : 'source-map',
  entry: ['whatwg-fetch', './src/index.jsx'],
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js'
  },
  resolve: {
    modules: ['node_modules', 'src'],
    extensions: ['.js', '.jsx']
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'src/index.html')
    }),
    new MiniCssExtractPlugin({
      filename: '[name].css',
      chunkFilename: '[id].css'
    }),
    new CopyWebpackPlugin([
      { from: path.resolve(__dirname, 'src/img'), to: path.resolve(__dirname, 'dist/img') }
    ]),
    new StyleLintPlugin({
      configFile: path.resolve(__dirname, '.stylelintrc'),
      emitErrors: true
    })
  ],
  module: {
    rules: [{
      test: /\.jsx?$/,
      exclude: /node_modules/,
      use: [{
        loader: 'babel-loader'
      }, {
        loader: 'eslint-loader',
        options: {
          emitError: true,
          emitWarning: true,
          failOnError: true,
          failOnWarning: true
        }
      }]
    }, {
      test: /\.scss$/,
      use: [{
        loader: prod ? MiniCssExtractPlugin.loader : 'style-loader'
      }, {
        loader: 'css-loader',
        options: {
          sourceMap: !prod
        }
      }, {
        loader: 'sass-loader',
        options: {
          sourceMap: !prod
        }
      }]
    }, {
      test: /\.(jpe?g|png|gif|woff|woff2|eot|ttf|svg)$/i,
      use: [{
        loader: 'file-loader',
        options: {
          name: '[path][name].[ext]'
        }
      }]
    }]
  },
  devServer: {
    host: 'localhost',
    port,
    proxy: {
      '/api': 'http://localhost:8080'
    },
    contentBase: path.resolve(__dirname, 'src'),
    historyApiFallback: true
  }
};
