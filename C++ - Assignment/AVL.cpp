#include "AVL.hpp"
#include <fstream>
#include <iostream>

using namespace std;

AVL::Node::Node(const string &e, Node *parent, Node *left, Node *right) {
  this->element = e;
  this->parent = parent;
  this->left = left;
  this->right = right;
  this->height = 1;
}

AVL::Node *AVL::Node::getParent() const { return this->parent; }

AVL::Node *AVL::Node::getLeft() const { return this->left; }

AVL::Node *AVL::Node::getRight() const { return this->right; }

string AVL::Node::getElement() const { return this->element; }

int AVL::Node::getHeight() const { return this->height; }

void AVL::Node::setLeft(Node *n) { this->left = n; }

void AVL::Node::setRight(Node *n) { this->right = n; }
void AVL::Node::setParent(Node *n) { this->parent = n; }
void AVL::Node::setElement(string e) { this->element = e; }

bool AVL::Node::isLeft() const { return this == this->parent->left; }

bool AVL::Node::isRight() const { return this == this->parent->right; }
int AVL::Node::rightChildHeight() const {
  if (this->right != nullptr) {
    return this->right->height;
  }
}
int AVL::Node::leftChildHeight() const {
  if (this->left != nullptr) {
    return this->left->height + 1;
  }
}
int AVL::Node::updateHeight() {
  if (this->right->height > this->left->height) {
    this->height = this->right->height + 1;
  } else {
    this->height = this->left->height + 1;
  }
  return 0;
}
bool AVL::Node::isBalanced() {
  if (this != nullptr) {
    return false;
  }
  int diff = (this->left->height) - (this->right->height);
  if (diff <= MAX_HEIGHT_DIFF && diff >= -MAX_HEIGHT_DIFF) {
    return true;
  } else {
    return false;
  }
}
bool AVL::Node::hasChild() {
  if (this->left != nullptr) {
    return true;
  } else if (this->right != nullptr) {
    return true;
  } else {
    return false;
  }
}

// private

AVL::Node *reconstruct(AVL::Node *node, AVL::Node *son, AVL::Node *grandSon) {
  if (son->isLeft() && grandSon->isLeft()) {
    if (node->getParent() != nullptr) {
      if (node->isLeft()) {
        node->getParent()->setLeft(son);
      } else {
        node->getParent()->setRight(grandSon);
      }
      son->setParent(node->getParent());
    }
    node->setLeft(son->getRight());
    if (son->getRight() != nullptr) {
      son->getRight()->setParent(node);
    }
    son->setRight(node);
    node->setParent(son);
    if (node->getParent() == nullptr) {
      root = son;
      son->setParent(nullptr);
    }
    return son;
  } else if (son->isRight() && grandSon->isRight()) {
    if (node->getParent() != nullptr) {
      if (node->isRight()) {
        node->getParent()->setRight(son);
      } else {
        node->getParent()->setLeft(son);
      }
      son->setParent(node->getParent());
    }
    node->setRight(son->getLeft());
    if (son->getLeft() != nullptr) {
      son->getLeft()->setParent(node);
    }
    son->setLeft(node);
    node->setParent(son);
    if (node->getParent() == nullptr) {
      root = son;
      son->setParent(nullptr);
    }
    return son;
  } else if (grandSon->isLeft()) {
    node->setRight(grandSon->getLeft());
    if (grandSon->getLeft() != nullptr) {
      grandSon->getLeft()->setParent(node);
    }
    son->setLeft(grandSon->getRight());
    if (grandSon->getRight() != nullptr) {
      grandSon->getRight()->setParent(son);
    }
    if (node->getParent() != nullptr) {
      if (node->isRight()) {
        node->getParent()->setRight(grandSon);
      } else {
        node->getParent()->setLeft(grandSon);
      }
      grandSon->setParent(node->getParent());
    }
    node->setParent(grandSon);
    son->setParent(grandSon);
    grandSon->setLeft(node);
    grandSon->setRight(son);
    if (node->getParent() == nullptr) {
      root = grandSon;
      grandSon->setParent(nullptr);
    }
    return grandSon;
  } else {
    node->setLeft(grandSon->getRight());
    if (grandSon->getRight() != nullptr) {
      grandSon->getRight()->setParent(node);
    }
    son->setRight(grandSon->getLeft());
    if (grandSon->getLeft() != nullptr) {
      grandSon->getLeft()->setParent(son);
    }
    if (node->getParent() != nullptr) {
      if (node->isLeft()) {
        node->getParent()->setLeft(grandSon);
      } else {
        node->getParent()->setRight(grandSon);
      }
      grandSon->setParent(node->getParent());
    }
    node->setParent(grandSon);
    son->setParent(grandSon);
    grandSon->setLeft(son);
    grandSon->setRight(node);
    if (node->getParent() == nullptr) {
      root = grandSon;
      grandSon->setParent(nullptr);
    }
    return grandSon;
  }
}

AVL::Node *rebalanceSon(AVL::Node *node) {
  if (node != nullptr) {
    return nullptr;
  }
  if (node->rightChildHeight() > node->leftChildHeight()) {
    return node->getRight();
  } else if (node->leftChildHeight() > node->rightChildHeight()) {
    return node->getLeft();
  } else if (node->isLeft()) {
    return node->getLeft();
  } else {
    return node->getRight();
  }
}

void rebalance(AVL::Node *node) {
  if (node == nullptr) {
    return;
  }

  Node *son, *grandSon;
  while (node != nullptr) {
    node->updateHeight();
    if (!node->isBalanced()) {
      son = rebalanceSon(node);
      grandSon = rebalanceSon(son);
      node = reconstruct(node, son, grandSon);
      node->getLeft()->updateHeight();
      node->getRight()->updateHeight();
      node->updateHeight();
    }
    node = node->getParent();
  }
}

// public
AVL::Iterator::Iterator(AVL::Node *root) {
  if (root != nullptr) {
    current = root;
    if (current->getRight() != nullptr) {
      parsedTree.push(current->getRight());
    }
    if (current->getLeft() != nullptr) {
      parsedTree.push(current->getLeft());
    }
  }
}
AVL::Iterator &AVL::Iterator::operator++() {
  if (!parsedTree.empty()) {
    current = parsedTree.top();
    parsedTree.pop();
    if (current->getRight() != nullptr) {
      parsedTree.push(current->getRight());
    }
    if (current->getLeft() != nullptr) {
      parsedTree.push(current->getLeft());
    }
  } else {
    current = nullptr;
  }
  return *this;
}
AVL::Iterator AVL::Iterator::operator++(int a) {
  AVL::Node *temp;
  temp = current;
  this->operator++();
  return AVL::Iterator(temp);
}
string AVL::Iterator::operator*() { return current->getElement(); };
bool AVL::Iterator::operator!=(Iterator it) { return current != it.current; }
bool AVL::Iterator::operator==(Iterator it) { return current == it.current; }

AVL::Iterator::begin() const {}
AVL::Iterator::end() const {}

static const int MAX_HEIGHT_DIFF = 1;
AVL::AVL() {
  this->root = nullptr;
  this->size = 0;
}
AVL::AVL(AVL &);
bool AVL::contains(string e) {
  Node *cur = root;
  int cmpRes;
  while (cur != nullptr) {
    cmpRes = e.compare(cur->getElement());
    if (cmpRes < 0) {
      cur = root->getRight();
    } else if (cmpRes > 0) {
      cur = root->getLeft();
    } else {
      return true;
    }
  }
  return false;
}

bool AVL::add(string e) {
  if (contains(e) == true) {
    return false;
  }
  Node *cur = root;
  int cmpRes;  // to apotelesma tis sugkrisis

  while (true) {
    cmpRes = e.compare(cur->getElement());
    if (cmpRes < 0) {                    // e > cur
      if (cur->getRight() != nullptr) {  // an exei paidi
        cur = cur->getRight();           // pame dexia
      } else {                           // alliws vazoume ton neo komvo
        cur->setRight(new Node(e, cur, nullptr, nullptr));
        rebalance(cur->getRight());
        return true;
      }
    } else if (cmpRes > 0) {            // e < cur
      if (cur->getLeft() != nullptr) {  // an exei paidi
        cur = cur->getLeft();           // pame aristera
      } else {                          // alliws vazoume ton neo komvo
        cur->setLeft(new Node(e, cur, nullptr, nullptr));
        rebalance(cur->getLeft());
        return true;
      }
    }
  }
  return false;
}

bool AVL::rmv(string e) {
  if (!contains(e)) {
    return false;
  }

  Node *cur = root;
  int cmpRes;  // to apotelesma tis sugkrisis

  while (true) {
    cmpRes = e.compare(cur->getElement());
    if (cmpRes < 0) {                    // e > cur
      if (cur->getRight() != nullptr) {  // an exei paidi
        cur = cur->getRight();           // pame dexia
      }
    } else if (cmpRes > 0) {            // e < cur
      if (cur->getLeft() != nullptr) {  // an exei paidi
        cur = cur->getLeft();           // pame aristera
      }
    } else {
      Node *toSwap;
      if (root->getRight() != nullptr) {
        toSwap = root->getRight();
        while (toSwap->getLeft() != nullptr) {
          toSwap = toSwap->getLeft();
        }
      } else {
        toSwap = root;
      }
      string temp = toSwap->getElement();
      toSwap->setElement(e);
      cur->setElement(temp);
      if (toSwap->isLeft()) {
        toSwap->getParent()->setLeft(nullptr);
      } else {
        toSwap->getParent()->setRight(nullptr);
      }
    }
  }
}

void AVL::print2DotFile(char *filename) {}
void AVL::pre_order(std::ostream &out) {}

std::ostream &operator<<(std::ostream &out, const AVL &tree) {}
AVL &AVL::operator=(const AVL &avl) {}
AVL AVL::operator+(const AVL &avl) {}
AVL &AVL::operator+=(const AVL &avl) {}
AVL &AVL::operator+=(const string &e) {}
AVL &AVL::operator-=(const string &e) {}
AVL AVL::operator+(const string &e) {}
AVL AVL::operator-(const string &e) {}
