package multiimplement;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Resources.Carriage;
import Timeslot.Timeslot;
import common.CommonPlanningEntry;
import compositeinterface.TrainEntry;

public class BlockableEntryImplTest {
	//测试策略:
	
	/*为了避免get函数与set函数之间的联系，先将timeslots属性改为public进行set函数的测试
	// 再改回private属性，进行之后的测试
	@Test
	public void setTimeTest() {
		BlockableEntryImpl<Carriage> bel=new BlockableEntryImpl<Carriage>();
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		Timeslot timeslot1=new Timeslot("2002-05-04 10:00", "2002-05-05 01:00");
		Timeslot timeslot2=new Timeslot("2002-08-04 07:00", "2002-08-06 00:00");
		timeslots.add(timeslot1);
		timeslots.add(timeslot2);
		bel.setTime(timeslots);
		assertEquals(timeslots, bel.timeslots);
	}*/
	
	
	@Test
	public void getTimetest() {
		BlockableEntryImpl<Carriage> bel=new BlockableEntryImpl<Carriage>();
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		Timeslot timeslot1=new Timeslot("2020-04-04 10:00", "2020-04-05 01:00");
		Timeslot timeslot2=new Timeslot("2020-04-05 07:00", "2020-04-06 00:00");
		timeslots.add(timeslot1);
		timeslots.add(timeslot2);
		bel.setTime(timeslots);
		assertEquals(timeslots, bel.getTime());
	}
	
	//测试策略:block的时间点在停靠中间站点时；时间点在运行时；时间点在启动前；时间点在结束后
	@Test
	public void blockTest() throws ParseException {
		BlockableEntryImpl<Carriage> bel=new BlockableEntryImpl<Carriage>();
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		Timeslot timeslot1=new Timeslot("2020-04-20 10:00", "2020-04-21 01:00");
		Timeslot timeslot2=new Timeslot("2020-04-21 02:00", "2020-04-22 00:00");
		timeslots.add(timeslot1);
		timeslots.add(timeslot2);
		bel.setTime(timeslots);
		CommonPlanningEntry<Carriage> cpe=new TrainEntry<Carriage>("a");
		List<Carriage> reource=new ArrayList<Carriage>();
		Carriage carriage=new Carriage("10", "type", 5, "manufactureyear");
		reource.add(carriage);
		bel.block(cpe);
		assertNotEquals("Blocked", cpe.getStateName());
		cpe.allocateResource(reource);
		bel.block(cpe);
		assertNotEquals("Blocked", cpe.getStateName());
		cpe.start();
		bel.block(cpe);
		assertEquals("Blocked", cpe.getStateName());
	}
}
