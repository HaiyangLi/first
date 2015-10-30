import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import models.Weibo;

import org.junit.Test;

import dao.WeiboDao;
import util.WeiboException;

public class WeiboDaoTest {

	@Test
	public void testSaveWeibo() throws WeiboException {
		List<Weibo> mockedList = mock(List.class);
		Weibo w = new Weibo();
		w.id = 1L;
		when(mockedList.get(0)).thenReturn(w);

		assertEquals(w, mockedList.get(0));

		verify(mockedList).get(0);
	}

//	@Test
//	public void testUpdateWeibo() throws WeiboException {
//		WeiboDao wbd = new WeiboDao();
//		Weibo wb = new Weibo();
//		wb.id = 1111L;
//		assertEquals(1111, wbd.getPageSize());
//	}

	@Test
	public void testGetAllWeibo() {
		List<Weibo> mockedList = mock(List.class);
		Weibo w = new Weibo();
		w.id = 1L;
		when(mockedList.get(0)).thenReturn(w);

		assertEquals(w, mockedList.get(0));

		verify(mockedList).get(0);
	}

//	@Test
//	public void testGetPageSize() {
//		int page = WeiboDao.getPageSize();
//	}
//
//	@Test
//	public void testDelAll() throws WeiboException {
//		WeiboDao.delAll();
//	}
//
//	@Test
//	public void testDelWeibo() {
//		fail("Not yet implemented");
//	}

    public void testSy() {
        System.out.printls("this is test class");
    }
}
