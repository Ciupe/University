#include "SortedSetIterator.h"
#include <exception>
#include <stdexcept>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : set(m)
{
	//TODO - Implementation
	currentNodeIndex = NULL_TELEM;
	stack = std::stack<int>{};

	first();
}

// O(h)
void SortedSetIterator::first() {
	//TODO - Implementation
	currentNodeIndex = set.root;
	stack = std::stack<int>{};

	while (currentNodeIndex != NULL_TELEM) {
		stack.push(currentNodeIndex);
		currentNodeIndex = set.nodes[currentNodeIndex].left;
	}

	if (stack.empty() == false)
		currentNodeIndex = stack.top();
	else 
		currentNodeIndex = NULL_TELEM;
}

// O(h)
void SortedSetIterator::next() {
	//TODO - Implementation

	if (valid() == false)
		throw std::runtime_error{ "Invalid iterator. (next)" };

	int currentIndex = stack.top();
	stack.pop();

	if (set.nodes[currentIndex].right != NULL_TELEM) {
		currentIndex = set.nodes[currentIndex].right;
		while (currentIndex != NULL_TELEM) {
			stack.push(currentIndex);
			currentIndex = set.nodes[currentIndex].left;
		}
	}

	if (stack.empty() == false)
		currentNodeIndex = stack.top();
	else
		currentNodeIndex = NULL_TELEM;
}

// Theta(1)
TElem SortedSetIterator::getCurrent()
{
	//TODO - Implementation
	
	if (valid() == false)
		throw std::runtime_error{ "Invalid iterator. (getCurrent)" };
	return set.nodes[currentNodeIndex].value;
}

// Theta(1)
bool SortedSetIterator::valid() const {
	//TODO - Implementation
	
	return (currentNodeIndex != NULL_TELEM);
}

