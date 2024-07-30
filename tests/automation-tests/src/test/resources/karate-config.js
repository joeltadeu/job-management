function fn() {
    let config = {};

    karate.configure('connectTimeout', 120000)
    karate.configure('readTimeout', 120000)

    let env = karate.env ? karate.env : 'local';
    config.env = env

    if (java.lang.System.getenv("URL") == null) {
        if (env == 'qa') {
            config.host = 'https://api.qa.jmanagement.com'
        } else if (env == 'dev') {
            config.host = 'https://api.dev.jmanagement.com'
        } else if (env == 'local') {
            config.host = 'http://localhost:9094'
        }
    }

    console.log('Testing URL: ', config.host);
    config.config = read('_config.json')
    return config;
}