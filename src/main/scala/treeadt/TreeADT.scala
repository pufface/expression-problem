package treeadt

sealed trait Tree[T]
case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[T](value: T) extends Tree[T]

object Operations {
  def depth[T](tree: Tree[T]): Int = tree match {
    case Node(left, right) => 1 + Math.max(depth(left), depth(right))
    case Leaf(value: T) => 1
  }
  def toList[T](tree: Tree[T]): List[T] = tree match {
    case Node(left, right) => toList(left) ::: toList(right)
    case Leaf(value) => List(value)
  }
}

// extend operations
object OperationsExt {
  import Operations._
  def max[T](tree: Tree[T], ordering: Ordering[T]): T = toList(tree).max(ordering)
}

// Algebraic data types
object TreeADT extends App {
  val tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  import Operations._
  println(s"depth: ${depth(tree)}")
  println(s"list: ${toList(tree)}")
  // apply extended operations
  import OperationsExt._
  println(s"max: ${max(tree, Ordering.Int)}")
}
