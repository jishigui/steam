import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.bean.WishListBean;
import com.dao.GamesDao;
import com.dao.OrdersDao;
import com.dao.impl.GamesDaoImpl;
import com.dao.impl.OrdersDaoImpl;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.service.OrderService;
import com.service.OrdersService;
import com.service.UserService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.OrdersServiceImpl;
import com.service.impl.UserServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.util.JdbcUtil;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class insert {
    @Test
    public void t1() throws Exception {

        OrdersService ordersService=new OrdersServiceImpl();
        OrderService orderService=new OrderServiceImpl();
        String osId= UUID.randomUUID().toString();
        String oId=UUID.randomUUID().toString();


        OrderDetailsTableBean order=new OrderDetailsTableBean();
        OrderListBean orders=new OrderListBean();

        order.setuID("cf756a88-96eb-45a2-aab0-b95dff7403a3");
        order.setoId(oId);
        order.setoDiscount(Double.parseDouble("1.00"));
        order.setoPrice(Double.parseDouble("1.00"));
        order.setoName("文明6");

        System.out.println(order);



        orders.setOsId(osId);
        orders.setoId(oId);
        orders.setuID("cf756a88-96eb-45a2-aab0-b95dff7403a3");
        orders.setOsPayment("支付宝");
        orders.setOsTotalPrice(Double.parseDouble("2.00"));

        System.out.println("++++++++orders"+orders);



        int b=orderService.orderInsert(order);
        int a=ordersService.OrderListInsert(orders);

    }

    @Test
    public void t2() throws Exception {
        GamesDao gamesDao=new GamesDaoImpl();
        boolean b=gamesDao.GameExist("死或生：沙滩排球","cf756a88-96eb-45a2-aab0-b95dff7403a3");
        boolean c=gamesDao.GameExist("死或生：沙滩排","cf756a88-96eb-45a2-aab0-b95dff7403a3");
        System.out.println(b);
        System.out.println(c);
    }

    @Test
    public void t3() throws Exception {
        OrdersService ordersService=new OrdersServiceImpl();
        OrderDetailsTableBean order =new OrderDetailsTableBean();
        OrdersDao ordersDao=new OrdersDaoImpl();
        order.setuID("cf756a88-96eb-45a2-aab0-b95dff7403a3");
        List list=ordersDao.orderListView(order);
//        String sql="SELECT `orders`.osId,oId,uID,osPaytime,osPayment,osTotalPrice,osCreatetime,osPaystatus,oDiscount,oPrice,oName\n" +
//                "FROM `orders`\n" +
//                "JOIN `order`\n" +
//                "ON(`orders`.osID=`order`.osID)\n" +
//                "WHERE `order`.uID=?\n" +
//                "\t";
//        List list= JdbcUtil.queryData(sql,"cf756a88-96eb-45a2-aab0-b95dff7403a3");
        System.out.println(list);
    }
    @Test
    public void t4() throws Exception {
        UserService userService=new UserServiceImpl();
        WishListBean wishListBean=new WishListBean();
        wishListBean.setgId("1");
        wishListBean.setuID("1");
        System.out.println(userService.wishExisit(wishListBean));
    }
}
