FROM openjdk:8-jre-slim

RUN apt-get update

RUN apt-get install -y \
	libgl1-mesa-dri \
	libgl1-mesa-glx \
	vlc \
	--no-install-recommends

RUN rm -rf /var/lib/apt/lists/*

ENV HOME /home/vlc

RUN useradd --create-home --home-dir $HOME app \
	&& chown -R app:app $HOME \
	&& usermod -a -G audio,video app

USER app

WORKDIR /home/app

COPY build/libs/scanner*.jar application.jar

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar -Dspring.profiles.active=prod application.jar" ]