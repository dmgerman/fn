begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.migrations
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|migrations
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
name|javax
operator|.
name|swing
operator|.
name|*
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
name|*
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
name|GUIGlobals
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
name|BrowseAction
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
name|entryeditor
operator|.
name|EntryEditorTabList
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|PostOpenAction
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
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
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|util
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * This class defines the warning that can be offered when opening a pre-2.3  * JabRef file into a later version. This warning mentions the new external file  * link system in this version of JabRef, and offers to:  *  * * upgrade old-style PDF/PS links into the "file" field  * * modify General fields to show "file" instead of "pdf" / "ps"  * * modify table column settings to show "file" instead of "pdf" / "ps"  */
end_comment

begin_class
DECL|class|FileLinksUpgradeWarning
specifier|public
class|class
name|FileLinksUpgradeWarning
implements|implements
name|PostOpenAction
block|{
DECL|field|FIELDS_TO_LOOK_FOR
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|FIELDS_TO_LOOK_FOR
init|=
operator|new
name|String
index|[]
block|{
literal|"pdf"
block|,
literal|"ps"
block|}
decl_stmt|;
comment|/**      * This method should be performed if the major/minor versions recorded in the ParserResult      * are less than or equal to 2.2.      * @param pr      * @return true if the file was written by a jabref version<=2.2      */
annotation|@
name|Override
DECL|method|isActionNecessary (ParserResult pr)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|pr
parameter_list|)
block|{
comment|// First check if this warning is disabled:
if|if
condition|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_FILE_LINKS_UPGRADE_WARNING
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|<
literal|0
condition|)
block|{
return|return
literal|false
return|;
comment|// non-JabRef file
block|}
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|<
literal|2
condition|)
block|{
return|return
literal|true
return|;
comment|// old
block|}
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|>
literal|2
condition|)
block|{
return|return
literal|false
return|;
comment|// wow, did we ever reach version 3?
block|}
return|return
name|pr
operator|.
name|getJabrefMinorVersion
argument_list|()
operator|<=
literal|2
return|;
block|}
comment|/**      * This method presents a dialog box explaining and offering to make the      * changes. If the user confirms, the changes are performed.      * @param panel      * @param pr      */
annotation|@
name|Override
DECL|method|performAction (BasePanel panel, ParserResult pr)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
block|{
comment|// Find out which actions should be offered:
comment|// Only offer to change Preferences if file column is not already visible:
name|boolean
name|offerChangeSettings
init|=
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_COLUMN
argument_list|)
operator|||
operator|!
name|showsFileInGenFields
argument_list|()
decl_stmt|;
comment|// Only offer to upgrade links if the pdf/ps fields are used:
name|boolean
name|offerChangeDatabase
init|=
name|linksFound
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|FileLinksUpgradeWarning
operator|.
name|FIELDS_TO_LOOK_FOR
argument_list|)
decl_stmt|;
comment|// If the "file" directory is not set, offer to migrate pdf/ps dir:
name|boolean
name|offerSetFileDir
init|=
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
operator|&&
operator|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
literal|"pdfDirectory"
argument_list|)
operator|||
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
literal|"psDirectory"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
operator|!
name|offerChangeDatabase
operator|&&
operator|!
name|offerChangeSettings
operator|&&
operator|!
name|offerSetFileDir
condition|)
block|{
return|return;
comment|// Nothing to do, just return.
block|}
name|JCheckBox
name|changeSettings
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change table column and General fields settings to use the new feature"
argument_list|)
argument_list|,
name|offerChangeSettings
argument_list|)
decl_stmt|;
name|JCheckBox
name|changeDatabase
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Upgrade old external file links to use the new feature"
argument_list|)
argument_list|,
name|offerChangeDatabase
argument_list|)
decl_stmt|;
name|JCheckBox
name|setFileDir
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set main external file directory"
argument_list|)
operator|+
literal|":"
argument_list|,
name|offerSetFileDir
argument_list|)
decl_stmt|;
name|JTextField
name|fileDir
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
name|JCheckBox
name|doNotShowDialog
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not show these options in the future"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|JPanel
name|message
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|FormBuilder
name|b
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref"
argument_list|,
literal|"p"
argument_list|)
argument_list|)
decl_stmt|;
comment|// Keep the formatting of these lines. Otherwise, strings have to be translated again.
comment|// See updated JabRef_en.properties modifications by python syncLang.py -s -u
name|int
name|row
init|=
literal|1
decl_stmt|;
name|b
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"This database was written using an older version of JabRef."
argument_list|)
operator|+
literal|"<br>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The current version features a new way of handling links to external files.<br>To take advantage of this, your links must be changed into the new format, and<br>JabRef must be configured to show the new links."
argument_list|)
operator|+
literal|"<p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want JabRef to do the following operations?"
argument_list|)
operator|+
literal|"</html>"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
expr_stmt|;
if|if
condition|(
name|offerChangeSettings
condition|)
block|{
name|b
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|row
operator|+=
literal|2
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|changeSettings
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|offerChangeDatabase
condition|)
block|{
name|b
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|row
operator|+=
literal|2
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|changeDatabase
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|offerSetFileDir
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
literal|"pdfDirectory"
argument_list|)
condition|)
block|{
name|fileDir
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fileDir
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"psDirectory"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|setFileDir
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|fileDir
argument_list|)
expr_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|BrowseAction
operator|.
name|buildForDir
argument_list|(
name|fileDir
argument_list|)
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|b
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|row
operator|+=
literal|2
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|pan
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
name|b
operator|.
name|appendRows
argument_list|(
literal|"6dlu, p"
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|doNotShowDialog
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
operator|+
literal|2
argument_list|)
expr_stmt|;
name|message
operator|.
name|add
argument_list|(
name|b
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Upgrade file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|doNotShowDialog
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_FILE_LINKS_UPGRADE_WARNING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|makeChanges
argument_list|(
name|panel
argument_list|,
name|pr
argument_list|,
name|changeSettings
operator|.
name|isSelected
argument_list|()
argument_list|,
name|changeDatabase
operator|.
name|isSelected
argument_list|()
argument_list|,
name|setFileDir
operator|.
name|isSelected
argument_list|()
condition|?
name|fileDir
operator|.
name|getText
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Check the database to find out whether any of a set of fields are used      * for any of the entries.      * @param database The bib database.      * @param fields The set of fields to look for.      * @return true if at least one of the given fields is set in at least one entry,      *  false otherwise.      */
DECL|method|linksFound (BibtexDatabase database, String[] fields)
specifier|private
name|boolean
name|linksFound
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
index|[]
name|fields
parameter_list|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * This method performs the actual changes.      * @param panel      * @param pr      * @param fileDir The path to the file directory to set, or null if it should not be set.      */
DECL|method|makeChanges (BasePanel panel, ParserResult pr, boolean upgradePrefs, boolean upgradeDatabase, String fileDir)
specifier|private
name|void
name|makeChanges
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|,
name|boolean
name|upgradePrefs
parameter_list|,
name|boolean
name|upgradeDatabase
parameter_list|,
name|String
name|fileDir
parameter_list|)
block|{
if|if
condition|(
name|upgradeDatabase
condition|)
block|{
comment|// Update file links links in the database:
name|NamedCompound
name|ce
init|=
name|Util
operator|.
name|upgradePdfPsToFile
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|FileLinksUpgradeWarning
operator|.
name|FIELDS_TO_LOOK_FOR
argument_list|)
decl_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|fileDir
operator|!=
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|,
name|fileDir
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|upgradePrefs
condition|)
block|{
comment|// Exchange table columns:
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_COLUMN
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_COLUMN
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
comment|// Modify General fields if necessary:
comment|// If we don't find the file field, insert it at the bottom of the first tab:
if|if
condition|(
operator|!
name|showsFileInGenFields
argument_list|()
condition|)
block|{
name|String
name|gfs
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
literal|"0"
argument_list|)
decl_stmt|;
comment|//System.out.println(gfs);
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|gfs
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|gfs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
literal|"0"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|removeCachedEntryEditors
argument_list|()
expr_stmt|;
block|}
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setupAllTables
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|showsFileInGenFields ()
specifier|private
name|boolean
name|showsFileInGenFields
parameter_list|()
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|EntryEditorTabList
name|tabList
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getEntryEditorTabList
argument_list|()
decl_stmt|;
name|outer
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tabList
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|tabList
operator|.
name|getTabFields
argument_list|(
name|i
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
break|break
name|outer
break|;
block|}
block|}
block|}
return|return
name|found
return|;
block|}
block|}
end_class

end_unit

