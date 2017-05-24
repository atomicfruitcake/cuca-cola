# cuca-cola
An Automated testing tool to utillise the Cucumber framework

## What is this?
An automated testing suite written in Java using Selenium and the Cucumber testing framework.
More information on cucumber can be found[here](https://cucumber.io/)

## How do I use it
#### Maven
If you are using maven, from your terminal, run:
```
mvn clean install -Denv=<LOCAL|DOCKER -Dbrowser=<CHROME|FIREFOX> 

```
Choosing either LOCAL or Docker and either CHROME or FIREFOX depending on your requirements

## Docker
### What is Docker?
Docker allows the running of test inside containers. For more information on how docker works
see https://www.docker.com/ . The result of using containerising our testing is that 
we can run server-side with no dependency on the OS of the server. Therefore we can run 
a Chrome or Firefox browser on CLI OS 

### Debugging Docker?
If you are attempting to debug the test running inside of a docker container, full logs of the tests are recorded inside
the container. First, use `docker ps` to all containers along with there IDs. Locate the ID of the container you wish to
view the logs of, and use `docker logs <container id>`

If you are still experiencing issues with docker itself, you may wish to clean docker
of all containers and images, and then re-install them. To do this, take the following steps:

1. Stop all containers `docker stop $(docker ps -a -q)`
2. Remove all containers `docker rm $(docker ps -a -q)`
3. Remove all images `docker rmi $(docker images -a -q)`
4. Restart docker `sudo systemctl restart docker`
5. Start up new containers `cd /home/ec2-user/docker/testing && docker-compose up -d`