## 说明

本篇用于介绍gradle当中的Project 接口,Project 作为gradle的核心接口、主API接口，默默的为gradle工程做了不少
事情,而我们大多用到的也是Project接口提供的相关功能.

gradle示例源码:[GitHub](https://github.com/fishly/gradleProject/tree/master/projectIntrod)


## project

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

```
hfcai@ubuntu:~$ gradle projects
:projects

------------------------------------------------------------
Root project
------------------------------------------------------------

Root project 'gradleProject'
+--- Project ':SimpleMultiProject'
+--- Project ':gradleWrapperProject'
\--- Project ':projectIntrod'

To see a list of the tasks of a project, run gradle <project-path>:tasks
For example, try running gradle :SimpleMultiProject:tasks

BUILD SUCCESSFUL
```


### project参数配置
project在运行task之前会先配置一些基本的project属性,如gradle文件名、project名称、路径等信息,project 提供两个方法
以供我们运行一些特定的代码块,project.



- project状态监听

  gradle提供了对project状态配置监听的接口回调,以方便我们来配置一些Project的配置属性,监听主要分为两大类,一种是通过project进行 回调，一种是通过gradle进行回调,作用域也有不同
  ,project是只针对当前project实现进行的监听,gradle监听是针对于所有的project而言的。接下来就其方式和具体的实现进行介绍说明。
  
  1) project回调
  
  Project api给我们提供了两个方法对当前project配置状态进行回调afterEvaluate(project开始配置前调用)和beforeEvaluate,afterEvaluate(project配置完成后回调)
，需要注意的我们所说的添加的代码块回调可以添加多次，运行顺序按照添加的顺序执行(同样使用于下面所说的所有回调).

  - beforeEvaluate
    这个方法很迷惑人,他是确实存在与Project API调用中的,API方法调用参数有两类,如下所示:
    
    ```
        /**
         * Adds an action to execute immediately before this project is evaluated.
         *
         * @param action the action to execute.
         */
        void beforeEvaluate(Action<? super Project> action);
        
        
            /**
             * <p>Adds a closure to be called immediately before this project is evaluated. The project is passed to the closure
             * as a parameter.</p>
             *
             * @param closure The closure to call.
             */
        void beforeEvaluate(Closure closure);


    ```

   方法说的很清楚是配置之前调用,但你要是直接当前build.gradle中使用是肯定不会调用到的,因为Project都没配置好还有他什么事情(也是无奈),这个代码块的添加只能放在
   父工程的build.gradle中,如此才可以调用的到,使用方法如下:
   
   ```
   
   this.project.subprojects { sub ->
   
       if(sub.path.endsWith("test")){
           sub.afterEvaluate {
               println "Evaluate before of "+sub.path
           }
       }
   }
     
   ```

这是比较简单的写法,通过遍历子工程并对其设置添加afterEvaluate回调,需要注意的是afterEvaluate的Closure方式其实是有传递一个参数的,只是我把他省略了，其实也可以加上，
也可以对上面的方法做些调用,使用如下写法实现调用(subprojects和allprojects均可,看个人习惯,就不一一列出)：

  ```
  
this.project.getSubprojects().each { sub ->

    if(sub.path.endsWith("test")){
        sub.afterEvaluate(new Action<Project>() {
            @Override
            void execute(Project project) {
                println "Evaluate before of "+sub.path
            }
        })

    }
}

//或者如下方式

this.project.subprojects{ sub ->
    if(sub.path.endsWith("test")){
        sub.afterEvaluate { p ->
            println "subprojects getAt Evaluate before of "+p.path

        }

    }
}

  ```



 - afterEvaluate
  afterEvaluate是一般比较常见的一个配置参数的回调方式,只要project配置成功均会调用,参数类型以及写法与afterEvaluate相同,示例如下所示:
  
  ```
  this.project.afterEvaluate{
      println "this.project.afterEvaluate"
      //这里可以添加一些版本控制信息
  }
  ```


 2) gradle回调
   可以通过project获取当前的gradle对象,gradle设置的回调监控的是所有的project实现.方式可以是通过gradle配置回调代码块,或者也可以通过添加监听接口方式获取回调监听.
   - 通过gradle直接添加回调,代码如下:
   
   ```
   
 gradle.afterProject {project,projectState ->
     if(projectState.failure){
         println "Evaluation afterProject of $project FAILED"
     } else {
         println "Evaluation afterProject of $project succeeded"
     }
 }


gradle.beforeProject { project ->
    println "Evaluation before of $project"

}
   ```
 
  需要注意,两种方法的传入参数有差异,afterProject在配置参数失败后会传入两个参数,后者显示失败信息.
  
  - 通过gradle 设置接口监听添加回调
    直接通过这是对工程的监听回调接口获取回调.同上，作用的对象均是所有的project实现,代码格式如下所示:
    
    ```
       gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
           @Override
           void beforeEvaluate(Project project) {
               println " add project evaluation lister beforeEvaluate,project path is: $project"
           }
       
           @Override
           void afterEvaluate(Project project, ProjectState state) {
               println " add project evaluation lister afterProject,project path is: $project"
           }
       }

    ```


   如上通过gradle设置监听的若需要指定特定对象操作的话,需要自己添加过滤规则.本篇结束,未完待续......