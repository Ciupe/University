#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	//TODO - Implementation
	current = bag.head;
}

TComp SortedBagIterator::getCurrent() {
	//TODO - Implementation
	if (current >= 0)
		return bag.sortedBag[current].info.first;
	throw exception("");
}
//TC: Theta(1)

bool SortedBagIterator::valid() {
	//TODO - Implementation
	return (current != NULL_POSITION);
}
//TC: Theta(1)

void SortedBagIterator::next() {
	//TODO - Implementation
	if (current == NULL_POSITION)
		throw exception("");
	else
		frequency++;
	if (frequency == bag.sortedBag[current].info.second) {
		current = bag.sortedBag[current].next;
		frequency = 0;
	}
}
//TC: Theta(1)

void SortedBagIterator::first() {
	//TODO - Implementation
	current = bag.head;
}
//TC: Theta(1)

