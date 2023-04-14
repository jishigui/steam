import com.bean.AdminBean;
import com.bean.GamesBean;
import com.bean.UserBean;
import com.dao.AdminDao;
import com.dao.GamesDao;
import com.dao.UserDao;
import com.dao.impl.AdminDaoImpl;
import com.dao.impl.GamesDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.service.AdminService;
import com.service.GamesService;
import com.service.UserService;
import com.service.impl.AdminServiceImpl;
import com.service.impl.GamesServiceImpl;
import com.service.impl.UserServiceImpl;
import com.servlet.UserServlet;
import com.util.JdbcUtil;
import com.util.PageUtil;
import com.util.SendMsg;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;

public class t1 {


    @Test
    public void test1() throws Exception {
        JdbcUtil jdbcUtil=new JdbcUtil();

        Connection conn=jdbcUtil.getConnection();

        String sql="insert into `user`()";

    }

    @Test
    public void test2() throws Exception{
        UserBean userBean=new UserBean();
        userBean.setuName("1");
        userBean.setuPwd("1");

        UserService userService=new UserServiceImpl();

        UserBean userBean1=userService.userLoginByName(userBean);

        System.out.println("--------"+userBean1.getuStatus());
        System.out.println("====="+userBean1);
        System.out.println(userService.userAccounExit("1"));
    }

    @Test
    public void test3(){
        int res =  SendMsg.getCode("18397315766");
        if (res==999999){
            System.out.println("短信发送失败");
        }else{
            System.out.println("短信发送成功:验证码为:"+res);
        }

    }

    @Test
    public void test4() throws Exception {
        AdminService adminService=new AdminServiceImpl();
        AdminBean adminBean=new AdminBean();
        adminBean.setaTel("18397315766");
        adminBean.setaPwd("1234");
        adminService.adminModifyPwd(adminBean);
    }

    @Test
    public void test5() throws Exception{
        UserBean userBean=new UserBean();
        UserService userService=new UserServiceImpl();
        userBean.setuPwd("555");
        userBean.setuTel("18397315766");
        userService.userModifyPwd(userBean);
    }


    @Test
    public void test6() throws Exception {
        AdminService adminService=new AdminServiceImpl();
        adminService.deleteUser("");
    }

    @Test
    public void insert() throws Exception {
        UserService userService=new UserServiceImpl();
        UserBean userBean=new UserBean();

        userBean.setuPwd("123");
        userBean.setuName("wq");


        UserBean userBean1=userService.userLoginByName(userBean);
        System.out.println("++++++++++++++"+userBean1.getuStatus());
    }

    @Test
    public void modifyStatus() throws Exception {
        AdminDao adminDao=new AdminDaoImpl();

        UserBean userBean=new UserBean();
        userBean.setuStatus(1);
        userBean.setuName("yixing");

        adminDao.modifyUser(userBean);
    }

    @Test
    public void insertGame() throws Exception {
        GamesBean gamesBean=new GamesBean();

        GamesService gamesService=new GamesServiceImpl();
        System.out.println((gamesService.gameExists("只狼：影逝二度")));


    }

    @Test
    public void view() throws Exception {
        String sql="SELECT uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,gName\n" +
                "FROM (\n" +
                "SELECT u.uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,gId \n" +
                "FROM `user` u\n" +
                "LEFT JOIN `wishlist` w\n" +
                "ON(u.uID=w.gId)\n" +
                ") t\n" +
                "LEFT JOIN `game` g\n" +
                "ON(g.gId=t.gId)\n" +
                "WHERE uName=?\n";

        UserBean userBean=new UserBean();
        userBean.setuName("1");
//
//        UserDao userDao=new UserDaoImpl();
//        userDao.userView(userBean);
//        System.out.println(userDao.userView(userBean));

        List list=JdbcUtil.queryData(sql,userBean.getuName());
        System.out.println(list);
    }

    @Test
    public void viewKu() throws Exception {
        String sql="SELECT u.uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,t.oName\n" +
                "FROM `user` u\n" +
                "LEFT JOIN (\n" +
                "SELECT oName,`order`.oId,orders.uID\n" +
                "FROM `order`\n" +
                "LEFT JOIN `orders`\n" +
                "ON(`order`.oId=`orders`.oId)\n" +
                "WHERE osPaystatus!=0\n" +
                ") t\n" +
                "ON(u.uID=t.uID)\n" +
                "WHERE uName=?\n";
        UserBean userBean=new UserBean();
        userBean.setuName("1");
        List list=JdbcUtil.queryData(sql,userBean.getuName());
        System.out.println(list);
    }

    @Test
    public void t3() throws Exception {
        UserService userService=new UserServiceImpl();
        UserBean userBean=new UserBean();
        userBean.setuName("1");
        List list=userService.userViewHome(userBean);
        List list1=userService.userViewWish(userBean);

        System.out.println(list);
        System.out.println(list1);
    }

    @Test
    public void t4() throws Exception {
        GamesBean gamesBean = new GamesBean();
        GamesService gamesService = new GamesServiceImpl();

        PageUtil pageUtil = gamesService.queryGamesPage(gamesBean, 5, 1);
        System.out.println(pageUtil);
    }

    @Test
    public void t5() throws Exception{
        String a="[ 1,2,3,4,5 ]";
        String str=a.replace("[","").replace("]","").trim();
        System.out.println(str);

        String[] strs=str.split(",");
        String sql="UPDATE `order` SET oStatus=1 WHERE oId=?";
        for(int i=0;i<strs.length;i++){
            JdbcUtil.insertOrUpdateOrDelete(sql,strs[i]);
        }



    }
}
