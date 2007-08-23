begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|javax
operator|.
name|swing
operator|.
name|Icon
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
name|table
operator|.
name|DefaultTableCellRenderer
import|;
end_import

begin_comment
comment|/**  * Renderer for table cells, which supports both Icons, JLabels and plain text.  */
end_comment

begin_class
DECL|class|GeneralRenderer
specifier|public
class|class
name|GeneralRenderer
comment|/*extends JTable implements TableCellRenderer {*/
extends|extends
name|DefaultTableCellRenderer
block|{
DECL|method|GeneralRenderer (Color c)
specifier|public
name|GeneralRenderer
parameter_list|(
name|Color
name|c
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setBackground
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|GeneralRenderer (Color c, Color fg)
specifier|public
name|GeneralRenderer
parameter_list|(
name|Color
name|c
parameter_list|,
name|Color
name|fg
parameter_list|)
block|{
name|this
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|setForeground
argument_list|(
name|fg
argument_list|)
expr_stmt|;
block|}
DECL|method|firePropertyChange (String propertyName, boolean old, boolean newV)
specifier|public
name|void
name|firePropertyChange
parameter_list|(
name|String
name|propertyName
parameter_list|,
name|boolean
name|old
parameter_list|,
name|boolean
name|newV
parameter_list|)
block|{}
DECL|method|firePropertyChange (String propertyName, Object old, Object newV)
specifier|public
name|void
name|firePropertyChange
parameter_list|(
name|String
name|propertyName
parameter_list|,
name|Object
name|old
parameter_list|,
name|Object
name|newV
parameter_list|)
block|{}
comment|/* For enabling the renderer to handle icons. */
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
comment|//System.out.println(""+value);
if|if
condition|(
name|value
operator|instanceof
name|Icon
condition|)
block|{
name|setIcon
argument_list|(
operator|(
name|Icon
operator|)
name|value
argument_list|)
expr_stmt|;
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//super.setValue(null);
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|JLabel
condition|)
block|{
name|JLabel
name|lab
init|=
operator|(
name|JLabel
operator|)
name|value
decl_stmt|;
name|setIcon
argument_list|(
name|lab
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
comment|//table.setToolTipText(lab.getToolTipText());
name|setToolTipText
argument_list|(
name|lab
operator|.
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|lab
operator|.
name|getIcon
argument_list|()
operator|!=
literal|null
condition|)
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//table.setToolTipText(null);
name|setToolTipText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|setText
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*  public void paint(Graphics g) {         Graphics2D g2 = (Graphics2D)g;         //System.out.println(antialiasing);         if (antialiasing) {             RenderingHints rh = g2.getRenderingHints();             rh.put(RenderingHints.KEY_ANTIALIASING,                 RenderingHints.VALUE_ANTIALIAS_ON);             rh.put(RenderingHints.KEY_RENDERING,                 RenderingHints.VALUE_RENDER_QUALITY);             g2.setRenderingHints(rh);         }           super.paint(g2);      }*/
block|}
end_class

end_unit

