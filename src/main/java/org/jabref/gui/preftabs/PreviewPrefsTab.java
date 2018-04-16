begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preftabs
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
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
name|Dimension
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|BoxLayout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListModel
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
name|JList
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
name|JScrollPane
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
name|SwingWorker
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|PreviewPanel
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
name|customjfx
operator|.
name|CustomJFXPanel
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
name|citationstyle
operator|.
name|CitationStyle
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
name|util
operator|.
name|TestEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|PreviewPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|primitives
operator|.
name|Ints
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
name|factories
operator|.
name|Paddings
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|PreviewPrefsTab
specifier|public
class|class
name|PreviewPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PreviewPrefsTab
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|discoverCitationStyleWorker
specifier|private
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|CitationStyle
argument_list|>
argument_list|,
name|Void
argument_list|>
name|discoverCitationStyleWorker
decl_stmt|;
DECL|field|availableModel
specifier|private
specifier|final
name|DefaultListModel
argument_list|<
name|Object
argument_list|>
name|availableModel
init|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|chosenModel
specifier|private
specifier|final
name|DefaultListModel
argument_list|<
name|Object
argument_list|>
name|chosenModel
init|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|available
specifier|private
specifier|final
name|JList
argument_list|<
name|Object
argument_list|>
name|available
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|availableModel
argument_list|)
decl_stmt|;
DECL|field|chosen
specifier|private
specifier|final
name|JList
argument_list|<
name|Object
argument_list|>
name|chosen
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|chosenModel
argument_list|)
decl_stmt|;
DECL|field|btnRight
specifier|private
specifier|final
name|JButton
name|btnRight
init|=
operator|new
name|JButton
argument_list|(
literal|"Â»"
argument_list|)
decl_stmt|;
DECL|field|btnLeft
specifier|private
specifier|final
name|JButton
name|btnLeft
init|=
operator|new
name|JButton
argument_list|(
literal|"Â«"
argument_list|)
decl_stmt|;
DECL|field|btnUp
specifier|private
specifier|final
name|JButton
name|btnUp
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Up"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|btnDown
specifier|private
specifier|final
name|JButton
name|btnDown
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Down"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|layout
specifier|private
specifier|final
name|JTextArea
name|layout
init|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
DECL|field|btnTest
specifier|private
specifier|final
name|JButton
name|btnTest
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|btnDefault
specifier|private
specifier|final
name|JButton
name|btnDefault
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|layout
argument_list|)
decl_stmt|;
DECL|method|PreviewPrefsTab ()
specifier|public
name|PreviewPrefsTab
parameter_list|()
block|{
name|setupLogic
argument_list|()
expr_stmt|;
name|setupGui
argument_list|()
expr_stmt|;
block|}
DECL|method|setupLogic ()
specifier|private
name|void
name|setupLogic
parameter_list|()
block|{
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|addListSelectionListener
argument_list|(
name|event
lambda|->
block|{
name|boolean
name|selectionEmpty
init|=
operator|(
operator|(
name|ListSelectionModel
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|isSelectionEmpty
argument_list|()
decl_stmt|;
name|btnLeft
operator|.
name|setEnabled
argument_list|(
operator|!
name|selectionEmpty
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|setEnabled
argument_list|(
operator|!
name|selectionEmpty
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|setEnabled
argument_list|(
operator|!
name|selectionEmpty
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|available
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|addListSelectionListener
argument_list|(
name|e
lambda|->
name|btnRight
operator|.
name|setEnabled
argument_list|(
operator|!
operator|(
operator|(
name|ListSelectionModel
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|isSelectionEmpty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|btnRight
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Object
name|object
range|:
name|available
operator|.
name|getSelectedValuesList
argument_list|()
control|)
block|{
name|availableModel
operator|.
name|removeElement
argument_list|(
name|object
argument_list|)
expr_stmt|;
name|chosenModel
operator|.
name|addElement
argument_list|(
name|object
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|btnLeft
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Object
name|object
range|:
name|chosen
operator|.
name|getSelectedValuesList
argument_list|()
control|)
block|{
name|availableModel
operator|.
name|addElement
argument_list|(
name|object
argument_list|)
expr_stmt|;
name|chosenModel
operator|.
name|removeElement
argument_list|(
name|object
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newSelectedIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|oldIndex
range|:
name|chosen
operator|.
name|getSelectedIndices
argument_list|()
control|)
block|{
name|boolean
name|alreadyTaken
init|=
name|newSelectedIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|-
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
name|oldIndex
operator|>
literal|0
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|-
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenModel
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|newSelectedIndices
operator|.
name|add
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
name|chosen
operator|.
name|setSelectedIndices
argument_list|(
name|Ints
operator|.
name|toArray
argument_list|(
name|newSelectedIndices
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newSelectedIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|int
index|[]
name|selectedIndices
init|=
name|chosen
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|selectedIndices
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|int
name|oldIndex
init|=
name|selectedIndices
index|[
name|i
index|]
decl_stmt|;
name|boolean
name|alreadyTaken
init|=
name|newSelectedIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
name|oldIndex
operator|<
name|chosenModel
operator|.
name|getSize
argument_list|()
operator|-
literal|1
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|+
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenModel
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|newSelectedIndices
operator|.
name|add
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
name|chosen
operator|.
name|setSelectedIndices
argument_list|(
name|Ints
operator|.
name|toArray
argument_list|(
name|newSelectedIndices
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnDefault
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
name|layout
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getPreviewStyleDefault
argument_list|()
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|btnTest
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
try|try
block|{
name|PreviewPanel
name|testPane
init|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|testPane
operator|.
name|setFixedLayout
argument_list|(
name|layout
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|testPane
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|)
expr_stmt|;
name|JFXPanel
name|container
init|=
name|CustomJFXPanel
operator|.
name|wrap
argument_list|(
operator|new
name|Scene
argument_list|(
name|testPane
argument_list|)
argument_list|)
decl_stmt|;
name|container
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|350
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreviewPrefsTab
operator|.
name|this
argument_list|,
name|container
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|PLAIN_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Parsing error."
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"illegal backslash expression"
argument_list|)
operator|+
literal|".\n"
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setupGui ()
specifier|private
name|void
name|setupGui
parameter_list|()
block|{
name|JPanel
name|chooseStyle
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"0:grow, $lcgap, pref, $lcgap, 0:grow"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, $lg, fill:pref:grow, $lg, pref:grow, $lg, pref:grow, $lg, pref:grow"
argument_list|)
operator|.
name|padding
argument_list|(
name|Paddings
operator|.
name|DIALOG
argument_list|)
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current Preview"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
name|available
argument_list|)
operator|.
name|xywh
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
operator|.
name|add
argument_list|(
name|chosen
argument_list|)
operator|.
name|xywh
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
operator|.
name|add
argument_list|(
name|btnRight
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|,
literal|"fill, bottom"
argument_list|)
operator|.
name|add
argument_list|(
name|btnLeft
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|"fill, top"
argument_list|)
operator|.
name|add
argument_list|(
name|btnUp
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|,
literal|"fill, bottom"
argument_list|)
operator|.
name|add
argument_list|(
name|btnDown
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|,
literal|"fill, top"
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|JPanel
name|preview
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"pref:grow, $lcgap, pref, $lcgap, pref"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, $lg, fill:pref:grow"
argument_list|)
operator|.
name|padding
argument_list|(
name|Paddings
operator|.
name|DIALOG
argument_list|)
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
operator|.
name|add
argument_list|(
name|btnTest
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
operator|.
name|add
argument_list|(
name|btnDefault
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
operator|.
name|add
argument_list|(
name|scrollPane
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
operator|.
name|build
argument_list|()
decl_stmt|;
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|this
argument_list|,
name|BoxLayout
operator|.
name|Y_AXIS
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|chooseStyle
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|preview
argument_list|,
name|BorderLayout
operator|.
name|PAGE_END
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|PreviewPreferences
name|previewPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
decl_stmt|;
name|chosenModel
operator|.
name|clear
argument_list|()
expr_stmt|;
name|boolean
name|isPreviewChosen
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|style
range|:
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
control|)
block|{
comment|// in case the style is not a valid citation style file, an empty Optional is returned
name|Optional
argument_list|<
name|CitationStyle
argument_list|>
name|citationStyle
init|=
name|CitationStyle
operator|.
name|createCitationStyleFromFile
argument_list|(
name|style
argument_list|)
decl_stmt|;
if|if
condition|(
name|citationStyle
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|chosenModel
operator|.
name|addElement
argument_list|(
name|citationStyle
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|isPreviewChosen
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Preview is already in the list, something went wrong"
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|isPreviewChosen
operator|=
literal|true
expr_stmt|;
name|chosenModel
operator|.
name|addElement
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|availableModel
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isPreviewChosen
condition|)
block|{
name|availableModel
operator|.
name|addElement
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|btnLeft
operator|.
name|setEnabled
argument_list|(
operator|!
name|chosen
operator|.
name|isSelectionEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|btnRight
operator|.
name|setEnabled
argument_list|(
operator|!
name|available
operator|.
name|isSelectionEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|setEnabled
argument_list|(
operator|!
name|chosen
operator|.
name|isSelectionEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|setEnabled
argument_list|(
operator|!
name|chosen
operator|.
name|isSelectionEmpty
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|discoverCitationStyleWorker
operator|!=
literal|null
condition|)
block|{
name|discoverCitationStyleWorker
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|discoverCitationStyleWorker
operator|=
operator|new
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|CitationStyle
argument_list|>
argument_list|,
name|Void
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|CitationStyle
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
return|return
name|CitationStyle
operator|.
name|discoverCitationStyles
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|done
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|isCancelled
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|get
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|style
lambda|->
operator|!
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
operator|.
name|contains
argument_list|(
name|style
operator|.
name|getFilePath
argument_list|()
argument_list|)
argument_list|)
operator|.
name|sorted
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|CitationStyle
operator|::
name|getTitle
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|availableModel
operator|::
name|addElement
argument_list|)
expr_stmt|;
name|btnRight
operator|.
name|setEnabled
argument_list|(
operator|!
name|availableModel
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"something went wrong while adding the discovered CitationStyles to the list "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
name|discoverCitationStyleWorker
operator|.
name|execute
argument_list|()
expr_stmt|;
name|layout
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getPreviewStyle
argument_list|()
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|styles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Enumeration
argument_list|<
name|Object
argument_list|>
name|elements
init|=
name|chosenModel
operator|.
name|elements
argument_list|()
decl_stmt|;
while|while
condition|(
name|elements
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|Object
name|obj
init|=
name|elements
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|instanceof
name|CitationStyle
condition|)
block|{
name|styles
operator|.
name|add
argument_list|(
operator|(
operator|(
name|CitationStyle
operator|)
name|obj
operator|)
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|obj
operator|instanceof
name|String
condition|)
block|{
name|styles
operator|.
name|add
argument_list|(
literal|"Preview"
argument_list|)
expr_stmt|;
block|}
block|}
name|PreviewPreferences
name|previewPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getBuilder
argument_list|()
operator|.
name|withPreviewCycle
argument_list|(
name|styles
argument_list|)
operator|.
name|withPreviewStyle
argument_list|(
name|layout
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|storePreviewPreferences
argument_list|(
name|previewPreferences
argument_list|)
expr_stmt|;
comment|// update preview
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
name|basePanel
operator|.
name|getPreviewPanel
argument_list|()
operator|.
name|updateLayout
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
operator|!
name|chosenModel
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry preview"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

