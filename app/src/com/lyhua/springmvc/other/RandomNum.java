package com.lyhua.springmvc.other;

import java.util.Random;

public class RandomNum {
	
	
	//����countλ��֤��
	public static String CreateRandomNum(int count)
	{
		StringBuilder code = new StringBuilder();
	    Random random = new Random();
	    // ����6λ��֤��
	    for (int i = 0; i < count; i++) {
	        code.append(String.valueOf(random.nextInt(10)));
	    }
	    return code.toString();
	}
	
	
	
	
	
}
