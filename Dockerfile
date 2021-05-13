FROM openjdk

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/financas-pessoais

COPY /target/financas-pessoais*.jar financas-pessoais.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080
EXPOSE 5005

CMD java ${ADDITIONAL_OPTS} -jar financas-pessoais.jar --spring.profiles.active=${PROFILE}