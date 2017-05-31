##说明
本工程是为了讲解gradle wrapper特性,使得gradle项目无需依赖系统gradle环境就可允许的功能

###gradle wrapper  目录结构

- gradlew 自动配置gradle wrapper工程环境脚本(linux)
- gradlew.bat 自动配置gradle wrapper工程环境脚本(window)
- gradle/wrapper/gradle-wrapper.jar gradle wrapper依赖jar包
- gradle/wrapper/gradle-wrapper.properties gradle wrapper属性文件


  如上所示,gradlew和gradlew.bat是不同系统中配置gradle临时运行环境的脚本文件。同时,在构建工程时也可代替gradle命令,实现gradle任务的执行.命令格式和gradle
  相同.而gradle/wrapper文件则是通过gradlew现在的gradle环境依赖。
  
  
### 如何实现一个gradle wrapper的搭建
  gradle内部默认内置一个wrapper任务,我们可以通过运行wrapper任务来创建gradle wrapper运行环境。同时,我们可以通过--gradle-version 参数来指定gradle版本，用以控制gradle编译版本，
  具体命令如下所示:
  ```
   caihaifei@hfcai:~$ gradle wrapper --gradle-version 2.10
   :wrapper
   
   BUILD SUCCESSFUL
   
   Total time: 1.505 secs
  
  ```
  也可以通过自定义一个wrapper任务来实现gradle wrapper的创建,但任务需要继承gradle中自带Wrapper任务.格式如下:
  
  ```
     task wrapper(type: Wrapper){
    
      gradleVersion = '2.3'
//    archiveBase = PathBase.GRADLE_USER_HOME         //默认 ${HOME_DIR}/.gradle
//    archivePath = 'wrapper/dists'
//    distributionBase = PathBase.GRADLE_USER_HOME    //默认 ${HOME_DIR}/.gradle
//    distributionPath = 'wrapper/dists'
//    distributionType = DistributionType.BIN
//    distributionUrl = "https\\://services.gradle.org/distributions/gradle-2.3-bin.zip"
//    gradleVersion = gradle.gradleVersion
//    jarFile = 'gradle/wrapper/gradle-wrapper.jar'
//    propertiesFile = "gradle/wrapper/gradle-wrapper.properties"
//    scriptFile = 'gradlew'
     }
  
  ```
 
  详细属性描述见[Wrapper](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.wrapper.Wrapper.html#org.gradle.api.tasks.wrapper.Wrapper:archivePath) API介绍。



