package treeoo

abstract class Tree {
  def depth: Int
  def sum: Int
}

case class Node(left: Tree, right: Tree) extends Tree {
  override def depth: Int = 1 + Math.max(left.depth, right.depth)
  override def sum: Int = left.sum + right.sum
}

case class Leaf(value: Int) extends Tree {
  override def depth: Int = 1
  override def sum: Int = value
}

// extend model
case class TripleNode(left: Tree, center: Tree, right: Tree) extends Tree {
  override def depth: Int = 1 + Math.max(left.depth, Math.max(center.depth, right.depth))
  override def sum: Int = left.sum + center.sum + right.sum
}

// Object Oriented
object TreeOO extends App {
  val tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  println(s"depth: ${tree.depth}")
  println(s"sum: ${tree.sum}")
  // apply extended model
  val treeExt = TripleNode(Node(Leaf(1), Leaf(2)), Leaf(3), Leaf(4))
  println(s"depth ext: ${treeExt.depth}")
  println(s"sum ext: ${treeExt.sum}")
}
