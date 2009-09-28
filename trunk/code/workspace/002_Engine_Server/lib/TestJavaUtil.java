import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;


public class TestJavaUtil
{
	
	
	
	public static void main(String[] args) 
	{
		{
			Map[] maps = new Map[]{
					new HashMap<Integer, String>(10000),
					new Hashtable<Integer, String>(10000),
					new ConcurrentHashMap<Integer, String>(10000),
					new ConcurrentSkipListMap<Integer, String>(),
					new FastMap<Integer, String>(10000),
			};
			
			for (int i=0; i<maps.length; i++) {
				testPutMap(maps[i]);
			}
			System.out.println();
			
			for (int i=0; i<maps.length; i++) {
				testGetMap(maps[i]);
			}
			System.out.println();
		}
		//--------------------------------------------------------------------------------------------------
		{
			List[] lists = new List[]{
					new ArrayList<Integer>(10000),
					new Vector<Integer>(10000),
					new FastList<Integer>(10000),
			};
			
			for (int i=0; i<lists.length; i++) {
				testAddList(lists[i]);
			}
			System.out.println();
			
			for (int i=0; i<lists.length; i++) {
				testGetList(lists[i]);
			}
			System.out.println();
		}
		//--------------------------------------------------------------------------------------------------
		{
			Queue[] queues = new Queue[]{
					new LinkedList<Integer>(),
					new ConcurrentLinkedQueue<Integer>(),
			};
			
			for (int i=0; i<queues.length; i++) {
				testAddQueue(queues[i]);
			}
			System.out.println();
			
			for (int i=0; i<queues.length; i++) {
				testPollQueue(queues[i]);
			}
			System.out.println();
		}
		//--------------------------------------------------------------------------------------------------
		{
			Set[] sets = new Set[]{
					new HashSet<Integer>(),
					new ConcurrentSkipListSet<Integer>(),
					new FastSet<Integer>(),
			};
			
			for (int i=0; i<sets.length; i++) {
				testAddSet(sets[i]);
			}
			System.out.println();
			
			for (int i=0; i<sets.length; i++) {
				testIterSet(sets[i]);
			}
			System.out.println();
		}
		//--------------------------------------------------------------------------------------------------
	}

	//
	private static void testPutMap(Map<Integer, String> map)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			map.put(i, "" + i);
		}
		System.out.println((System.nanoTime() - time) + "\t : put " + map.getClass().getName());
		System.gc();
		Thread.yield();
	}

	private static void testGetMap(Map<Integer, String> map)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			map.get(i);
		}
		System.out.println((System.nanoTime() - time) + "\t : get " + map.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	//
	private static void testAddList(List<Integer> list)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			list.add(i);
		}
		System.out.println((System.nanoTime() - time) + "\t : add " + list.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	private static void testGetList(List<Integer> list)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			list.get(i);
		}
		System.out.println((System.nanoTime() - time) + "\t : get " + list.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	//
	private static void testAddQueue(Queue<Integer> queue)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			queue.add(i);
		}
		System.out.println((System.nanoTime() - time) + "\t : add " + queue.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	private static void testPollQueue(Queue<Integer> queue)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			queue.poll();
		}
		System.out.println((System.nanoTime() - time) + "\t : poll " + queue.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	//
	private static void testAddSet(Set<Integer> set)
	{
		long time = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			set.add(i);
		}
		System.out.println((System.nanoTime() - time) + "\t : add " + set.getClass().getName());
		System.gc();
		Thread.yield();
	}
	
	private static void testIterSet(Set<Integer> set)
	{
		long time = System.nanoTime();
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			it.next();
		}
		System.out.println((System.nanoTime() - time) + "\t : iterator " + set.getClass().getName());
		System.gc();
		Thread.yield();
	}
}
