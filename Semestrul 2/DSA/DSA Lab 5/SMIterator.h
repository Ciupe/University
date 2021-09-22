#pragma once
#include "SortedMap.h"

//DO NOT CHANGE THIS PART
class SMIterator {
	friend class SortedMap;

	const SortedMap& map;

	unsigned int listsCount;
	Node** nodesLists;

	unsigned int currentListIndex;
	Node* currentNode;

	SMIterator(const SortedMap& mapionar);

public:
	~SMIterator();

	void first();
	void next();
	bool valid() const;
	TElem getCurrent() const;
};