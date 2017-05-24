#!/usr/bin/env bashdock

echo Please ensure Docker image is deleted from virtualbox GUI

# Remove default machine (ensure image does not exist in virtualbox GUI)
if [ $(docker-machine ls | wc -l) -eq 1 ]; 
then
	echo "No default docker machine detected, starting docker-machine"
else
	docker-machine rm -f default
fi

# Generate machine
docker-machine create --driver virtualbox default

# Connect the shell to the machine
eval "$(docker-machine env default)"

# Start up the selenium testing grid
docker-compose up
