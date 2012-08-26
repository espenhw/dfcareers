package org.grumblesmurf.df.careers

import javax.swing._
import java.awt._

class DwarfRenderer extends ListCellRenderer {
  def getListCellRendererComponent(list: JList, value: AnyRef, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component = {
    val dwarf = value.asInstanceOf[Dwarf]
    val jLabel = new JLabel(value.toString)
    jLabel.setIcon(createImageIconFor(dwarf))
    if (isSelected) {
      jLabel.setOpaque(true)
      jLabel.setBackground(list.getSelectionBackground)
      jLabel.setForeground(list.getSelectionForeground)
    } else {
      jLabel.setBackground(list.getBackground)
      jLabel.setForeground(list.getForeground)
    }
    jLabel
  }

  private def createImageIconFor(dwarf: Dwarf) = {
    if (("Female" == dwarf.sex)) female else male
  }

  protected def createImageIcon(path: String, description: String) = {
    val imgURL = getClass.getResource(path)
    new ImageIcon(imgURL, description)
  }

  private final val male = createImageIcon("/icons/user.png", "Male")
  private final val female = createImageIcon("/icons/user_female.png", "Female")
}
