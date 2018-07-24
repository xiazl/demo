
#  CDN日志分析
## 说明
### 1. 安装包
    #### 1 hbase-2.0.1
    #### 2 mongo-4.0.0
    #### 3 phoenix-5.0.0-HBase-2.0
   
    
### 2.jar打包与启动
    mvn clean package 
    #### 2 启动方式1
    java -jar AdminBoard.jar --spring.profiles.active=${profile.active}
    #### 1 启动方式2
    nohup java -jar AdminBoard.jar --spring.profiles.active=${profile.active} &
	