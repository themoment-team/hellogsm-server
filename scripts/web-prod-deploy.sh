#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/builds/hellogsm-web/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)

DEPLOY_PATH=/home/ec2-user/deploy/
cp $BUILD_JAR $DEPLOY_PATH

CURRENT_PID=$(pgrep -f $JAR_NAME)
if [ -z $CURRENT_PID ]
then
  echo "현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME

chmod +x $DEPLOY_JAR

nohup java -jar $DEPLOY_JAR --spring.profiles.active=prod &