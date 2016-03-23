begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.dbproperties
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|dbproperties
package|;
end_package

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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultComboBoxModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComboBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|exporter
operator|.
name|FieldFormatterCleanups
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
name|SaveOrderConfigDisplay
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
name|keyboard
operator|.
name|KeyBinding
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
name|MetaData
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
name|config
operator|.
name|SaveOrderConfig
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
name|ButtonBarBuilder
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
name|Encodings
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 31, 2005  * Time: 10:46:03 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|DatabasePropertiesDialog
specifier|public
class|class
name|DatabasePropertiesDialog
extends|extends
name|JDialog
block|{
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|Charset
argument_list|>
name|encoding
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
name|JButton
name|cancel
decl_stmt|;
DECL|field|fileDir
specifier|private
specifier|final
name|JTextField
name|fileDir
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|fileDirIndv
specifier|private
specifier|final
name|JTextField
name|fileDirIndv
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|oldFileVal
specifier|private
name|String
name|oldFileVal
init|=
literal|""
decl_stmt|;
DECL|field|oldFileIndvVal
specifier|private
name|String
name|oldFileIndvVal
init|=
literal|""
decl_stmt|;
DECL|field|oldSaveOrderConfig
specifier|private
name|SaveOrderConfig
name|oldSaveOrderConfig
decl_stmt|;
DECL|field|defaultSaveOrderConfig
specifier|private
name|SaveOrderConfig
name|defaultSaveOrderConfig
decl_stmt|;
comment|/* The code for "Save sort order" is copied from FileSortTab and slightly updated to fit storing at metadata */
DECL|field|saveInOriginalOrder
specifier|private
name|JRadioButton
name|saveInOriginalOrder
decl_stmt|;
DECL|field|saveInSpecifiedOrder
specifier|private
name|JRadioButton
name|saveInSpecifiedOrder
decl_stmt|;
DECL|field|protect
specifier|private
specifier|final
name|JCheckBox
name|protect
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Refuse to save the database before external changes have been reviewed."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|oldProtectVal
specifier|private
name|boolean
name|oldProtectVal
decl_stmt|;
DECL|field|saveOrderPanel
specifier|private
name|SaveOrderConfigDisplay
name|saveOrderPanel
decl_stmt|;
DECL|field|fieldFormatterCleanupsPanel
specifier|private
name|FieldFormatterCleanupsPanel
name|fieldFormatterCleanupsPanel
decl_stmt|;
DECL|method|DatabasePropertiesDialog (JFrame parent)
specifier|public
name|DatabasePropertiesDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database properties"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|encoding
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|encoding
operator|.
name|setModel
argument_list|(
operator|new
name|DefaultComboBoxModel
argument_list|<>
argument_list|(
name|Encodings
operator|.
name|ENCODINGS
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|init
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
DECL|method|setPanel (BasePanel panel)
specifier|public
name|void
name|setPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
block|}
DECL|method|init (JFrame parent)
specifier|private
name|void
name|init
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|JButton
name|browseFile
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
name|JButton
name|browseFileIndv
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
name|browseFile
operator|.
name|addActionListener
argument_list|(
name|BrowseAction
operator|.
name|buildForDir
argument_list|(
name|parent
argument_list|,
name|fileDir
argument_list|)
argument_list|)
expr_stmt|;
name|browseFileIndv
operator|.
name|addActionListener
argument_list|(
name|BrowseAction
operator|.
name|buildForDir
argument_list|(
name|parent
argument_list|,
name|fileDirIndv
argument_list|)
argument_list|)
expr_stmt|;
name|setupSortOrderConfiguration
argument_list|()
expr_stmt|;
name|FormLayout
name|form
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, pref:grow, 4dlu, pref:grow, 4dlu, pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 160dlu, pref,"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|form
argument_list|)
decl_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database encoding"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|encoding
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Override default file directories"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"General file directory"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileDir
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseFile
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"User-specific file directory"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileDirIndv
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseFileIndv
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save sort order"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|13
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveInOriginalOrder
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|15
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveInSpecifiedOrder
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|17
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|saveOrderPanel
operator|=
operator|new
name|SaveOrderConfigDisplay
argument_list|()
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveOrderPanel
operator|.
name|getPanel
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|21
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database protection"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|23
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|protect
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|25
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|fieldFormatterCleanupsPanel
operator|=
operator|new
name|FieldFormatterCleanupsPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enable save actions"
argument_list|)
argument_list|,
name|FieldFormatterCleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save actions"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|27
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fieldFormatterCleanupsPanel
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|29
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|AbstractAction
name|closeAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ActionMap
name|am
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|storeSettings
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setupSortOrderConfiguration ()
specifier|private
name|void
name|setupSortOrderConfiguration
parameter_list|()
block|{
name|saveInOriginalOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|saveInSpecifiedOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries ordered as specified"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveInOriginalOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveInSpecifiedOrder
argument_list|)
expr_stmt|;
name|ActionListener
name|listener
init|=
name|e
lambda|->
block|{
name|boolean
name|selected
init|=
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|saveInSpecifiedOrder
decl_stmt|;
name|saveOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
block|}
decl_stmt|;
name|saveInOriginalOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|saveInSpecifiedOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
block|{
name|setValues
argument_list|()
expr_stmt|;
block|}
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|private
name|void
name|setValues
parameter_list|()
block|{
name|encoding
operator|.
name|setSelectedItem
argument_list|(
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
name|defaultSaveOrderConfig
operator|=
operator|new
name|SaveOrderConfig
argument_list|()
expr_stmt|;
name|defaultSaveOrderConfig
operator|.
name|setSaveInOriginalOrder
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|storedSaveOrderConfig
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|MetaData
operator|.
name|SAVE_ORDER_CONFIG
argument_list|)
decl_stmt|;
name|boolean
name|selected
decl_stmt|;
if|if
condition|(
name|storedSaveOrderConfig
operator|==
literal|null
condition|)
block|{
name|saveInOriginalOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|oldSaveOrderConfig
operator|=
literal|null
expr_stmt|;
name|selected
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|SaveOrderConfig
name|saveOrderConfig
decl_stmt|;
name|saveOrderConfig
operator|=
operator|new
name|SaveOrderConfig
argument_list|(
name|storedSaveOrderConfig
argument_list|)
expr_stmt|;
name|oldSaveOrderConfig
operator|=
name|saveOrderConfig
expr_stmt|;
if|if
condition|(
name|saveOrderConfig
operator|.
name|saveInOriginalOrder
condition|)
block|{
name|saveInOriginalOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|selected
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|saveInSpecifiedOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|selected
operator|=
literal|true
expr_stmt|;
block|}
name|saveOrderPanel
operator|.
name|setSaveOrderConfig
argument_list|(
name|saveOrderConfig
argument_list|)
expr_stmt|;
block|}
name|saveOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fileD
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileD
operator|==
literal|null
condition|)
block|{
name|fileDir
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
operator|!
operator|(
name|fileD
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|fileDir
operator|.
name|setText
argument_list|(
operator|(
name|fileD
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|String
argument_list|>
name|fileDI
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR_INDIVIDUAL
argument_list|)
argument_list|)
decl_stmt|;
comment|// File dir setting
name|List
argument_list|<
name|String
argument_list|>
name|fileDIL
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR_IND_LEGACY
argument_list|)
argument_list|)
decl_stmt|;
comment|// Legacy file dir setting for backward comp.
if|if
condition|(
name|fileDI
operator|==
literal|null
condition|)
block|{
name|oldFileIndvVal
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
expr_stmt|;
comment|// Record individual file dir setting as originally empty if reading from legacy setting
if|if
condition|(
name|fileDIL
operator|==
literal|null
condition|)
block|{
name|fileDirIndv
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Insert path from legacy setting if possible
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
operator|!
operator|(
name|fileDIL
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|fileDirIndv
operator|.
name|setText
argument_list|(
operator|(
name|fileDIL
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
operator|!
operator|(
name|fileDI
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|fileDirIndv
operator|.
name|setText
argument_list|(
operator|(
name|fileDI
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|oldFileIndvVal
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
expr_stmt|;
comment|// Record individual file dir setting normally if reading from ordinary setting
block|}
name|List
argument_list|<
name|String
argument_list|>
name|prot
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|)
decl_stmt|;
if|if
condition|(
name|prot
operator|==
literal|null
condition|)
block|{
name|protect
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
operator|(
name|prot
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|protect
operator|.
name|setSelected
argument_list|(
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|prot
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Store original values to see if they get changed:
name|oldFileVal
operator|=
name|fileDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldProtectVal
operator|=
name|protect
operator|.
name|isSelected
argument_list|()
expr_stmt|;
comment|//set save actions
name|fieldFormatterCleanupsPanel
operator|.
name|setValues
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
block|{
name|Charset
name|oldEncoding
init|=
name|panel
operator|.
name|getEncoding
argument_list|()
decl_stmt|;
name|Charset
name|newEncoding
init|=
operator|(
name|Charset
operator|)
name|encoding
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setEncoding
argument_list|(
name|newEncoding
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|fileDir
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Repeat for individual file dir - reuse 'text' and 'dir' vars
name|text
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR_INDIVIDUAL
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR_INDIVIDUAL
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|protect
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"true"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|)
expr_stmt|;
block|}
name|SaveOrderConfig
name|newSaveOrderConfig
init|=
name|saveOrderPanel
operator|.
name|getSaveOrderConfig
argument_list|()
decl_stmt|;
if|if
condition|(
name|saveInOriginalOrder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|newSaveOrderConfig
operator|.
name|setSaveInOriginalOrder
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|newSaveOrderConfig
operator|.
name|setSaveInSpecifiedOrder
argument_list|()
expr_stmt|;
block|}
comment|// See if any of the values have been modified:
name|boolean
name|saveOrderConfigChanged
decl_stmt|;
if|if
condition|(
name|newSaveOrderConfig
operator|.
name|equals
argument_list|(
name|oldSaveOrderConfig
argument_list|)
condition|)
block|{
name|saveOrderConfigChanged
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|saveOrderConfigChanged
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|saveOrderConfigChanged
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|serialized
init|=
name|newSaveOrderConfig
operator|.
name|getConfigurationList
argument_list|()
decl_stmt|;
if|if
condition|(
name|newSaveOrderConfig
operator|.
name|equals
argument_list|(
name|defaultSaveOrderConfig
argument_list|)
condition|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|MetaData
operator|.
name|SAVE_ORDER_CONFIG
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|putData
argument_list|(
name|MetaData
operator|.
name|SAVE_ORDER_CONFIG
argument_list|,
name|serialized
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|saveActionsChanged
init|=
name|fieldFormatterCleanupsPanel
operator|.
name|hasChanged
argument_list|()
decl_stmt|;
if|if
condition|(
name|saveActionsChanged
condition|)
block|{
if|if
condition|(
name|fieldFormatterCleanupsPanel
operator|.
name|isDefaultSaveActions
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|MetaData
operator|.
name|SAVE_ACTIONS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fieldFormatterCleanupsPanel
operator|.
name|storeSettings
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|changed
init|=
name|saveOrderConfigChanged
operator|||
operator|!
name|newEncoding
operator|.
name|equals
argument_list|(
name|oldEncoding
argument_list|)
operator|||
operator|!
name|oldFileVal
operator|.
name|equals
argument_list|(
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldFileIndvVal
operator|.
name|equals
argument_list|(
name|fileDirIndv
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|(
name|oldProtectVal
operator|!=
name|protect
operator|.
name|isSelected
argument_list|()
operator|)
operator|||
name|saveActionsChanged
decl_stmt|;
comment|// ... if so, mark base changed. Prevent the Undo button from removing
comment|// change marking:
if|if
condition|(
name|changed
condition|)
block|{
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

