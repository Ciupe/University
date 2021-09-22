#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <exception>
#include <iostream>
SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d){
	//TODO - Implementation
	this->current = this->map.head;
}
// TC Theta (1)

void SMMIterator::first(){	// makes the current node point to the head
	//TODO - Implementation
	this->current = this->map.head;
}
// TC Theta(1)

void SMMIterator::next(){
	//TODO - Implementation
	if (!valid())	// checks if the next node is still valid and throws and exception if it isnt
		throw runtime_error("Error thrown.\n");
	else							// if the next node is valid, it takes the step
		this->current = this->current->next;
}
// TC Theta(1)

bool SMMIterator::valid() const{	// checks if the current node is a null pointer
	//TODO - Implementation
	if (this->current == nullptr)
		return false;
	return true;
}
// TC Theta(1)

TElem SMMIterator::getCurrent() const {	// returns the <key, value> pair of the current node
	//TODO - Implementation
	if (!valid())
		throw runtime_error("Error thrown.\n");
	else {
		TElem* currentPair = new TElem;
		currentPair->first = this->current->info.first;
		currentPair->second = this->current->info.second;
		return *currentPair;
	}
}