package dao;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import models.Weibo;
import play.db.ebean.Model.Finder;
import util.WeiboException;

public class WeiboDao {
	public static Finder<Long,Weibo> find = new Finder<Long,Weibo>(Long.class, Weibo.class);
	/**
	 * 保存
	 * @param weibo
	 */
	public static void saveWeibo(Weibo weibo) throws WeiboException{
		try {
			weibo.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新
	 * @param wb
	 */
	public static void updateWeibo(Weibo wb) throws WeiboException{
		wb.update();
	}
	/**
	 * 查询当前页数据
	 * @param pageNum
	 * @return
	 */
	public static List<Weibo> getAllWeibo(int pageNum){
		Page<Weibo> pages = find.where().orderBy("id desc").findPagingList(5).getPage(pageNum);
		List<Weibo> w = pages.getList();
		return w;
	}
	/**
	 * 得到总页数
	 * @return
	 */
	public static int getPageSize(){
		int pagesize = find.where().findPagingList(5).getTotalPageCount();
		return pagesize;
	}
	/**
	 * 删除所有记录，在用户登录的时候删除所有微博记录，插入最新的微博记录
	 */
	public static void delAll() throws WeiboException{
		List<Weibo> w = find.all();
		if(w != null && w.size() != 0){
			for(int i=0;i<w.size();i++){
				Ebean.delete(w.get(i));
			}
		}
	}
	/**
	 * 页面操作上的删除，包括单一删除和批量删除
	 * @param id
	 */
	public static void delWeibo(String id) throws WeiboException{
		Long ids = Long.valueOf(id);
		Weibo fs = WeiboDao.find.byId(ids);
		fs.delete();
	}
}
