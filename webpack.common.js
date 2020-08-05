const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const OpenBrowserPlugin = require('open-browser-webpack-plugin')


module.exports = {
    entry: path.join(__dirname, 'src', 'main', 'resources', 'js', 'index.js'),
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            // QUESTION: Нужен ли этот обработчик ?
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    'sass-loader'
                ]
            },
            {
                test: /\.s([ca])ss$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    {
                        loader: 'sass-loader',
                        // Requires sass-loader@^8.0.0
                        options: {
                            implementation: require('sass'),
                            sassOptions: {
                                fiber: require('fibers'),
                                indentedSyntax: true // optional
                            },
                        },
                    },
                ],
            },

        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new OpenBrowserPlugin({})
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}