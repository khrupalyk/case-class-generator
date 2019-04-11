import shapeless.Generic.Aux

object Main extends App {
  import shapeless.{::, CNil, Coproduct, Generic, HList, HNil, Lazy}

  import scala.util.Random

  trait Generator[A] {
    def generate: A
  }

  def generatorEncoder[A](func:  => A): Generator[A] =
    new Generator[A] {
      def generate: A = func
    }

  implicit val stringGenerator: Generator[String] = generatorEncoder(Random.nextString(10))
  implicit val intGenerator: Generator[Int] = generatorEncoder(Random.nextInt())
  implicit val booleanGenerator: Generator[Boolean] = generatorEncoder(Random.nextBoolean())

  implicit val hnilGenerator: Generator[HNil] = generatorEncoder( HNil)

  implicit def hlistGenerator[H, T <: HList](
                                              implicit
                                              hEncoder: Lazy[Generator[H]],
                                              tEncoder: Lazy[Generator[T]]
                                            ): Generator[H :: T] = new Generator[H :: T] {
    override def generate: H :: T = {
      hEncoder.value.generate :: tEncoder.value.generate
    }
  }

  object Generator {
    def apply[T: Generator] = implicitly[Generator[T]].generate
  }

  implicit def objectGenerator[T, R <: HList](implicit gen: Generic.Aux[T, R],
                                  ff: Lazy[Generator[R]]): Generator[T] = new Generator[T] {
    override def generate: T = gen.from(Generator[R](ff.value))
  }

  case class DD(t: Int, b: Boolean, str: String)
  case class Test(t: Int, b: Boolean, str: String, dd: DD)
  case class Data(t: Int, t2: Test)

  val data = Generator[Data]

  println(data)


  //sealed trait Enum
  //case object Enum2 extends Enum
  //case object Enum3 extends Enum
  //case class Enum4(i: String) extends Enum
  //
  //
  //Generic[Enum]
  //implicit val cnilGenerator = createEncoder[CNil](???)

  //implicit def coproductGenerator[H, T <: Coproduct](implicit enc: Generator[H, ])
}
