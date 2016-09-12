# Makefile: {{ cookiecutter.project_name }}

SERVICE_NAME=$(shell cat Datawirefile | python -c 'import sys, json; print json.load(sys.stdin)["service"]["name"]')
SERVICE_VERSION=$(shell cat Datawirefile | python -c 'import sys, json; print json.load(sys.stdin)["service"]["version"]')

DOCKER_REGISTRY_HOST={{ cookiecutter.docker_registry_host }}
DOCKER_REPO={{ cookiecutter.docker_organization }}/$(SERVICE_NAME)

.PHONY: all

all: clean build

build:
	./gradlew test shadowJar

clean:
	./gradlew clean

compile:
	./gradlew build

docker-build:
	docker build -t $(DOCKER_REPO):$(SERVICE_VERSION) .

docker-sh:
	( \
		docker run -it --rm \
		--name $(SERVICE_NAME)-shell \
		--entrypoint /bin/sh \
		$(DOCKER_REPO):$(SERVICE_VERSION) \
	)

docker-run: docker-build
	( \
		docker run \
		-it \
		--rm \
		--name $(SERVICE_NAME)-$(SERVICE_VERSION) \
		-p 5000:5000 \
		-v $$(pwd)/config:/opt/{{ cookiecutter.project_name }}/config \
		$(DOCKER_REPO):$(SERVICE_VERSION) \
	)
 
test:
	./gradlew test
 
unit-test:
	./gradlew test

version:
	@echo SERVICE_VERSION

service-name:
	@echo SERVICE_NAME