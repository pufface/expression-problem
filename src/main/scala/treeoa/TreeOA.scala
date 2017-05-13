package treeoa

trait Tree[E] {
  def node(left: E, right: E): E
  def leaf(value: Int): E
}

trait Operation {
  def depth: Int
  def sum: Int
}

trait TreeOperation extends Tree[Operation] {
  override def node(left: Operation, right: Operation) = new Operation {
    override def depth = 1 + Math.max(left.depth, right.depth)
    override def sum = left.sum + right.sum
  }
  override def leaf(value: Int) = new Operation {
    override def depth = 1
    override def sum = value
  }
}

// extend model
trait TreeExt[E] extends Tree[E] {
  def tripleNode(left: E, center: E, right: E): E
}

trait TreeExtOpertation extends TreeOperation with TreeExt[Operation] {
  override def tripleNode(left: Operation, center: Operation, right: Operation) = new Operation {
    override def depth = 1 + Math.max(left.depth, Math.max(left.depth, right.depth))
    override def sum = left.sum + center.sum + right.sum
  }
}

// extend operation
trait OperationExt {
  def max: Int
}

trait TreeExtOperationExt extends TreeExt[OperationExt] {
  override def tripleNode(left: OperationExt, center: OperationExt, right: OperationExt) = new OperationExt {
    override def max = Math.max(left.max, Math.max(center.max, right.max))
  }
  override def node(left: OperationExt, right: OperationExt) = new OperationExt {
    override def max = Math.max(left.max, right.max)
  }
  override def leaf(value: Int) = new OperationExt {
    override def max = value
  }
}

// Object algebra
object TreeOA extends App {
  def tree[E](op: Tree[E]) = {
    import op._
    node(node(leaf(1), leaf(2)), leaf(3))
  }
  val opOnTree = new TreeOperation {}
  println(s"depth: ${tree(opOnTree).depth}")
  println(s"sum: ${tree(opOnTree).sum}")

  // apply extended model and operation
  def treeExt[T](op: TreeExt[T]) = {
    import op._
    tripleNode(node(leaf(1), leaf(2)), leaf(3), leaf(4))
  }
  val opOnTreeExt = new TreeExtOpertation {}
  val extOpOnTreeExt = new TreeExtOperationExt {}
  println(s"depth: ${treeExt(opOnTreeExt).depth}")
  println(s"sum: ${treeExt(opOnTreeExt).sum}")
  println(s"max: ${treeExt(extOpOnTreeExt).max}")
}
