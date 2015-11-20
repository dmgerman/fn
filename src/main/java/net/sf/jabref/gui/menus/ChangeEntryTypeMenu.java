begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.menus
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|menus
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefPreferences
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
name|bibtex
operator|.
name|EntryTypes
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
name|BasePanel
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
name|actions
operator|.
name|ChangeTypeAction
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
name|actions
operator|.
name|NewEntryAction
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
name|keyboard
operator|.
name|KeyBinds
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
name|CustomEntryTypesManager
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
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
name|model
operator|.
name|entry
operator|.
name|EntryType
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
name|model
operator|.
name|entry
operator|.
name|IEEETranEntryTypes
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
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_class
DECL|class|ChangeEntryTypeMenu
specifier|public
class|class
name|ChangeEntryTypeMenu
block|{
DECL|field|biblatexMode
specifier|private
specifier|static
specifier|final
name|boolean
name|biblatexMode
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|)
decl_stmt|;
DECL|field|entryShortCuts
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|KeyStroke
argument_list|>
name|entryShortCuts
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_ARTICLE
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|BOOK
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_BOOK
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|PHDTHESIS
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_PHDTHESIS
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|INBOOK
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_MASTERSTHESIS
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|INBOOK
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_INBOOK
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|PROCEEDINGS
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_PROCEEDINGS
argument_list|)
argument_list|)
expr_stmt|;
name|entryShortCuts
operator|.
name|put
argument_list|(
name|BibtexEntryTypes
operator|.
name|UNPUBLISHED
operator|.
name|getName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
operator|.
name|NEW_UNPUBLISHED
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getChangeEntryTypeMenu (BasePanel panel)
specifier|public
specifier|static
name|JMenu
name|getChangeEntryTypeMenu
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|JMenu
name|menu
init|=
operator|new
name|JMenu
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change entry type"
argument_list|)
argument_list|)
decl_stmt|;
name|populateChangeEntryTypeMenu
argument_list|(
name|menu
argument_list|,
name|panel
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|getChangeentryTypePopupMenu (BasePanel panel)
specifier|public
specifier|static
name|JPopupMenu
name|getChangeentryTypePopupMenu
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|JMenu
name|menu
init|=
name|getChangeEntryTypeMenu
argument_list|(
name|panel
argument_list|)
decl_stmt|;
return|return
name|menu
operator|.
name|getPopupMenu
argument_list|()
return|;
block|}
comment|/**      * Remove all types from the menu. Then cycle through all available      * types, and add them.      */
DECL|method|populateChangeEntryTypeMenu (JMenu menu, BasePanel panel)
specifier|private
specifier|static
name|void
name|populateChangeEntryTypeMenu
parameter_list|(
name|JMenu
name|menu
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|menu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
comment|// biblatex?
if|if
condition|(
name|biblatexMode
condition|)
block|{
for|for
control|(
name|String
name|key
range|:
name|EntryTypes
operator|.
name|getAllTypes
argument_list|()
control|)
block|{
name|menu
operator|.
name|add
argument_list|(
operator|new
name|ChangeTypeAction
argument_list|(
name|EntryTypes
operator|.
name|getType
argument_list|(
name|key
argument_list|)
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Bibtex
name|createEntryTypeSection
argument_list|(
name|panel
argument_list|,
name|menu
argument_list|,
literal|"BibTeX Entries"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|menu
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
comment|// ieeetran
name|createEntryTypeSection
argument_list|(
name|panel
argument_list|,
name|menu
argument_list|,
literal|"IEEETran Entries"
argument_list|,
name|IEEETranEntryTypes
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|menu
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
comment|// custom types
name|createEntryTypeSection
argument_list|(
name|panel
argument_list|,
name|menu
argument_list|,
literal|"Custom Entries"
argument_list|,
name|CustomEntryTypesManager
operator|.
name|ALL
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|createEntryTypeSection (BasePanel panel, JMenu menu, String title, java.util.List<EntryType> types)
specifier|private
specifier|static
name|void
name|createEntryTypeSection
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|JMenu
name|menu
parameter_list|,
name|String
name|title
parameter_list|,
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|EntryType
argument_list|>
name|types
parameter_list|)
block|{
comment|// bibtex
name|JMenuItem
name|header
init|=
operator|new
name|JMenuItem
argument_list|(
name|title
argument_list|)
decl_stmt|;
name|Font
name|font
init|=
operator|new
name|Font
argument_list|(
name|menu
operator|.
name|getFont
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|Font
operator|.
name|ITALIC
argument_list|,
name|menu
operator|.
name|getFont
argument_list|()
operator|.
name|getSize
argument_list|()
argument_list|)
decl_stmt|;
name|header
operator|.
name|setFont
argument_list|(
name|font
argument_list|)
expr_stmt|;
name|header
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|header
argument_list|)
expr_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|types
control|)
block|{
name|menu
operator|.
name|add
argument_list|(
operator|new
name|ChangeTypeAction
argument_list|(
name|type
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

