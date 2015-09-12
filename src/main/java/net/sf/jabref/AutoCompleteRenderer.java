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

begin_comment
comment|/**  * Renders the list of possible autocomplete items. Also takes care of the currently selected item.  *   * @param<E> the type of the items  */
end_comment

begin_class
DECL|class|AutoCompleteRenderer
specifier|public
specifier|abstract
class|class
name|AutoCompleteRenderer
parameter_list|<
name|E
parameter_list|>
block|{
comment|/**      * Refreshes the list of possible autocomplete items. Clears the currently selected item.      *       * @param items list of possible autocomplete items      */
DECL|method|update (E[] items)
specifier|public
specifier|abstract
name|void
name|update
parameter_list|(
name|E
index|[]
name|items
parameter_list|)
function_decl|;
comment|/**      * Creates the control which will be shown in the autocomplete popup.      *       * @param acceptAction the action to be performed if the current selection is chosen as the autocompletion      * @return the control to be added to the autocomplete popup      */
DECL|method|init (ActionListener acceptAction)
specifier|public
specifier|abstract
name|Component
name|init
parameter_list|(
name|ActionListener
name|acceptAction
parameter_list|)
function_decl|;
comment|/**      * Selects the item at the given position. If the specified index is not valid, then the selection will be cleared.      *       * @param index position of the item      */
DECL|method|selectItem (int index)
specifier|public
specifier|abstract
name|void
name|selectItem
parameter_list|(
name|int
name|index
parameter_list|)
function_decl|;
comment|/**      * Selects the item relative to the currently selected item. If the specified offset is not valid, then the      * selection will be cleared.      *       * @param offset offset of the item      */
DECL|method|selectItemRelative (int offset)
specifier|public
name|void
name|selectItemRelative
parameter_list|(
name|int
name|offset
parameter_list|)
block|{
name|int
name|newIndex
init|=
name|getSelectedIndex
argument_list|()
operator|+
name|offset
decl_stmt|;
name|selectItem
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the index of the currently selected item.      *       * @return index of the selected item      */
DECL|method|getSelectedIndex ()
specifier|public
specifier|abstract
name|int
name|getSelectedIndex
parameter_list|()
function_decl|;
comment|/**      * Returns the currently selected item.      *       * @return selected item      */
DECL|method|getSelectedItem ()
specifier|public
specifier|abstract
name|E
name|getSelectedItem
parameter_list|()
function_decl|;
block|}
end_class

end_unit

