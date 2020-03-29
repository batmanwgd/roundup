unrootify:
	sudo chown -R $$(id -u):$$(id -g) .

up-roundup:
	docker-compose up --force-recreate -d roundup

up-stub:
	docker-compose up --force-recreate -d stub

into-roundup:
	docker-compose exec roundup bash

into-stub:
	docker-compose exec stub bash

run:
	docker-compose run --rm roundup \
	'mvn package spring-boot:repackage && java -jar /app/target/roundup-0.0.1-SNAPSHOT.jar'
