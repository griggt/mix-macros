package location

import scala.quoted.{Quotes, Expr}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

case class Location(path: String, line: Int)

object Macros {
  def location: Location = macro MacrosScala2.locationImpl
  inline def location: Location = ${locationImpl}

  private def locationImpl(using quotes: Quotes): Expr[Location] =
    import quotes.reflect.Position
    val file = Expr(Position.ofMacroExpansion.sourceFile.jpath.toString)
    val line = Expr(Position.ofMacroExpansion.startLine + 1)
    '{new Location($file, $line)}
}

object MacrosScala2 {
  def locationImpl(c: Context): c.Tree =  {
    import c.universe._
    val line = Literal(Constant(c.enclosingPosition.line))
    val path = Literal(Constant(c.enclosingPosition.source.path))
    New(c.mirror.staticClass(classOf[Location].getName()), path, line)
  }
}
