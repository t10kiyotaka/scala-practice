package shepeless

import shapeless.{HNil, ::, HList}

object sample extends App {
  val product: String :: Int :: Boolean :: HNil =
    "Sunday" :: 1 :: false :: HNil

  println(product)
}
