#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

//DO NOT CHANGE THIS PART
const int NULL_TCOMP = -11111;
const int NULL_POSITION = - 1;
#include <iostream>
typedef int TComp;
typedef TComp TElem;
typedef std::pair <TComp, int> TPair;
typedef bool(*Relation)(TComp, TComp);


class SortedBagIterator;

struct Node {
	int previous;
	TPair info;
	int next;
};

class SortedBag {
	friend class SortedBagIterator;

private:
	//TODO - Representation
	Node* sortedBag;
	Relation relation;
	int capacity;
	int head;
	int tail;
	int firstEmpty;
	int bagSize;

public:
	//constructor
	SortedBag(Relation r);

	//adds an element to the sorted bag
	void add(TComp e);

	//removes one occurence of an element from a sorted bag
	//returns true if an eleent was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an element appearch is the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an element in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the first empty spot in the array
	int getFirstEmpty();

	//returns the number of elements from the sorted bag
	int size() const;

	//resizes the array containing the elements
	void resize();

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() const;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//adds nr occurrences of elem into the SortedBag.
	//throws an exception if nr is negative
	void addOccurrences(int nr, TComp elem);

	//destructor
	~SortedBag();
};