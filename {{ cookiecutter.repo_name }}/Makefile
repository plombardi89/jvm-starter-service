# Makefile: {{ cookiecutter.project_name }}

VERSION=$(shell cat VERSION)

.PHONY: all

all: clean build

build:
	# Produces a language build artifact (e.g.: .jar, .whl, .gem).
	./gradlew build test shadowJar
 
docker: build
	# Produces a Docker image.
	docker build -t datawire/{{ cookiecutter.project_name }}:$(VERSION) .

docker-no-jar-build:
	# Produces a Docker image but do not trigger recompilation and packaging of the source.
	docker build -t datawire/{{ cookiecutter.project_name }}:$(VERSION) .

clean:
	# Clean previous build outputs (e.g. class files) and temporary files.
	./gradlew clean
 
compile:
	# Compile code (may do nothing for interpreted languages).
	./gradlew build

run-docker: docker
	# Run the service or application in docker.
	( \
		docker run -it --rm --name datawire-{{ cookiecutter.project_name }} \
		-p 5000:5000 \
		-v $$(pwd)/{{ cookiecutter.project_name }}-web/config:/opt/{{ cookiecutter.project_name }}/config \
		datawire/{{ cookiecutter.project_name }}:$(VERSION) \
	)

run-docker-no-jar-rebuild:
	# Run the service or application in docker.
	( \
		docker run -it --rm --name datawire-{{ cookiecutter.project_name }} \
		-p 5000:5000 \
		-v $$(pwd)/{{ cookiecutter.project_name }}-web/config:/opt/{{ cookiecutter.project_name }}/config \
		datawire/{{ cookiecutter.project_name }}:$(VERSION) \
	)
 
test:
	./gradlew test
 
unit-test:
	./gradlew test

version:
	@echo VERSION