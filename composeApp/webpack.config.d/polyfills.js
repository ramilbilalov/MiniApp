// Глушим предупреждения Webpack 5 о Node.js core-модулях, которые
// некоторые JS-зависимости транзитивно импортируют, но в браузере не используют.
// (Симптомы: "Can't resolve 'os' / 'path' / 'fs'".)
config.resolve = config.resolve || {};
config.resolve.fallback = Object.assign({}, config.resolve.fallback, {
    os: false,
    path: false,
    fs: false,
    crypto: false,
    stream: false,
    buffer: false,
    util: false,
});
