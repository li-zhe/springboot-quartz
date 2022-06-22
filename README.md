# 项目说明

本项目为独立运行的定时任务工具。<br />
基于SpringBoot整合了Quartz，项目拆解自ruoyi微服务项目。<br />
此外做了些调整，移除了用户系统和权限，将vue生成的前端资源融合进java工程。可独立作为一个工具运行。
如果需要使用的场景中，定时执行的任务较为简单，建议直接使用Linux系统中的crontab。
<br />

# 主要用到的技术

+ 前端采用Vue2.0、WebPack、Element UI。
+ 后端采用Spring Boot。
<br />

# 问题：如何启动本系统？

1、将quartz.sql文件在MySQL运行生成表和数据<br />
2、修改配置文件中数据库的用户名和密码<br />
3、启动SpringbootQuartzApplication类后访问http://localhost:8080 就可以进入管理页面！<br />


# 功能展示

<img width="1676" alt="WX20220622-133007@2x" src="https://user-images.githubusercontent.com/19565705/174955229-8f0ed284-dc31-4113-b026-905e2a6238fe.png">
<img width="1672" alt="Snipaste_2022-06-22_13-30-30" src="https://user-images.githubusercontent.com/19565705/174955653-a67e7fb8-1e28-49df-8105-3ff2db522cfa.png">
<img width="1678" alt="image" src="https://user-images.githubusercontent.com/19565705/174955625-c7723e90-bd76-4d20-b6b2-677e568b91be.png">
