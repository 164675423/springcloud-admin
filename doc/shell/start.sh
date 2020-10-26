#!/bin/bash

read -p "输入服务名:" APP_NAME
read -p "是否加入skywalking [y/n] " joinskywalking

basepath=$(cd `dirname $0`; pwd)
cd ${basepath}

# SkyWalking Agent 配置
export SW_AGENT_NAME=${APP_NAME} # 配置 Agent 名字。一般来说，我们直接使用 Spring Boot 项目的 `spring.application.name` 
export SW_AGENT_COLLECTOR_BACKEND_SERVICES=111.229.165.30:11800 # 配置 Collector 地址。
export JAVA_AGENT=-javaagent:/opt/agent/skywalking-agent.jar # SkyWalking Agent jar 地址

JAVA_OPTS="-Xms512m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS2="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./"

echo "******************** JAVA info*************************"
echo "*JAVA_OPTS:"${JAVA_OPTS}
echo "*PWD      :"${PWD}
echo "*APP_NAME :"${APP_NAME}
echo "*******************************************************"
echo "                                                       "

if [ "$joinskywalking" = "y" ] || [ "$joinskywalking" = "Y" ]; then
 echo "******************** start with skywalking *************************"
 nohup java -Dfile.encoding=utf-8 ${JAVA_OPTS2} ${JAVA_AGENT} -jar ${PWD}/${APP_NAME}.jar > /dev/null &
else 
  echo "******************** start without skywalking *************************"
  nohup java -Dfile.encoding=utf-8 ${JAVA_OPTS2} -jar ${PWD}/${APP_NAME}.jar > /dev/null &
fi

COUNT=0
while [ $COUNT -lt 1 ]; do
  sleep 1
  COUNT=`ps -ef | grep ${PWD} | grep java | wc -l`
  if [ $COUNT -gt 0 ]; then
    break
  fi
done

PIDS=`ps -ef | grep java | grep "${PWD}"`
echo "The running PID: $PIDS" 
