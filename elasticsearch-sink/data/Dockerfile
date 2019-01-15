FROM java:8
 
RUN wget http://mirror.intergrid.com.au/apache//jmeter/binaries/apache-jmeter-5.0.tgz
RUN tar -xvzf apache-jmeter-5.0.tgz
RUN rm apache-jmeter-5.0.tgz

RUN mv apache-jmeter-5.0 /jmeter

ENV JMETER_HOME /jmeter

# Add Jmeter to the Path
ENV PATH $JMETER_HOME/bin:$PATH
WORKDIR $JMETER_HOME

COPY pepper-box-1.0.jar /jmeter/lib/ext 
COPY insert-data-jmeter.jmx /data/

CMD ["jmeter", "-n", "-t", "/data/insert-data-jmeter.jmx"]