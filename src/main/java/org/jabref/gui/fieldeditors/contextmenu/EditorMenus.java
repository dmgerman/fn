begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors.contextmenu
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|contextmenu
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Supplier
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
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

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|CustomMenuItem
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|MenuItem
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|SeparatorMenuItem
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextArea
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tooltip
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|CopyDoiUrlAction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|EditorTextArea
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeNamesFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * Provides context menus for the text fields of the entry editor. Note that we use {@link Supplier} to prevent an early  * instantiation of the menus. Therefore, they are attached to each text field but instantiation happens on the first  * right-click of the user in that field. The late instantiation is done by {@link  * EditorTextArea#addToContextMenu(java.util.function.Supplier)}.  */
end_comment

begin_class
DECL|class|EditorMenus
specifier|public
class|class
name|EditorMenus
block|{
comment|/**      * The default menu that contains functions for changing the case of text and doing several conversions.      *      * @param textArea text-area that this menu will be connected to      * @return default context menu available for most text fields      */
DECL|method|getDefaultMenu (TextArea textArea)
specifier|public
specifier|static
name|Supplier
argument_list|<
name|List
argument_list|<
name|MenuItem
argument_list|>
argument_list|>
name|getDefaultMenu
parameter_list|(
name|TextArea
name|textArea
parameter_list|)
block|{
return|return
parameter_list|()
lambda|->
block|{
name|List
argument_list|<
name|MenuItem
argument_list|>
name|menuItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|CaseChangeMenu
argument_list|(
name|textArea
operator|.
name|textProperty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|ConversionMenu
argument_list|(
name|textArea
operator|.
name|textProperty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|ProtectedTermsMenu
argument_list|(
name|textArea
argument_list|)
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|menuItems
return|;
block|}
return|;
block|}
comment|/**      * The default context menu with a specific menu for normalizing person names regarding to BibTex rules.      *      * @param textArea text-area that this menu will be connected to      * @return menu containing items of the default menu and an item for normalizing person names      */
DECL|method|getNameMenu (TextArea textArea)
specifier|public
specifier|static
name|Supplier
argument_list|<
name|List
argument_list|<
name|MenuItem
argument_list|>
argument_list|>
name|getNameMenu
parameter_list|(
name|TextArea
name|textArea
parameter_list|)
block|{
return|return
parameter_list|()
lambda|->
block|{
name|CustomMenuItem
name|normalizeNames
init|=
operator|new
name|CustomMenuItem
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normalize to BibTeX name format"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|normalizeNames
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|textArea
operator|.
name|setText
argument_list|(
operator|new
name|NormalizeNamesFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|textArea
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Tooltip
name|toolTip
init|=
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"If possible, normalize this list of names to conform to standard BibTeX name formatting"
argument_list|)
argument_list|)
decl_stmt|;
name|Tooltip
operator|.
name|install
argument_list|(
name|normalizeNames
operator|.
name|getContent
argument_list|()
argument_list|,
name|toolTip
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|MenuItem
argument_list|>
name|menuItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|6
argument_list|)
decl_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
name|normalizeNames
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|addAll
argument_list|(
name|getDefaultMenu
argument_list|(
name|textArea
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|menuItems
return|;
block|}
return|;
block|}
comment|/**      * The default context menu with a specific menu copying a DOI URL.      *      * @param textArea text-area that this menu will be connected to      * @return menu containing items of the default menu and an item for copying a DOI URL      */
DECL|method|getDOIMenu (TextArea textArea)
specifier|public
specifier|static
name|Supplier
argument_list|<
name|List
argument_list|<
name|MenuItem
argument_list|>
argument_list|>
name|getDOIMenu
parameter_list|(
name|TextArea
name|textArea
parameter_list|)
block|{
return|return
parameter_list|()
lambda|->
block|{
name|AbstractAction
name|copyDoiUrlAction
init|=
operator|new
name|CopyDoiUrlAction
argument_list|(
name|textArea
argument_list|)
decl_stmt|;
name|MenuItem
name|copyDoiUrlMenuItem
init|=
operator|new
name|MenuItem
argument_list|(
operator|(
name|String
operator|)
name|copyDoiUrlAction
operator|.
name|getValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
argument_list|)
decl_stmt|;
name|copyDoiUrlMenuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|copyDoiUrlAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|MenuItem
argument_list|>
name|menuItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
name|copyDoiUrlMenuItem
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menuItems
operator|.
name|addAll
argument_list|(
name|getDefaultMenu
argument_list|(
name|textArea
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|menuItems
return|;
block|}
return|;
block|}
block|}
end_class

end_unit

