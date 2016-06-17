FROM lwieske/java-8
VOLUME /tmp
ADD openshift-gray-flter-0.1.0.jar openshift-gray-flter-0.1.0.jar
RUN bash -c 'touch /openshift-gray-flter-0.1.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/openshift-gray-flter-0.1.0.jar"]
expose 8081
