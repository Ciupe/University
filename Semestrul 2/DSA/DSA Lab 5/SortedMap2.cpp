//#include "SMIterator.h"
//#include "SortedMap2.h"
//#include <exception>
//using namespace std;
//
//SortedMap::SortedMap(Relation r) {
//	//TODO - Implementation
//	this->size = 0;
//	this->table.m = 13;
//	this->table.ht = new Node*[this->table.m];
//	for (int i = 0; i < this->table.m; ++i) {
//		this->table.ht[i] = new Node[this->table.m];
//		for (int j = 0; j < this->table.m; ++j)
//			this->table.ht[i][j].key = NULL_TVALUE;
//	}
//}
//
//int HashTable::h(int n)
//{
//	return (n % m);
//}
//
//void SortedMap::resize()
//{
//	int newCapacity = this->table.m * 2;
//	HashTable newTable;
//	for (int i = 0; i < this->table.m; ++i) {
//		Node currentNode = this->table.ht[i][0];		
//		while (currentNode.key != NULL_TVALUE) {			//iterating through all the keys from each value of the TFunction of the initial HashTable
//			int newKey = newTable.h(currentNode.key);		//the new value of the key (on which row we add it)
//			Node iteratorNode = newTable.ht[newKey][0];		//we iterate through all existing nodes with that value
//			while (iteratorNode.key != NULL_TVALUE && this->r(iteratorNode.key, newKey) == true) {		//we find the position where we should add the new key value
//
//			}
//		}
//	}
//}
//
//TValue SortedMap::add(TKey k, TValue v) {
//	//TODO - Implementation
//	return NULL_TVALUE;
//}
//
//TValue SortedMap::search(TKey k) const {
//	//TODO - Implementation
//	return NULL_TVALUE;
//}
//
//TValue SortedMap::remove(TKey k) {
//	//TODO - Implementation
//	return NULL_TVALUE;
//}
//
//int SortedMap::size() const {
//	//TODO - Implementation
//	return 0;
//}
//
//bool SortedMap::isEmpty() const {
//	//TODO - Implementation
//	return false;
//}
//
//SMIterator SortedMap::iterator() const {
//	return SMIterator(*this);
//}
//
//SortedMap::~SortedMap() {
//	//TODO - Implementation
//}