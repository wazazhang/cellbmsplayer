

public class TestObjectPool
{
	static class ObjectPool extends com.cell.util.ObjectPool<Object> 
	{
		public ObjectPool(int size) {
			super(size);
		}
		
		@Override
		protected Object createObject() {
			return new Object();
		}
		
	}
	
	
	public static void main(String[] args) 
	{
//		ObjectPool pool = new ObjectPool(1000);
//		
//		long time = System.nanoTime();
//		for (int i=0; i<1000000; i++) {
//			Object obj = new Object();
//			obj = null;
//		}
//		System.out.println((System.nanoTime() - time) + "\t new Object");
//		
//		
//		time = System.nanoTime();
//		for (int i=0; i<1000000; i++) {
//			Object obj = pool.getObject();
//			pool.returnObject(obj);
//			obj = null;
//		}
//		System.out.println((System.nanoTime() - time) + "\t pool Object");
		
	}

	
}
