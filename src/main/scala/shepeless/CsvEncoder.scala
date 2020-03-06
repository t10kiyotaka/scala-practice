package shepeless

import shapeless.Generic

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  def pure[A](func: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] =
        func(value)
    }


  implicit def genericEnc[A, R](
    implicit
    gen: Generic[A] { type Repr = R }, // Repr is output type
    enc: CsvEncoder[R]
  ): CsvEncoder[A] =
    pure(a => enc.encode(gen.to(a)))

  // Aux Pattern
  implicit def genericEnc[A, R](
    implicit
    gen: Generic.Aux[A, R],
    enc: CsvEncoder[R]
  ): CsvEncoder[A] =
    pure(a => enc.encode(gen.to(a)))
}
