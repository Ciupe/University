#pragma once
//DO NOT INCLUDE SETITERATOR

/*
transform your SortedSet into a fixed capacity SortedSet. 
Constructor receives the maximum size as a parameter.  
Change every operation that needs to be changed. 
Add the following operations which checks if the SortedSet is full
bool isFull() const;

Obs: Since adding a new parameter to the constructor will lead to errors in all the tests,
create a copy of your project and perform the modifications on the copy.
*/

//DO NOT CHANGE THIS PART
typedef int TElem;
typedef TElem TComp;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TELEM -11111
class SortedSetIterator;

struct BSTNode {
	int left = NULL_TELEM, right = NULL_TELEM;
	int value;
};

class SortedSet {
	friend class SortedSetIterator;
private:
	//TODO - Representation
	int root;
	int numberOfElements, capacity;
	Relation rel;
	BSTNode* nodes;

public:
	//constructor
	SortedSet(Relation r, int capacity);

	/*void resize();*/

	int getFirstEmpty();

	int getMaximum(int root);

	int removeNode(int root, TComp elem, bool& removed);

	bool isFull() const;

	//adds an element to the sorted set
	//if the element was added, the operation returns true, otherwise (if the element was already in the set) 
	//it returns false
	bool add(TComp e);

	
	//removes an element from the sorted set
	//if the element was removed, it returns true, otherwise false
	bool remove(TComp e);

	//checks if an element is in the sorted set
	bool search(TComp elem) const;


	//returns the number of elements from the sorted set
	int size() const;

	//checks if the sorted set is empty
	bool isEmpty() const;

	//returns an iterator for the sorted set
	SortedSetIterator iterator() const;

	// destructor
	~SortedSet();


};
