package treeoo

sealed trait Tree[T] {
  def depth: Int
  def toList: List[T]
}

case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def depth: Int = 1 + Math.max(left.depth, right.depth)
  override def toList: List[T] = left.toList ::: right.toList
}

case class Leaf[T](value: T) extends Tree[T] {
  override def depth: Int = 1
  override def toList: List[T] = List(value)
}

// extend model
case class TripleNode[T](left: Tree[T], center: Tree[T], right: Tree[T]) extends Tree[T] {
  override def depth: Int = 1 + Math.max(left.depth, Math.max(center.depth, right.depth))
  override def toList: List[T] = left.toList ::: center.toList ::: right.toList
}

// Object Oriented
object TreeOO extends App {
  val tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  println(s"depth: ${tree.depth}")
  println(s"list: ${tree.toList}")
  // apply extended model
  val treeExt = TripleNode(Node(Leaf(1), Leaf(2)), Leaf(3), Leaf(4))
  println(s"depth ext: ${treeExt.depth}")
  println(s"list ext: ${treeExt.toList}")
}
