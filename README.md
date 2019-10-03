# New way to deploy app
- create jar with 'mvn package'
- after that copy file to user bin 'sudo cp loteriab.r.jar /usr/local/bin'
- ressource 'https://www.sma.im/how-to-run-your-java-springboot-app-jar-war-as-systemd-service-on-linux/'

If you make some change to the myapp.service
- sudo systemctl enable myapp.service
- sudo service myapp start
- sudo service myapp stop
- sudo service myapp status
- journalctl -u myapp.service 
# Nginx
- systemctl restart nginx



# Quick Run
- http://appsdeveloperblog.com/run-spring-boot-app-from-a-command-line/
- mvn spring-boot:run


# lotoSoft
- git pull
- mvn compile
- mvn clean install
- mvn clean package # create a jar file
- java -jar loteriab.r-0.0.1-SNAPSHOT.jar
- https://www.vogella.com/tutorials/ApacheMaven/article.html

- kill service: 
- [service number for now] -> 10677
- kill -9 [service number]

