package tech.hongjian.algorithms.structure.hash;

public interface HashFamily<T> {
	int hash(T e, int which);
	int getNumberOfFunctions();
	void generateNewFunctions();
}
