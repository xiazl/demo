
#  CDN日志分析
## 说明
    
### 1.jar打包与启动
    mvn clean package 
    #### 2 启动方式1
    java -jar AdminBoard.jar --spring.profiles.active=${profile.active}
    #### 1 启动方式2
    nohup java -jar AdminBoard.jar --spring.profiles.active=${profile.active} &
	