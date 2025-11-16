#!/bin/sh
#sudo sysctl -w vm.max_map_count=262144
#docker run --rm --privileged --pid=host alpine sh -c "sysctl -w vm.max_map_count=262144"
docker run --rm --privileged alpine sysctl -w vm.max_map_count=262144
docker-compose up