package cn.enjoytoday;
import java.lang.*;


public class UserBeans{
 
 private  String name ;
 private String domain;
 private String sex;
 private int age;
 private String tag;


 public void setName(String name){

    this.name=name;
}

public void setSex(String sex){
   this.sex=sex;
}


public void setDomain(String domain){
    this.domain=domain;
}

public void setAge(int age){
     this.age=age;
}


public void setTag(String tag){
    this.tag=tag;
}

 public String  printInfo(){
     StringBuffer stringBuffer = new StringBuffer();
      if(null!=sex && sex.length()>0){
            if(sex.equals("man")){
                  stringBuffer.append("Mr.");
              }else if(sex.equals("woman")){
                  stringBuffer.append("Miss.");
              }
         }

    if(null!=name && name.length()>0){
            stringBuffer.append(" " +name);
     }
   
    if(null !=tag && tag.length()>0){
         stringBuffer.append(" is a " +tag+" person,");
    }

    stringBuffer.append(",and age is "+age+", come from "+domain);
    return stringBuffer.toString();

 }



}
