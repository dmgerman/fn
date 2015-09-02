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
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_interface
DECL|interface|AutoCompleteRenderer
specifier|public
interface|interface
name|AutoCompleteRenderer
parameter_list|<
name|E
parameter_list|>
block|{
DECL|method|updateListData (E[] strings)
name|boolean
name|updateListData
parameter_list|(
name|E
index|[]
name|strings
parameter_list|)
function_decl|;
DECL|method|init ()
name|Component
name|init
parameter_list|()
function_decl|;
comment|/**      * This method will attempt to locate a reasonable autocomplete item      * from all combo box items and select it. It will also populate the      * combo box editor with the remaining text which matches the      * autocomplete item and select it. If the selection changes and the      * JComboBox is not a Table Cell Editor, an ActionEvent will be      * broadcast from the combo box.      */
DECL|method|selectAutoCompleteTerm (String text)
name|void
name|selectAutoCompleteTerm
parameter_list|(
name|String
name|text
parameter_list|)
function_decl|;
DECL|method|selectNewItem (int offset)
name|void
name|selectNewItem
parameter_list|(
name|int
name|offset
parameter_list|)
function_decl|;
DECL|method|getSelectedItem ()
name|E
name|getSelectedItem
parameter_list|()
function_decl|;
DECL|method|registerAcceptAction (ActionListener acceptAction)
name|void
name|registerAcceptAction
parameter_list|(
name|ActionListener
name|acceptAction
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

