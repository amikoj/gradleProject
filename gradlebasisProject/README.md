## Gradle基础介绍



### 说明
介绍gradle使用基础，gradle基础脚本结构和常规使用方法，以及一个简单的gradle示例.主要是为了简单的介绍gradle使用。


### gradle环境配置
gradle可以通过两种方式运行gradle脚本:一种是通过配置系统gradle环境，运行gradle命令；一是通过gradle warpper配置临时gradle运行环境，通过脚本方式运行gradle命令。两种方式的效果相同，使得gradle的使用的成本更加方便。

1) 系统gradle配置

由于gradle常用于java工程的构建，所以gradle环境的配置需要依赖于java环境，需要先确认是否已经配置java运行环境,可以通过如下命令子啊dos命令框(windows)或者shell命令框(linux)中确认:
```
#:java -version
java version "1.8.0_101"
Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
```
确认返回的话，
- 手动下载gradle的二进制文件

  地址为:https://gradle.org/releases/  选择binary-only，可以选择你希望安装的版本,版本尽量不要太高3左右即可，放置高版本对于某些插件的适配存在一些绑定性质的配置要求。

- 文件解压并配置系统环境变量

  解压文件不再多说(linux下可通过uzip进行解压)，若是linux系统需要将解压后文件的bin目录添加到PATH路径下，可以通过export设置临时配置也可直接在enviroment中添加永久配置,配置如下:

  ```
   $GRADLE_HOME=/opt/gradle/gradle-3.0  #解压后的gradle路径
   export PATH=$PATH:$GRADLE_HOME/bin
  ```
  可以将这两句话添加在~/.bashrc,~/.profile,/etc/profile文件的末尾，然后通过如下命令进行激活:
  ```
  #source ~/.profile

  ```

  windows环境的话，通过右键"计算机"——>"属性"———>"系统属性"———>"高级"
  ———>"环境变量"进入环境变量设置界面。
  1. 新建一个用户变量，变量名:GRADLE_HOME,变量地址指向解压后的gradle的文件的根目录。如:"C:\Program Files\gradle\gradle-3.0"
  2. 在系统变量path的变量值头部添加"%GRADLE_HOME%\bin;"

2) gradle wrapper临时环境

gradle wrapper一般临时环境一般是通过脚本gradlew或者gradlew.bat(windows)通过命令下载环境依赖/gradle/wrapper/* 文件.
运行gradle时可直接通过将gradle用gradlew或者gradlew.bat替代操作.可通过
如下命令生成gradle wrapper环境，之后可以将该部分文件拷贝到任意一个未安装gradle环境的机器上实现gradle工程管理。

```
#gradle wrapper #生成wrapper临时gradle环境

```

### 简单的gradle 示例
为了方便理解gradle wrapper的使用，本示例通过gradle wrapper搭载gradle环境实现一个简单的hello world输出操作.操作步骤如下所示:

1) 构建gradle wrapper环境
 ```
 hfcai@:~/root/gradlebasisProject#gradle wrapper
 Starting a Gradle Daemon (subsequent builds will be faster)
:wrapper

BUILD SUCCESSFUL

Total time: 6.409 secs
hfcai@:~/root/gradlebasisProject# ll
总用量 28
drwxrwxr-x  4 caihaifei caihaifei 4096  7月  3 13:52 ./
drwxrwxr-x 11 caihaifei caihaifei 4096  7月  3 13:51 ../
drwxrwxr-x  3 caihaifei caihaifei 4096  7月  3 13:52 gradle/
drwxrwxr-x  3 caihaifei caihaifei 4096  7月  3 13:52 .gradle/
-rwxrwxr-x  1 caihaifei caihaifei 5242  7月  3 13:52 gradlew*
-rw-rw-r--  1 caihaifei caihaifei 2260  7月  3 13:52 gradlew.bat

```
如上，完成wrapper环境搭建，之后管理工程可不用提交“gradle/”文件夹，直接通过"./gradlew wrapper"重新创建。

2) 创建build.gradle脚本

build.gradle脚本是默认运行的构建脚本，用于配置和编译工程。这里，我们只是简单的演示一下gradle的使用，根目录创建文件build.gradle,写入如下代码:

```
task sayHello << {
  println "Hello World!"
}

project.afterEvaluate {
    println "config project parameters over."
}
```
如上，task 为gradle最小的运行单元，上面代码创建了一个sayHello的task,project.afterEvaluate为当前工程配置完成的一个回调，上面所有代码均采用groovy闭包(Closure)的形式进行表达，如想要详细了解，需要了解基础的groovy语法(或会单独就此做一篇介绍)。通过如下命令运行task达到配置的最终结果:
```
hfcai@:~/root/gradlebasisProject# ./gradlew sayHello
config project parameters over.
:sayHello
Hello World!

BUILD SUCCESSFUL

Total time: 0.552 secs
```
[Enjoytoday,EnjoyCoding](http://www.enjoytoday.cn)
