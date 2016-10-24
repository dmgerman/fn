begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
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
name|awt
operator|.
name|event
operator|.
name|WindowAdapter
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
name|WindowEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
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
name|BorderFactory
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
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingWorker
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
name|gui
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|EntryFetchers
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
name|importer
operator|.
name|FetcherException
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
name|importer
operator|.
name|IdBasedFetcher
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
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
name|BibEntry
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jdesktop
operator|.
name|swingx
operator|.
name|VerticalLayout
import|;
end_import

begin_comment
comment|/**  * Dialog that prompts the user to choose a type for an entry.  * Returns null if canceled.  */
end_comment

begin_class
DECL|class|EntryTypeDialog
specifier|public
class|class
name|EntryTypeDialog
extends|extends
name|JDialog
implements|implements
name|ActionListener
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|EntryTypeDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|type
specifier|private
name|EntryType
name|type
decl_stmt|;
DECL|field|fetcherWorker
specifier|private
name|SwingWorker
argument_list|<
name|Optional
argument_list|<
name|BibEntry
argument_list|>
argument_list|,
name|Void
argument_list|>
name|fetcherWorker
init|=
operator|new
name|FetcherWorker
argument_list|()
decl_stmt|;
DECL|field|generateButton
specifier|private
name|JButton
name|generateButton
decl_stmt|;
DECL|field|idTextField
specifier|private
name|JTextField
name|idTextField
decl_stmt|;
DECL|field|comboBox
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|comboBox
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|COLUMN
specifier|private
specifier|static
specifier|final
name|int
name|COLUMN
init|=
literal|3
decl_stmt|;
DECL|field|biblatexMode
specifier|private
specifier|final
name|boolean
name|biblatexMode
decl_stmt|;
DECL|field|cancelAction
specifier|private
specifier|final
name|CancelAction
name|cancelAction
init|=
operator|new
name|CancelAction
argument_list|()
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|class|TypeButton
specifier|static
class|class
name|TypeButton
extends|extends
name|JButton
implements|implements
name|Comparable
argument_list|<
name|TypeButton
argument_list|>
block|{
DECL|field|type
specifier|private
specifier|final
name|EntryType
name|type
decl_stmt|;
DECL|method|TypeButton (String label, EntryType type)
name|TypeButton
parameter_list|(
name|String
name|label
parameter_list|,
name|EntryType
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compareTo (TypeButton o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|TypeButton
name|o
parameter_list|)
block|{
return|return
name|type
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|type
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getType ()
specifier|public
name|EntryType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
block|}
DECL|method|EntryTypeDialog (JabRefFrame frame)
specifier|public
name|EntryTypeDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
comment|// modal dialog
name|super
argument_list|(
name|frame
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|bibDatabaseContext
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
expr_stmt|;
name|biblatexMode
operator|=
name|bibDatabaseContext
operator|.
name|isBiblatexMode
argument_list|()
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select entry type"
argument_list|)
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|cancelAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|createCancelButtonBarPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|createEntryGroupsPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|createEntryGroupsPanel ()
specifier|private
name|JPanel
name|createEntryGroupsPanel
parameter_list|()
block|{
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|VerticalLayout
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"BibLateX"
argument_list|,
name|EntryTypes
operator|.
name|getAllValues
argument_list|(
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"BibTeX"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ALL
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"IEEETran"
argument_list|,
name|IEEETranEntryTypes
operator|.
name|ALL
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|CustomEntryTypesManager
operator|.
name|ALL
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom"
argument_list|)
argument_list|,
name|CustomEntryTypesManager
operator|.
name|ALL
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|panel
operator|.
name|add
argument_list|(
name|createIdFetcherPanel
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|panel
return|;
block|}
DECL|method|createCancelButtonBarPanel ()
specifier|private
name|JPanel
name|createCancelButtonBarPanel
parameter_list|()
block|{
name|JButton
name|cancel
init|=
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
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Make ESC close dialog, equivalent to clicking Cancel.
name|cancel
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
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
name|cancel
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
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
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
return|return
name|buttons
return|;
block|}
DECL|method|createEntryGroupPanel (String groupTitle, Collection<EntryType> entries)
specifier|private
name|JPanel
name|createEntryGroupPanel
parameter_list|(
name|String
name|groupTitle
parameter_list|,
name|Collection
argument_list|<
name|EntryType
argument_list|>
name|entries
parameter_list|)
block|{
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|bagLayout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
name|bagLayout
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|constraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|constraints
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|constraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|constraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
expr_stmt|;
comment|// column count
name|int
name|col
init|=
literal|0
decl_stmt|;
for|for
control|(
name|EntryType
name|entryType
range|:
name|entries
control|)
block|{
name|TypeButton
name|entryButton
init|=
operator|new
name|TypeButton
argument_list|(
name|entryType
operator|.
name|getName
argument_list|()
argument_list|,
name|entryType
argument_list|)
decl_stmt|;
name|entryButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Check if we should finish the row.
name|col
operator|++
expr_stmt|;
if|if
condition|(
name|col
operator|==
name|EntryTypeDialog
operator|.
name|COLUMN
condition|)
block|{
name|col
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
block|}
else|else
block|{
name|constraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
block|}
name|bagLayout
operator|.
name|setConstraints
argument_list|(
name|entryButton
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|entryButton
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|groupTitle
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|panel
return|;
block|}
DECL|method|createIdFetcherPanel ()
specifier|private
name|JPanel
name|createIdFetcherPanel
parameter_list|()
block|{
name|JLabel
name|fetcherLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"ID type"
argument_list|)
argument_list|)
decl_stmt|;
name|JLabel
name|idLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"ID"
argument_list|)
argument_list|)
decl_stmt|;
name|generateButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate"
argument_list|)
argument_list|)
expr_stmt|;
name|idTextField
operator|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|comboBox
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|EntryFetchers
operator|.
name|getIdFetchers
argument_list|()
operator|.
name|forEach
argument_list|(
name|fetcher
lambda|->
name|comboBox
operator|.
name|addItem
argument_list|(
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|addActionListener
argument_list|(
name|action
lambda|->
block|{
name|fetcherWorker
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|idTextField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|idTextField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|idTextField
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
name|fetcherWorker
operator|.
name|execute
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|jPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|constraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|constraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|GridBagLayout
name|layout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|jPanel
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|constraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|jPanel
operator|.
name|add
argument_list|(
name|fetcherLabel
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|constraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|weightx
operator|=
literal|2
expr_stmt|;
name|jPanel
operator|.
name|add
argument_list|(
name|comboBox
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|constraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|jPanel
operator|.
name|add
argument_list|(
name|idLabel
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|constraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|constraints
operator|.
name|weightx
operator|=
literal|2
expr_stmt|;
name|jPanel
operator|.
name|add
argument_list|(
name|idTextField
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|constraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|constraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|jPanel
operator|.
name|add
argument_list|(
name|generateButton
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|jPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"ID-based_entry_generator"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|idTextField
operator|.
name|requestFocus
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|jPanel
return|;
block|}
DECL|method|stopFetching ()
specifier|private
name|void
name|stopFetching
parameter_list|()
block|{
if|if
condition|(
name|fetcherWorker
operator|.
name|getState
argument_list|()
operator|==
name|SwingWorker
operator|.
name|StateValue
operator|.
name|STARTED
condition|)
block|{
name|fetcherWorker
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|instanceof
name|TypeButton
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|TypeButton
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
name|stopFetching
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|getChoice ()
specifier|public
name|EntryType
name|getChoice
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|class|CancelAction
class|class
name|CancelAction
extends|extends
name|AbstractAction
block|{
DECL|method|CancelAction ()
specifier|public
name|CancelAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Cancel"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|stopFetching
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|FetcherWorker
specifier|private
class|class
name|FetcherWorker
extends|extends
name|SwingWorker
argument_list|<
name|Optional
argument_list|<
name|BibEntry
argument_list|>
argument_list|,
name|Void
argument_list|>
block|{
DECL|field|fetcherException
specifier|private
name|boolean
name|fetcherException
init|=
literal|false
decl_stmt|;
DECL|field|fetcherExceptionMessage
specifier|private
name|String
name|fetcherExceptionMessage
init|=
literal|""
decl_stmt|;
DECL|field|fetcher
specifier|private
name|IdBasedFetcher
name|fetcher
init|=
literal|null
decl_stmt|;
DECL|field|searchID
specifier|private
name|String
name|searchID
init|=
literal|""
decl_stmt|;
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|searchID
operator|=
name|idTextField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|fetcher
operator|=
name|EntryFetchers
operator|.
name|getIdFetchers
argument_list|()
operator|.
name|get
argument_list|(
name|comboBox
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|searchID
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|bibEntry
operator|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
name|searchID
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|fetcherException
operator|=
literal|true
expr_stmt|;
name|fetcherExceptionMessage
operator|=
name|e
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|bibEntry
return|;
block|}
annotation|@
name|Override
DECL|method|done ()
specifier|protected
name|void
name|done
parameter_list|()
block|{
try|try
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|result
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|searchID
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The given search ID was empty."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Empty search ID"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|fetcherException
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Fetcher_'%0'_did_not_find_an_entry_for_id_'%1'."
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|,
name|searchID
argument_list|)
operator|+
literal|"\n"
operator|+
name|fetcherExceptionMessage
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No files found."
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|"."
operator|+
literal|"\n"
operator|+
name|fetcherExceptionMessage
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|fetcherWorker
operator|=
operator|new
name|FetcherWorker
argument_list|()
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|idTextField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|idTextField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|generateButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate"
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
decl||
name|InterruptedException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Exception during fetching when using fetcher '%s' with entry id '%s'."
argument_list|,
name|searchID
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

