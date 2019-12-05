package com.example.demo;

import cn.wandingkeji.yueke.company.model.CompanyGroup;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
public class YuekeApplicationTests {

	@Test
	public void contextLoads() {


		String a ="[{\"id\":3019,\"name\":\"会员\",\"created_at\":\"2019-12-04T16:08:23.105525+08:00\",\"updated_at\":\"2019-12-05T15:07:03.074022+08:00\",\"company_id\":884,\"company_name\":\"音悦汇KTV\",\"created_by\":\"user\",\"customer_count\":1,\"group_type\":\"normal\",\"company_group_id\":0},{\"id\":2850,\"name\":\"员工\",\"created_at\":\"2019-09-25T11:24:49.24997+08:00\",\"updated_at\":\"2019-11-14T15:37:28.62278+08:00\",\"company_id\":884,\"company_name\":\"音悦汇KTV\",\"created_by\":\"\",\"customer_count\":0,\"group_type\":\"blacklist\",\"company_group_id\":0},{\"id\":2849,\"name\":\"老客\",\"created_at\":\"2019-09-25T11:24:49.225529+08:00\",\"updated_at\":\"2019-12-05T17:39:50.421601+08:00\",\"company_id\":884,\"company_name\":\"音悦汇KTV\",\"created_by\":\"\",\"customer_count\":1,\"group_type\":\"potential\",\"company_group_id\":0}]\n";

		List<CompanyGroup> companyGroupList = JSONArray.parseArray(a, CompanyGroup.class);

		System.out.println(companyGroupList);





	}

}
