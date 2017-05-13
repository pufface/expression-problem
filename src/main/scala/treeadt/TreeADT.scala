package treeadt

sealed trait Tree
case class Node(left: Tree, right: Tree) extends Tree
case class Leaf(value: Int) extends Tree

trait Operations {
  def depth(tree: Tree): Int = tree match {
    case Node(l: Tree, r: Tree) => 1 + Math.max(depth(l), depth(r))
    case Leaf(v: Int) => 1
  }
  def sum(tree: Tree): Int = tree match {
    case Node(l, r) => sum(l) + sum(r)
    case Leaf(v) => v
  }
}

// extend operations
trait OperationsExtended extends Operations {
  def max(tree: Tree): Int = tree match {
    case Node(l, r) =>  Math.max(max(l), max(r))
    case Leaf(v) => v
  }
}

// Algebraic data types
object TreeADT extends App {
  val tree: Tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  val op = new Operations{}
  println(s"depth: ${op.depth(tree)}")
  println(s"sum: ${op.sum(tree)}")
  // apply extended operations
  val opExt = new OperationsExtended{}
  println(s"max: ${opExt.max(tree)}")
}
