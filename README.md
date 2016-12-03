# `yr.no` API proxy

## Development
First prepare by doing `gcloud init`, then:

* To test locally: `mvn jetty:run`
* To deploy: `mvn appengine:deploy`
* To read production logs: `gcloud app logs read -s default`
