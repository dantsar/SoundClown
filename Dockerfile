FROM eclipse-temurin:latest
# ADD . /app
WORKDIR /code

RUN set -x \
    && apt-get update \
    && apt-get install unzip \
    && VERSION=8.5 \
    && wget https://services.gradle.org/distributions/gradle-${VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${VERSION} /opt/gradle/latest \
    && rm /tmp/gradle-${VERSION}-bin.zip \
    && wget https://downloads.apache.org/ant/binaries/apache-ant-1.10.13-bin.tar.gz -P /tmp \
    && tar -xvzf /tmp/apache-ant-1.10.13-bin.tar.gz -C /opt


ENV GRADLE_HOME=/opt/gradle/latest/bin/
ENV PATH=$PATH:$GRADLE_HOME

ENV ANT_HOME=/opt/apache-ant-1.10.13
ENV PATH=$PATH:$ANT_HOME/bin

VOLUME /code
CMD ["/bin/bash"]
