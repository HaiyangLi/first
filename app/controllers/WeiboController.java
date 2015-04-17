package controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Users;
import models.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import views.html.view;
import play.Logger;
import play.cache.Cache;
import play.data.Form;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http.Response;
import play.mvc.Result;
import scala.concurrent.Await;
import service.WeiboService;
import util.WeiboException;
import util.Utils;

import com.fasterxml.jackson.databind.JsonNode;

import dao.WeiboDao;


public class WeiboController extends Controller{
	/**
	 * 新浪微博授权成功后的回调地址
	 * 1， 如果在Cache中有access_token
	 * 	就会直接用access_token去发送WS请求得到微博内容的json
	 * 2，再将json解析插入到数据库中
	 * 3，从数据库中将插入的数据读取出来
	 * @return
	 */
	public static Promise<Result> setToken(){
		String access_token = Utils.trimNull(Cache.get("access_token"));
		String uri = Utils.trimNull(request().uri());
		String code  = uri.substring(uri.indexOf("="), uri.length());
		if("".equals(access_token)){
			String urlone = "https://api.weibo.com/oauth2/access_token?client_id=3856530859&client_secret=ce019c7a93abdb9a1f11d1fd3104945f&grant_type=authorization_code&redirect_uri=127.0.0.1:9000/setToken&code"+code;
			Promise<Result> responseThreePromise = WS.url(urlone).post(urlone).flatMap(
		        new Function<WSResponse, Promise<Result>>() {
		            public Promise<Result> apply(WSResponse responseOne) {
		                JsonNode json = responseOne.asJson();
		            	String access_token = json.findPath("access_token").textValue();
		            	Cache.set("access_token", access_token);
		            	String urltwo = "https://api.weibo.com/2/statuses/friends_timeline.json?access_token="+access_token;
	            		Promise<Result> result = weiboAdd(access_token);
	            		return result;
		            }
		        }
			);
			return responseThreePromise;
			
		}else{
			String url = "https://api.weibo.com/2/statuses/friends_timeline.json?access_token="+access_token;
    		Promise<Result> result = weiboAdd(access_token);
    		return result;
		}
	}
	/**
	 * 获取微博的结果
	 * @param access_token
	 * @param uid
	 */
	public static Promise<Result> weiboAdd(String access_token){
		String url = "https://api.weibo.com/2/statuses/friends_timeline.json?access_token="+access_token;
		Promise<Result> jsonPromise = WS.url(url).get().map(
	        new Function<WSResponse, Result>() {
	            public Result apply(WSResponse response){
	            	JsonNode json = response.asJson();
	            	try {
	            		WeiboService.addWeibo(json);
					} catch (WeiboException e) {
						e.printStackTrace();
					} catch(JSONException ex){
						ex.printStackTrace();
					}
	            	List<Weibo> list = WeiboService.getAllWeibo(0);
					int pagesize = WeiboDao.getPageSize();
					return ok(view.render(list,0,pagesize,"新浪微博","版权所有Copyright ©"));
	            }
	        }
		);
		return jsonPromise;
	}
	public static Result weiboList(int page){
		List<Weibo> list = WeiboService.getAllWeibo(page);
		int pagesize = WeiboDao.getPageSize();
		return ok(view.render(list,page,pagesize,"新浪微博","版权所有Copyright ©"));
	}
	/**
	 * 编辑微博
	 * @return
	 */
	public static Result editWeibo(){
		//获取view中提交的表单
		Form<Weibo> userForm = Form.form(Weibo.class);
		Weibo frs = userForm.bindFromRequest().get();
		try {
			WeiboService.updateWeibo(frs);
		} catch (WeiboException e) {
			Logger.error("编辑微博失败！");
			return ok("编辑微博失败！");
		}
		return weiboList(0);
	}
	/**
	 * 单一删除和批量删除
	 * @param id
	 * @return
	 */
	public static Result del(String id){
		try {
			WeiboService.del(id);
		} catch (WeiboException e) {
			Logger.error("删除微博失败！");
			return ok("删除微博失败！");
		}
		return weiboList(0);
	}
}
