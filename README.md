Hades
====
> 基于zookeeper的配置中心

![](https://raw.githubusercontent.com/zcfrank1st/hades/master/logo/hades.png)
    

* 描述

    **hades-core:** hades项目的核心模块

    **hades-client:** 基于spring的hades客户端和基于配置文件的hades客户端
    
    **hades-restful-client:** 提供restful接口获取配置

    **hades-web:** 供 [hades-chrome-extension](https://github.com/zcfrank1st/hades-chrome-extension) 使用

* 如何使用
    > 项目使用`gradle 3.2.1` 编译
    
    [hades-core]
    
        基本不单独使用，为后续模块提供核心功能
    
    [hades-client]
    
        使用方式见hades-client模块test目录示例
    
    [hades-restful-client]
    
        使用 gradle :hades-restful-client:distZip 编译可执行 jar
    
        获取特定配置：
        Request： GET /config/{env}/{project}/{key}/
        Response： 
        {
            "code": int,  // 0 成功 1 失败
            "value": string // 正常配置或““
        }
        
        获取特定环境项目的全部配置：
        Request: GET /configs/{env}/{project}/
        Response:
        {
            "code": int, // 0 成功 1 失败
            "value": map
        }
        
    [hades-web]
    
        使用 gradle :hades-web:bootRepackage 编译可执行 jar
    
        基本不单独使用，为[hades-chrome-extension]项目提供后端服务
        
        
    
* LICENCE

    MIT

* TODO

    * 使用zookeeper的watch机制，热更新配置
