## Project配置状态监听


### 说明
本文档主要是用于介绍如何在gradle中监听project中配置的完成状态并对其进行添加代码块以完成一些特定操作.


### 配置方式
具体的配置方式了解:[gradle 参数配置监听](http://www.enjoytoday.cn/posts/279)
+ project 配置
  通过build.gradle的project对象完成配置,本案例采用该种类方式.
  
+ gradle 配置
  通过gradle对象对于全局的project配置进行监听.
  
  
  
  
### 案例说明
案例采用配置两个发布task:debug和release通过控制发布task的不同从而显示不同的版本号码.案例配置如下:

```
task versionControl << {
    println "release success,and version is ${version}"
}


task debug(dependsOn:versionControl) << {
    print "current version is debug."
}


task release(dependsOn:versionControl) << {
    print "current version is release."
}


this.afterEvaluate(new Action<Project>() {
    @Override
    void execute(Project project) {
        println "project configuration over."
        project.gradle.whenReady{  taskGraph ->
            println "this is taskGraph whenReady."
            if (taskGraph.hasTask(debug)) {
                println "this is a debug version"
                project.version = "debug"
            } else if (taskGraph.hasTask(release)) {
                println "this is a release version"
                project.version = "release"
            }

        }
    }
})


```

如上,创建了三个task任务versionControl、debug、release.其中versionControl用于输出版本号的.debug和release都
依赖于versionControl(dependsOn:versionControl).afterEvaluate在配置结束后执行,通过taskGraph(执行任务的一个
视图集合)对当前的执行任务判断,若包含对应发布版本对版本进行对应设置.



- 运行测试


```
#debug
caihaifei@hfcai:# gradle debug
project configuration over.
this is taskGraph whenReady.
this is a debug version
:versionControl
release success,and version is debug
:debug
current version is debug.
BUILD SUCCESSFUL


#release
caihaifei@hfcai:~# gradle release
project configuration over.
this is taskGraph whenReady.
this is a release version
:versionControl
release success,and version is release
:release
current version is release.
BUILD SUCCESSFUL

Total time: 0.534 secs


```


如上,配置完成后回调afterEvaluate,通过设置task设置运行task准备回调设置发布版本号.



### 相关配置介绍
- [task](../../../../tree/master/projectTask)：gradle的一个运行单元,任务的创建
- dependsOn:任务的依赖表示
- taskGraph:所有待运行task的集合.
- whenReady:taskGraph装载成功回调.
