## Gradle之多工程创建

本例子是演示如何通过gradle构建一个多工程管理的Gradle工程.案例采用两个简单的java工程的构建作为展示.


## 需要用到的知识

+ groovy基本语法
+ java语言
+ gradle基本属性配置


## 配置流程
1) 创建settings.gradle
   settings.gradle是配置多工程的必须文件,其中需要配置需要使用构建的非当前工程,设置为当前工程的子工程.代码如下:
   
   ```
      include ':simpleJava1',':simpleJava2'
   
   ```
   
   这是settings.gradle基本写法,用于导入子工程,include导入的是工程的工程名,并不一定是gradle工程的路径.具体介绍
   可以查看Groovy对应的API{[org.gradle.api.initialization.Settings]{https://docs.gradle.org/3.5/dsl/org.gradle.api.initialization.Settings.html}}.
   
2) 创建当前项目的构建脚本build.gradle
    build.gradle脚本是构建工程的基础,每一个Gradle工程有且仅有一个构建脚本build.gradle(子工程中的build.gradle)
    并不包含在内.build.gradle中我们可以配置一些所有工程共同需要使用的功能,如依赖库路径等,代码如下:
    
```
    
subprojects{ sub ->

 apply plugin: 'java'

 //配置共有仓库
 repositories{
   mavenCentral()
 }

 //配置共有依赖
 dependencies {
  testCompile 'junit:junit:4.11'
 }




  sub.afterEvaluate{
        task B << {
            println "this is the shared task of sub project."
        }
    }
}



task A << {
  println "this is the unique task of main project."
}

    
````

如上,task A为主工程自己创建的一个任务,为其的单独拥有的,而subprojects中的配置则为所有子工程所共有,上面有一点需要
注意的是task B的创建方式,之所以是使用这种方式是为了能让我在子工程中也能找到task B.其实,我们也可以通过如下这种方式
创建:

```
subprojects{ 
   //...
     task B << {
               println "this is the shared task of sub project."
           }
   //....
}
```
这种方式其实也可以，并不影响gradle的运行，可以通过gradle tasks --all查看我们所创建的task是否符合依赖关系,结果显
示如下:

```
   caihaifei@hfcai:~# gradle tasks --all
   :tasks
   
   ------------------------------------------------------------
   All tasks runnable from root project
   ------------------------------------------------------------
   
   Build tasks
   -----------
   simpleJava1:assemble - Assembles the outputs of this project. [simpleJava1:jar]
   simpleJava2:assemble - Assembles the outputs of this project. [simpleJava2:jar]
   simpleJava1:build - Assembles and tests this project. [simpleJava1:assemble, simpleJava1:check]
   simpleJava2:build - Assembles and tests this project. [simpleJava2:assemble, simpleJava2:check]
   simpleJava1:buildDependents - Assembles and tests this project and all projects that depend on it. [simpleJava1:build]
   simpleJava2:buildDependents - Assembles and tests this project and all projects that depend on it. [simpleJava2:build]
   simpleJava1:buildNeeded - Assembles and tests this project and all projects it depends on. [simpleJava1:build]
   simpleJava2:buildNeeded - Assembles and tests this project and all projects it depends on. [simpleJava2:build]
   simpleJava1:classes - Assembles main classes.
       simpleJava1:compileJava - Compiles main Java source.
       simpleJava1:processResources - Processes main resources.
   simpleJava2:classes - Assembles main classes.
       simpleJava2:compileJava - Compiles main Java source.
       simpleJava2:processResources - Processes main resources.
   simpleJava1:clean - Deletes the build directory.
   simpleJava2:clean - Deletes the build directory.
   simpleJava1:jar - Assembles a jar archive containing the main classes. [simpleJava1:classes]
   simpleJava2:jar - Assembles a jar archive containing the main classes. [simpleJava2:classes]
   simpleJava1:testClasses - Assembles test classes. [simpleJava1:classes]
       simpleJava1:compileTestJava - Compiles test Java source.
       simpleJava1:processTestResources - Processes test resources.
   simpleJava2:testClasses - Assembles test classes. [simpleJava2:classes]
       simpleJava2:compileTestJava - Compiles test Java source.
       simpleJava2:processTestResources - Processes test resources.
   
   Build Setup tasks
   -----------------
   init - Initializes a new Gradle build. [incubating]
   wrapper - Generates Gradle wrapper files. [incubating]
   
   Documentation tasks
   -------------------
   simpleJava1:javadoc - Generates Javadoc API documentation for the main source code. [simpleJava1:classes]
   simpleJava2:javadoc - Generates Javadoc API documentation for the main source code. [simpleJava2:classes]
   
   Help tasks
   ----------
   buildEnvironment - Displays all buildscript dependencies declared in root project 'SimpleMultiProject'.
   simpleJava1:buildEnvironment - Displays all buildscript dependencies declared in project ':simpleJava1'.
   simpleJava2:buildEnvironment - Displays all buildscript dependencies declared in project ':simpleJava2'.
   components - Displays the components produced by root project 'SimpleMultiProject'. [incubating]
   simpleJava1:components - Displays the components produced by project ':simpleJava1'. [incubating]
   simpleJava2:components - Displays the components produced by project ':simpleJava2'. [incubating]
   dependencies - Displays all dependencies declared in root project 'SimpleMultiProject'.
   simpleJava1:dependencies - Displays all dependencies declared in project ':simpleJava1'.
   simpleJava2:dependencies - Displays all dependencies declared in project ':simpleJava2'.
   dependencyInsight - Displays the insight into a specific dependency in root project 'SimpleMultiProject'.
   simpleJava1:dependencyInsight - Displays the insight into a specific dependency in project ':simpleJava1'.
   simpleJava2:dependencyInsight - Displays the insight into a specific dependency in project ':simpleJava2'.
   help - Displays a help message.
   simpleJava1:help - Displays a help message.
   simpleJava2:help - Displays a help message.
   model - Displays the configuration model of root project 'SimpleMultiProject'. [incubating]
   simpleJava1:model - Displays the configuration model of project ':simpleJava1'. [incubating]
   simpleJava2:model - Displays the configuration model of project ':simpleJava2'. [incubating]
   projects - Displays the sub-projects of root project 'SimpleMultiProject'.
   simpleJava1:projects - Displays the sub-projects of project ':simpleJava1'.
   simpleJava2:projects - Displays the sub-projects of project ':simpleJava2'.
   properties - Displays the properties of root project 'SimpleMultiProject'.
   simpleJava1:properties - Displays the properties of project ':simpleJava1'.
   simpleJava2:properties - Displays the properties of project ':simpleJava2'.
   tasks - Displays the tasks runnable from root project 'SimpleMultiProject' (some of the displayed tasks may belong to subprojects).
   simpleJava1:tasks - Displays the tasks runnable from project ':simpleJava1'.
   simpleJava2:tasks - Displays the tasks runnable from project ':simpleJava2'.
   
   Verification tasks
   ------------------
   simpleJava1:check - Runs all checks. [simpleJava1:test]
   simpleJava2:check - Runs all checks. [simpleJava2:test]
   simpleJava1:test - Runs the unit tests. [simpleJava1:classes, simpleJava1:testClasses]
   simpleJava2:test - Runs the unit tests. [simpleJava2:classes, simpleJava2:testClasses]
   
   Other tasks
   -----------
   A
   simpleJava1:B
   simpleJava2:B
   
   BUILD SUCCESSFUL
   
   Total time: 0.586 secs
```

内容较多,具体查看Other tasks即可.


3)  分别创建simpleJava1和simpleJava2工程
     java代码如何写就不一一叙述了,主要是在两个工程根目录下均需要创建一个构建build.gradle,可以在里面做一些简单的
     配置,如此就完成了一个简单的gradle多工程配置了,子工程配置如下:
     
     
```
     version '1.0.2'         //编译生成的jar的版本号
     jar{
       manifest{
         attributes 'Main-Class':'cn.enjoytoday.BeginGradle'   //设置jar主程序入口
       }
     }

```

4)  工程运行
    分别在需要运行的工程根目录下运行:
    
```
     #gradle build
     #java -jar /build/libs/*.jar 
```
    
如此，就可完成一个简单的java程序运行调式.当然,这种方式略显简单,我们可以通过添加脚本的方式通过脚本对其进行继承运行
配置,此是后话.