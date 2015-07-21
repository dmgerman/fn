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
name|util
operator|.
name|Collections
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
DECL|class|KeyBinds
specifier|public
class|class
name|KeyBinds
block|{
DECL|field|keyBindMap
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|keyBindMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|KeyBinds ()
specifier|public
name|KeyBinds
parameter_list|()
block|{
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Push to application"
argument_list|,
literal|"ctrl L"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Push to LyX"
argument_list|,
literal|"ctrl L"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Push to WinEdt"
argument_list|,
literal|"ctrl shift W"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Quit JabRef"
argument_list|,
literal|"ctrl Q"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open database"
argument_list|,
literal|"ctrl O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Save database"
argument_list|,
literal|"ctrl S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Save database as ..."
argument_list|,
literal|"ctrl shift S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Save all"
argument_list|,
literal|"ctrl alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Close database"
argument_list|,
literal|"ctrl W"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New entry"
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Cut"
argument_list|,
literal|"ctrl X"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Copy"
argument_list|,
literal|"ctrl C"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Paste"
argument_list|,
literal|"ctrl V"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Undo"
argument_list|,
literal|"ctrl Z"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Redo"
argument_list|,
literal|"ctrl Y"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Help"
argument_list|,
literal|"F1"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New article"
argument_list|,
literal|"ctrl shift A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New book"
argument_list|,
literal|"ctrl shift B"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New phdthesis"
argument_list|,
literal|"ctrl shift T"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New inbook"
argument_list|,
literal|"ctrl shift I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New mastersthesis"
argument_list|,
literal|"ctrl shift M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New proceedings"
argument_list|,
literal|"ctrl shift P"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New unpublished"
argument_list|,
literal|"ctrl shift U"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Edit strings"
argument_list|,
literal|"ctrl T"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Edit preamble"
argument_list|,
literal|"ctrl P"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Select all"
argument_list|,
literal|"ctrl A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Toggle groups interface"
argument_list|,
literal|"ctrl shift G"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Autogenerate BibTeX keys"
argument_list|,
literal|"ctrl G"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search"
argument_list|,
literal|"ctrl F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Incremental search"
argument_list|,
literal|"ctrl shift F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Repeat incremental search"
argument_list|,
literal|"ctrl shift F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Close dialog"
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Close entry editor"
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Close preamble editor"
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Back, help dialog"
argument_list|,
literal|"LEFT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Forward, help dialog"
argument_list|,
literal|"RIGHT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Preamble editor, store changes"
argument_list|,
literal|"alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Clear search"
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, next panel"
argument_list|,
literal|"ctrl TAB"
argument_list|)
expr_stmt|;
comment|//"ctrl PLUS");//"shift Right");
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, previous panel"
argument_list|,
literal|"ctrl shift TAB"
argument_list|)
expr_stmt|;
comment|//"ctrl MINUS");
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, next panel 2"
argument_list|,
literal|"ctrl PLUS"
argument_list|)
expr_stmt|;
comment|//"ctrl PLUS");//"shift Right");
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, previous panel 2"
argument_list|,
literal|"ctrl MINUS"
argument_list|)
expr_stmt|;
comment|//"ctrl MINUS");
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, next entry"
argument_list|,
literal|"ctrl shift DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, previous entry"
argument_list|,
literal|"ctrl shift UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Entry editor, store field"
argument_list|,
literal|"alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"String dialog, add string"
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"String dialog, remove string"
argument_list|,
literal|"shift DELETE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"String dialog, move string up"
argument_list|,
literal|"ctrl UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"String dialog, move string down"
argument_list|,
literal|"ctrl DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Save session"
argument_list|,
literal|"F11"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Load session"
argument_list|,
literal|"F12"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|,
literal|"ctrl K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Copy BibTeX key"
argument_list|,
literal|"ctrl shift K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Copy BibTeX key and title"
argument_list|,
literal|"ctrl shift alt K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Next tab"
argument_list|,
literal|"ctrl PAGE_DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Previous tab"
argument_list|,
literal|"ctrl PAGE_UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Replace string"
argument_list|,
literal|"ctrl R"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Delete"
argument_list|,
literal|"DELETE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open file"
argument_list|,
literal|"F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open folder"
argument_list|,
literal|"ctrl shift O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open PDF or PS"
argument_list|,
literal|"shift F5"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open URL or DOI"
argument_list|,
literal|"F3"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Open SPIRES entry"
argument_list|,
literal|"ctrl F3"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Toggle entry preview"
argument_list|,
literal|"ctrl F9"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Switch preview layout"
argument_list|,
literal|"F9"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Edit entry"
argument_list|,
literal|"ctrl E"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Mark entries"
argument_list|,
literal|"ctrl M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Unmark entries"
argument_list|,
literal|"ctrl shift M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Fetch Medline"
argument_list|,
literal|"F5"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search ScienceDirect"
argument_list|,
literal|"ctrl F5"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search ADS"
argument_list|,
literal|"ctrl shift F6"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New from plain text"
argument_list|,
literal|"ctrl shift N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Synchronize files"
argument_list|,
literal|"ctrl F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Synchronize PDF"
argument_list|,
literal|"shift F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Synchronize PS"
argument_list|,
literal|"ctrl shift F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Focus entry table"
argument_list|,
literal|"ctrl shift E"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Abbreviate"
argument_list|,
literal|"ctrl alt A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Unabbreviate"
argument_list|,
literal|"ctrl alt shift A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search IEEEXplore"
argument_list|,
literal|"alt F8"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search ACM Portal"
argument_list|,
literal|"ctrl shift F8"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Fetch ArXiv.org"
argument_list|,
literal|"shift F8"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Search JSTOR"
argument_list|,
literal|"shift F9"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Cleanup"
argument_list|,
literal|"ctrl shift F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Write XMP"
argument_list|,
literal|"ctrl F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"New file link"
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Fetch SPIRES"
argument_list|,
literal|"ctrl F8"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Fetch INSPIRE"
argument_list|,
literal|"ctrl F2"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Back"
argument_list|,
literal|"alt LEFT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Forward"
argument_list|,
literal|"alt RIGHT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Import into current database"
argument_list|,
literal|"ctrl I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Import into new database"
argument_list|,
literal|"ctrl alt I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FindUnlinkedFilesDialog
operator|.
name|ACTION_KEYBINDING_ACTION
argument_list|,
literal|"shift F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Increase table font size"
argument_list|,
literal|"ctrl PLUS"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Decrease table font size"
argument_list|,
literal|"ctrl MINUS"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Automatically link files"
argument_list|,
literal|"alt F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Resolve duplicate BibTeX keys"
argument_list|,
literal|"ctrl shift D"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Refresh OO"
argument_list|,
literal|"ctrl alt O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"File list editor, move entry up"
argument_list|,
literal|"ctrl UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"File list editor, move entry down"
argument_list|,
literal|"ctrl DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Minimize to system tray"
argument_list|,
literal|"ctrl alt W"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
literal|"Hide/show toolbar"
argument_list|,
literal|"ctrl alt T"
argument_list|)
expr_stmt|;
block|}
DECL|method|get (String key)
specifier|public
name|String
name|get
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|keyBindMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
DECL|method|put (String key, String value)
specifier|public
name|void
name|put
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|keyBindMap
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|getKeyBindings ()
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getKeyBindings
parameter_list|()
block|{
return|return
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|keyBindMap
argument_list|)
argument_list|)
return|;
block|}
DECL|method|overwriteBindings (Map<String, String> newBindings)
specifier|public
name|void
name|overwriteBindings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|newBindings
parameter_list|)
block|{
name|keyBindMap
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keyBindMap
operator|.
name|putAll
argument_list|(
name|newBindings
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

