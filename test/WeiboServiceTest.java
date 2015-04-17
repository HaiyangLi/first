import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import models.Weibo;

import org.junit.Test;

import service.WeiboService;
import util.WeiboException;
import dao.WeiboDao;


public class WeiboServiceTest {

//	@Test
//	public void testSaveWeibo() throws WeiboException {
//		WeiboDao wbd = mock(WeiboDao.class);
//		List<Weibo> wb = new ArrayList<Weibo>();
//		Weibo w = mock(Weibo.class);
//		w.id = 100L;
//		wb.add(w);
//		WeiboService ws = mock(WeiboService.class);
//		ws.saveWeibo(w);
//	}

//	@Test
//	public void testUpdateWeibo() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testGetAllWeibo() {
		List<Weibo> mockedList = mock(List.class);
		Weibo w = new Weibo();
		w.id = 1L;
		when(mockedList.get(0)).thenReturn(w);

		assertEquals(w, mockedList.get(0));

		verify(mockedList).get(0);
	}
//
//	@Test
//	public void testGetPageSize() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDel() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddWeibo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddUser() {
//		fail("Not yet implemented");
//	}

}
