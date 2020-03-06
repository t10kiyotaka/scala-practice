import shapeless.{::, HList, HNil, Poly1}
import shapeless._
import shapeless.ops.hlist
import shapeless.ops.hlist.Mapper.Aux

trait ProductMapper[A, B, P] {
  def apply(a: A): B
}

implicit def genericProductMapper[
  A, B,
  P <: Poly,
  ARepr <: HList,
  BRepr <: HList
](
  implicit
  aGen: Generic.Aux[A, ARepr],
  bGen: Generic.Aux[B, BRepr],
  mapper: hlist.Mapper.Aux[P, ARepr, BRepr]
): ProductMapper[A, B, P] =
  new ProductMapper[A, B, P] {
//    def apply(a: A): B =
//      bGen.from(mapper.apply(aGen.to(a)))
    def apply(a: A): B = {
      val aaa: ARepr = aGen.to(a)
      val bbb: Aux[P, ARepr, BRepr] = mapper
      val ccc: BRepr = mapper.apply(aGen.to(a))
      bGen.from(mapper.apply(aGen.to(a)))
    }

  }

object conversions extends Poly1 {
  implicit val intCase:  Case.Aux[Int, Boolean]   = at(_ > 0)
  implicit val boolCase: Case.Aux[Boolean, Int]   = at(if(_) 1 else 0)
  implicit val strCase:  Case.Aux[String, String] = at(identity)
}


implicit class ProductMapperOps[A](a: A) {
  class Builder[B] {
    def apply[P <: Poly](poly: P)
      (implicit pm: ProductMapper[A, B, P]): B =
      pm.apply(a)
  }

  def mapTo[B]: Builder[B] = new Builder[B]
}

case class IceCream1(name: String, numCherries: Int, inCone: Boolean)
case class IceCream2(name: String, hasCherries: Boolean, numCones: Int)

IceCream1("Sundae", 1, false).mapTo[IceCream2](conversions)






//object valueAndSizeOf extends Poly1 {
//  implicit val intCase: Case.Aux[Int, Int :: Int :: HNil] =
//    at(num => num :: num :: HNil)
//
//  implicit val stringCase: Case.Aux[String, String :: Int :: HNil] =
//    at(str => str :: str.length :: HNil)
//
//  implicit val booleanCase: Case.Aux[Boolean, Boolean :: Int :: HNil] =
//    at(bool => bool :: (if(bool) 1 else 0) :: HNil)
//}
//
//val aaa: HList = (10 :: "hello" :: true :: HNil).map(valueAndSizeOf)
//val bbb: HList = (10 :: "hello" :: true :: HNil).flatMap(valueAndSizeOf)

//object sizeOf extends Poly1 {
//  implicit val intCase: Case.Aux[Int, Int] =
//    at(identity)
//
//  implicit val stringCase: Case.Aux[String, Int] =
//    at(_.length)
//
//  implicit val booleanCase: Case.Aux[Boolean, Int] =
//    at(bool => if(bool) 1 else 0)
//}
//
//(10 :: "hello" :: true :: HNil).map(sizeOf)

//
//object myPoly extends Poly1 {
//  implicit val intCase: Case.Aux[Int, Double] =
//    at(num => num / 2.0)
//
//  implicit val stringCase: Case.Aux[String, Int] =
//    at(str => str.length)
//
//
//
//}
//val a = myPoly.apply(123)
//
