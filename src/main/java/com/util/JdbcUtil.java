package com.util;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class JdbcUtil {

    static PropertiesUtil propertiesUtil;
    static {
        try {
            propertiesUtil = new PropertiesUtil();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String driver=propertiesUtil.getDriver();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception{
        Connection connection=null;

        PropertiesUtil propertiesUtil=new PropertiesUtil();
        String url=propertiesUtil.getUrl();
        String username=propertiesUtil.getUsername();
        String password=propertiesUtil.getPassword();

        if(url!=null && username!=null && password!=null){

            connection = DriverManager.getConnection(url,username,password);
        }else if(url!=null && username==null && password==null){
            connection = DriverManager.getConnection(url);
        }else {
            System.out.println("连接错误");
        }
        return connection;
    }

    public static void close(Connection conn, ResultSet resultSet, PreparedStatement ps) throws Exception{
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param sql  传入 insert,update,delet语句
     * @param params  传入 SQL语句 ?号点位符的值， 要求?点位符对应的数据类型要与数据库的数据类型匹配
     * @return
     */
    public static int insertOrUpdateOrDelete(String sql,Object ...params)throws SQLException {
        Connection connection  = null;
        PreparedStatement pstmt = null;
        try{
            try {
                connection = JdbcUtil.getConnection();

                //开启事务
                connection.setAutoCommit(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pstmt = connection.prepareStatement(sql);
            if (params!=null){
                for(int i=0;i<params.length;i++){
                    pstmt.setObject(i+1,params[i]);
                }
            }

            int res =pstmt.executeUpdate();

            //成功则提交事务
            connection.commit();
            return res;
        }catch(SQLException e){
            throw e;
        }finally {
            try {
                JdbcUtil.close(connection,null,pstmt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//    /**
//     * 第一个ArrayList<T> 表示声明一个泛型变量，名字为 T
//     * List<T> 表示方法的返回类型是不确定的，有可能返回  List<UsrBean>也有可能是List<StudentBean>
//     *    方法的具体返回 类型取决于方法的第二个参数RowMappr接口实现中定义的泛型类型
//     *
//     *
//     * @param sql
//     * @param rowMapper  //UserBeanMapper
//     * @param params
//     * @param <T>
//     * @return
//     *
//     * RowMapper<UserBean>
//     */
//    public static <T> List<T> queryData(String sql, RowMapper<T> rowMapper, Object... params)throws  SQLException{
//        Connection connection  = null;
//        PreparedStatement pstmt = null;
//        List<T> returnData = new ArrayList<>();
//        try{
//            connection = JdbcUtil.getConnection();
//
//            //SQl   ??
//            pstmt = connection.prepareStatement(sql);
//            if (params!=null){
//                for(int i=0;i<params.length;i++){
//                    pstmt.setObject(i+1,params[i]);
//                }
//            }
//            ResultSet rs =   pstmt.executeQuery();
//            while(rs.next()){
//                T  t =   rowMapper.rowMap(rs);//调用RowMapper接口实现类对象的rowMap方法
//                returnData.add(t);
//            }
//            return returnData;
//        }catch(SQLException e){
//            throw e;
//        }finally {
//            JdbcUtil.close(connection,null,pstmt);
//        }
//    }


    public static <T> List<T> queryData(String sql, RowMapper<T> rowMapper, Object ...params) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<T> dataList = new ArrayList<>();

        try {
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            if(params != null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            rs = ps.executeQuery();

            //SQL成功执行就提交
            conn.commit();
            while (rs.next()){
                T t = rowMapper.rowMap(rs);
                dataList.add(t);
            }
            return dataList;
        } catch (Exception e) {
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }


//    //sql = select * from tbl_user where userid=? and userAccount=? ;  params=[a001,amdin]
//    public static PageUtil queryData(String sql,int pagesize,int currentpage,Object... params)throws  SQLException{
//        String sqlCount  = "select count(*) count from ("+sql+") t";
//
//        sql += " limit ?,?";//select * from tbl_user where userid=? and userAccount=?   limit ?,?
//
//      //  params=[a001,amdin,0,5];
//      //  List<Map<String,Object>>  mapList= queryData(sql,params);
//       // PageUtil pageUtil = new PageUtil();
//       // pageUtil.setDbdata(mapList);
//    }


    /**
     *
     * @param sql
     * @param params
     * @return List<Map<String,Object>>  Map中存储一行数据，Map的key是列名，Map的value是列值
     * @throws SQLException
     */
    public static List<Map<String,Object>> queryData(String sql, Object... params) throws Exception {
        Connection connection  = null;
        PreparedStatement pstmt = null;

        List<Map<String,Object>>  returnData = new ArrayList<>();
        try{
            connection = JdbcUtil.getConnection();


            pstmt = connection.prepareStatement(sql);
            if (params!=null){
                for(int i=0;i<params.length;i++){
                    System.out.println(params[i]);
                    pstmt.setObject(i+1,params[i]);
                }
            }
            ResultSet rs =   pstmt.executeQuery();//执行查询


            //查询结果对应的元素数据对象，可通过该对象得到查询的表名，列名，列的数据类型和长度
            ResultSetMetaData metadata =  rs.getMetaData();
            int columnCount =   metadata.getColumnCount();//得到查询结果的列数
            for(int i=1;i<=columnCount;i++){
                System.out.print("查询的列名为:"+metadata.getColumnLabel(i));
                System.out.print(" 列的数据类型:"+metadata.getColumnTypeName(i));
                System.out.println("列的长度:"+metadata.getPrecision(i));
            }


            while(rs.next()){//取出一行数据
                Map<String,Object> rowData = new HashMap<>();//存储一行数据的Map对象
                for(int i=1;i<=columnCount;i++){
                    String columnName = metadata.getColumnName(i);
                    //通过列名取列值
                    Object columnValue =   rs.getObject(columnName);
                    if (metadata.getColumnTypeName(i).contains("DATE")){
                        columnValue = rs.getString(columnName);
                    }
//                    if(columnValue.getClass().getName().equals("java.lang.String")){
//                        if(columnValue==null){
//                            columnValue="";
//                        }
//                    }
//                    if(columnValue.getClass().getName().equals("java.time.LocalDateTime")){
//                        if(columnValue==null){
//                            columnValue="";
//                        }
//                    }

                    if(columnValue==null){
                        columnValue="";
                    }

                    System.out.print("\t列名:"+columnName +"列值:"+columnValue +"值的类型为:"+columnValue.getClass().getName());
                    //  System.out.print("  列的数据类型:"+metadata.getColumnTypeName(i));
                    // System.out.println("列的长度:"+metadata.getPrecision(i));

                    rowData.put(columnName,columnValue);

                }
                System.out.println("");
                //把Map对象放到List集合中
                returnData.add(rowData);

            }
            return returnData;
        }catch(SQLException e){
            throw e;
        }finally {
            JdbcUtil.close(connection,null,pstmt);
        }
    }

    //String sql = "select  userid,userAcccount,userBirthday,userHobby,userSex  from tbl_user where 1=1 "

    public static PageUtil  queryDataByPage(String sql,int pageSize,int currentPage, Object... params) throws Exception {
        System.out.println("传递过来的sql语句为:"+sql);

        String sqlCount = "select count(*) coun from ("+sql+") t";

        List<Map<String,Object>> countList =  queryData(sqlCount,params);
        System.out.println("countlist的值为："+countList);
        //从countList中取出总记录数
        Object countObj =  countList.get(0).get("coun");
        //把countObj转成int类型
        int totalRecord =  Integer.parseInt(String.valueOf(countObj));
        System.out.println("总记录数为:"+totalRecord);


        System.out.println("传入过来的parsms的值为:"+ params.length);
        //把传入进来的sql后面追加一段 limit ?,?字符串
        //select  userid,userAcccount,userBirthday,userHobby,userSex  from tbl_user where 1=1 limit ?,?"
        sql += " limit ?,?";
        //执行分页查询
        //Object[] params=[]
        //在形参parsm的长度上+2创建新数组
        Object[] newParams = new Object[params.length+2];
        for(int i=0;i<params.length;i++ ) {//数组拷贝
            newParams[i] = params[i];//把方法形参params的值放到新数中
        }
        newParams[newParams.length-2]= (currentPage-1)*pageSize;
        newParams[newParams.length-1]= pageSize;

        System.out.println("newParams的值为为:"+ Arrays.toString(newParams));
        List<Map<String,Object>> resultData  =  queryData(sql,newParams);//传递新数组
        PageUtil pageUtil = new PageUtil(totalRecord,pageSize,currentPage,resultData);
        return pageUtil;
    }



    /**
     *
     *
     * 利用反射技术把查询结果映射到JavaBean中
     *
     * @param sql  要执行select语句
     * @param t     方法返回类型List<T>  取决于第二个参数Class<T>    第二参数传递JavaBean对应的Class
     * @param params
     * @param <T>
     * @return  该始终会返回一个不为空的List集合，应该根据List集合中的size判断是否查询到的数据  size为0表示未找到数据
     *
     *  List<UserBean> UserBeanList =  quereryData(sql,UserBean.class)
     *  List<StudentBean> UserBeanList =  quereryData(sql,StudentBean.class)
     */
    public static <T> List<T> queryData(String sql,Class<T> t,Object... params)throws  Exception{
        Connection connection  = null;
        PreparedStatement pstmt = null;
        List<T>  returnData = new ArrayList<>();
        try{
            connection = JdbcUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            if (params!=null){
                for(int i=0;i<params.length;i++){
                    pstmt.setObject(i+1,params[i]);
                }
            }
            ResultSet rs =   pstmt.executeQuery();//执行查询

            ResultSetMetaData metadata =  rs.getMetaData();//查询结果对应的元素数据对象，可通过该对象得到查询的表名，列名，列的数据类型和长度
            int columnCount =   metadata.getColumnCount();//得到查询结果的列数
            for(int i=1;i<=columnCount;i++){
                System.out.print("查询的列名为:"+metadata.getColumnLabel(i));
                System.out.print(" 列的数据类型:"+metadata.getColumnTypeName(i));
                System.out.println("列的长度:"+metadata.getPrecision(i));
            }


            while(rs.next()){//取出一行数据


                Map<String,Object> rowData = new HashMap<>();//存储一行数据的Map对象
                for(int i=1;i<=columnCount;i++){
                    String columnName = metadata.getColumnName(i);
                    //通过列名取列值
                    Object columnValue =   rs.getObject(columnName);
                    if(columnValue!=null){
                        System.out.print("\t列名:"+columnName +"列值:"+columnValue +"值的类型为:"+columnValue.getClass().getName());
                    }
                    //  System.out.print("  列的数据类型:"+metadata.getColumnTypeName(i));
                    // System.out.println("列的长度:"+metadata.getPrecision(i));

                    rowData.put(columnName,columnValue);

                }

                //把循环过程中得到的Map 反射到javaBean中
                T javaBean =   ReflectUtil.setMapValueToJavaBean(rowData, t);
////
//                System.out.println("+++++++++++rowData"+rowData);
//                System.out.println("+++++++++++javaBean"+javaBean);
                returnData.add(javaBean);//把javaBean放到List集合中做为方法的返回 值
            }
            System.out.println("returnData"+returnData);
            return returnData;
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            JdbcUtil.close(connection,null,pstmt);
        }


    }

}
