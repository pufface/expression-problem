package treeimplicit

sealed trait Tree
case class Leaf(value: Int) extends Tree
case class Node[L <: Tree, R <: Tree](left: L, right: R) extends Tree

trait TreeLike[E] {
  def depth(tree: E): Int
  def sum(tree: E): Int
}

object TreeLikeImplicits {
  implicit class TreeLikeAddon[A](tree: A) {
    def depth(implicit treeLike: TreeLike[A]): Int = treeLike.depth(tree)
    def sum(implicit treeLike: TreeLike[A]): Int = treeLike.sum(tree)
  }
  implicit def leaf = new TreeLike[Leaf] {
    override def depth(leaf: Leaf): Int = 1
    override def sum(leaf: Leaf): Int = leaf.value
  }
  implicit def node[L <: Tree : TreeLike, R <: Tree : TreeLike] = new TreeLike[Node[L, R]] {
    override def depth(node: Node[L, R]): Int = Math.max(node.left.depth, node.right.depth)
    override def sum(node: Node[L, R]): Int = node.left.sum + node.right.sum
  }
}

// extend model
case class TripleNode[L <: Tree, C <: Tree, R <: Tree](left: L, center: C, right: R) extends Tree

object TreeLikeImplicits2 {
  import TreeLikeImplicits._
  implicit def tripleNode[L <: Tree : TreeLike, C <: Tree : TreeLike, R <: Tree : TreeLike] = new TreeLike[TripleNode[L,C,R]] {
    override def depth(node: TripleNode[L,C,R]): Int = Math.max(node.left.depth, Math.max(node.center.depth, node.right.depth))
    override def sum(node: TripleNode[L,C,R]): Int = node.left.sum + node.center.sum + node.right.sum
  }
}

// extend operation
trait TreeLikeExt[E] {
  def max(tree: E): Int
}

object TreeLikeExtImplicits {
  implicit class TreeLikeExtAddon[A](tree: A) {
    def max(implicit treeLike: TreeLikeExt[A]): Int = treeLike.max(tree)
  }
  implicit def leafExt = new TreeLikeExt[Leaf] {
    override def max(leaf: Leaf): Int = leaf.value
  }
  implicit def nodeExt[L <: Tree : TreeLikeExt, R <: Tree : TreeLikeExt] = new TreeLikeExt[Node[L, R]] {
    override def max(node: Node[L, R]): Int = Math.max(node.left.max, node.right.max)
  }
  implicit def tripleNodeExt[L <: Tree : TreeLikeExt, C <: Tree : TreeLikeExt, R <: Tree : TreeLikeExt] = new TreeLikeExt[TripleNode[L,C,R]] {
    override def max(node: TripleNode[L,C,R]): Int = Math.max(node.left.max, Math.max(node.center.max, node.right.max))
  }
}

// Implicit
object TreeImplicit extends App {
  val tree = Node(Node(Leaf(1), Leaf(2)), Leaf(3))
  import TreeLikeImplicits._
  println(s"depth: ${tree.depth}")
  println(s"sum: ${tree.sum}")
  // apply extended model and operation
  val treeExt = TripleNode(Node(Leaf(1), Leaf(2)), Leaf(3), Leaf(4))
  import TreeLikeImplicits2._
  import TreeLikeExtImplicits._
  println(s"depth: ${treeExt.depth}")
  println(s"sum: ${treeExt.sum}")
  println(s"max: ${treeExt.max}")
}