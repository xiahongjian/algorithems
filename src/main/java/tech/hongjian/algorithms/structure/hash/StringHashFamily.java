/**
 * @author xiahongjian 
 * @time   Mar 14, 2018
 */
package tech.hongjian.algorithms.structure.hash;

import java.util.Random;

/**
 * @author xiahongjian 
 * @time   2018-03-14 17:10:42
 *
 */
public class StringHashFamily implements HashFamily<String> {
	private final int[] MULTIPLIERS;
	private final Random r = new Random();
	
	public StringHashFamily(int n) {
		MULTIPLIERS = new int[n];
		generateNewFunctions();
	}
	
	@Override
	public int hash(String e, int which) {
		final int multipier = MULTIPLIERS[which];
		int hashVal = 0;
		
		for (int i = 0; i < e.length(); i++) {
			hashVal = multipier * hashVal + e.charAt(i);
		}
		return hashVal;
	}

	@Override
	public int getNumberOfFunctions() {
		return MULTIPLIERS.length;
	}

	@Override
	public void generateNewFunctions() {
		for (int i = 0; i < MULTIPLIERS.length; i++)
			MULTIPLIERS[i] = r.nextInt();
	}

}
