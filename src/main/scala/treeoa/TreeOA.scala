package treeoa

trait Tree[T, OP] {
  def node(left: OP, right: OP): OP
  def leaf(value: T): OP
}

trait Operation[T] {
  def depth(): Int
  def toList(): List[T]
}

case class TreeOperation[T]() extends Tree[T, Operation[T]] {
  override def node(left: Operation[T], right: Operation[T]) = new Operation[T] {
    override def depth(): Int = 1 + Math.max(left.depth, right.depth)
    override def toList(): List[T] = left.toList() ::: right.toList()
  }
  override def leaf(value: T) = new Operation[T] {
    override def depth(): Int = 1
    override def toList(): List[T] = List(value)
  }
}

// extend model
trait TreeExt[T, OP] extends Tree[T, OP] {
  def tripleNode(left: OP, center: OP, right: OP): OP
}

class TreeExtOpertation[T]() extends TreeOperation[T] with TreeExt[T, Operation[T]] {
  override def tripleNode(left: Operation[T], center: Operation[T], right: Operation[T]) = new Operation[T] {
    override def depth(): Int = 1 + Math.max(left.depth, Math.max(left.depth, right.depth))
    override def toList(): List[T] = left.toList ::: center.toList ::: right.toList
  }
}

// extend operation
trait OperationExt[T] {
  def max(ordering: Ordering[T]): T
}

class TreeExtOperationExt[T]() extends TreeExt[T, OperationExt[T]] {
  override def tripleNode(left: OperationExt[T], center: OperationExt[T], right: OperationExt[T]) = new OperationExt[T] {
    override def max(ordering: Ordering[T]) = ordering.max(left.max(ordering), ordering.max(center.max(ordering), right.max(ordering)))
  }
  override def node(left: OperationExt[T], right: OperationExt[T]) = new OperationExt[T] {
    override def max(ordering: Ordering[T]) = ordering.max(left.max(ordering), right.max(ordering))
  }
  override def leaf(value: T) = new OperationExt[T] {
    override def max(ordering: Ordering[T]) = value
  }
}

// Object algebra
object TreeOA extends App {
  def tree[OP](op: Tree[Int, OP]) = {
    import op._
    node(node(leaf(1), leaf(2)), leaf(3))
  }
  val opOnTree = new TreeOperation[Int]()
  println(s"depth: ${tree(opOnTree).depth}")
  println(s"sum: ${tree(opOnTree).toList}")

  // apply extended model and operation
  def treeExt[OP](op: TreeExt[Int, OP]) = {
    import op._
    tripleNode(node(leaf(1), leaf(2)), leaf(3), leaf(4))
  }
  val opOnTreeExt = new TreeExtOpertation[Int]()
  val extOpOnTreeExt = new TreeExtOperationExt[Int]()
  println(s"depth: ${treeExt(opOnTreeExt).depth}")
  println(s"list: ${treeExt(opOnTreeExt).toList}")
  println(s"max: ${treeExt(extOpOnTreeExt).max(Ordering.Int)}")
}
