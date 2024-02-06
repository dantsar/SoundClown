FROM eclipse-temurin:latest
# ADD . /app
WORKDIR /code

RUN set -x \
    && apt-get update \
    && apt-get install unzip \
    && apt-get install --assume-yes git ffmpeg \
    && VERSION=8.5 \
    && wget https://services.gradle.org/distributions/gradle-${VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${VERSION} /opt/gradle/latest \
    && rm /tmp/gradle-${VERSION}-bin.zip

    # ABORTED THIS PROJECT
    # && wget https://downloads.apache.org/ant/binaries/apache-ant-1.10.13-bin.tar.gz -P /tmp \
    # && tar -xvzf /tmp/apache-ant-1.10.13-bin.tar.gz -C /opt \
    # && git clone https://JorenSix@github.com/JorenSix/TarsosTranscoder.git /opt/TarsosTranscoder \
    # && cd /opt/TarsosTranscoder/build \
    # && /opt/apache-ant-1.10.13/bin/ant


ENV GRADLE_HOME=/opt/gradle/latest/bin/
ENV PATH=$PATH:$GRADLE_HOME

ENV ANT_HOME=/opt/apache-ant-1.10.13
ENV PATH=$PATH:$ANT_HOME/bin

VOLUME /code
CMD ["/bin/bash"]
