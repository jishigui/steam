import com.bean.GamesBean;
import com.bean.UserBean;
import com.service.GamesService;
import com.service.UserService;
import com.service.impl.GamesServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class reflectTest {


    @Test
    public void test(){


        GamesService gs = new GamesServiceImpl();
        try {
            System.out.println(gs.queryGames(new GamesBean()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1() throws Exception {
        UserBean userBean=new UserBean();
        userBean.setuName("1");

        UserService userService=new UserServiceImpl();
        List list1=userService.userViewWish(userBean);
        List list2=userService.userViewHome(userBean);

        System.out.println("愿望单"+list1);
        System.out.println("库"+list2);
    }

    @Test
    public void t2() throws Exception {
        UserBean userBean=new UserBean();

        userBean.setuID(UUID.randomUUID().toString());
        userBean.setuName("易");
        userBean.setuPwd("yi");
        userBean.setuNick("易");
        userBean.setuTel("18397315766");

        UserService userService=new UserServiceImpl();
        int a=userService.save(userBean);
        System.out.println(a);
    }

    @Test
    public void t3() throws Exception {
        String sql="SELECT oId FROM `orders` WHERE osId=1 ";

        Connection connection=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;





    }

}
