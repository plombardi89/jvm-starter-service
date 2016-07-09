FROM alpine:3.4
MAINTAINER Datawire Inc, <dev@datawire.io>
EXPOSE 5000

LABEL PROJECT_REPO_URL         = "{{ cookiecutter.repo_url }}"
LABEL PROJECT_REPO_BROWSER_URL = "{{ cookiecutter.repo_browser_url }}"
LABEL DESCRIPTION              = "Datawire {{ cookiecutter.name }}"
LABEL VENDOR                   = "Datawire"
LABEL VENDOR_URL               = "https://datawire.io/"

ENV JAVA_HOME=/usr/lib/jvm/default-jvm

RUN apk add --update bash && \
    apk add --no-cache openjdk8 && \
    ln -sf "${JAVA_HOME}/bin/"* "/usr/bin/" && \
    rm -rf /var/cache/apk/*

COPY {{ cookiecutter.project_name }}-web/build/libs/{{ cookiecutter.project_name }}-web-*-fat.jar /opt/{{ cookiecutter.project_name }}/
RUN  ln -s /opt/{{ cookiecutter.project_name }}/{{ cookiecutter.project_name }}-web-*-fat.jar /opt/{{ cookiecutter.project_name }}/{{ cookiecutter.project_name }}-web.jar

ENTRYPOINT ["java", \
            "-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory", \
            "-Dlogback.configurationFile="/opt/{{ cookiecutter.project_name }}/logback.xml", \
            "-jar",  "/opt/{{ cookiecutter.project_name }}/{{ cookiecutter.project_name }}-web.jar", \
            "-conf", "/opt/{{ cookiecutter.project_name }}/{{ cookiecutter.project_name }}.json"] 
