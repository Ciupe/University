#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r)  {
	//TODO - Implementation
	this->numberOfElements = 0;
	root = NULL_TELEM;
	this->capacity = 20;
	this->rel = r;
	nodes = new BSTNode[capacity];

	for (int i = 0; i < this->capacity; ++i)
		nodes[i].value = NULL_TELEM;
}

void SortedSet::resize()
{
	this->capacity *= 2;
	BSTNode* newArray = new BSTNode[this->capacity];
	for (int i = 0; i < this->numberOfElements; ++i) 
		newArray[i] = nodes[i];
	delete[] nodes;

	nodes = new BSTNode[this->capacity];

	for (int i = 0; i < this->capacity; ++i)
		nodes[i].value = NULL_TELEM;

	for (int i = 0; i < this->numberOfElements; ++i)
		nodes[i] = newArray[i];
}

int SortedSet::getFirstEmpty()
{
	for (int i = 0; i < this->capacity; ++i)
		if (nodes[i].value == NULL_TELEM)
			return i;
	return -1;
}

int SortedSet::getMaximum(int root)
{
	int currentIndex = root;
	int maxValue = NULL_TELEM;
	int maxNodeIndex = -1;

	while (currentIndex != NULL_TELEM) {
		if (nodes[currentIndex].value > maxValue) {
			maxValue = nodes[currentIndex].value;
			maxNodeIndex = currentIndex;
		}

		currentIndex = nodes[currentIndex].right;
	}

	return maxNodeIndex;
}

int SortedSet::removeNode(int root, TComp elem, bool& removed)
{
	if (root == NULL_TELEM)
		return root;

	if (nodes[root].value == elem) {
		removed = true;

		if (nodes[root].left == NULL_TELEM) {
			nodes[root].value = NULL_TELEM;
			return nodes[root].right;
		}
		else if (nodes[root].right == NULL_TELEM) {
			nodes[root].value = NULL_TELEM;
			return nodes[root].left;
		}
		else {
			int maxNodeIndex = getMaximum(nodes[root].left);
			nodes[root].value = nodes[maxNodeIndex].value;
			nodes[root].left = removeNode(nodes[root].left, elem, removed);
		}
	}
	if (rel(elem, nodes[root].value) == true)
		nodes[root].left = removeNode(nodes[root].left, elem, removed);
	else
		nodes[root].right = removeNode(nodes[root].right, elem, removed);
	
	return root;
}

// O(h)
bool SortedSet::add(TComp elem) {
	//TODO - Implementation

	BSTNode newNode;
	newNode.value = elem;

	if (numberOfElements == 0) {
		root = 0;
		nodes[0] = newNode;
		numberOfElements++;
		return true;
	}
	
	if (numberOfElements >= capacity)
		resize();

	int firstEmpty = getFirstEmpty();
	int currentIndex = root;
	int parentIndex = NULL_TELEM;

	while (currentIndex != NULL_TELEM) {
		parentIndex = currentIndex;
		if (elem == nodes[currentIndex].value)
			return false;

		if (rel(elem, nodes[currentIndex].value) == true)
			currentIndex = nodes[currentIndex].left;
		else
			currentIndex = nodes[currentIndex].right;
	}

	if (rel(elem, nodes[parentIndex].value) == true)
		nodes[parentIndex].left = firstEmpty;
	else
		nodes[parentIndex].right = firstEmpty;

	nodes[firstEmpty] = newNode;
	numberOfElements++;

	return true;
}

// O(h)
bool SortedSet::remove(TComp elem) {
	//TODO - Implementation

	bool removed = false;
	root = removeNode(root, elem, removed);

	if (removed == true)
		numberOfElements--;

	return removed;
}

// O(h)
bool SortedSet::search(TComp elem) const {
	//TODO - Implementation
	
	int current = root;

	while (current != NULL_TELEM) {
		if (nodes[current].value == elem)
			return true;
		else if (rel(elem, nodes[current].value) == true)
			current = nodes[current].left;
		else
			current = nodes[current].right;
	}
	
	return false;
}

// Theta(1)
int SortedSet::size() const {
	//TODO - Implementation

	return numberOfElements;
}

// Theta(1)
bool SortedSet::isEmpty() const {
	//TODO - Implementation

	return (numberOfElements == 0);
}

SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
	//TODO - Implementation

	delete[] nodes;
	this->numberOfElements = 0;
}


