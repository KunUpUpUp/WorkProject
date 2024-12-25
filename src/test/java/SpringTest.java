import com.seasugar.ProjectTestApplication;
import com.seasugar.spring.LifeDemo;
import com.seasugar.spring.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = ProjectTestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {
    @Autowired
    private LifeDemo lifeDemo;

    @Autowired
    private User user;

    @Test
    public void testOne() {
//        user.setName("zhukunpeng");
//        System.out.println(user.getName());
//        user.getName();
    }
}
