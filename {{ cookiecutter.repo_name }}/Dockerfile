FROM openjdk:8-jdk-alpine
MAINTAINER Datawire Inc, <dev@datawire.io>

LABEL PROJECT_REPO_URL         = "{{ cookiecutter.repo_url }}" \
      PROJECT_REPO_BROWSER_URL = "{{ cookiecutter.repo_browser_url }}" \
      PROJECT_LICENSE          = "{{ cookiecutter.repo_browser_url }}/LICENSE" \
      DESCRIPTION              = "Datawire {{ cookiecutter.name }}" \
      VENDOR                   = "Datawire Inc." \
      VENDOR_URL               = "https://datawire.io/"

WORKDIR /opt/{{ cookiecutter.project_name }}/
COPY    . ./

RUN apk --no-cache add --virtual .build-deps \
        bash \
        libstdc++ \
    && ./gradlew clean build \
    && apk del .build-deps \
    && mv build/libs/{{ cookiecutter.project_name }}*.jar ./{{ cookiecutter.project_name }}.jar \
    && rm -rf src build gradle .gradle .git .gitignore .dockerignore Dockerfile

ENTRYPOINT ["java", "-jar", "{{ cookiecutter.project_name }}.jar"]
