FROM openjdk:8-jdk-alpine

WORKDIR /opt/{{ cookiecutter.project_name }}
COPY    . ./

RUN apk --no-cache add --virtual .build-deps \
        bash \
        libstdc++ \
    && ./gradlew shadowJar \
    && apk del .build-deps \
    && mv build/libs/{{ cookiecutter.project_name }}*.jar ./{{ cookiecutter.project_name }}.jar \
    && chmod +x entrypoint.sh \
    && rm -rf src build gradle .vertx .gradle .git .gitignore .dockerignore Dockerfile

EXPOSE 5000
ENTRYPOINT ["./src/main/shell/entrypoint-docker.sh"]
