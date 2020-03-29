unrootify:
	sudo chown -R $$(id -u):$$(id -g) .

up-stub:
	docker-compose up --force-recreate -d stub

into-stub:
	docker-compose exec stub bash

build:
	docker-compose run --rm roundup \
		'mvn package spring-boot:repackage'

run:
	docker-compose run --rm roundup \
		'java -jar /app/target/roundup-0.0.1-SNAPSHOT.jar 2020 11'

env:
	@echo \# run:
	@echo "#    source <(make env)"
	export ROUNDUP_STARLING_HOST=http://localhost:3000
	export ROUNDUP_ACCESS_TOKEN=not_used