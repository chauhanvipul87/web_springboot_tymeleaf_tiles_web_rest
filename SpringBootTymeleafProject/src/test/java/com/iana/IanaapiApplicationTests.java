package com.iana;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.caonline.config.CAOnlineApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CAOnlineApplication.class)
@WebAppConfiguration
public class IanaapiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
