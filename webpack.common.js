const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const OpenBrowserPlugin = require('open-browser-webpack-plugin')


const commonPath = (dir) => {
    const dirs = ['src', 'main', 'resources', 'js']
    if (dir) {
        dirs.push(...dir)
    }
    return  path.join(__dirname, ...dirs);
}

const cssUse = (preset) => {
    const use = ['vue-style-loader', 'css-loader',]
    if (preset) {
        use.push(preset)
    }
    return use
}

const babelOptions = (preset, plugin) => {
    const options = {
        presets: [
            '@babel/preset-env',
        ],
        plugins: [],

    }

    if (preset) {
        options.presets.push(preset)
    }
    if (plugin) {
        options.plugins.push(plugin)
    }

    return options
}

module.exports = {
    entry: ['@babel/polyfill', commonPath(['index.js'])],
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                loader: {
                    loader: "babel-loader",
                    options: babelOptions()
                }
            },
            {
                test: /\.ts$/,
                exclude: /(node_modules|bower_components)/,

                loader: {
                    loader: "babel-loader",
                    options: babelOptions('@babel/preset-typescript')
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: cssUse()
            },
            {
                test: /\.s[ca]ss$/,
                use: cssUse({
                        loader: 'sass-loader',
                        options: {
                            implementation: require('sass'),
                            sassOptions: {
                                fiber: require('fibers'),
                                indentedSyntax: true
                            },
                        },
                    }
                ),
            },

        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new OpenBrowserPlugin({})
    ],
    resolve: {
        alias: {
            '@': commonPath(),
            '@api': commonPath(['API']),
            '@component': commonPath(['components']),
            '@entity': commonPath(['entity']),
            '@util': commonPath(['util']),
        },
        extensions: ['.wasm', '.mjs', '.js', '.json','.vue'],
        modules: [
            commonPath(),
            path.join(__dirname, 'node_modules'),
        ],
    }
}