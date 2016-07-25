FROM filipecabaco/sbt:0.13.12

RUN apk --update add git

RUN mkdir -p /tmp/basset-hound /var/basset-hound

#Clone/ Compile/ Cleanup Basset-Hound files
WORKDIR /tmp/basset-hound
RUN git clone https://github.com/filipecabaco/basset-hound.git . && \
    sbt assembly && \
    cp ./target/scala-2.11/bassethound-assembly-0.1.jar /var/basset-hound/basset-hound.jar && \
    rm -r /tmp/*

WORKDIR /var/basset-hound/

#Set up volume that will be used by basset-hound to run its scan
VOLUME /tmp

CMD ["java", "-jar" , "basset-hound.jar", "-f" ,"/tmp"]
