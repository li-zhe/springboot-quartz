package com.example.springbootquartz;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class SpringbootQuartzApplicationTests {

	@Test
	public void testDesensitization(){
		//1:保留前n后m 2.保留自x至y 3.遮盖前n后m 4.遮盖自x至y 5.全部遮盖
		String str="abcdefghijklmnopqrstuvwxyz";
		int desensitizationType  = 3;
		Long n = 0L;
		Long m = 3L;

		System.out.println(dataDesensitization(str,desensitizationType,n,m));
	}
	private String dataDesensitization(String str,int desensitizationType,Long n,Long m){
		String ret = "";
		switch (desensitizationType){
			case 1:{
				//如果n大于等于字符长度，则不进行遮盖操作，start赋值字符长度,保证后边不会进入替换条件内。
				Long start = n>str.length()?str.length():n;
				Long end = str.length()-m;
				if(start>=0&&end>start){
					ret = StrUtil.hide(str,start.intValue(),end.intValue());
				}
				break;
			}
			case 2:{
				n = n-1;
				Long x = n>str.length()?str.length():n;
				if(x>0){
					str = StrUtil.hide(str,0,x.intValue());
				}
				Long y = str.length()<m?str.length():m;
				if(y<str.length()){
					str = StrUtil.hide(str,y.intValue(),str.length());
				}
				ret = str;
				break;
			}
			case 3:{
				Long end = n>str.length()?str.length():n;
				if(end>0){
					str = StrUtil.hide(str,0,end.intValue());
				}
				Long start = str.length()-m;
				start = start<0?0:start;
				if(start<str.length()){
					str = StrUtil.hide(str,start.intValue(),str.length());
				}
				ret = str;
				break;
			}
			case 4:{
				n = n-1;
				//如果x大于等于字符长度，则不进行遮盖操作，start赋值字符长度,保证后边不会进入替换条件内。
				Long x = n>str.length()?str.length():n;
				Long y = str.length()<m?str.length():m;
				if(x<y){
					ret = StrUtil.hide(str,x.intValue(),y.intValue());
				}
				break;
			}
			case 5:{
				ret = StrUtil.hide(str, 0, str.length());
				break;
			}
			default:
				break;
		}
		return  ret;
	}
}
