import com.bean.AdminBean;
import com.bean.UserBean;
import com.dao.AdminDao;
import com.dao.OrdersDao;
import com.dao.TopUp;
import com.dao.UserDao;
import com.dao.impl.AdminDaoImpl;
import com.dao.impl.OrdersDaoImpl;
import com.dao.impl.TopUpDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.service.AdminService;
import com.service.UserService;
import com.service.impl.AdminServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.JdbcUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class t2 {

    OrdersDao ordersDao=new OrdersDaoImpl();
    @Test
    public void t1() throws Exception {
        String sql="SELECT oName FROM `order` WHERE 1=1 AND oName=? OR oName=?";

        List list=JdbcUtil.queryData(sql,"只狼：影逝二度","巫师3：狂猎");



        sql="INSERT INTO `orders`(osId,oId,uID,osPaytime,osPayment,osTotalPrice,osCreatetime,osPaystatus) VALUES(11,1,1,NOW(),'支付宝',11,NOW(),0)";
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString().replace("oName=","").replace("{","").replace("}",""));

        }
        System.out.println(list);
    }

    @Test
    public void t3(){
        String sql="[5]";
        String[] str=sql.replace("[","").replace("]","").split(",");
        System.out.println(str[0]);
    }

    @Test
    public void viewAll() throws Exception {
        String uName="wang";
        UserBean userBean=new UserBean();
        userBean.setuName(uName);

        UserService userService=new UserServiceImpl();
        String sql="SELECT uID,uName,uPwd,uNick,uMail,uRegTime,uStatus,uIden,uBalance FROM `user` WHERE uName=?";
        UserDao userDao=new UserDaoImpl();
        System.out.println("===="+userBean.getuName());
        List list=userService.viewAll(userBean);
        System.out.println(list);

    }

    @Test
    public void t4() throws SQLException {
        AdminService adminService=new AdminServiceImpl();
        AdminBean adminBean=new AdminBean();
        adminBean.setaName("jiang");
        adminBean.setaPwd("123");
        adminBean.setaId("2");
        adminBean.setaTel("123");
        adminService.save(adminBean);


    }

    @Test
    public void t5() throws Exception {
        String uID="1";
        UserService userService=new UserServiceImpl();
        UserBean userBean=new UserBean();
        userBean.setuID(uID);

        List<Map<String,Object>> list=userService.viewAllByID(userBean);

        int a=1;
        Double b=2.01;
        System.out.println(a+b);
    }

    @Test
    public void t6() throws Exception {
        TopUp topUp=new TopUpDaoImpl();
        UserBean userBean=new UserBean();
        userBean.setuBalance(100000);
        userBean.setuID("1");
        topUp.TopUpTo(userBean);
    }

    @Test
    public void t7() throws Exception {
        UserBean userBean=new UserBean();
        userBean.setuName("wang");

        UserDao userDao=new UserDaoImpl();

        List list=userDao.userViewWish(userBean);

        System.out.println("t7"+list);
    }

}
