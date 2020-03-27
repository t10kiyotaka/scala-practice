import shapeless.ops.record._
import shapeless.{Generic, LabelledGeneric, Poly1}

// Json Value Definition
sealed trait JsonValue
case class JsonObject(fields: Seq[(String, JsonValue)]) extends JsonValue
case class JsonArray(items: Seq[JsonValue]) extends JsonValue
case class JsonString(value: String) extends JsonValue
case class JsonNumber(value: Double) extends JsonValue
case class JsonBoolean(value: Boolean) extends JsonValue
case object JsonNull extends JsonValue

// User class
case class User(name: String, age: Int)


// Generic はcase classとHListを相互変換する
val genUser = Generic[User]

val user = User("Bob", 12)

val hList = genUser.to(user)

val userFromHList = genUser.from(hList)


object toJsValue extends Poly1 {
  implicit val atInt = at[Int](JsonNumber(_))
  implicit val atString = at[String](JsonString(_))
}

val labelledGenUser = LabelledGeneric[User]
val fields: Seq[String] = Keys[labelledGenUser.Repr].apply.toList[Symbol].map(_.name)
val values: Seq[JsonValue] = labelledGenUser.to(user).map(toJsValue).toList
