# Expression problem

The Expression Problem is a new name for an old problem.  The goal is
to define a datatype by cases, where one can add new cases to the
datatype and new functions over the datatype, without recompiling
existing code, and while retaining static type safety (e.g., no
casts).  For the concrete example, we take expressions as the data
type, begin with one case (constants) and one function (evaluators),
then add one more construct (plus) and one more function (conversion
to a string). [Wadler]

Can your application be structured in such a way that both the data model and
the set of virtual operations over it can be extended without the need to modify existing
code, without the need for code repetition and without runtime type errors. [Torgersen]

# Tree example
This project demonstrates partial and full solutions to expression problem on the example of Tree structure.
There are more solutions possible, but those too difficult for real usage.

Operation on tree: depth, sum,  max  
Model on tree: Node(left, right), Leaf(value), TripleNode(left, center, right)

## Tree ADT
Tree implemented with Functional approach (Algebraic Data Types).

- easy to add operation without modification existing code
- hard to add model

## Tree OO
Tree implemented with Object Oriented approach.

- easy to add model without modification existing code
- hard to add operation

## Tree Visitor
Tree implemented with Visitor pattern. OO equivalent to ADT.

- same as Tree ADT, but less concise
- easy to add operation without modification existing code
- hard to add model

## Tree Object Algebra
Tree implemented with Object Algebra approach [Bruno].

- can extend both model and operations
- bit harder concept to follow

## Tree Implicits
Tree implementation with Scala implicits

- can extend both model and operations
- nice concept of implicits, quite elegant solutions of expression problem
- need importing them explicitly 
