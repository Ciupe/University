#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <exception>

SortedBag::SortedBag(Relation r) {
	//TODO - Implementation
	capacity = 2;
	sortedBag = new Node[capacity];
	relation = r;
	head = NULL_POSITION;
	tail = NULL_POSITION;
	firstEmpty = 0;
	bagSize = 0;
}

void SortedBag::add(TComp e) {
	//TODO - Implementation	
	if (bagSize == capacity)
		resize();

	for (int i = head; i != NULL_POSITION; i = sortedBag[i].next) //if the element exists in the array
		if (sortedBag[i].info.first == e) {
			sortedBag[i].info.second++;
			bagSize++;
			return;
		}

	firstEmpty = getFirstEmpty();

	if (head == NULL_POSITION || relation(sortedBag[head].info.first, e) == false) { //first position ( head )
		Node newNode;
		newNode.previous = NULL_POSITION;
		newNode.info.first = e;
		newNode.info.second = 1;
		newNode.next = head;
		if (head != NULL_POSITION) {
			sortedBag[head].previous = firstEmpty;
		}
		else {
			tail = firstEmpty;
		}
		sortedBag[firstEmpty] = newNode;
		head = firstEmpty;
	}
	else {			//any other position
		int currentNode = head;
		while (relation(sortedBag[currentNode].info.first, e) == true && currentNode != NULL_POSITION) {
			currentNode = sortedBag[currentNode].next;
		}
		Node newNode;
		newNode.info.first = e;
		newNode.info.second = 1;

		if (currentNode == NULL_POSITION) {	//we add it to the tail
			sortedBag[tail].next = firstEmpty;
			newNode.previous = tail;
			newNode.next = NULL_POSITION;
			tail = firstEmpty;
		}

		else {		//we add it somewhere between the head and the tail
			sortedBag[sortedBag[currentNode].previous].next = firstEmpty;	// 4, 6   add(5) => 4,5,6
			newNode.previous = sortedBag[currentNode].previous;
			sortedBag[currentNode].previous = firstEmpty;
			newNode.next = currentNode;
		}
		sortedBag[firstEmpty] = newNode;
	}
	bagSize++;
	
}
// TC: O(n)


bool SortedBag::remove(TComp e) {
	//TODO - Implementation
	int foundElementIndex = NULL_POSITION;
	for (int i = head; i != NULL_POSITION; i = sortedBag[i].next) {
		if (sortedBag[i].info.first == e) {
			foundElementIndex = i;
			break;
		}
	}
	if (foundElementIndex == NULL_POSITION)		//return false if the element doesn't exist in the array
		return false;
	if (sortedBag[foundElementIndex].info.second > 1) {	//if the element has more occurences, delete 1 occurence
		sortedBag[foundElementIndex].info.second--;
		bagSize--;
		return true;
	}
	if (foundElementIndex != tail)
		sortedBag[sortedBag[foundElementIndex].next].previous = sortedBag[foundElementIndex].previous;	//if there is only one element of that kind, set its value
	else
		tail = sortedBag[foundElementIndex].previous;
	if (foundElementIndex != head)
		sortedBag[sortedBag[foundElementIndex].previous].next = sortedBag[foundElementIndex].next;		//to NULL_POSITION and update the links in the array
	else
		head = sortedBag[foundElementIndex].next;
	sortedBag[foundElementIndex].info.first = NULL_TCOMP;
	sortedBag[foundElementIndex].info.second = 0;
	bagSize--;
	return true;
}
//TC: O(n)

bool SortedBag::search(TComp elem) const {
	//TODO - Implementation
	for (int i = head; i != NULL_POSITION; i = sortedBag[i].next)
	{
		//std::cout << sortedBag[i].info.first << "->" << sortedBag[i].info.second << "\n";
		if (sortedBag[i].info.first == elem)
			return true;
	}
	return false;
}
//TC: O(n)


int SortedBag::nrOccurrences(TComp elem) const {
	//TODO - Implementation
	for (int i = head; i != NULL_POSITION; i = sortedBag[i].next)
		if (sortedBag[i].info.first == elem)
			return sortedBag[i].info.second;
	return 0;
}
//TC: O(n)

int SortedBag::getFirstEmpty()
{
	for (int i = head; i != NULL_POSITION; i = sortedBag[i].next)
		if (sortedBag[i].info.first == NULL_TCOMP)
			return i;
	return bagSize;
}
//TC: O(n)

// firstEmpty = 5
// 6, 7, 8, 12
// remove (6)
// firstEmpty = sortedBag[5].next = 7;

int SortedBag::size() const {
	//TODO - Implementation
	return bagSize;
}
//TC: Theta (1)

void SortedBag::resize()
{
	Node* newBag = new Node[capacity * 2];
	for (int current = head; current != NULL_POSITION; current = sortedBag[current].next)
		newBag[current] = sortedBag[current];
	delete[] sortedBag;
	sortedBag = newBag;
	capacity *= 2;
}
//TC: O(n)


bool SortedBag::isEmpty() const {
	//TODO - Implementation
	return (bagSize == 0);
}
//TC: Theta(1)

SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}


void SortedBag::addOccurrences(int nr, TComp elem)
{
	if (nr < 0){
		throw std::runtime_error("Number of occurences can't be a negative number. \n");
		return;
	}
	if (nr > 0) {
		add(elem);
		nr--;
	}
	for (int current = head; current != NULL_POSITION; current = sortedBag[current].next)
		if (sortedBag[current].info.first == elem){
			sortedBag[current].info.second += nr;
			bagSize += nr;
			return;
		}
}
//TC: O(n);

SortedBag::~SortedBag() {
	//TODO - Implementation
	delete[] sortedBag;
}
