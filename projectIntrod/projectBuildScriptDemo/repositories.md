## gradle依赖和插件管理


### 说明
本篇主要是说明gradle中工程依赖管理和buildscript配置的相关基础知识,不涉及具体的工程构建.依赖是每个工程很难避免谈
论的话题,依赖可以使得工程的实现变得更加方便,缩短工程周期,因此对于一个工程构建脚本而言依赖的使用便捷性与否十分重要.
而gradle对于依赖的控制管理十分出色,配置语句简单明了,这也是让人喜欢上用gradle的一个因素.


###依赖
首先需要介绍的是gradle的依赖管理,由于buildscript也同样使用到了gradle的依赖管理,所以在此之前我们需要现了解
gradle中的依赖.


#### 属于gradle自己的依赖
  作为gradle本身,也为它的每一个运行单元(task)做了依赖配置管理.比较简单,对于task的依赖主要有两种方式进行表达,一种
  是task创建时声明,一种是task创建后声明.其使用方式具体如下；
  
  ```
  //依赖配置
  task A << {
      print "this is task A"
  }
 
  A.dependsOn('D')
  
  task B(dependsOn: [A,'C']) <<{
      print "this is task B"
  }
  
  task C << {
      print "this is task C"
  }
  
  task D << {
      print "this is task D"
  }
  
  D.dependsOn(C)
  
  ```
  
  如上,为各种task依赖写法,注意在依赖声明时被依赖任务还未声明的话,需要通过引号表示如'A'.
  

#### 库依赖

   库依赖可以通过如下几个方面对其进行了解.
  1. build.gradle中的依赖
  build.gradle中对于依赖的配置十分简单,配置的格式如下:
  
  ```
  //库依赖
  project.repositories{
      mavenCentral()
  }
  project.dependencies{
      compile 'com.google.android.gms:play-services-base:6.5.87'
  
  }
  
  
  ```
  
   如上,结构比较清晰.一个依赖管理配置由两个部分构成：一是依赖库(依赖查找下载路径);另一个就是依赖名称.需要注意的是
   依赖配置属于配置累加类型,也就是说会在原有配置基础上增加一个新的库位置或者依赖名而不是对其进行替换覆盖(gradle中
   配置均是此种模式,累加型).
  
   2. DSL中的依赖
   依赖的配置我们也只在gradle中追述到gradle本省内置的插件api中.其中,类似上面的配置脚本一样,gradle依赖也是主要由
   两个部分组成的:RepositoryHandler分管repositories配置;DependencyHandler分管dependencies配置.可以看出我们
   的repositories和dependencies分别是调用Project中的对应的方法:
  
  
```  
     /**
         * <p>Configures the repositories for this project.
         *
         * <p>This method executes the given closure against the {@link RepositoryHandler} for this project. The {@link
         * RepositoryHandler} is passed to the closure as the closure's delegate.
         *
         * @param configureClosure the closure to use to configure the repositories.
         */
        void repositories(Closure configureClosure);
        
        
        /**
             * <p>Configures the dependencies for this project.
             *
             * <p>This method executes the given closure against the {@link DependencyHandler} for this project. The {@link
             * DependencyHandler} is passed to the closure as the closure's delegate.
             *
             * <h3>Examples:</h3>
             * See docs for {@link DependencyHandler}
             *
             * @param configureClosure the closure to use to configure the dependencies.
             */
            void dependencies(Closure configureClosure);
   ```
   
   
   
   对应的Closure会分别给我们传入RepositoryHandler、DependencyHandler对象用于配置依赖.那么下面我们再来仔细查
   看这两个API的介绍.
   (1) RepositoryHandler
       和它名字本省一样资料管理处,主要是管理依赖查找下载的位置的,原文的介绍如下:
      
```
   A {@code RepositoryHandler} manages a set of repositories, allowing repositories to be defined
    and queried
```
   
   简介明了,通过其api我们可以发现api给我们提供了几个默认的资料库.当然,我们本身也可以指定某个特定的资料库(类型需
   要是RepositoryHandler中指定的某一类).使用官方指定的资料库,只需要通过调用方法即可.api提供的资料库种类如下；
   - flatDir
     依赖类型FlatDirectoryArtifactRepository.默认为gradle根目录,一般用于本地库导入,使用格式：
     
```
   repositories {
       flatDir name: 'libs', dirs: "$projectDir/libs"
       flatDir dirs: ["$projectDir/libs1", "$projectDir/libs2"]
   }
   
   
   //如
   repositories{
      faltDir{
        dirs: 'libs'
      }
   }
```
   
   
   - jcenter
     依赖类型MavenArtifactRepository.添加一个在Bintray的JCenter存储库中查找依赖关系的存储库.其实也是一个
     maven库,默认存储地址:https://jcenter.bintray.com/. 可以自行配置位置也可以使用默认的url.使用格式如下:
     
     
```
     repositories {
             jcenter {
               artifactUrls = ["http://www.mycompany.com/artifacts1", "http://www.mycompany.com/artifacts2"]
             }
             jcenter {
               name = "nonDefaultName"
               artifactUrls = ["http://www.mycompany.com/artifacts1"]
             }
             
      //如
       repositories {
            jcenter()
       }
      
```
     
     
   - mavenCentral
     依赖类型MavenArtifactRepository.常见的maven中心仓库,包括maven远程仓库:
     https://repo1.maven.org/maven2/ ,也可以自行配置,一般是用来配置jar依赖.使用格式如下:

```
       repositories {
           mavenCentral artifactUrls: ["http://www.mycompany.com/artifacts1", 
           "http://www.mycompany.com/artifacts2"]
           mavenCentral name: "nonDefaultName", artifactUrls: 
           ["http://www.mycompany.com/artifacts1"]
       }
       
       
       //example
       repositories {
          mavenCentral()
       }
       
        repositories {
            mavenCentral{
                 artifactUrls 'http://www.mycompany.com/artifacts1'
                 
             }
        }
       
```
       
   - ivy
     依赖类型IvyArtifactRepository,ivy仓库的使用并不是很了解,但可以通过api了解其基本的使用方法,使用格式如下:
     
     
```
    repositories {
      ivy {
          layout 'pattern' , {
               artifact '[module]/[revision]/[artifact](.[ext])'
                ivy '[module]/[revision]/ivy.xml'
           }
      }
   }
    
    
    //可以直接指定url即可
    
    repositories{
       ivy{
       
         url 'http://ivy.mycompany.net/repo'
       
       }
    
    }
    
      
```

   - mavenLocal
     依赖类型MavenArtifactRepository.maven本地依赖仓库,本地依赖不需要你在配置中填写file位置,其默认maven仓库
     位置的优先级顺序如下:
     1) 系统属性 'maven.repo.path'
     2) ~/.m2/settings.xml
     3) M2_HOME/conf/settings.xml,其中M2_Home为maven的环境变量.
     
     使用方法如下:
     
```
      repositories {
          mavenLocal()
       }
```
     
     
   - maven
      依赖类型MavenArtifactRepository.这个没什么可说,jcenter、mavenLocal、mavenCentral都是通过它配置,配置
      方法如下:
      
```
        maven{
         
            artifactUrls 'http://www.mycompany.com/artifacts1'
        }
      
```
   (2)DependencyHandler
      DependencyHandler是一个比较神奇的API,它可以添加多组依赖关系,而且依赖的类别需要你自己去配置,也就是说你可
      以自己定义你每个依赖所需要做的事情,其本的配置方法如下:
      
```
      dependencies {
            configurationName dependencyNotation1, dependencyNotation2, ...
        }
      
```
   如下主要在添加java插件的基础上介绍(针对java工程,其他工程或并不相同).在java中的依赖主要分为如下几个类别:
   1)compile
   
   2)testCompile
      
如上,就是对库依赖的简单叙述,完整详情可以参考gradle DSL的API.



## buildscript