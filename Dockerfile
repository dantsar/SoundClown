FROM eclipse-temurin:latest
ADD . /app
WORKDIR /app

RUN set -x \
    && apt-get update \
    && apt-get install unzip \
    && VERSION=6.5.1 \
    && wget https://services.gradle.org/distributions/gradle-${VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${VERSION} /opt/gradle/latest \
    && rm /tmp/gradle-${VERSION}-bin.zip

ENV GRADLE_HOME=/opt/gradle/latest/bin/
ENV PATH=$PATH:$GRADLE_HOME


# VOLUME /code
CMD ["/bin/bash"]
