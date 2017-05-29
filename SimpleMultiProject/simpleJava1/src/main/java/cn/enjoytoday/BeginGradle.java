package cn.enjoytoday;
public  class BeginGradle{
 
     public static void main(String[] args){
          UserBeans userBeans=new UserBeans();
          userBeans.setName("hfcai");
          userBeans.setTag("bravely");
          userBeans.setAge(25);
          userBeans.setDomain("www.enjoytoday.cn");

         System.out.println("we knows:"+userBeans.printInfo());
                  
     }

}
