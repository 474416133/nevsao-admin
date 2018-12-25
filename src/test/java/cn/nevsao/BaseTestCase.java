package java.cn.nevsao;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
@Slf4j
public class BaseTestCase {

    @Before
    public void setUp(){
        log.debug("----test setUp-----------");
    }

    @After
    public void tearDown(){
        log.debug("----test tearDown-----------");
    }
}
