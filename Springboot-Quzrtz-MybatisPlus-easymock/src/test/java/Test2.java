import com.java4ye.demo.DemoApplication;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.RouteMatcher;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Java4ye
 * @date 2021/5/1 10:32
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */

@SpringBootTest(classes = DemoApplication.class)
public class Test2 {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void jdbcTest() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/db";
        String username = "";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement= connection.prepareStatement("select * from User where name=?");
            preparedStatement.setString(1, "Java4ye");
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement!=null){
                    preparedStatement.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @Test
    public void tes() throws InterruptedException {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(6);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(1);
        poolingHttpClientConnectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.baidu.com")), 4);

        ConnectionKeepAliveStrategy myStrategy = (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(
                    HttpClientContext.HTTP_TARGET_HOST);
            if ("www.baidu.com".equalsIgnoreCase(target.getHostName())) {
                // Keep alive for 5 seconds only
                return 5 * 1000;
            } else {
                // otherwise keep alive for 30 seconds
                return 30 * 1000;
            }
        };

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(10000)
                .setSocketTimeout(5000).build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(myStrategy)
                .setDefaultRequestConfig(requestConfig)
                .build();


//        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
//        simpleClientHttpRequestFactory.set
//        restTemplate.setRequestFactory();

//        CloseableHttpClient httpclient = HttpClients.createDefault();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String forObject = restTemplate.getForObject("http://www.baidu.com", String.class);
                System.out.println(forObject);
            }).start();
           /* new Thread(() -> {
                HttpGet httpGet = new HttpGet("http://www.baidu.com");
                CloseableHttpResponse response1 = null;
                try {
                    response1 = httpclient.execute(httpGet);
                    HttpEntity entity1 = response1.getEntity();
                    EntityUtils.consume(entity1);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        response1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                HttpGet httpGet2 = new HttpGet("http://juejin.cn");
                CloseableHttpResponse response2 = null;
                try {
                    response2 = httpclient.execute(httpGet2);
                    HttpEntity entity1 = response2.getEntity();
                    EntityUtils.consume(entity1);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        response2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                HttpGet httpGet3 = new HttpGet("http://www.csdn.net");
                CloseableHttpResponse response3 = null;
                try {
                    response3 = httpclient.execute(httpGet3);
                    HttpEntity entity1 = response3.getEntity();
                    EntityUtils.consume(entity1);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        response3.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/

        }

        Thread.sleep(5000);
    }
}
