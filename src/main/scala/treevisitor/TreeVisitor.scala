package treevisitor

sealed trait Tree {
  def operate(op: Operation): Int
}
case class Node(left: Tree, right: Tree) extends Tree {
  override def operate(op: Operation) = op.forNode(this)
}
case class Leaf(value: Int) extends Tree {
  override def operate(op: Operation) = op.forLeaf(this)
}

trait Operation {
  def forNode(tree: Node): Int
  def forLeaf(tree: Leaf): Int
}
case object Depth extends Operation {
  override def forNode(node: Node): Int = 1 + Math.max(node.left.operate(Depth), node.right.operate(Depth))
  override def forLeaf(leaf: Leaf): Int = 1
}
case object Sum extends Operation {
  override def forNode(node: Node): Int = node.left.operate(Sum) + node.right.operate(Sum)
  override def forLeaf(leaf: Leaf): Int = leaf.value
}

// extend operations
case object Max extends Operation {
  override def forNode(node: Node): Int = Math.max(node.left.operate(Max), node.right.operate(Max))
  override def forLeaf(leaf: Leaf): Int = leaf.value
}

// Visitor pattern
object TreeVisitor extends App {
  val tree: Tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  println(s"depth: ${tree.operate(Depth)}")
  println(s"sum: ${tree.operate(Sum)}")
  // apply extended operations
  println(s"max: ${tree.operate(Max)}")
}

