import shapeless.Generic.Aux
import shapeless._
import Main._
import shapeless.ops.coproduct._
sealed trait Enum
case object Enum2 extends Enum
case object Enum3 extends Enum

case class Enum4(i: String) extends Enum

//implicit def names[T <: Coproduct](
//implicit keys: ops.union.Keys[T]
//            ) = keys()

//def call[T <: Coproduct] = names

val l = Generic[Enum]
def awd[A <: Coproduct](implicit at: ToHList[A]) = ToHList(at)

val n = Nat(1)
val l2 = awd[l.Repr]
println(l2())
//val keys = ops.union.Values[l.Repr]
//val result = keys()

//result.head
//result.head
//println(result.tail.head.getClass)
