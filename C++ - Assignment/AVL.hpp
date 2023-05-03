#ifndef __AVL_HPP_
#define __AVL_HPP_

#include <fstream>
#include <iostream>
#include <stack>

using namespace std;

class AVL {
 private:
  class Node {
    Node *parent, *left, *right;
    int height;
    string element;

   public:
    Node(const string &e, Node *parent, Node *left, Node *right);

    Node *getParent() const;
    Node *getLeft() const;
    Node *getRight() const;
    string getElement() const;
    int getHeight() const;

    void setLeft(Node *);
    void setRight(Node *);
    void setParent(Node *);
    void setElement(string e);

    bool isLeft() const;
    bool isRight() const;
    int rightChildHeight() const;
    int leftChildHeight() const;
    int updateHeight();
    bool isBalanced();
    bool hasChild();
  };

 private:
  int size;
  Node *root;
  void rebalance(Node *);
  Node *rebalanceSon(Node *);
  Node *reconstruct(Node *, Node *, Node *);

 public:
  class Iterator {
   private:
    int position;
    Node *current;
    std::stack<Node *> parsedTree;

   public:
    Iterator &operator++();
    Iterator operator++(int a);
    string operator*();
    bool operator!=(Iterator it);
  };

  Iterator begin() const;
  Iterator end() const;

  static const int MAX_HEIGHT_DIFF = 1;
  AVL();
  AVL(const AVL &);
  bool contains(string e);
  bool add(string e);
  bool rmv(string e);
  void print2DotFile(char *filename);
  void pre_order(std::ostream &out);

  friend std::ostream &operator<<(std::ostream &out, const AVL &tree);
  AVL &operator=(const AVL &avl);
  AVL operator+(const AVL &avl);
  AVL &operator+=(const AVL &avl);
  AVL &operator+=(const string &e);
  AVL &operator-=(const string &e);
  AVL operator+(const string &e);
  AVL operator-(const string &e);
};

#endif
