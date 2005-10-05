begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
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
name|JabRefFrame
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
name|GUIGlobals
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
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|AbstractTableModel
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|DefaultFormBuilder
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
name|ButtonStackBuilder
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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|Iterator
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Sep 19, 2005  * Time: 7:57:29 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ManageJournalsPanel
specifier|public
class|class
name|ManageJournalsPanel
extends|extends
name|JPanel
block|{
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|personalFile
name|JTextField
name|personalFile
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|tableModel
name|AbbreviationsTableModel
name|tableModel
init|=
literal|null
decl_stmt|;
DECL|field|builtInTable
DECL|field|userTable
name|JTable
name|builtInTable
decl_stmt|,
name|userTable
decl_stmt|;
DECL|field|userPanel
name|JPanel
name|userPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|journalEditPanel
name|journalEditPanel
decl_stmt|;
DECL|field|nameTf
name|JTextField
name|nameTf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|,
DECL|field|abbrTf
name|abbrTf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|dialog
name|JDialog
name|dialog
decl_stmt|;
DECL|field|add
name|JButton
name|add
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|addIconFile
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|remove
name|remove
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|removeIconFile
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|ok
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|ManageJournalsPanel (JabRefFrame frame)
specifier|public
name|ManageJournalsPanel
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, fill:250dlu, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu, left:pref, 4dlu",
literal|"40dlu, 20dlu, 200dlu, 200dlu"
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JLabel
name|description
init|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"JabRef can switch journal names between "
operator|+
literal|"abbreviated and full form. Since it knows only a limited number of journal names, "
operator|+
literal|"you may need to add your own definitions."
argument_list|)
operator|+
literal|"</HTML>"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|personalFile
argument_list|)
expr_stmt|;
name|BrowseAction
name|action
init|=
operator|new
name|BrowseAction
argument_list|(
name|personalFile
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Globals
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
name|action
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|userPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|builtInTable
operator|=
operator|new
name|JTable
argument_list|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getTableModel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|userPanel
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonStackBuilder
name|butBul
init|=
operator|new
name|ButtonStackBuilder
argument_list|()
decl_stmt|;
name|butBul
operator|.
name|addGridded
argument_list|(
name|add
argument_list|)
expr_stmt|;
name|butBul
operator|.
name|addGridded
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|butBul
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|butBul
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|builtInTable
argument_list|)
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
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
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|dialog
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Journal abbrebiations"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|this
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|dialog
operator|.
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
comment|//add(new JScrollPane(builtInTable), BorderLayout.CENTER);
comment|// Set up panel for editing a single journal, to be used in a dialog box:
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 4dlu, fill:180dlu"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder2
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout2
argument_list|)
decl_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Journal name"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|nameTf
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|abbrTf
argument_list|)
expr_stmt|;
name|journalEditPanel
operator|=
name|builder2
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|storeSettings
argument_list|()
expr_stmt|;
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|getDialog ()
specifier|public
name|JDialog
name|getDialog
parameter_list|()
block|{
return|return
name|dialog
return|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Ju"
argument_list|)
expr_stmt|;
name|personalFile
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"personalJournalList"
argument_list|)
argument_list|)
expr_stmt|;
name|JournalAbbreviations
name|userAbbr
init|=
operator|new
name|JournalAbbreviations
argument_list|()
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"personalJournalList"
argument_list|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|userAbbr
operator|.
name|readJournalList
argument_list|(
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"personalJournalList"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
name|tableModel
operator|=
operator|new
name|AbbreviationsTableModel
argument_list|(
name|userAbbr
operator|.
name|getJournals
argument_list|()
argument_list|)
expr_stmt|;
name|add
operator|.
name|addActionListener
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|userTable
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|userTable
operator|.
name|addMouseListener
argument_list|(
name|tableModel
operator|.
name|getMouseListener
argument_list|()
argument_list|)
expr_stmt|;
name|userPanel
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|userTable
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
if|if
condition|(
name|personalFile
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|personalFile
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|FileWriter
name|fw
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fw
operator|=
operator|new
name|FileWriter
argument_list|(
name|f
argument_list|,
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|tableModel
operator|.
name|getJournals
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|JournalEntry
name|entry
init|=
operator|(
name|JournalEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|entry
operator|.
name|name
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
literal|" = "
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|entry
operator|.
name|abbreviation
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|fw
operator|!=
literal|null
condition|)
try|try
block|{
comment|//fw.flush();
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|//(!personalFile.getText().trim().equals(Globals.prefs.get("personalJournalList"))) {
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"personalJournalList"
argument_list|,
name|personalFile
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|initializeJournalNames
argument_list|()
expr_stmt|;
comment|//}
block|}
DECL|class|BrowseAction
class|class
name|BrowseAction
extends|extends
name|AbstractAction
block|{
DECL|field|comp
name|JTextField
name|comp
decl_stmt|;
DECL|field|dir
name|boolean
name|dir
decl_stmt|;
DECL|method|BrowseAction (JTextField tc, boolean dir)
specifier|public
name|BrowseAction
parameter_list|(
name|JTextField
name|tc
parameter_list|,
name|boolean
name|dir
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|dir
operator|=
name|dir
expr_stmt|;
name|comp
operator|=
name|tc
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|chosen
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dir
condition|)
name|chosen
operator|=
name|Globals
operator|.
name|getNewDir
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
else|else
name|chosen
operator|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|AbbreviationsTableModel
class|class
name|AbbreviationsTableModel
extends|extends
name|AbstractTableModel
implements|implements
name|ActionListener
block|{
DECL|field|names
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Journal name"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
block|}
decl_stmt|;
DECL|field|journals
name|ArrayList
name|journals
decl_stmt|;
DECL|method|AbbreviationsTableModel (Map journals)
specifier|public
name|AbbreviationsTableModel
parameter_list|(
name|Map
name|journals
parameter_list|)
block|{
name|this
operator|.
name|journals
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|journals
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|journal
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|,
name|abbr
init|=
operator|(
name|String
operator|)
name|journals
operator|.
name|get
argument_list|(
name|journal
argument_list|)
decl_stmt|;
name|this
operator|.
name|journals
operator|.
name|add
argument_list|(
operator|new
name|JournalEntry
argument_list|(
name|journal
argument_list|,
name|abbr
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getJournals ()
specifier|public
name|ArrayList
name|getJournals
parameter_list|()
block|{
return|return
name|journals
return|;
block|}
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|journals
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|getValueAt (int row, int col)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|==
literal|0
condition|)
return|return
operator|(
operator|(
name|JournalEntry
operator|)
name|journals
operator|.
name|get
argument_list|(
name|row
argument_list|)
operator|)
operator|.
name|name
return|;
else|else
return|return
operator|(
operator|(
name|JournalEntry
operator|)
name|journals
operator|.
name|get
argument_list|(
name|row
argument_list|)
operator|)
operator|.
name|abbreviation
return|;
block|}
DECL|method|setValueAt (Object object, int row, int col)
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|object
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
name|JournalEntry
name|entry
init|=
operator|(
name|JournalEntry
operator|)
name|journals
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|col
operator|==
literal|0
condition|)
name|entry
operator|.
name|name
operator|=
operator|(
name|String
operator|)
name|object
expr_stmt|;
else|else
name|entry
operator|.
name|abbreviation
operator|=
operator|(
name|String
operator|)
name|object
expr_stmt|;
block|}
DECL|method|getColumnName (int i)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|names
index|[
name|i
index|]
return|;
block|}
DECL|method|isCellEditable (int i, int i1)
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|i
parameter_list|,
name|int
name|i1
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|getMouseListener ()
specifier|public
name|MouseListener
name|getMouseListener
parameter_list|()
block|{
return|return
operator|new
name|MouseAdapter
argument_list|()
block|{
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
condition|)
block|{
name|JTable
name|table
init|=
operator|(
name|JTable
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
name|int
name|row
init|=
name|table
operator|.
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
name|nameTf
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|getValueAt
argument_list|(
name|row
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|abbrTf
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|getValueAt
argument_list|(
name|row
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|dialog
argument_list|,
name|journalEditPanel
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit journal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|setValueAt
argument_list|(
name|nameTf
operator|.
name|getText
argument_list|()
argument_list|,
name|row
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|setValueAt
argument_list|(
name|abbrTf
operator|.
name|getText
argument_list|()
argument_list|,
name|row
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|journals
argument_list|)
expr_stmt|;
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
return|;
block|}
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
operator|==
name|add
condition|)
block|{
comment|//int sel = userTable.getSelectedRow();
comment|//if (sel< 0)
comment|//    sel = 0;
name|nameTf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|abbrTf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|dialog
argument_list|,
name|journalEditPanel
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit journal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|journals
operator|.
name|add
argument_list|(
operator|new
name|JournalEntry
argument_list|(
name|nameTf
operator|.
name|getText
argument_list|()
argument_list|,
name|abbrTf
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//setValueAt(nameTf.getText(), sel, 0);
comment|//setValueAt(abbrTf.getText(), sel, 1);
name|Collections
operator|.
name|sort
argument_list|(
name|journals
argument_list|)
expr_stmt|;
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|remove
condition|)
block|{
name|int
index|[]
name|rows
init|=
name|userTable
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|rows
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
name|journals
operator|.
name|remove
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|class|JournalEntry
class|class
name|JournalEntry
implements|implements
name|Comparable
block|{
DECL|field|name
DECL|field|abbreviation
name|String
name|name
decl_stmt|,
name|abbreviation
decl_stmt|;
DECL|method|JournalEntry (String name, String abbreviation)
specifier|public
name|JournalEntry
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|abbreviation
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|abbreviation
operator|=
name|abbreviation
expr_stmt|;
block|}
DECL|method|compareTo (Object other)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|other
parameter_list|)
block|{
name|JournalEntry
name|entry
init|=
operator|(
name|JournalEntry
operator|)
name|other
decl_stmt|;
return|return
name|this
operator|.
name|name
operator|.
name|compareTo
argument_list|(
name|entry
operator|.
name|name
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

