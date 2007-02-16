begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Nizar N. Batada, Morten O. Alver   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|RenderingHints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
import|;
end_import

begin_comment
comment|/**  * An implementation of the FieldEditor backed by a JTextArea. Used for  * multi-line input.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|FieldTextArea
specifier|public
class|class
name|FieldTextArea
extends|extends
name|JTextArea
implements|implements
name|FieldEditor
block|{
DECL|field|PREFERRED_SIZE
name|Dimension
name|PREFERRED_SIZE
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
decl_stmt|;
DECL|field|label
name|FieldNameLabel
name|label
decl_stmt|;
DECL|field|fieldName
name|String
name|fieldName
decl_stmt|;
DECL|field|bull
specifier|final
specifier|static
name|Pattern
name|bull
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s*[-\\*]+.*"
argument_list|)
decl_stmt|;
DECL|field|indent
specifier|final
specifier|static
name|Pattern
name|indent
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s+.*"
argument_list|)
decl_stmt|;
DECL|field|antialias
specifier|final
name|boolean
name|antialias
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"antialias"
argument_list|)
decl_stmt|;
DECL|method|FieldTextArea (String fieldName_, String content)
specifier|public
name|FieldTextArea
parameter_list|(
name|String
name|fieldName_
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|super
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|updateFont
argument_list|()
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field
comment|// was focused when an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
name|addFocusListener
argument_list|(
operator|new
name|FieldEditorFocusListener
argument_list|()
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|sp
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|200
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setWrapStyleWord
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|fieldName
operator|=
name|fieldName_
expr_stmt|;
name|label
operator|=
operator|new
name|FieldNameLabel
argument_list|(
literal|" "
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackground
argument_list|)
expr_stmt|;
name|FieldTextMenu
name|popMenu
init|=
operator|new
name|FieldTextMenu
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|this
operator|.
name|addMouseListener
argument_list|(
name|popMenu
argument_list|)
expr_stmt|;
name|label
operator|.
name|addMouseListener
argument_list|(
name|popMenu
argument_list|)
expr_stmt|;
block|}
DECL|method|getPreferredScrollableViewportSize ()
specifier|public
name|Dimension
name|getPreferredScrollableViewportSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
DECL|method|paint (Graphics g)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
if|if
condition|(
name|antialias
condition|)
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|super
operator|.
name|paint
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
DECL|method|setFieldName (String newName)
specifier|public
name|void
name|setFieldName
parameter_list|(
name|String
name|newName
parameter_list|)
block|{
name|fieldName
operator|=
name|newName
expr_stmt|;
block|}
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
DECL|method|setLabelColor (Color c)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|Color
name|c
parameter_list|)
block|{
name|label
operator|.
name|setForeground
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{
name|setFont
argument_list|(
name|GUIGlobals
operator|.
name|CURRENTFONT
argument_list|)
expr_stmt|;
block|}
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{
name|int
name|sel
init|=
name|getSelectionEnd
argument_list|()
operator|-
name|getSelectionStart
argument_list|()
decl_stmt|;
if|if
condition|(
name|sel
operator|>
literal|0
condition|)
comment|// selected text available
name|replaceSelection
argument_list|(
name|textToInsert
argument_list|)
expr_stmt|;
else|else
block|{
name|int
name|cPos
init|=
name|this
operator|.
name|getCaretPosition
argument_list|()
decl_stmt|;
name|this
operator|.
name|insert
argument_list|(
name|textToInsert
argument_list|,
name|cPos
argument_list|)
expr_stmt|;
block|}
block|}
comment|// public void keyPressed(KeyEvent event) {
comment|// int keyCode = event.getKeyCode();
comment|// if (keyCode == KeyEvent.VK_ENTER) {
comment|// // Consume; we will handle this ourselves:
comment|// event.consume();
comment|// autoWrap();
comment|//
comment|// }
comment|//
comment|// }
comment|//
comment|// private void autoWrap() {
comment|// int pos = getCaretPosition();
comment|// int posAfter = pos + 1;
comment|// StringBuffer sb = new StringBuffer(getText());
comment|// // First insert the line break:
comment|// sb.insert(pos, '\n');
comment|//
comment|// // We want to investigate the beginning of the last line:
comment|// // int end = sb.length();
comment|//
comment|// // System.out.println("."+sb.substring(0, pos)+".");
comment|//
comment|// // Find 0 or the last line break before our current position:
comment|// int idx = sb.substring(0, pos).lastIndexOf("\n") + 1;
comment|// String prevLine = sb.substring(idx, pos);
comment|// if (bull.matcher(prevLine).matches()) {
comment|// int id = findFirstNonWhitespace(prevLine);
comment|// if (id>= 0) {
comment|// sb.insert(posAfter, prevLine.substring(0, id));
comment|// posAfter += id;
comment|// }
comment|// } else if (indent.matcher(prevLine).matches()) {
comment|// int id = findFirstNonWhitespace(prevLine);
comment|// if (id>= 0) {
comment|// sb.insert(posAfter, prevLine.substring(0, id));
comment|// posAfter += id;
comment|// }
comment|// }
comment|// /*
comment|// * if (prevLine.startsWith(" ")) { sb.insert(posAfter, " "); posAfter++; }
comment|// */
comment|//
comment|// setText(sb.toString());
comment|// setCaretPosition(posAfter);
comment|// }
comment|//
comment|// private int findFirstNonWhitespace(String s) {
comment|// for (int i = 0; i< s.length(); i++) {
comment|// if (!Character.isWhitespace(s.charAt(i)))
comment|// return i;
comment|// }
comment|// return -1;
comment|// }
comment|//
comment|// public void keyReleased(KeyEvent event) {
comment|//
comment|// }
comment|//
comment|// public void keyTyped(KeyEvent event) {
comment|// }
block|}
end_class

end_unit

