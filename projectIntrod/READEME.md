## Gradle脚本配置介绍之Project



### 说明
本篇主要用于介绍如何使用build.gradle工程所需用到的有关project的配置,仅限于project的配置.




### 介绍
project是gradle配置中的一个核心API接口库.他提供包括但不仅包括:对构建脚本的依赖库的配置、任务task的创建、依赖的
配置、设置编译生成目录、工程发布等等配置功能.如下,我会根据配置功能的特性将其分类介绍,并通过具体的示例来示范验证该
功能的使用.



### 示例介绍
+ [Project配置状态监听](projectStateDemo)
+ [Project构建脚本配置](projectBuildScriptDemo)
+ [Project依赖管理配置](projectRelyDemo)
+ [Project配置Ant](projectAntDemo)
+ [Project多工程管理](../SimpleMultiProject)
+ [Project配置artifacts](projectArtifactsDemo)





### 相关参考
+ [个人博客Gradle专题](http://www.enjoytoday.cn/categorys/Gradle)
+ [CSDN博客Gradle相关](http://blog.csdn.net/chf1142152101/article/category/6944395)
+ [Gradle官方DSL API资料](https://docs.gradle.org/3.5/dsl/)


### 备注
相关工程资料正在整合中,还请耐心等待.