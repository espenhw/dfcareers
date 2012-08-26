package org.grumblesmurf.df.careers

import java.io.File
import java.io.IOException
import javax.swing._
import javax.swing.text.BadLocationException
import javax.swing.text.html.HTMLDocument
import scala.collection.{mutable, SortedMap}
import scala.collection.mutable.ArrayBuffer
import scala.xml.XML

object Main {
  def main(args: Array[String]) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
    frame = new JFrame("DFCareers")
    form = new MainForm
    frame.setContentPane(form.contentPane)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(800, 800)
    frame.setVisible(true)
  }

  def readDwarves() {
    dwarvesList.clear()
    val file = File.createTempFile("dfcareers", ".xml")

    try {
      exportDwarvesTo(file)
      val doc = XML.loadFile(file)
      val newDwarves = doc \\ "Creature" map { n =>
        Dwarf.from(n)
      }

      dwarvesList.addAll(newDwarves)
      generateEmbarkHelp()
    } catch {
      case e: Exception =>
        reportError(e)
    } finally {
      file.delete
    }
  }

  private def exportDwarvesTo(file: File) {
    val dfhack = RemoteDFHack.connect
    try {
      val path = file.getAbsolutePath
      dfhack.runCommand("dwarfexport", path)
    } finally {
      dfhack.close()
    }
  }

  def reportError(e: Exception) {
    e.printStackTrace()
    JOptionPane.showMessageDialog(frame, e)
  }

  def dwarvesModel: ListModel = dwarvesList

  def displayDwarf(index: Int) {
    val dwarf = dwarvesList.getElementAt(index)
    val document = form.dwarfInfo.getDocument.asInstanceOf[HTMLDocument]
    try {
      val body = newBodyElement(document)
      dwarf.renderTo(body)
    } catch {
      case e: Exception =>
        reportError(e)
    }
  }

  private def newBodyElement(document: HTMLDocument) = {
    val body = document.getElement("body")
    document.setOuterHTML(body, "<body id='body'></body>")
    document.getElement("body")
  }

  def displayJob(selected: String) {
    val position = Position.fromString(selected)

    val evaluations = SortedMap.empty[Evaluation, mutable.Buffer[Dwarf]] ++ dwarvesList.dwarves.map(d => position.evaluate(d) -> d).filter(_._1.isPositive).groupBy(_._1).map(e => e._1 -> e._2.map(_._2))

    val document: HTMLDocument = form.jobInfo.getDocument.asInstanceOf[HTMLDocument]
    try {
      val body = newBodyElement(document)
      val sb = new StringBuilder
      sb.append(<h1>{position}</h1>)
      evaluations.foreach {
        case (evaluation, dwarves) => {
          sb.append(<h2>{evaluation}</h2>)
          sb.append(<ul>{dwarves.map(d => <li>{d}</li>)}</ul>)
        }
      }
      document.insertBeforeEnd(body, sb.toString())
    } catch {
      case e: Exception =>
        reportError(e)
    }
  }

  private def generateEmbarkHelp() {
    val positionsToFill = Set(Position.Broker, Position.Manager, Position.RecordKeeper, Position.Carpenter, Position.Woodcutter, Position.Mason, Position.Grower, Position.Brewer, Position.Mechanic, Position.Miner)

    val rankings = pickDwarvesFor(positionsToFill)
    val document = form.embarkHelp.getDocument.asInstanceOf[HTMLDocument]
    try {
      val body = newBodyElement(document)
      val sb = new StringBuilder
      rankings.foreach {
        case (position, ranks) => {
          sb.append(<h2>{position}</h2>)
          val best = ranks.head
          sb.append(<p>Your best choice is {best}</p>)
          val suitables = ranks.tail.takeWhile(_.eval.compareTo(Evaluation.Subpar) < 0)
          if (suitables.nonEmpty) {
            sb.append(<p>Other reasonable choices are {suitables.mkString(", ")}</p>)
          }
        }
      }
      document.insertBeforeEnd(body, sb.toString())
    } catch {
      case e: Exception =>
        reportError(e)
    }
  }

  private def pickDwarvesFor(positionsToFill: Set[Position]) = {
    positionsToFill.map { p =>
      p -> dwarvesList.dwarves.map(d => DwarfRanking(d, p.evaluate(d))).sorted
    }.toMap
  }

  private var frame: JFrame = null
  private val dwarvesList = new Main.DwarvesListModel
  private var form: MainForm = null

  private class DwarvesListModel extends AbstractListModel {
    var dwarves: mutable.Buffer[Dwarf] = ArrayBuffer.empty

    def getSize: Int = {
      dwarves.size
    }

    def getElementAt(index: Int): Dwarf = {
      dwarves(index)
    }

    def clear() {
      val size = dwarves.size
      dwarves = ArrayBuffer.empty
      fireIntervalRemoved(this, 0, math.max(0, size - 1))
    }


    def addAll(newDwarves: Seq[Dwarf]) {
      dwarves ++= newDwarves
      dwarves = dwarves.sorted
      fireIntervalAdded(this, 0, math.max(0, dwarves.size - 1))
    }
  }
}

case class DwarfRanking(dwarf: Dwarf, eval: Evaluation) extends Comparable[DwarfRanking] {
  def compareTo(o: DwarfRanking): Int = {
    val i = eval.compareTo(o.eval)
    if (i == 0) dwarf.compareTo(o.dwarf) else i
  }

  override def toString = "%s (%s)" format (dwarf, eval)
}
