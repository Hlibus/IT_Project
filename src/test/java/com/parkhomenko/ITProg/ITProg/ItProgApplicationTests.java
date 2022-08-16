package com.parkhomenko.ITProg.ITProg;

import com.parkhomenko.ITProg.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItProgApplicationTests {

	@Test
	void contextLoads() {
		System.out.print("MD5 testing:");
		UserEntity usEnt = new UserEntity();
		usEnt.setPassword("123321");
		System.out.print(usEnt.md5Custom(usEnt.getPassword()));
	}

}
