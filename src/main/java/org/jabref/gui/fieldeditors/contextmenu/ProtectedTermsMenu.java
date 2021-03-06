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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|Menu
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
name|TextInputControl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|casechanger
operator|.
name|ProtectTermsFormatter
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsList
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
name|protectedterms
operator|.
name|ProtectedTermsLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|ProtectedTermsMenu
class|class
name|ProtectedTermsMenu
extends|extends
name|Menu
block|{
DECL|field|FORMATTER
specifier|private
specifier|static
specifier|final
name|Formatter
name|FORMATTER
init|=
operator|new
name|ProtectTermsFormatter
argument_list|(
name|Globals
operator|.
name|protectedTermsLoader
argument_list|)
decl_stmt|;
DECL|field|externalFiles
specifier|private
specifier|final
name|Menu
name|externalFiles
decl_stmt|;
DECL|field|opener
specifier|private
specifier|final
name|TextInputControl
name|opener
decl_stmt|;
DECL|method|ProtectedTermsMenu (final TextInputControl opener)
specifier|public
name|ProtectedTermsMenu
parameter_list|(
specifier|final
name|TextInputControl
name|opener
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Protect terms"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|opener
operator|=
name|opener
expr_stmt|;
name|MenuItem
name|protectItem
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add {} around selected text"
argument_list|)
argument_list|)
decl_stmt|;
name|protectItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|String
name|selectedText
init|=
name|opener
operator|.
name|getSelectedText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|selectedText
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|selectedText
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|opener
operator|.
name|replaceSelection
argument_list|(
literal|"{"
operator|+
name|selectedText
operator|+
literal|"}"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|MenuItem
name|formatItem
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Format field"
argument_list|)
argument_list|)
decl_stmt|;
name|formatItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|opener
operator|.
name|setText
argument_list|(
name|FORMATTER
operator|.
name|format
argument_list|(
name|opener
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|externalFiles
operator|=
operator|new
name|Menu
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add selected text to list"
argument_list|)
argument_list|)
expr_stmt|;
name|updateFiles
argument_list|()
expr_stmt|;
name|this
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|protectItem
argument_list|)
expr_stmt|;
name|this
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|externalFiles
argument_list|)
expr_stmt|;
name|this
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|formatItem
argument_list|)
expr_stmt|;
block|}
DECL|method|updateFiles ()
specifier|private
name|void
name|updateFiles
parameter_list|()
block|{
name|externalFiles
operator|.
name|getItems
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|ProtectedTermsLoader
name|loader
init|=
name|Globals
operator|.
name|protectedTermsLoader
decl_stmt|;
name|List
argument_list|<
name|ProtectedTermsList
argument_list|>
name|nonInternal
init|=
name|loader
operator|.
name|getProtectedTermsLists
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|list
lambda|->
operator|!
name|list
operator|.
name|isInternalList
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|ProtectedTermsList
name|list
range|:
name|nonInternal
control|)
block|{
name|MenuItem
name|fileItem
init|=
operator|new
name|MenuItem
argument_list|(
name|list
operator|.
name|getDescription
argument_list|()
argument_list|)
decl_stmt|;
name|fileItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|String
name|selectedText
init|=
name|opener
operator|.
name|getSelectedText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|selectedText
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|selectedText
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|list
operator|.
name|addProtectedTerm
argument_list|(
name|selectedText
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|externalFiles
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|fileItem
argument_list|)
expr_stmt|;
block|}
name|externalFiles
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

