FROM ubuntu:latest
RUN
ADD /src
tripPricer, gpsUtil...
RUN ?
&& apt-get clean \
EXPOSE 9001
VOLUME /src
CMD npm RUN START