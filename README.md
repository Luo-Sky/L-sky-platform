#L-sky-platform

## 系统描述

珞Sky遥感智能解译平台，包含目标检测、目标提取、地物分类、变化检测四个主要功能。

此项目为该平台的后端部分。

### 技术栈

- 数据库：mysql，redis（用于邮箱认证）
- 持久化层：mybatis，mybatis-plus
- mvc框架：springmvc
- 应用层容器：springboot

## 分层架构
- API文档说明：[API文档链接](https://www.apifox.cn/apidoc/shared-d515d3eb-29cf-49a5-b604-dab28962306b/api-19186503)

- 后端基于MVC架构，分为`Controller`层，`Service`层，`Dao`层经典三层架构。

    - `Controller`层

        采取Restful风格，`Controller`层均使用`@RestController`注解，前后端交互使用JSON格式数据，定义的数据格式详见springboot项目文件夹下的源代码里的common文件夹下的`Result.java`,统一格式为`Result`类的类型格式。

    - `Service`层

        利用mybatis-plus，继承其提供的服务类，省去基本的单表的增删改查操作，获得封装好的函数。

    - `Dao`层

        数据库使用mysql进行存储，并利用mybatis-plus进行架构，省去基本的单表的数据库操作



## 使用方法

### 后端运行

- 配置`mysql`数据库

    `mysql`版本要求：8.0以上

    运行springboot项目文件夹下的resource目录（src/main/resources）下的`platform.sql`文件导入数据库。

- 修改项目配置文件

    - 修改`resource`目录下的`application.yml`中的`datasource`配置中的`url`,`username`,`password`:

        ```yml
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: your database url (jdbc:mysql://localhost:3306/webservice?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC)
            username: your username
            password: your password
        ```

    - 配置邮箱认证的功能。

        - 修改`resource`目录下的`application.yml`中的`mail`配置中的相关信息。这里使用了qq邮箱的pop3/smtp服务，可以去开启将得到的密码输入下面配置(注意不是qq邮箱密码)

            ![image-20220620172440990](README.assets\image-20220620172440990.png)

            ```yml
             mail:
                host: smtp.qq.com
                port: 465
                email: xxxxxx@qq.com
                username: xxxxxx@qq.com
                password: 密码
                properties:
                  mail:
                    smtp:
                      ssl:
                        enable: true
            ```

            

        - 修改`resource`目录下的`application.yml`中的`redis`配置中的相关信息，该项目只配置了redis的服务在java服务本地

            ```yml
              redis:
                #数据库索引
                database: 0
                host: 127.0.0.1
                port: 6379(默认端口)
                password: yourpassword(默认没设置没有)
            ```

    - …..

- 运行主函数

    主函数位于`src`目录下`java`源代码里的`PlatformApplication.java`文件中的`PlatformApplication`类里

## 开发日志

2022/5/7 完成用户查询记录基本的一些功能

2022/6/20 完成邮箱认证以及对接基本的模型推理功能