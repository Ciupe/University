#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

SortedMultiMap::SortedMultiMap(Relation r) {
	//TODO - Implementation
	this->relation = r;
	this->head = nullptr;
	this->tail = nullptr;
}
// Theta (3)

void SortedMultiMap::add(TKey c, TValue v) {
	//TODO - Implementation

	if (this->head == nullptr || this->relation ( this->head->info.first, c ) == false ) 
	{
		// there are no existing nodes or we have to insert it in the first pos ( the head )

		Node* node = new Node;
		node->previous = nullptr;
		node->next = this->head;
		node->info.first = c;
		node->info.second = v;

		if (this->tail == nullptr)	// if there are no nodes, the head and the tail will both point to the one we add
			this->tail = node;

		if ( this->head != nullptr )	// if there is an existing node, we will make it's previous value point to the one we add
			this->head->previous = node;

		this->head = node;
	}
	else {
		// we have to insert the node somewhere between other 2 nodes or at the end

		bool found = false;
		Node* current = this->head;
		while (current != nullptr)
		{
			if (current->info.first == c && current->info.second == v) {	// searching for a node with the same information
				found = true;
				break;
			}
			current = current->next;
		}

		if (found == false) {	// we haven't found a node with the same info
			current = this->head;
			while (current != nullptr) {
				if (this->relation(current->info.first, c) == false) {	// we add the node in the place determined by the given relation
					Node* node = new Node;
					current->previous->next = node;
					node->previous = current->previous;
					node->next = current;
					node->info.first = c;
					node->info.second = v;
					current->previous = node;
					break;
				}
				current = current->next;
			}

			if (current == nullptr) {	// we add the node at the tail
				Node* node = new Node;
				this->tail->next = node;
				node->previous = this->tail;
				node->next = nullptr;
				node->info.first = c;
				node->info.second = v;
				this->tail = node;
			}
		}
	}
}
// BC Theta (7)
// WC O(n)
// AC O(n)

vector<TValue> SortedMultiMap::search(TKey c) const {
	//TODO - Implementation
	Node* current = new Node;
	current = this->head;
	vector <TValue> values;
	while (current != nullptr) {
		if (current->info.first == c)
			values.push_back(current->info.second);
		current = current->next;
	}
	return values;
}
// AC Theta(n)

bool SortedMultiMap::remove(TKey c, TValue v) {
	//TODO - Implementation

	Node* current = new Node;
	current = this->head;
	if (current == nullptr)	// if there are no nodes, return false ;
		return false;

	TElem* info = new TElem;
	info->first = c;
	info->second = v;

	while (current != nullptr) {
		if (current->info == *info) {	// if we found the node we were looking for 
			
			if (this->size() == 1) {	// if we only have 1 node, both the head and the tail will point to nullptr
				this->head = nullptr;
				this->tail = nullptr;
				delete current;
				return true;
			}

			else if (current == this->tail) {	// if the found node is the tail, we'll change the tail to the previous node
				this->tail = current->previous;
				current->previous->next = nullptr;
				delete current;
				return true;
			}

			else if (current == this->head) {	// if the node is the head, we'll change the head to the next node
				this->head = current->next;
				current->next->previous = nullptr;
				delete current;
				return true;
			}

			else {
				current->previous->next = current->next;
				current->next->previous = current->previous;
				delete current;
				return true;
			}
		}
		current = current->next;
	}
	return false;
}
// BC Theta(1)
// WC Theta(n)
// AC O(n)

int SortedMultiMap::size() const {
	//TODO - Implementation
	int size = 0;
	Node* current = this->head;
	while (current != nullptr) {
		size++;
		current = current->next;
	}
	return size;
}
// AC Theta(n)

bool SortedMultiMap::isEmpty() const {
	//TODO - Implementation
	if (this->size() == 0)
		return true;
	return false;
}
// TC Theta(1)

vector<TValue> SortedMultiMap::removeKey(TKey key)
{
	vector <TValue> removedValues;
	Node* current = new Node;
	current = this->head;

	if (current == nullptr)
		return removedValues;

	while (current != nullptr) {
		if (current->info.first == key) {
			if (current->next != nullptr)
				current->next->previous = current->previous;
			else
				this->tail = current->previous;

			if (current->previous != nullptr)
				current->previous->next = current->next;
			else
				this->head = current->next;

			removedValues.push_back(current->info.second);

			if (current->next == nullptr) {
				delete current;
				return removedValues;
			}
			else {
				current = current->next;
				delete current->previous;
			}
		}
		else
			current = current->next;
	}
	return removedValues;
}
// BC Theta(1)
// WC Theta(n)
// AC Theta(n)

SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}

SortedMultiMap::~SortedMultiMap() {
	//TODO - Implementation
	Node* current = this->head;
	while (this->head != nullptr) {
		current = current->next;
		delete this->head;
		this->head = current;
	}
}
// TC Theta(n)
