FROM openjdk:8-alpine


ENV APP_EXECUTABLE="app.jar"
ENV SERVER_PORT=8080

#создаем папку, где будет лежать наша программа
RUN mkdir -p /springboot-app

#идем в папку
WORKDIR /springboot-app

#копируем все файлы из текущего пути к файлу DOCKER на вашей системе в нашу новую папку докера
COPY /target/task17-1.0.jar /springboot-app/${APP_EXECUTABLE}

#пробрасываем порт вашей программы наружу образа
EXPOSE ${SERVER_PORT}

CMD java -Dfile.encoding=UTF-8 -jar ${APP_EXECUTABLE}
