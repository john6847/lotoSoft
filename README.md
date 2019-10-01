# New way to deploy app
- create jar with 'mvn package'
- after that copy file to user bin 'sudo cp loteriab.r.jar /usr/local/bin'

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

