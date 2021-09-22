#pragma once
#include "SortedSet.h"
#include <stack>

//DO NOT CHANGE THIS PART
class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& set;
	SortedSetIterator(const SortedSet& m);

	//TODO - Representation
	int currentNodeIndex;
	std::stack <int> stack;

public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
};

