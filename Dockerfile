FROM eclipse-temurin:latest
# ADD . /app
WORKDIR /code

ARG MAVEN_VERSION=3.9.6

RUN set -x \
    && apt-get update \
    && wget https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -P /tmp \
    && tar -zxvf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt/ \
    && rm /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

ENV M2_HOME=/opt/apache-maven-3.9.6/bin
ENV PATH=$PATH:$M2_HOME

VOLUME /code
CMD ["/bin/bash"]
