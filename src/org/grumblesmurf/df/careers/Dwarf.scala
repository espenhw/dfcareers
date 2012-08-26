package org.grumblesmurf.df.careers

import javax.swing.text.html.HTMLDocument
import xml.{Elem, Node}
import scala.collection.SortedMap

object Dwarf {
  def from(node: Node): Dwarf = {
    var name = (node \ "Name").text
    val bits = name.split(" ")
    if (bits.length > 1 && (bits(0) == bits(1))) {
      name = name.substring(bits(0).length + 1)
    }
    val nickName = (node \ "Nickname").text
    val sex = (node \ "Sex").text
    val attributes = SortedMap.empty[Attribute,Int] ++ (node \ "Attributes").head.nonEmptyChildren.collect {
      case a: Elem =>
        Attribute.valueOf(a.label) -> a.text.toInt
    }.toMap

    val traits = SortedMap.empty[String,Trait] ++ (node \\ "Trait").map { t =>
      (t \ "@name").text -> Trait((t \ "@value").text.toInt, t.text)
    }.toMap

    new Dwarf(name, nickName, sex, attributes, traits)
  }
}

case class Trait(value: Int, description: String)

class Dwarf(val name: String, val nickName: String, val sex: String, val attributes: Map[Attribute, Int], val traits: Map[String, Trait]) extends Comparable[Dwarf] {
  val displayName: String = if (nickName.isEmpty) name else "%s (%s)" format (name, nickName)

  def compareTo(o: Dwarf): Int = displayName.compareTo(o.displayName)

  override def toString: String = displayName

  def renderTo(body: javax.swing.text.Element) {
    val d = body.getDocument.asInstanceOf[HTMLDocument]
    d.insertBeforeEnd(body, nameHeader)
    d.insertBeforeEnd(body, mainLayout)
    d.insertBeforeEnd(d.getElement("left"), attributesTable)
    d.insertBeforeEnd(d.getElement("right"), traitsList)
    d.insertBeforeEnd(d.getElement("right"), positionList)
  }

  private def positionList = {
    val evaluations = SortedMap.empty[Evaluation,Array[Position]] ++ Position.values().map(p => p.evaluate(this) -> p).groupBy(_._1).map {
      case (e, ps) => e -> ps.map(_._2)
    }
    evaluations.map {
      case (evaluation, positions) =>
        <div>
          <h2>{evaluation}</h2>
          <ul>
            {positions.map {p => <li>{p}</li> }}
          </ul>
        </div>
    }.mkString("\n")
  }

  private def mainLayout = <table valign='top'>
    <tr>
      <td id='left'></td> <td id='right'></td>
    </tr>
  </table>.toString()

  private def nameHeader = {
    <h1>
      {displayName}
    </h1>.toString()
  }

  private def attributesTable = {
    <table>
      {attributes.map{
      case (attr, value) => <tr><th>{attr}</th><td>{value}</td></tr>
    }}
    </table>.toString()
  }

  private def traitsList = {
    <div>
      <ul>
        {traits.values.filter(_.description.nonEmpty).map(t => <li>{t.description}</li>)}
      </ul>
      <hr/>
    </div>.toString()
  }

  def attribute(key: String) = attributes(Attribute.valueOf(key))

  def getTrait(key: String) = traits(key)

  override def equals(o: Any): Boolean = o.isInstanceOf[Dwarf] && compareTo(o.asInstanceOf[Dwarf]) == 0

  override def hashCode: Int = {
    if (displayName != null) displayName.hashCode else 0
  }
}
