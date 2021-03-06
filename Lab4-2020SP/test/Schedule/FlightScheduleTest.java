package Schedule;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Exceptions.*;
import Location.Location;
import Resources.Plane;
import Timeslot.Timeslot;
import compositeinterface.FlightEntry;

public class FlightScheduleTest {
	FlightSchedule fs;
	Plane plane=new Plane("N3025", "type", 50, 2.5);
	Location start=new Location("130E","45S" , "test", true);
	Location end=new Location("10E","47n" , "tet", true);
	
	@Before
	public void prepare() {
		fs=new FlightSchedule();
	}
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	/*为了避免get函数与该函数之间的联系，先将属性改为public进行测试
	//测试策略：单次添加飞机；重复添加飞机
	//			添加一个飞机；添加多个飞机
	@Test
	public void addPlanetest() throws IllegalPlaneContentException {
		fs.addPlane(plane);
		assertEquals(1,fs.planes.size());
		assertTrue(fs.planes.contains(plane));
		Plane plane2=new Plane("N3074", "type", 50, 2.5);
		fs.addPlane(plane2);
		assertEquals(2,fs.planes.size());
		assertTrue(fs.planes.contains(plane2));
		fs.addPlane(plane);
		assertEquals(2,fs.planes.size());
	}

	//为了避免get函数与该函数之间的联系，先将属性改为public进行函数的测试
	//测试策略：单次添加位置；重复添加位置
	//			添加一个位置；添加多个位置
	@Test
	public void addLocationTest() {
		fs.addLocation(start);
		assertEquals(1, fs.locations.size());
		assertTrue(fs.locations.contains(start));
		fs.addLocation(end);
		assertTrue(fs.locations.contains(end));
		assertEquals(2, fs.locations.size());
		fs.addLocation(end);
		assertEquals(2, fs.locations.size());
	}*/
	
	@Test
	public void getPlanesTest() throws IllegalPlaneContentException {
		fs.addPlane(plane);
		Plane plane2=new Plane("N3074", "type", 50, 2.5);
		assertEquals(1, fs.getPlanes().size());
		assertTrue(fs.getPlanes().contains(plane));
		fs.addPlane(plane2);
		assertTrue(fs.getPlanes().contains(plane2));
		assertEquals(2, fs.getPlanes().size());
	}
	
	@Test
	public void getLocationsTest() {
		fs.addLocation(start);
		assertEquals(1, fs.getLocations().size());
		assertTrue(fs.getLocations().contains(start));
		fs.addLocation(end);
		assertEquals(2, fs.getLocations().size());
		assertTrue(fs.getLocations().contains(end));
	}
	
	//为了避免get函数与该函数之间的联系，先将属性改为public进行函数的测试
	//测试策略:位置尚未纳入管理；位置已经纳入管理
	//		   航班之前从未创建过；航班已经创建但位置改变；航班已创建,有相同日期但时间点改变;
	//		  航班已创建，完全重复地二次创建;航班已创建，日期不同时间点不同；航班已创建，日期不同时间点相同(这种情形应该是成立的)；
	//异常情况：位置尚未纳入管理
	@Test
	public void createFlightTest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		expectedEx.expect(LocationNotFoundException.class);
		fs.createFlight("NR7412", "test", "tet", timeslot);
	}
	
	/*位置已经纳入管理、航班之前从未创建过
	@Test
	public void createFlightTest2() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
	}
	
	//异常情况：航班已创建，完全重复地二次创建
	@Test
	public void createFlightTest3() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
		expectedEx.expect(SameLabelException.class);
		fs.createFlight("NR7412", "test", "tet", timeslot);
	}
	
	//异常情况：航班已经创建但位置改变
	@Test
	public void createFlightTest4() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
		expectedEx.expect(InconsistentStartOrEndException.class);
		fs.createFlight("NR7412", "tet", "test", timeslot);
	}
	
	//异常情况：航班已创建,有相同日期但时间点改变;
	@Test
	public void createFlightTest5() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
		Timeslot timeslot2=new Timeslot("2020-04-06 10:14", "2020-04-07 14:15");
		expectedEx.expect(InconsistentStartOrEndException.class);
		fs.createFlight("NR7412", "test", "tet", timeslot2);
	}
	
	//异常情况：航班已创建，日期不同时间点不同
	@Test
	public void createFlightTest6() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
		Timeslot timeslot3=new Timeslot("2020-04-03 12:14", "2020-04-04 14:15");
		expectedEx.expect(InconsistentStartOrEndException.class);
		fs.createFlight("NR7412", "test", "tet", timeslot3);
	}
	
	//航班已创建，日期不同时间点相同
	@Test
	public void createFlightTest7() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.flights.size());
		Timeslot timeslot4=new Timeslot("2020-04-03 10:14", "2020-04-04 14:15");
		fs.createFlight("NR7412", "test", "tet", timeslot4);
		assertEquals(2, fs.flights.size());
	}*/
	
	//测试策略: 位置纳入管理 ；位置尚未纳入管理
	//			航班之前从未创建过；航班已经创建过
	//异常情况:位置尚未纳入管理；
	@Test
	public void getFlightsTest() throws ParseException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		expectedEx.expect(LocationNotFoundException.class);
		fs.createFlight("NR7412","test", "tet", timeslot);
	}
	
	// 位置纳入管理、  航班之前从未创建过；
	@Test
	public void getFlightsTest2() throws ParseException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.getFlights().size());
	}
	
	//异常情况：航班之前已创建；
	@Test
	public void getFlightsTest3() throws ParseException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		assertEquals(1, fs.getFlights().size());
		expectedEx.expect(SameLabelException.class);
		fs.createFlight("NR7412", "test", "tet", timeslot);
	}
	
	//测试策略：指定航班已创建；指定航班未创建
	//指定航班已创建
	@Test
	public void getFlightStateTest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, PlanEntryNotCreateException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(end);
		fs.addLocation(start);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		assertEquals("Waiting", fs.getFlightState("NR7412",timeslots));
	}
	
	//指定航班未创建
	@Test
	public void getFlightStateTest2() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, PlanEntryNotCreateException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		expectedEx.expect(PlanEntryNotCreateException.class);
		fs.getFlightState("nul;",timeslots);
	}
	
	//测试策略:删除的飞机还未创建；删除的飞机已创建
	//	   	     删除的飞机被占用；删除的飞机未被占用
	//异常情况：删除的飞机还未创建
	@Test
	public void deletePlaneTest() throws IllegalPlaneContentException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, ResourceNotFoundException, PlanEntryOccupyResourceException, ResourceConflictException, PlanEntryNotCreateException {
		expectedEx.expect(ResourceNotFoundException.class);
		fs.deletePlane("N3025");
	}
	
	//删除的飞机已创建、未被占用
	@Test
	public void deletePlaneTest2() throws IllegalPlaneContentException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, ResourceNotFoundException, PlanEntryOccupyResourceException, ResourceConflictException, PlanEntryNotCreateException {
		fs.addPlane(plane);
		Plane plane2=new Plane("N3074", "type", 50, 2.5);
		assertEquals(1, fs.getPlanes().size());
		fs.addPlane(plane2);
		fs.deletePlane("N3074");
		assertEquals(1, fs.getPlanes().size());
	}
	
	//异常情况：删除的飞机被占用
	@Test
	public void deletePlaneTest3() throws IllegalPlaneContentException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, ResourceNotFoundException, PlanEntryOccupyResourceException, ResourceConflictException, PlanEntryNotCreateException {
		fs.addPlane(plane);
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NQ7851", start.getName(), end.getName(), timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		fs.allocatePlane("NQ7851", "N3025",timeslots);
		expectedEx.expect(PlanEntryOccupyResourceException.class);
		fs.deletePlane("N3025");
	}
	
	//测试策略:删除的位置还未创建；删除的位置已创建
	//		   删除的位置被占用；删除的位置未被占用
	//异常情况:删除的位置未创建
	@Test
	public void deleteLocationTest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, PlanEntryOccupyLocationException {
		expectedEx.expect(LocationNotFoundException.class);
		fs.deleteLocation("tet");
	}
	
	//删除的位置已创建、删除的位置未被占用
	@Test
	public void deleteLocationTest2() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, PlanEntryOccupyLocationException {
		fs.addLocation(start);
		fs.addLocation(end);
		assertEquals(2, fs.getLocations().size());
		fs.deleteLocation("tet");
		assertEquals(1, fs.getLocations().size());
	}
	
	//异常情况:删除的位置被占用
	@Test
	public void deleteLocationTest3() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, PlanEntryOccupyLocationException {
		fs.addLocation(start);
		fs.addLocation(end);
		assertEquals(2, fs.getLocations().size());
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.createFlight("NQ7851", start.getName(), end.getName(), timeslot);
		expectedEx.expect(PlanEntryOccupyLocationException.class);
		fs.deleteLocation("tet");
	}
	
	//测试策略：分配的飞机已经纳入管理；分配的飞机还未纳入管理
	//			航班未创建；航班已创建
	//异常情况:分配的飞机还未纳入管理
	@Test
	public void allocatePlaneTest() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		expectedEx.expect(ResourceNotFoundException.class);
		fs.allocatePlane("NR7412", "N3025",timeslots);
	}
	
	//分配的飞机已经纳入管理、航班已创建
	@Test
	public void allocatePlaneTest2() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		fs.addPlane(plane);
		fs.allocatePlane("NR7412", "N3025",timeslots);
		assertEquals("Allocated", fs.getFlightState("NR7412",timeslots));
	}

	//异常情况：	航班未创建；
	@Test
	public void allocatePlaneTest3() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		fs.addPlane(plane);
		expectedEx.expect(PlanEntryNotCreateException.class);
		fs.allocatePlane("NULL", "N3025",timeslots);
	}
	
	//测试策略：航班已创建；航班未创建
	//			航班已分配飞机；航班未分配飞机
	//异常情况：航班未创建
	@Test
	public void depatureTest() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		expectedEx.expect(PlanEntryNotCreateException.class);
		fs.departure("null",timeslots);
	}
	
	//航班已创建、航班已分配飞机；航班已创建、航班未分配飞机
	@Test
	public void depatureTest2() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.addPlane(plane);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		Timeslot timeslot2=new Timeslot("2020-04-01 10:14", "2020-04-03 14:15");
		List<Timeslot> timeslots2=new ArrayList<Timeslot>();
		timeslots2.add(timeslot2);
		fs.createFlight("NR7417", "test", "tet", timeslot2);
		fs.allocatePlane("NR7417", "N3025",timeslots2);
		fs.departure("NR7412",timeslots);
		assertNotEquals("Running", fs.getFlightState("NR7412",timeslots));
		fs.departure("NR7417",timeslots2);
		assertEquals("Running", fs.getFlightState("NR7417",timeslots2));
	}
	
	//测试策略：航班已创建；航班未创建
	//			航班未起飞；航班已起飞
	//异常情况：航班未创建
	@Test
	public void cancelFlightTest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		expectedEx.expect(PlanEntryNotCreateException.class);
		fs.cancelFlight("null",timeslots);
	}
	
	//航班已创建、航班未起飞；航班已创建、航班已起飞
	@Test
	public void cancelFlightTest2() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.addPlane(plane);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		Timeslot timeslot2=new Timeslot("2020-04-01 10:14", "2020-04-03 14:15");
		fs.createFlight("NR7417", "test", "tet", timeslot2);
		List<Timeslot> timeslots2=new ArrayList<Timeslot>();
		timeslots2.add(timeslot2);
		fs.allocatePlane("NR7412", "N3025",timeslots);
		fs.departure("NR7412",timeslots);
		fs.cancelFlight("NR7412",timeslots);
		fs.cancelFlight("NR7417",timeslots2);
		assertNotEquals("Cancelled", fs.getFlightState("NR7412",timeslots));
		assertEquals("Cancelled", fs.getFlightState("NR7417",timeslots2));
	}
	
	//测试策略：航班已创建；航班未创建
	//			航班未起飞；航班已起飞
	//异常情况：航班未创建
	@Test
	public void endFlightTest() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		expectedEx.expect(PlanEntryNotCreateException.class);
		fs.endFlight("null",timeslots);
	}
	
	//航班已创建、航班未起飞；航班已创建、航班已起飞
	@Test
	public void endFlightTest2() throws ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException, InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-07 14:15");
		fs.addLocation(start);
		fs.addLocation(end);
		fs.addPlane(plane);
		fs.createFlight("NR7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		Timeslot timeslot2=new Timeslot("2020-04-01 10:14", "2020-04-03 14:15");
		fs.createFlight("NR7417", "test", "tet", timeslot2);
		List<Timeslot> timeslots2=new ArrayList<Timeslot>();
		timeslots2.add(timeslot2);
		fs.allocatePlane("NR7412", "N3025",timeslots);
		fs.departure("NR7412",timeslots);
		fs.endFlight("NR7412",timeslots);
		fs.endFlight("NR7417",timeslots2);
		assertNotEquals("Ended", fs.getFlightState("NR7417",timeslots2));
		assertEquals("Ended", fs.getFlightState("NR7412",timeslots));
	}
	
	@Test
	public void getFlightsofassignPlanetest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException, IllegalPlaneContentException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-05 14:15");
		fs.addPlane(plane);
		Plane plane2=new Plane("N3125", "type", 50, 2.5);
		fs.addPlane(plane2);
		fs.addLocation(start);
		fs.addLocation(end);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		Timeslot timeslot2=new Timeslot("2020-04-04 10:14", "2020-04-04 14:15");
		List<Timeslot> timeslots2=new ArrayList<Timeslot>();
		timeslots2.add(timeslot2);
		fs.createFlight("NA5874", "test", "tet", timeslot2);
		fs.createFlight("WR7412", "test", "tet", timeslot);
		fs.allocatePlane("NA5874", "N3125",timeslots2);
		fs.allocatePlane("WR7412", "N3025",timeslots);
		List<FlightEntry<Plane>> fes=fs.getFlightssofassignPlane("N3025");
		assertEquals(1, fes.size());
		assertEquals("WR7412", fes.get(0).getName());
		fes=fs.getFlightssofassignPlane("N3125");
		assertEquals(1, fes.size());
		assertEquals("NA5874", fes.get(0).getName());
	}
	
	
	@Test
	public void getPlanebyIDTest() throws IllegalPlaneContentException {
		fs.addPlane(plane);
		assertEquals("N3025", fs.getPlanebyID("N3025").getId());
		assertEquals("type", fs.getPlanebyID("N3025").getType());
	}
	
	@Test
	public void getLocationbyNameTest() {
		fs.addLocation(start);
		fs.addLocation(end);
		assertEquals("test", fs.getLocationbyName("test").getName());
		assertEquals("tet", fs.getLocationbyName("tet").getName());
		assertEquals("10E", fs.getLocationbyName("tet").getLongitude());
		assertEquals("45S", fs.getLocationbyName("test").getLatitude());
	}
	
	@Test
	public void getFlightbyNameTest() throws InconsistentStartOrEndException, LocationNotFoundException, SameLabelException {
		fs.addLocation(start);
		fs.addLocation(end);
		Timeslot timeslot=new Timeslot("2020-04-05 10:14", "2020-04-05 14:15");
		fs.createFlight("NH7412", "test", "tet", timeslot);
		List<Timeslot> timeslots=new ArrayList<Timeslot>();
		timeslots.add(timeslot);
		assertEquals("NH7412", fs.getFlightbyName("NH7412",timeslots).getName());
		assertEquals(2, fs.getFlightbyName("NH7412",timeslots).getLocation().size());
		assertEquals(1, fs.getFlightbyName("NH7412",timeslots).getTime().size());
	}
	
	//测试策略:两个航班名称完全相同；两个航班名称如CA0001和CA01是否能正确判别为相同
	//			字符部分不相同的航班名称；数字部分值不同的航班名称
	@Test
	public void ifTwoSameFlightNameTest() {
		String name1="equals";
		String name2="equals";//完全相同
		assertTrue(fs.ifTwoSameFlightName(name1, name2));
		String name3="FA0007";
		String name4="FG0007";//字符部分不同
		assertFalse(fs.ifTwoSameFlightName(name3, name4));
		String name5="FA07";//数字部分值相同
		assertTrue(fs.ifTwoSameFlightName(name5, name3));
		String name6="FG0004";//数字部分值不同
		assertFalse(fs.ifTwoSameFlightName(name6, name4));
	}
	
	//异常测试：飞机机龄超出范围
	@Test
	public void AgeOutofBoundExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {
		String path="test\\ExceptionTXT\\AgeOutofBoundException.txt";
		expectedEx.expect(Exceptions.AgeOutofBoundException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：起飞日期与降落日期超过一天
	@Test
	public void DateDifferMuchExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\DateDifferMuchException.txt";
		expectedEx.expect(DateDifferMuchException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：日期格式错误
	@Test
	public void DateFormatExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\DateFormatException.txt";
		expectedEx.expect(DateFormatException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：航班名称格式错误
	@Test
	public void FlightNameFormatExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\FlightNameFormatException.txt";
		expectedEx.expect(FlightNameFormatException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：机场名称出现非法字符
	@Test
	public void IllegalCharacterForAirportNameExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\IllegalCharacterForAirportNameException.txt";
		expectedEx.expect(IllegalCharacterForAirportNameException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：相同编号的飞机，其他内容不一致
	@Test
	public void IllegalPlaneContentExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\IllegalPlaneContentException.txt";
		expectedEx.expect(IllegalPlaneContentException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：出现航班信息不完整
	@Test
	public void IncompleteFlightInformationExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\IncompleteFlightInformationException.txt";
		expectedEx.expect(IncompleteFlightInformationException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：航班第一行指定日期与起飞日期不一致
	@Test
	public void InconsistentDateExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\InconsistentDateException.txt";
		expectedEx.expect(InconsistentDateException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：应该只能是数字的地方出现了非数字
	@Test
	public void NonNumberExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\NonNumberException.txt";
		expectedEx.expect(NonNumberException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：飞机ID格式错误
	@Test
	public void PlaneIDFormatExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\PlaneIDFormatException.txt";
		expectedEx.expect(PlaneIDFormatException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：飞机座位数超出范围
	@Test
	public void SeatsSizeOutofBoundExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\SeatsSizeOutofBoundException.txt";
		expectedEx.expect(SeatsSizeOutofBoundException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：机型出现非法字符
	@Test
	public void TypeContainsOtherSymbolExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\TypeContainsOtherSymbolException.txt";
		expectedEx.expect(TypeContainsOtherSymbolException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：文件不存在
	@Test
	public void FileNotFoundExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\null.txt";
		expectedEx.expect(FileNotFoundException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：标签相同
	@Test
	public void SameLabelExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\SameLabelException.txt";
		expectedEx.expect(SameLabelException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：相同名称航班起飞/降落的机场/时间点不同
	@Test
	public void InconsistentStartOrEndExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\InconsistentStartOrEndException.txt";
		expectedEx.expect(InconsistentStartOrEndException.class);
		fs.createFlightByFile(path);
	}
	
	//异常测试：相同名称航班起飞/降落的机场/时间点不同
	@Test
	public void ResourceConflictExceptionTest() throws FileNotFoundException, LocationNotFoundException, IncorrectElementDependencyException, SameLabelException, UnGrammaticalWordException, ResourceNotFoundException, ResourceConflictException, PlanEntryNotCreateException {			
		String path="test\\ExceptionTXT\\ResourceConflictException.txt";
		expectedEx.expect(ResourceConflictException.class);
		fs.createFlightByFile(path);
	}
	
}
