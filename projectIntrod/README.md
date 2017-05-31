## 说明

本篇用于介绍gradle当中的Project 接口,Project 作为gradle的核心接口、主API接口，默默的为gradle工程做了不少
事情,而我们大多用到的也是Project接口提供的相关功能.

gradle示例源码:[GitHub](https://github.com/fishly/gradleProject/tree/master/projectIntrod)


## 基本概念

gradle默认以build.gradle脚本为识别一个gradle工程的参考(build.gradle是默认构建脚本，我们也可以指定脚本名称)，
而gradle构建脚本与Project为一对一的关系,也可以理解:一个gradle构建脚本本身就是一个Project对象,我们也可以通过官方
文档了解Project的生命周期:

``` 
  * Lifecycle
  *
  * There is a one-to-one relationship between a Project and a {@value #DEFAULT_BUILD_FILE}
  * file. During build initialisation, Gradle assembles a Project object for each project which is to
  * participate in the build, as follows:
  * 1) Create a {@link org.gradle.api.initialization.Settings} instance for the build.
  * 2) Evaluate the {@value org.gradle.api.initialization.Settings#DEFAULT_SETTINGS_FILE} present, 
  *     against the {@link org.gradle.api.initialization.Settings} object to configure it.
  *  3) Use the configured {@link org.gradle.api.initialization.Settings} object to create the hierarchy of
  *     Project instances.
  *  4) Finally, evaluate each Project by executing its {@value #DEFAULT_BUILD_FILE} file, if present,
  *     against the project. The project are evaluated in breadth-wise order, such that a project is evaluated
  *     before its child projects. This order can be overridden by calling {@link #evaluationDependsOnChildren()}
  *     or by adding an explicit evaluation dependency using {@link #evaluationDependsOn(String)}.
```

写了这么多其实就是三句话：1)build.gradle(默认状态)与Project一一对应；2)每个Project都会创建一个Settings接口,用于导入
所有的子工程(子工程根据检索其构架脚本build.gradle进行导入)；3）子工程依赖于该project的配置.在开始下面的话题前，先介绍一个查看
当前工程的目录结构(主工程以及子工程的工程结构)的命令,命令如下:





                       