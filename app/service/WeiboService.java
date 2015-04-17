package service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import models.Users;
import models.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.Logger;
import util.Utils;
import util.WeiboException;

import com.fasterxml.jackson.databind.JsonNode;

import dao.WeiboDao;

public class WeiboService {
	/**
	 * 保存
	 * @param weibo
	 */
	public static void saveWeibo(Weibo weibo) throws WeiboException{
		WeiboDao.saveWeibo(weibo);
	}
	/**
	 * 更新
	 * @param wb
	 */
	public static void updateWeibo(Weibo frs) throws WeiboException{
		String text = frs.text;
		Weibo fs = WeiboDao.find.byId(frs.id);
	    fs.text = text;
	    WeiboDao.updateWeibo(fs);
	}
	/**
	 * 查询当前页数据
	 * @param pageNum
	 * @return
	 */
	public static List<Weibo> getAllWeibo(int pageNum){
		int pagesize = getPageSize();
		if(pageNum<0){
			pageNum = 0;
		}
		if(pageNum>=pagesize){
			pageNum = pagesize-1;
		}
		List<Weibo> w = WeiboDao.getAllWeibo(pageNum);
		return w;
	}
	/**
	 * 得到总页数
	 * @return
	 */
	public static int getPageSize(){
		int pagesize = WeiboDao.getPageSize();
		return pagesize;
	}
	/**
	 * 删除微博记录
	 * @throws SQLException 
	 * @throws WeiboException 
	 */
	public static void del(String id) throws WeiboException{
		if("".equals(id)){
			WeiboDao.delAll();
		}else if(id.indexOf(",")!=-1){
			String[] ids = id.split(",");
			for(int i=0;i<ids.length;i++){
				WeiboDao.delWeibo(ids[i]);
			}
		}else{
			WeiboDao.delWeibo(id);
		}
	}
	/**
	 * 插入微博
	 * @param json
	 */
	public static void addWeibo(JsonNode json) throws WeiboException,JSONException{
		String js = json.toString();
		List<HashMap<String, String>> listArrays = Utils.weiboList(js);
		//Ebean.beginTransaction();
		WeiboService.del("");
		for(int i=0;i<listArrays.size();i++){
			Weibo fs = new Weibo();
			HashMap map = listArrays.get(i);
			fs.id = Long.valueOf(Utils.trimNull(map.get("id")));
			fs.text = Utils.trimNull(map.get("text"));
			fs.username = Utils.trimNull(map.get("name"));
			fs.source = Utils.trimNull(map.get("source"));
//			fs.retweetedContent = Utils.trimNull(map.get("retweetedContent"));
//			fs.retweetedText = Utils.trimNull(map.get("retweetedText"));
			WeiboDao.saveWeibo(fs);
		}
//		Ebean.commitTransaction();
//		Ebean.endTransaction();
	}
	/**
	 * 添加用户
	 * @param json
	 */
	public static void addUser(JsonNode json) throws WeiboException,JSONException{
		String result = Utils.parseJsonText("["+json.toString()+"]");
		JSONArray jsonArrays = new JSONArray(result);
    	for(int i=0;i<jsonArrays.length();i++){
    		JSONObject jsonObject = jsonArrays.getJSONObject(i);
    		Users u = new Users();
    		u.userid = Long.valueOf(jsonObject.getString("id"));
    		u.username = jsonObject.getString("name");
    		u.description = jsonObject.getString("description");
    		Users.saveUsers(u);
    	}
	}
}
