begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf.kunststoff
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
package|;
end_package

begin_comment
comment|/*  * This code was developed by INCORS GmbH (www.incors.com).  * It is published under the terms of the GNU Lesser General Public License.  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
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
name|*
import|;
end_import

begin_comment
comment|/**  * The only difference between this class and the DefaultListCellRenderer is that  * objects of this class are not opaque by default.  */
end_comment

begin_class
DECL|class|ModifiedDefaultListCellRenderer
specifier|public
class|class
name|ModifiedDefaultListCellRenderer
extends|extends
name|DefaultListCellRenderer
block|{
DECL|method|getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|index
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|cellHasFocus
parameter_list|)
block|{
name|super
operator|.
name|getListCellRendererComponent
argument_list|(
name|list
argument_list|,
name|value
argument_list|,
name|index
argument_list|,
name|isSelected
argument_list|,
name|cellHasFocus
argument_list|)
expr_stmt|;
name|setOpaque
argument_list|(
name|isSelected
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

