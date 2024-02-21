.PHONY: up down all

all: up

up:
	docker-compose pull
	docker-compose up -d
	docker image prune -f

down:
	docker-compose down

up-user:
	docker-compose -f docker-compose-user.yml pull
	docker-compose -f docker-compose-user.yml up -d
	docker image prune -f

down-user:
	docker-compose -f docker-compose-user.yml down
