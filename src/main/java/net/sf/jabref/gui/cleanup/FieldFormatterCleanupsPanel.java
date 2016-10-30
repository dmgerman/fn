begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|cleanup
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
name|MouseEvent
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
name|MouseMotionAdapter
import|;
end_import

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
name|Collections
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
name|Objects
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListCellRenderer
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
name|JList
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
name|JTextArea
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UIManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataListener
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
name|logic
operator|.
name|cleanup
operator|.
name|Cleanups
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|cleanup
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
name|model
operator|.
name|cleanup
operator|.
name|Formatter
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
name|InternalBibtexFields
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
name|metadata
operator|.
name|MetaData
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

begin_class
DECL|class|FieldFormatterCleanupsPanel
specifier|public
class|class
name|FieldFormatterCleanupsPanel
extends|extends
name|JPanel
block|{
DECL|field|DESCRIPTION
specifier|private
specifier|static
specifier|final
name|String
name|DESCRIPTION
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Description"
argument_list|)
operator|+
literal|": "
decl_stmt|;
DECL|field|cleanupEnabled
specifier|private
specifier|final
name|JCheckBox
name|cleanupEnabled
decl_stmt|;
DECL|field|fieldFormatterCleanups
specifier|private
name|FieldFormatterCleanups
name|fieldFormatterCleanups
decl_stmt|;
DECL|field|actionsList
specifier|private
name|JList
argument_list|<
name|?
argument_list|>
name|actionsList
decl_stmt|;
DECL|field|formattersCombobox
specifier|private
name|JComboBox
argument_list|<
name|?
argument_list|>
name|formattersCombobox
decl_stmt|;
DECL|field|selectFieldCombobox
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|selectFieldCombobox
decl_stmt|;
DECL|field|addButton
specifier|private
name|JButton
name|addButton
decl_stmt|;
DECL|field|descriptionAreaText
specifier|private
name|JTextArea
name|descriptionAreaText
decl_stmt|;
DECL|field|removeButton
specifier|private
name|JButton
name|removeButton
decl_stmt|;
DECL|field|resetButton
specifier|private
name|JButton
name|resetButton
decl_stmt|;
DECL|field|recommendButton
specifier|private
name|JButton
name|recommendButton
decl_stmt|;
DECL|field|defaultFormatters
specifier|private
specifier|final
name|FieldFormatterCleanups
name|defaultFormatters
decl_stmt|;
DECL|method|FieldFormatterCleanupsPanel (String description, FieldFormatterCleanups defaultFormatters)
specifier|public
name|FieldFormatterCleanupsPanel
parameter_list|(
name|String
name|description
parameter_list|,
name|FieldFormatterCleanups
name|defaultFormatters
parameter_list|)
block|{
name|this
operator|.
name|defaultFormatters
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|cleanupEnabled
operator|=
operator|new
name|JCheckBox
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (MetaData metaData)
specifier|public
name|void
name|setValues
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|saveActions
init|=
name|metaData
operator|.
name|getSaveActions
argument_list|()
decl_stmt|;
name|setValues
argument_list|(
name|saveActions
operator|.
name|orElse
argument_list|(
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (FieldFormatterCleanups formatterCleanups)
specifier|public
name|void
name|setValues
parameter_list|(
name|FieldFormatterCleanups
name|formatterCleanups
parameter_list|)
block|{
name|fieldFormatterCleanups
operator|=
name|formatterCleanups
expr_stmt|;
comment|// first clear existing content
name|this
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|configuredActions
init|=
name|fieldFormatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
comment|//The copy is necessary because the original List is unmodifiable
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionsToDisplay
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|configuredActions
argument_list|)
decl_stmt|;
name|buildLayout
argument_list|(
name|actionsToDisplay
argument_list|)
expr_stmt|;
block|}
DECL|method|buildLayout (List<FieldFormatterCleanup> actionsToDisplay)
specifier|private
name|void
name|buildLayout
parameter_list|(
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionsToDisplay
parameter_list|)
block|{
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
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 13dlu, left:pref:grow, 4dlu, pref, 4dlu, pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 4dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, fill:pref:grow, 2dlu"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanupEnabled
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|actionsList
operator|=
operator|new
name|JList
argument_list|<>
argument_list|(
operator|new
name|CleanupActionsListModel
argument_list|(
name|actionsToDisplay
argument_list|)
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|addMouseMotionListener
argument_list|(
operator|new
name|MouseMotionAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseMoved
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|super
operator|.
name|mouseMoved
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|CleanupActionsListModel
name|m
init|=
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|actionsList
operator|.
name|locationToIndex
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
condition|)
block|{
name|actionsList
operator|.
name|setToolTipText
argument_list|(
name|m
operator|.
name|getElementAt
argument_list|(
name|index
argument_list|)
operator|.
name|getFormatter
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|getModel
argument_list|()
operator|.
name|addListDataListener
argument_list|(
operator|new
name|ListDataListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|intervalRemoved
parameter_list|(
name|ListDataEvent
name|e
parameter_list|)
block|{
comment|//index0 is sufficient, because of SingleSelection
if|if
condition|(
name|e
operator|.
name|getIndex0
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|//when an item gets deleted, the next one becomes the new 0
name|actionsList
operator|.
name|setSelectedIndex
argument_list|(
name|e
operator|.
name|getIndex0
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getIndex0
argument_list|()
operator|>
literal|0
condition|)
block|{
name|actionsList
operator|.
name|setSelectedIndex
argument_list|(
name|e
operator|.
name|getIndex0
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|intervalAdded
parameter_list|(
name|ListDataEvent
name|e
parameter_list|)
block|{
comment|//empty, not needed
block|}
annotation|@
name|Override
specifier|public
name|void
name|contentsChanged
parameter_list|(
name|ListDataEvent
name|e
parameter_list|)
block|{
comment|//empty, not needed
block|}
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|actionsList
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|resetButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset"
argument_list|)
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|reset
argument_list|(
name|defaultFormatters
argument_list|)
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|databaseContext
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getDatabaseContext
argument_list|()
decl_stmt|;
name|recommendButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Recommended for %0"
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|isBibLaTeX
init|=
name|databaseContext
operator|.
name|isBiblatexMode
argument_list|()
decl_stmt|;
name|recommendButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|isBibLaTeX
condition|)
block|{
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|reset
argument_list|(
name|Cleanups
operator|.
name|RECOMMEND_BIBLATEX_ACTIONS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|reset
argument_list|(
name|Cleanups
operator|.
name|RECOMMEND_BIBTEX_ACTIONS
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|removeButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove selected"
argument_list|)
argument_list|)
expr_stmt|;
name|removeButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|removeAtIndex
argument_list|(
name|actionsList
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|removeButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|resetButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|recommendButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|getSelectorPanel
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|15
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|makeDescriptionTextAreaLikeJLabel
argument_list|()
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|descriptionAreaText
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|17
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|this
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
name|WEST
argument_list|)
expr_stmt|;
name|updateDescription
argument_list|()
expr_stmt|;
comment|// make sure the layout is set according to the checkbox
name|cleanupEnabled
operator|.
name|addActionListener
argument_list|(
operator|new
name|EnablementStatusListener
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|cleanupEnabled
operator|.
name|setSelected
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create a TextArea that looks and behaves like a JLabel. Has the advantage of supporting multine and wordwrap      */
DECL|method|makeDescriptionTextAreaLikeJLabel ()
specifier|private
name|void
name|makeDescriptionTextAreaLikeJLabel
parameter_list|()
block|{
name|descriptionAreaText
operator|=
operator|new
name|JTextArea
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setWrapStyleWord
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setColumns
argument_list|(
literal|6
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setFocusable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setCursor
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setFont
argument_list|(
name|UIManager
operator|.
name|getFont
argument_list|(
literal|"Label.font"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|updateDescription ()
specifier|private
name|void
name|updateDescription
parameter_list|()
block|{
name|FieldFormatterCleanup
name|formatterCleanup
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
name|formatterCleanup
operator|!=
literal|null
condition|)
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|formatterCleanup
operator|.
name|getFormatter
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Formatter
name|selectedFormatter
init|=
name|getFieldFormatter
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedFormatter
operator|!=
literal|null
condition|)
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|selectedFormatter
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * This panel contains the two comboboxes and the Add button      * @return Returns the created JPanel      */
DECL|method|getSelectorPanel ()
specifier|private
name|JPanel
name|getSelectorPanel
parameter_list|()
block|{
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
operator|new
name|FormLayout
argument_list|(
literal|"left:pref:grow, 4dlu, left:pref:grow, 4dlu, pref:grow, 4dlu, right:pref"
argument_list|,
literal|"pref, 2dlu, pref:grow, 2dlu"
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
name|InternalBibtexFields
operator|.
name|getAllPublicAndInteralFieldNames
argument_list|()
decl_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|String
index|[]
name|allPlusKey
init|=
name|fieldNames
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fieldNames
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|selectFieldCombobox
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|selectFieldCombobox
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatterNames
init|=
name|Cleanups
operator|.
name|getAvailableFormatters
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Formatter
operator|::
name|getName
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
name|List
argument_list|<
name|String
argument_list|>
name|formatterDescriptions
init|=
name|Cleanups
operator|.
name|getAvailableFormatters
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Formatter
operator|::
name|getDescription
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
name|formattersCombobox
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|formatterNames
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|setRenderer
argument_list|(
operator|new
name|DefaultListCellRenderer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
argument_list|<
name|?
argument_list|>
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|index
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|cellHasFocus
parameter_list|)
block|{
if|if
condition|(
operator|(
operator|-
literal|1
operator|<
name|index
operator|)
operator|&&
operator|(
name|index
operator|<
name|formatterDescriptions
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
name|value
operator|!=
literal|null
operator|)
condition|)
block|{
name|setToolTipText
argument_list|(
name|formatterDescriptions
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|super
operator|.
name|getListCellRendererComponent
argument_list|(
name|list
argument_list|,
name|value
argument_list|,
name|index
argument_list|,
name|isSelected
argument_list|,
name|cellHasFocus
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|addItemListener
argument_list|(
name|e
lambda|->
name|updateDescription
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|formattersCombobox
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|addButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|FieldFormatterCleanup
name|newAction
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
name|newAction
operator|==
literal|null
condition|)
block|{
return|return;
block|}
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|addCleanupAction
argument_list|(
name|newAction
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|addButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return
name|builder
operator|.
name|getPanel
argument_list|()
return|;
block|}
DECL|method|storeSettings (MetaData metaData)
specifier|public
name|void
name|storeSettings
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|FieldFormatterCleanups
name|formatterCleanups
init|=
name|getFormatterCleanups
argument_list|()
decl_stmt|;
comment|// if all actions have been removed, remove the save actions from the MetaData
if|if
condition|(
name|formatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearSaveActions
argument_list|()
expr_stmt|;
return|return;
block|}
name|metaData
operator|.
name|setSaveActions
argument_list|(
name|formatterCleanups
argument_list|)
expr_stmt|;
block|}
DECL|method|getFormatterCleanups ()
specifier|public
name|FieldFormatterCleanups
name|getFormatterCleanups
parameter_list|()
block|{
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
init|=
operator|(
operator|(
name|CleanupActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getAllActions
argument_list|()
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanups
argument_list|(
name|cleanupEnabled
operator|.
name|isSelected
argument_list|()
argument_list|,
name|actions
argument_list|)
return|;
block|}
DECL|method|hasChanged ()
specifier|public
name|boolean
name|hasChanged
parameter_list|()
block|{
return|return
operator|!
name|fieldFormatterCleanups
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isDefaultSaveActions ()
specifier|public
name|boolean
name|isDefaultSaveActions
parameter_list|()
block|{
return|return
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getFieldFormatterCleanup ()
specifier|private
name|FieldFormatterCleanup
name|getFieldFormatterCleanup
parameter_list|()
block|{
name|Formatter
name|selectedFormatter
init|=
name|getFieldFormatter
argument_list|()
decl_stmt|;
name|String
name|fieldKey
init|=
name|selectFieldCombobox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanup
argument_list|(
name|fieldKey
argument_list|,
name|selectedFormatter
argument_list|)
return|;
block|}
DECL|method|getFieldFormatter ()
specifier|private
name|Formatter
name|getFieldFormatter
parameter_list|()
block|{
name|Formatter
name|selectedFormatter
init|=
literal|null
decl_stmt|;
name|String
name|selectedFormatterName
init|=
name|formattersCombobox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
for|for
control|(
name|Formatter
name|formatter
range|:
name|Cleanups
operator|.
name|getAvailableFormatters
argument_list|()
control|)
block|{
if|if
condition|(
name|formatter
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|selectedFormatterName
argument_list|)
condition|)
block|{
name|selectedFormatter
operator|=
name|formatter
expr_stmt|;
break|break;
block|}
block|}
return|return
name|selectedFormatter
return|;
block|}
DECL|class|EnablementStatusListener
class|class
name|EnablementStatusListener
implements|implements
name|ActionListener
block|{
DECL|method|EnablementStatusListener (boolean initialStatus)
specifier|public
name|EnablementStatusListener
parameter_list|(
name|boolean
name|initialStatus
parameter_list|)
block|{
name|setStatus
argument_list|(
name|initialStatus
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
name|boolean
name|enablementStatus
init|=
name|cleanupEnabled
operator|.
name|isSelected
argument_list|()
decl_stmt|;
name|setStatus
argument_list|(
name|enablementStatus
argument_list|)
expr_stmt|;
block|}
DECL|method|setStatus (boolean status)
specifier|private
name|void
name|setStatus
parameter_list|(
name|boolean
name|status
parameter_list|)
block|{
name|actionsList
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|removeButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|recommendButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

