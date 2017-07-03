## Project构建脚本配置


### 说明
本示例主要是介绍project中一个重要的特性buildscript构建导入.对于需要扩展外来的插件来构建工程,需要我们通过
buildscript来配置插件的classpath,下载依赖插件.



### 配置方法
通过project对象配置buildscript,指定插件classpath.配置格式如下:

```

//project可以没有,书写习惯问题.
project.buildscript{

    repositories{
        jcenter()                 //插件仓库
    }

    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'    //插件

    }

}

```

### 案例说明