##Blog>>>progress
1. 代码架构完成
2. 数据库配置完成
3. 相关表建立完成
4. 接口开发


###接口开发进度

+ Article测试通过

+ BlogFile 测试通过

+ MessageIO 测试通过

+ othertidings 测试通过  

+ Menu  待测试




接下来完成menu的Dao

service层与view层的开发
20号
service与controller层的测试基类创建完成,测试相应的service与controller
+ BlogContentService 测试通过
+ BlogFileSerivce   测试通过
+ MessageIOService  测试通过
+ OthertidingService 测试通过
blongContentService
业务分析:主要是对Article即相关博文进行管理。
主要的管理逻辑应该有
+ 博文添加
+ 博文加载
+ 博文修改
+ 博文删除（分逻辑删除与实体删除）

BlogFileSerivce
业务分析：主要是针对的是博客中的一些文件的上传与下载等管理
主要的管理逻辑应该有
+ 文件的上传 上传时即要上传文件至服务器指定目录，还得保存相关上传文件的相关信息
+ 文件的下载
+ 文件的存放(名称，存放地址，大小，类型)
+ 文件的删除(逻辑删除、实体删除)

MessageIOService
业务分析:主要针对的是消息交流模块
主要的管理逻辑应该有:
+ 添加留言
+ 修改留言
+ 删除留言(逻辑删除、实体删除)
+ 加载留言

OthertidingService 
业务分析:主要是用针对一些非主要的文本，如公告，新闻等，与博文区分。
主要的管理逻辑：与blogConent类似


MenuDao



