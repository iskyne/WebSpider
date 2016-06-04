package main.spider;

import java.util.BitSet;

public class StandardBloomFilter {
	public static final int DEFAULT_SIZE = 2<<25;
	
	public static final int BLOOM_FUNC_SIZE=7;
	
	private BitSet store;
	private BloomFunc[] funcs;
	private int[] seeds;
	
	public StandardBloomFilter(){
		store = new BitSet(DEFAULT_SIZE);
		funcs = new BloomFunc[BLOOM_FUNC_SIZE];
		seeds=new int[]{7,11,13,17,31,37,61};
		for(int i=0;i<BLOOM_FUNC_SIZE;i++){
			funcs[i] = new BloomFunc(seeds[i]);
		}
	}
	
	public synchronized void add(String value){
		for(int i=0;i<BLOOM_FUNC_SIZE;i++){
			int result=funcs[i].hash(value);
			store.set(result, true);
		}
	}
	
	public synchronized boolean contain(String value){
		for(int i=0;i<BLOOM_FUNC_SIZE;i++){
			int result=funcs[i].hash(value);
			if(!store.get(result)){
				return false;
			}
		}
		return true;
	}

	public BitSet getStore() {
		return store;
	}

	public void setStore(BitSet store) {
		this.store = store;
	}
	
	private static class BloomFunc{
		private int seed;
		BloomFunc(int i){
			this.seed=i;
		}
		
		public int hash(String value){
			int result=0;
			for(int i=0;i<value.length();i++){
				result=seed*result+Integer.valueOf(value.charAt(i));
			}
			return result&(DEFAULT_SIZE-1);
		}
	}
	
	public static void main(String args[]){
		
	}
}
