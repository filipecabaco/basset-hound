FROM java:7


RUN mkdir -p /tmp/basset-hound /tmp/sbt /var/basset-hound

# Install sbt
WORKDIR /tmp/sbt
ADD https://dl.bintray.com/sbt/native-packages/sbt/0.13.11/sbt-0.13.11.tgz .
RUN tar -xvf /tmp/sbt/sbt-0.13.11.tgz

#Clone/ Compile/ Cleanup Basset-Hound files
#TODO - Remove the checkout command-line before merge
WORKDIR /tmp/basset-hound
RUN git clone https://github.com/filipecabaco/basset-hound.git . && \
    git checkout command-line && \
    /tmp/sbt/sbt/bin/sbt assembly && \
    cp ./target/scala-2.11/bassethound-assembly-0.1.jar /var/basset-hound/basset-hound.jar && \
    rm -r /tmp/*

WORKDIR /var/basset-hound/

#Set up volume that will be used by basset-hound to run its scan
VOLUME /tmp

CMD ["java", "-jar" , "basset-hound.jar", "/tmp"]
