package com.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ReflectUtil {


    /**
     *
     * @param mapData  传入Map对象
     * @param cls    传入JavaBean对应的Class
     *               如果传入UserBean.class ,此方法就尽可能的将Map中key与UserBean的属性有对应关系的值通过反射调用set方法将map的值传入到JavaBean的相应属性中
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T  setMapValueToJavaBean(Map<String,Object> mapData,Class<T> cls) throws IllegalAccessException, InstantiationException {

        //非静态方法必需用对象名.方法名的形式调用
        Object javaBean = cls.newInstance();//创建cls代表的类型对象  等价于 UserBean2 javaBean = new UserBean2();
        //通过Class得到类中所有的属性或所有的方法
        Method[] methods =  cls.getDeclaredMethods();


        for(Method m :methods){//遍历cls对应的类的所有方法

            Class[] paramTypes = m.getParameterTypes();//得到方法参数  类型列表
            //只拿set开头且只有一个参数的 方法
            String methodName = m.getName();
            if (methodName.startsWith("set") && paramTypes.length==1){
                String  paramTypeName = paramTypes[0].getName();//拿到set方法的第一个参数的数据类型 返回的是字符串  (String)   java.lang.String
                //   System.out.println("方法名称"+ m.getName() +"方法的形数据类型"+paramTypeName);
                //把setXXX方法set后面的第一个字母写成小写，把set去掉
                methodName = methodName.substring(3).substring(0,1).toLowerCase()+methodName.substring(4);
                //  System.out.println("转换后的方法名为:"+methodName);
                //根据转换后的方法名称从map中取出对应的值
                Object o =  mapData.get(methodName);
                if (o!=null) {
                    System.out.println("根据MethodName: " + methodName + "  从HashMap中取出的值为:" + o);
                    System.out.println("方法的形数据类型" + paramTypeName + " Map中取出值的数据类型为" + o.getClass().getName());
                    //调用setXX方法把Map中取出的值传递到set方法中去

                    // UserBean2 ub = new UserBean2();
                    // ub.setUserBirthday("");
                    try {
                       // "java.lang.String".equals("java.lang.String")
                        //"float".equals("java.lang.Float")
                        if (paramTypeName.equals(o.getClass().getName())){//当方法的参数类型与Map中取出的值的类型是完全匹配的，直接用invok即可
                            m.invoke(javaBean, o);
                        }else if (paramTypeName.equals("java.sql.Date")){//set方法的形参为java.sql.Date类型时
                            //判断取出来的值的类型、
                            if (o.getClass().getName().equals("java.lang.String")){
                                //判断String类型的格式是否为yyyy-MM-dd格式
                                if (o.toString().matches("[0-9]{4}(-[0-9]{2}){2}")){
                                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        java.util.Date date =   sd.parse(o.toString());

                                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                        m.invoke(javaBean,sqlDate);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                          //  Object o1 = null;
                          //  public void m1(String s){ o1.toString()  String.valueOf(o1)
                         //   }

                        }else if (paramTypeName.equals("float")|| paramTypeName.equals("java.lang.Float")){//判断方法的参数为开为float或java.lang.Float时
                            if (o.getClass().getName().equals("float")|| o.getClass().getName().equals("java.lang.Float")){//判断map中取出的值类型为float或java.lang.Float时
                                m.invoke(javaBean,o);//直接调用m.invoke方法，
                            }else if (o.getClass().getName().equals("java.lang.String")){
                                if (o.toString().matches("[0-9]+([.][0-9]+)?")){
                                    m.invoke(javaBean,Float.parseFloat(o.toString()));
                                }
                            }
                        }else if (paramTypeName.equals("java.lang.String")){//当方法参数类型为String时，但Map中的值类型不是String时
                            m.invoke(javaBean,o.toString());

                        }else if(paramTypeName.equals("int") || paramTypeName.equals("java.lang.Integer")){
                            if (o.getClass().getName().equals("int")|| o.getClass().getName().equals("java.lang.Integer")){//判断map中取出的值类型为float或java.lang.Float时
                                m.invoke(javaBean,o);//直接调用m.invoke方法，
                            }else if (o.getClass().getName().equals("java.lang.String")){
                                m.invoke(javaBean,Float.parseFloat(o.toString()));

                            }
                        }else if(paramTypeName.equals("double") || paramTypeName.equals("java.lang.Double")) {
                            if (o.getClass().getName().equals("double") || o.getClass().getName().equals("java.lang.Double")) {//判断map中取出的值类型为float或java.lang.Float时
                                m.invoke(javaBean, o);//直接调用m.invoke方法，
                            } else if (o.getClass().getName().equals("java.lang.String")) {
                                m.invoke(javaBean, Double.parseDouble(o.toString()));

                            }
                        }
                        //后续要添加JavaBean中的属性的数据类型与Map中取出的值类型的不匹配进的数据转换代码。。。

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return (T)javaBean;
    }


}
