FROM java:openjdk-8-jre-alpine
MAINTAINER Datawire Inc, <dev@datawire.io>

# Exposed Ports
# ----------------------------------------------------------
# 5000  : {{ cookiecutter.project_name }}
# 5701  : Hazelcast Clustering
EXPOSE 5000 5701

LABEL PROJECT_REPO_URL = "{{ cookiecutter.repo_url }}" \
      PROJECT_REPO_BROWSER_URL = "{{ cookiecutter.repo_browser_url }}" \
      PROJECT_LICENSE = "{{ cookiecutter.repo_browser_url }}/LICENSE" \
      DESCRIPTION = "Datawire {{ cookiecutter.name }}" \
      VENDOR = "Datawire" \
      VENDOR_URL = "https://datawire.io/"

RUN apk --no-cache add \
    bash \
  && ln -snf /bin/bash /bin/sh

RUN mkdir /var/log/datawire

WORKDIR /opt/{{ cookiecutter.project_name }}/

COPY {{ cookiecutter.project_name }}/build/libs/flobber-web-*-fat.jar ./{{ cookiecutter.project_name }}.jar

COPY {{ cookiecutter.project_name }}/src/docker/entrypoint.sh ./entrypoint.sh
RUN  chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
