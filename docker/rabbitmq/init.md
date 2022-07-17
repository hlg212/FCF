# 启动 management 插件
```
docker exec -it rabbitmq rabbitmq-plugins enable rabbitmq_management
```

# 安装rz命令
```
yum install lrzsz
```

# 启动延时插件
```
docker exec -it rabbitmq rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```

# 开启事件插件
```
docker exec -it rabbitmq rabbitmq-plugins enable rabbitmq_event_exchange
```

# 开启mqtt
```
docker exec -it rabbitmq rabbitmq-plugins enable rabbitmq_web_mqtt
```

# channels 报错
`Stats in management UI are disabled on this node`
进入容器，开启
```
docker exec -it rabbitmq /bin/bash

cd /etc/rabbitmq/conf.d/
echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf
```
