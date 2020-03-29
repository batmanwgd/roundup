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
		'java -jar /app/target/roundup-0.0.1-SNAPSHOT.jar'

test:
	docker-compose run --rm roundup 'mvn test'
