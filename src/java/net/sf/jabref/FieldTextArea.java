begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|EtchedBorder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_comment
comment|//import java.awt.Color;
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|*
import|;
end_import

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
specifier|protected
name|JScrollPane
name|sp
decl_stmt|;
DECL|field|label
specifier|protected
name|FieldNameLabel
name|label
decl_stmt|;
DECL|field|fieldName
specifier|protected
name|String
name|fieldName
decl_stmt|;
comment|//protected Completer completer;
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
comment|// Add the global focus listener, so a menu item can see if this field was focused when
comment|// an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
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
comment|//label.setBorder(BorderFactory.createEtchedBorder
comment|//		 (GUIGlobals.lightGray, Color.gray));
comment|//label.setBorder(BorderFactory.createEtchedBorder());
comment|//label.setOpaque(true);
comment|//label.setBackground(GUIGlobals.lightGray);
comment|//label.setForeground(Color.gray);
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackground
argument_list|)
expr_stmt|;
comment|//if ((content != null)&& (content.length()> 0))
comment|//label.setForeground(GUIGlobals.validFieldColor);
comment|// At construction time, the field can never have an invalid value.
comment|//else
comment|//    label.setForeground(GUIGlobals.nullFieldColor);
block|}
comment|/*     public void setAutoComplete(Completer completer) { 	addKeyListener(new AutoCompListener(completer));     }     */
comment|/*public Dimension getPreferredSize() { 	return PREFERRED_SIZE; 	}*/
DECL|method|getPreferredScrollableViewportSize ()
specifier|public
name|Dimension
name|getPreferredScrollableViewportSize
parameter_list|()
block|{
return|return
name|PREFERRED_SIZE
return|;
block|}
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
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
name|RenderingHints
name|rh
init|=
name|g2
operator|.
name|getRenderingHints
argument_list|()
decl_stmt|;
name|rh
operator|.
name|put
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
name|rh
operator|.
name|put
argument_list|(
name|RenderingHints
operator|.
name|KEY_RENDERING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_RENDER_QUALITY
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setRenderingHints
argument_list|(
name|rh
argument_list|)
expr_stmt|;
name|super
operator|.
name|paintComponent
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
block|}
end_class

end_unit

