begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.fieldeditors.contextmenu
package|package
name|net
operator|.
name|sf
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
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|JabRefGUI
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|protectedterms
operator|.
name|NewProtectedTermsFileDialog
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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

begin_class
DECL|class|ProtectedTermsMenu
specifier|public
class|class
name|ProtectedTermsMenu
extends|extends
name|JMenu
block|{
DECL|field|formatter
specifier|private
specifier|static
specifier|final
name|ProtectTermsFormatter
name|formatter
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
name|JMenu
name|externalFiles
decl_stmt|;
DECL|field|opener
specifier|private
specifier|final
name|JTextComponent
name|opener
decl_stmt|;
DECL|method|ProtectedTermsMenu (JTextComponent opener)
specifier|public
name|ProtectedTermsMenu
parameter_list|(
name|JTextComponent
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
name|JMenuItem
name|protectItem
init|=
operator|new
name|JMenuItem
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
name|addActionListener
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
name|JMenuItem
name|formatItem
init|=
operator|new
name|JMenuItem
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
name|addActionListener
argument_list|(
name|event
lambda|->
name|opener
operator|.
name|setText
argument_list|(
name|formatter
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
name|JMenu
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
name|add
argument_list|(
name|protectItem
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|externalFiles
argument_list|)
expr_stmt|;
name|this
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|formatItem
argument_list|)
expr_stmt|;
block|}
DECL|method|updateFiles ()
specifier|public
name|void
name|updateFiles
parameter_list|()
block|{
name|externalFiles
operator|.
name|removeAll
argument_list|()
expr_stmt|;
for|for
control|(
name|ProtectedTermsList
name|list
range|:
name|Globals
operator|.
name|protectedTermsLoader
operator|.
name|getProtectedTermsLists
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|list
operator|.
name|isInternalList
argument_list|()
condition|)
block|{
name|JMenuItem
name|fileItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|list
operator|.
name|getDescription
argument_list|()
argument_list|)
decl_stmt|;
name|externalFiles
operator|.
name|add
argument_list|(
name|fileItem
argument_list|)
expr_stmt|;
name|fileItem
operator|.
name|addActionListener
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
block|}
block|}
name|externalFiles
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|JMenuItem
name|addToNewFileItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New"
argument_list|)
operator|+
literal|"..."
argument_list|)
decl_stmt|;
name|addToNewFileItem
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|NewProtectedTermsFileDialog
name|dialog
init|=
operator|new
name|NewProtectedTermsFileDialog
argument_list|(
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
argument_list|,
name|Globals
operator|.
name|protectedTermsLoader
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|dialog
operator|.
name|isOKPressed
argument_list|()
condition|)
block|{
comment|// Update preferences with new list
name|Globals
operator|.
name|prefs
operator|.
name|setProtectedTermsPreferences
argument_list|(
name|Globals
operator|.
name|protectedTermsLoader
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|externalFiles
operator|.
name|add
argument_list|(
name|addToNewFileItem
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

