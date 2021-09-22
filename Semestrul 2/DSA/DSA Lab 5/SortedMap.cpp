#include <cmath>
#include <exception>
#include <stdexcept>
#include "SMIterator.h"
#include "SortedMap.h"


unsigned int defaultHash(TKey key) {
    return (unsigned int)key;
}

SortedMap::SortedMap(Relation r) : rel{ r }, n{ 0 }, ht{ defaultHash, 8, new Node * [8]{} } {}


//returns a pointer to the array element corresponding to the key's hash value
Node** SortedMap::getSlot(TKey key) const {
    auto index = ht.hash(key) % ht.m;
    return &ht.array[index];
}

void SortedMap::resize() {
    auto oldHT = ht;

    ht.m *= 2;
    ht.array = new Node * [ht.m]{};
    n = 0;

    for (int i = 0; i < oldHT.m; ++i) {
        auto node = oldHT.array[i];

        while (node != nullptr) {
            add(node->info.first, node->info.second);
            node = node->next;
        }
    }
}

//Amortized Theta(1)
TValue SortedMap::add(TKey k, TValue v) {
    double loadFactor = (double)n / (double)ht.m;

    if (loadFactor >= 0.7)
        resize();

    auto slot = getSlot(k);

    if (*slot == nullptr) {
        *slot = new Node{ {k, v}, nullptr };
        ++n;
        return NULL_TVALUE;
    }

    while (*slot != nullptr && (*slot)->info.first != k && rel((*slot)->info.first, k) == true)
        slot = &((*slot)->next);

    if (*slot != nullptr) {
        if ((*slot)->info.first == k) {             //we found an element with the same key
            auto oldValue = (*slot)->info.second;
            (*slot)->info.second = v;

            return oldValue;
        }
        else {                                      //we add according to relation
            auto newInfo = (*slot)->info;
            (*slot)->info = { k, v };
            (*slot)->next = new Node{ newInfo, (*slot)->next };

            ++n;
            return NULL_TVALUE;
        }
    }
    else {
        *slot = new Node{ {k, v}, nullptr };
        ++n;

        return NULL_TVALUE;
    }
}

//Theta(1)
TValue SortedMap::search(TKey k) const {
    auto slot = getSlot(k);

    while (*slot != nullptr && (*slot)->info.first != k)
        slot = &((*slot)->next);

    if (*slot != nullptr)
        return (*slot)->info.second;
    
    return NULL_TVALUE;
}

//Theta(1)
TValue SortedMap::remove(TKey k) {
    auto slot = getSlot(k);

    if (*slot == nullptr)
        return NULL_TVALUE;

    if ((*slot)->info.first == k) {
        auto value = (*slot)->info.second;
        auto oldNode = *slot;

        *slot = (*slot)->next;

        delete oldNode;

        --n;
        return value;
    }

    auto next = &((*slot)->next);

    while (*next != nullptr && (*next)->info.first != k) {
        slot = next;
        next = &((*slot)->next);
    }

    if (*next != nullptr) {
        (*slot)->next = (*next)->next;
        auto value = (*next)->info.second;

        delete next;
        --n;
        return value;
    }

    return NULL_TVALUE;
}

//Theta(1)
int SortedMap::size() const {
    return (int)n;
}

//Theta(1)
bool SortedMap::isEmpty() const {
    return (n == 0);
}


SMIterator SortedMap::iterator() const {
    return SMIterator( *this );
}

void SortedMap::replace(TKey k, TValue oldValue, TValue newValue)
{
    auto slot = getSlot(k);
    if (slot == nullptr || (*slot) == NULL)
        return;

    if ((*slot)->info.first == k && (*slot)->info.second == oldValue) {
        (*slot)->info.second = newValue;
        std::cout << "Value replaced! \n";
        return;
    }

    while ((*slot)->info.first != k && slot != nullptr) {
        if ((*slot)->info.first == k && (*slot)->info.second == oldValue) {
            (*slot)->info.second = newValue;
            std::cout << "Value replaced! \n";
            return;
        }
        slot = &(*slot)->next;
    }
}

SortedMap::~SortedMap() {
    for (int i = 0; i < ht.m; ++i) {
        auto currentNode = ht.array[i];

        while (currentNode != nullptr) {
            auto oldNode = currentNode;
            currentNode = currentNode->next;
            delete oldNode;
        }
    }
    delete[] ht.array;
    ht = {};
}