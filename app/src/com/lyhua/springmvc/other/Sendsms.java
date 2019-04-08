package com.lyhua.springmvc.other;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



/**
 * 
 * @author Administrator
 * ���Ͷ�����֤�ӿ�
 * account C68792049
 * password 1ca34fc3b4cd455a721cab712282e943
 */

public class Sendsms {
	
//�����������ĵ�ַ
private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	public static int senfCode(String account,String password,String mobile,String mobile_code) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

	    String content = new String("������֤���ǣ�" + mobile_code + "���벻Ҫ����֤��й¶�������ˡ�");

		NameValuePair[] data = {//�ύ����
			    new NameValuePair("account", account), 
			    new NameValuePair("password", password), //�鿴�������¼�û�����->��֤�롢֪ͨ����->�ʻ���ǩ������->APIKEY
			    //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
			    new NameValuePair("mobile", mobile), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			
			String SubmitResult =method.getResponseBodyAsString();

			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);

			 if("2".equals(code)){
				System.out.println("�����ύ�ɹ�");
				//���ŷ��ͳɹ�
				return 1008;
			}

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ŷ���ʧ��
		return 1009;
		
	}
	
	
}
