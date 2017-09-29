package treevisitor

sealed trait Tree[T] {
  def operate[E](op: Operation[T, E]): E
}
case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def operate[E](op: Operation[T, E]) = op.visit(this)
}
case class Leaf[T](value: T) extends Tree[T] {
  override def operate[E](op: Operation[T, E]) = op.visit(this)
}

trait Operation[T, E] {
  def visit(tree: Node[T]): E
  def visit(tree: Leaf[T]): E
}
case class Depth[T]() extends Operation[T, Int] {
  override def visit(node: Node[T]): Int = 1 + Math.max(node.left.operate(this), node.right.operate(this))
  override def visit(leaf: Leaf[T]): Int = 1
}
case class ToList[T]() extends Operation[T, List[T]] {
  override def visit(node: Node[T]): List[T] = node.left.operate(this) ::: node.right.operate(this)
  override def visit(leaf: Leaf[T]): List[T] = List(leaf.value)
}

// extend operations
case class Max[T](ordering: Ordering[T]) extends Operation[T, T] {
  override def visit(node: Node[T]): T = node.operate(ToList()).max(ordering)
  override def visit(leaf: Leaf[T]): T = leaf.value
}

// Visitor pattern
object TreeVisitor extends App {
  val tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  println(s"depth: ${tree.operate(Depth())}")
  println(s"list: ${tree.operate(ToList())}")
  // apply extended operations
  println(s"max: ${tree.operate(Max(Ordering.Int))}")
}

