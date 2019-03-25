const fetch = require('isomorphic-fetch');
const path = require('path');
const spawn = require('child_process').spawn;
const url = require('url');
const urlencode = require('form-urlencoded').default;
const clone = require('lodash.clone');
const fs = require('fs');


module.exports = class FusekiHTTP {

    constructor(options){
        this.options = options || {};
        this.options.home = this.options.home || './fuseki';
        this.options.url = this.options.url || 'http://localhost:3030/';
    }

    start() {
        var self = this;
        var script = path.join(this.options.home, 'fuseki-server.bat');

        var environment = clone(process.env);

        environment.FUSEKI_HOME = this.options.home;
        this.process = spawn('bash', [script], {env: environment});

        if (this.options.pipeOutput) {
            this.process.stdout.pipe(process.stdout);
            this.process.stderr.pipe(process.stderr);
        }

        this.running = true;

        this.process.on('close', function () {
            self.running = false
        });

        return Promise.resolve()
    }

    stop() {
        this.process.kill();

        return Promise.resolve();
    }

    alive() {
        return fetch(url.resolve(this.options.url, '$/ping')).then(function (res) {
            if (res.status !== 200) {
                return Promise.reject()
            }
        })
    }

    datasets() {
        return fetch(url.resolve(this.options.url, '$/datasets')).then(function (res) {
            return res.json()
        }).then(function (json) {
            var datasets = {};

            json.datasets.forEach(function (dataset) {
                datasets[dataset['ds.name']] = dataset
            });

            return datasets;
        })
    }

    createDataset(name, type, filename, graph) {
        var self = this;

        return this.datasets().then(function (datasets) {
            if (name in datasets) {
                return Promise.resolve()
            }

            return fetch(url.resolve(self.options.url, '$/datasets'), {
                method: 'post',
                headers: {'content-type': 'application/x-www-form-urlencoded'},
                body: urlencode({
                    dbName: name,
                    dbType: type
                })
            }).then(function () {
                if (!filename) {
                    return Promise.resolve()
                }

                return self.uploadDataset(name, filename);
            })
        })
    }

    uploadDataset(name, filename) {
        return fetch(url.resolve(this.options.url, name + '/data'), {
            method: 'post',
            headers: {'content-type': 'application/ld+json'},
            body: fs.createReadStream(filename)
        }).then(function (res) {
            if (res.status !== 200) {
                return Promise.reject();
            }
            return Promise.resolve();
        });
    }

    query(dataset, querySparql) {
        return fetch(url.resolve(this.options.url, dataset + '?query=' + encodeURIComponent(querySparql)), {
            method: 'get',
            headers: {'Accept': 'application/ld+json'}
        }).then(function (res) {
            if (res.status !== 200) {
                return Promise.reject()
            }
            else {
                return Promise.resolve(res.json())
            }
        })
    }
}
