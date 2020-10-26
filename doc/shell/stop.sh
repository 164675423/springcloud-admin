#!/bin/bash

read -p "输入服务名:" APP_NAME

basepath=$(cd `dirname $0`; pwd)
cd ${basepath}

echo "************************************************************"
echo "******************** stop app info*************************"
echo "*The app name is: "${APP_NAME}
echo "************************************************************"
echo "                                                            "

PIDS=`ps -ef | grep java | grep "${APP_NAME}" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
  echo "INFO: The ${APP_NAME} does not started!"
  exit 0
fi

echo -e "Stopping the ${PWD} ...\c"
for PID in $PIDS ; do
  kill -9 $PID
done

COUNT=0
while [ $COUNT -lt 1 ]; do
  echo -e ".\c"
  sleep 1
  COUNT=1
  for PID in $PIDS ; do
    PID_EXIST=`ps -f -p $PID | grep java`
    if [ -n "$PID_EXIST" ]; then
      COUNT=0
      break
    fi
  done
done

echo "OK!"
echo "PID: $PIDS has stopped!!!"
