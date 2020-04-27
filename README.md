# springboot-admin

#### 介绍
本项目是一个前后端分离的管理系统，采用当前流行的技术开发，后端使用SpringBoot2.x，SpringCloud，结合Mybatis-plus，Redis，Kafka...等常用组件,前端采用React，Redux，Ant design构建的单页应用

* 目前已实现用户管理及授权管理，做到按钮级的动态权限控制
* 线上地址 http://118.178.254.170/ 账号guest 密码123123

#### 项目结构
* access-management 为权限管理，包含用户角色资源等，提供REST api
* eureka 为注册中心，进行服务发现与注册
* gateway 为api网关入口，进行统一的身份认证及鉴权
* file-storage 为文件服务，提供文件上传下载及存储功能
* web-client 为前端展示项目

#### 如何运行
* 后端:
       分别启动 eureka，gateway，access-management，如果需要debug可以访问 localhost:{host}/swagger-ui.html
* 前端:  
    * 1.执行 yarn install 安装依赖  
    * 2.调整 webpack.dev.js 中的代理地址为后端网关地址
    * 3.执行 yarn start 运行项目
