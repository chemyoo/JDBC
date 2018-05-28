package test;

/**
 * ��Ԫ���Ի���,��ͨ��URL���в��� ʾ�� Map<String, String[]> params = new
 * HashMap<String, String[]>(); params.put("shopId", new
 * String[]{"fhu478fay7fs7aegthaea"});
 * getLog().info(getRestults("/compDrugCollect/getCollectList.do",
 * HttpMethod.GET, params));
 */
// package com.chemyoo.test;
//
// import java.io.UnsupportedEncodingException;
// import java.util.Map;
// import java.util.Set;
//
// import javax.servlet.ServletRequestEvent;
//
// import org.apache.log4j.Logger;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockHttpServletRequest;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.mock.web.MockServletContext;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import
// org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;
// import org.springframework.web.context.request.RequestContextListener;
//
// import lombok.extern.log4j.Log4j;
//
/// **
// * @author ���� : jianqing.liu
// * @version ����ʱ�䣺2018��1��23�� ����5:32:48
// * @since 2018��1��23�� ����5:32:48
// * @description ��˵��
// */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
// @Log4j
// @WebAppConfiguration
// public class BaseUnitTest {
//
// public static MockServletContext context;
// public static MockHttpServletRequest request;
// public static MockHttpSession session;
// @Autowired
// private WebApplicationContext wac;
//
// protected static MockMvc mockMvc;
//
// @Before
// public synchronized void startUp() {
// RequestContextListener listener = new RequestContextListener();
// context = new MockServletContext();
// request = new MockHttpServletRequest(context);
// context.setContextPath("hydwb-maintain");
// listener.requestInitialized(new ServletRequestEvent(context, request));
// session = new MockHttpSession();
// request.setSession(session);
// if(mockMvc == null) {
// mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
// }
// }
//
// @Test
// public void test() {
// log.info("��Ԫ���Խ���");
// }
//
// public Logger getLog() {
// return log;
// }
//
// /**��ȡController ���صĽ��*/
// public String getRestults (String url,HttpMethod method,Map<String,String[]>
// params) throws UnsupportedEncodingException, Exception {
// MockHttpServletRequestBuilder requestBuilder = null;
// if(method == HttpMethod.POST) {
// requestBuilder = MockMvcRequestBuilders.post(url) //�����url,����ķ�����get
// .contentType(MediaType.APPLICATION_JSON); //���ݵĸ�ʽ
// } else if(method == HttpMethod.GET){
// requestBuilder = MockMvcRequestBuilders.get(url) //�����url,����ķ�����get
// .contentType(MediaType.APPLICATION_JSON); //���ݵĸ�ʽ
// }
//
// if (requestBuilder == null) {
// return "";
// }
//
// Set<String> keys= params.keySet();
//
// for(String key : keys) {
// requestBuilder.param(key, params.get(key)); //��Ӳ���
// }
//
// return mockMvc.perform(requestBuilder)
// .andExpect(MockMvcResultMatchers.status().isOk()) //���ص�״̬��200
// .andDo(MockMvcResultHandlers.print()) //��ӡ���������Ӧ������
// .andReturn()
// .getResponse()
// .getContentAsString(); //����Ӧ������ת��Ϊ�ַ���
// }
//
// }
