# TP Angular

### Authors
* Thomas Daniellou (<thomas.danll@gmail.com>)
* Amona Souliman (<amona.souliman1@gmail.com>)

### Getting started
Make sure you have `NodeJS` installed.
Install `grunt-cli` and `bower` globally :
```
npm install -g grunt-cli
npm install -g bower
```

Run `npm install` and `bower install` in your terminal to download the project's dependencies.

### Running the application

##### In Development mode #####
You might want to edit the `Gruntfile.js` to match your application settings:

```
// The actual grunt server settings
    connect: {
      options: {
        port: 9000,
        // Change this to '0.0.0.0' to access the server from outside.
        hostname: 'localhost',
        livereload: 35729
      },
      proxies: [
        {
          context: '/rest',
          // Change this if your REST API is on another Host
          host: 'localhost',  #The REST API's host
          port: 8080,         #The REST API's port
          rewrite: {
            '^/rest': '/rest'
          }
        }
      ],
      ...
      },
```

Then run `grunt serve` to launch the development mode.

##### Deploying the application #####
Run `grunt build` to start the minification of the project. The minified application will be located in the `dist` folder.