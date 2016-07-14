# Makefile: {{ cookiecutter.project_name }}

.PHONY: all

all: clean build

build:
	# Produces a language build artifact (e.g.: .jar, .whl, .gem).
	./gradlew build shadowJar
 
docker:
	# Produces a Docker image.
	./gradlew dockerize
 
deb:
	# Produces a Debian package.
 
clean:
	# Clean previous build outputs (e.g. class files) and temporary files.
	./gradlew clean
 
compile:
	# Compile code (may do nothing for interpreted languages).
	./gradlew compile
  
quark-compile:
	# Compiles Quark sources if there are any.
  
quark-install:
	# Compiles AND installs Quark language sources if there are any.
	
run-docker:
	# Startup the application for dev/testing/experimentation.
 
test:
	./gradlew test
 
unit-test:
	# Run only the unit tests.
