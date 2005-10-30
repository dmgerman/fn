begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * EntryCustomizationDialog2.java  *  * Created on February 10, 2005, 9:57 PM  */
end_comment

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
name|event
operator|.
name|ListSelectionEvent
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
name|ListSelectionListener
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
name|HashSet
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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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

begin_comment
comment|/**  * NOTAT:   *    * S?rg for at ting oppdateres riktig ved tillegg av ny type. (velge, unng? at det blir feil innhold i listene).  *    *   *  *  * @author alver  */
end_comment

begin_class
DECL|class|EntryCustomizationDialog2
specifier|public
class|class
name|EntryCustomizationDialog2
extends|extends
name|JDialog
implements|implements
name|ListSelectionListener
implements|,
name|ActionListener
block|{
DECL|field|frame
specifier|protected
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|gbl
specifier|protected
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|protected
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|reqComp
DECL|field|optComp
specifier|protected
name|FieldSetComponent
name|reqComp
decl_stmt|,
name|optComp
decl_stmt|;
DECL|field|typeComp
specifier|protected
name|EntryTypeList
name|typeComp
decl_stmt|;
DECL|field|ok
DECL|field|cancel
DECL|field|apply
DECL|field|helpButton
DECL|field|delete
DECL|field|importTypes
DECL|field|exportTypes
specifier|protected
name|JButton
name|ok
decl_stmt|,
name|cancel
decl_stmt|,
name|apply
decl_stmt|,
name|helpButton
decl_stmt|,
name|delete
decl_stmt|,
name|importTypes
decl_stmt|,
name|exportTypes
decl_stmt|;
DECL|field|preset
specifier|protected
specifier|final
name|List
name|preset
init|=
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
argument_list|)
decl_stmt|;
DECL|field|lastSelected
specifier|protected
name|String
name|lastSelected
init|=
literal|null
decl_stmt|;
DECL|field|reqLists
specifier|protected
name|Map
name|reqLists
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|,
DECL|field|optLists
name|optLists
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|defaulted
DECL|field|changed
specifier|protected
name|Set
name|defaulted
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|,
name|changed
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
comment|/** Creates a new instance of EntryCustomizationDialog2 */
DECL|method|EntryCustomizationDialog2 (JabRefFrame frame)
specifier|public
name|EntryCustomizationDialog2
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Customize entry types"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|initGui
argument_list|()
expr_stmt|;
block|}
DECL|method|initGui ()
specifier|protected
specifier|final
name|void
name|initGui
parameter_list|()
block|{
name|Container
name|pane
init|=
name|getContentPane
argument_list|()
decl_stmt|;
name|pane
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|right
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|right
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|java
operator|.
name|util
operator|.
name|List
name|entryTypes
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|BibtexEntryType
operator|.
name|ALL_TYPES
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
name|entryTypes
operator|.
name|add
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|typeComp
operator|=
operator|new
name|EntryTypeList
argument_list|(
name|entryTypes
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addAdditionActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addDefaultActionListener
argument_list|(
operator|new
name|DefaultListener
argument_list|()
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|setListSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
comment|//typeComp.setEnabled(false);
name|reqComp
operator|=
operator|new
name|FieldSetComponent
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Required fields"
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|preset
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|ListDataListener
name|dataListener
init|=
operator|new
name|DataListener
argument_list|()
decl_stmt|;
name|reqComp
operator|.
name|addListDataListener
argument_list|(
name|dataListener
argument_list|)
expr_stmt|;
name|optComp
operator|=
operator|new
name|FieldSetComponent
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Optional fields"
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|preset
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|addListDataListener
argument_list|(
name|dataListener
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
name|reqComp
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
name|optComp
argument_list|)
expr_stmt|;
comment|//right.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), Globals.lang("Fields")));
name|right
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|ok
operator|=
operator|new
name|JButton
argument_list|(
literal|"OK"
argument_list|)
expr_stmt|;
name|cancel
operator|=
operator|new
name|JButton
argument_list|(
literal|"Cancel"
argument_list|)
expr_stmt|;
name|apply
operator|=
operator|new
name|JButton
argument_list|(
literal|"Apply"
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|apply
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|apply
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
comment|//con.fill = GridBagConstraints.BOTH;
comment|//con.weightx = 0.3;
comment|//con.weighty = 1;
comment|//gbl.setConstraints(typeComp, con);
name|main
operator|.
name|add
argument_list|(
name|typeComp
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|right
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|main
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
name|pane
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|pane
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|lastSelected
operator|!=
literal|null
condition|)
block|{
comment|// The entry type lastSelected is now unselected, so we store the current settings
comment|// for that type in our two maps.
name|reqLists
operator|.
name|put
argument_list|(
name|lastSelected
argument_list|,
name|reqComp
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
name|optLists
operator|.
name|put
argument_list|(
name|lastSelected
argument_list|,
name|optComp
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|s
init|=
name|typeComp
operator|.
name|getFirstSelected
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return;
name|Object
name|rl
init|=
name|reqLists
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|rl
operator|==
literal|null
condition|)
block|{
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|rf
init|=
name|type
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|,
name|of
init|=
name|type
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
name|List
name|req
decl_stmt|,
name|opt
decl_stmt|;
if|if
condition|(
name|rf
operator|!=
literal|null
condition|)
name|req
operator|=
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|rf
argument_list|)
expr_stmt|;
else|else
name|req
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
if|if
condition|(
name|of
operator|!=
literal|null
condition|)
name|opt
operator|=
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|of
argument_list|)
expr_stmt|;
else|else
name|opt
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|reqComp
operator|.
name|setFields
argument_list|(
name|req
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
name|opt
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// New entry, veintle
name|reqComp
operator|.
name|setFields
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|reqComp
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|reqComp
operator|.
name|setFields
argument_list|(
operator|(
name|List
operator|)
name|rl
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
operator|(
name|List
operator|)
name|optLists
operator|.
name|get
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|lastSelected
operator|=
name|s
expr_stmt|;
name|typeComp
operator|.
name|enable
argument_list|(
name|s
argument_list|,
name|changed
operator|.
name|contains
argument_list|(
name|lastSelected
argument_list|)
operator|&&
operator|!
name|defaulted
operator|.
name|contains
argument_list|(
name|lastSelected
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|applyChanges ()
specifier|protected
name|void
name|applyChanges
parameter_list|()
block|{
name|valueChanged
argument_list|(
operator|new
name|ListSelectionEvent
argument_list|(
operator|new
name|JList
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
comment|// Iterate over our map of required fields, and list those types if necessary:
name|List
name|types
init|=
name|typeComp
operator|.
name|getFields
argument_list|()
decl_stmt|;
name|boolean
name|globalChangesMade
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|reqLists
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
name|typeName
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|types
operator|.
name|contains
argument_list|(
name|typeName
argument_list|)
condition|)
continue|continue;
name|List
name|reqFields
init|=
operator|(
name|List
operator|)
name|reqLists
operator|.
name|get
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
name|List
name|optFields
init|=
operator|(
name|List
operator|)
name|optLists
operator|.
name|get
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
name|String
index|[]
name|reqStr
init|=
operator|new
name|String
index|[
name|reqFields
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|reqFields
operator|.
name|toArray
argument_list|(
name|reqStr
argument_list|)
expr_stmt|;
name|String
index|[]
name|optStr
init|=
operator|new
name|String
index|[
name|optFields
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|optFields
operator|.
name|toArray
argument_list|(
name|optStr
argument_list|)
expr_stmt|;
comment|// If this type is already existing, check if any changes have
comment|// been made
name|boolean
name|changesMade
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|defaulted
operator|.
name|contains
argument_list|(
name|typeName
argument_list|)
condition|)
block|{
comment|// This type should be reverted to its default setup.
comment|//System.out.println("Defaulting: "+typeName);
name|String
name|nm
init|=
name|Util
operator|.
name|nCase
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
name|BibtexEntryType
operator|.
name|removeType
argument_list|(
name|nm
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|nm
argument_list|)
expr_stmt|;
name|globalChangesMade
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
name|BibtexEntryType
name|oldType
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldType
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|oldReq
init|=
name|oldType
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|,
name|oldOpt
init|=
name|oldType
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|equalArrays
argument_list|(
name|oldReq
argument_list|,
name|reqStr
argument_list|)
operator|&&
name|equalArrays
argument_list|(
name|oldOpt
argument_list|,
name|optStr
argument_list|)
condition|)
name|changesMade
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|changesMade
condition|)
block|{
comment|//System.out.println("Updating: "+typeName);
name|CustomEntryType
name|typ
init|=
operator|new
name|CustomEntryType
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|typeName
argument_list|)
argument_list|,
name|reqStr
argument_list|,
name|optStr
argument_list|)
decl_stmt|;
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|typeName
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|typ
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|typ
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|globalChangesMade
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|Set
name|toRemove
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|BibtexEntryType
operator|.
name|ALL_TYPES
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
name|Object
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|types
operator|.
name|contains
argument_list|(
name|o
argument_list|)
condition|)
block|{
comment|//System.out.println("Deleted entry type (TODO): "+o);
name|toRemove
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Remove those that should be removed:
if|if
condition|(
name|toRemove
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|toRemove
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
name|typeDeletion
argument_list|(
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|updateTables
argument_list|()
expr_stmt|;
block|}
DECL|method|typeDeletion (String name)
specifier|protected
name|void
name|typeDeletion
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|type
operator|instanceof
name|CustomEntryType
operator|)
condition|)
empty_stmt|;
comment|//messageLabel.setText("'"+type.getName()+"' "+
comment|//        Globals.lang("is a standard type."));
else|else
block|{
if|if
condition|(
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|name
argument_list|)
operator|==
literal|null
condition|)
block|{
name|int
name|reply
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"All entries of this "
operator|+
literal|"type will be declared "
operator|+
literal|"typeless. Continue?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete custom format"
argument_list|)
operator|+
literal|" '"
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|name
argument_list|)
operator|+
literal|"'"
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|reply
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
return|return;
block|}
name|BibtexEntryType
operator|.
name|removeType
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
name|changed
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|reqLists
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|optLists
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|equalArrays (String[] one, String[] two)
specifier|protected
name|boolean
name|equalArrays
parameter_list|(
name|String
index|[]
name|one
parameter_list|,
name|String
index|[]
name|two
parameter_list|)
block|{
if|if
condition|(
operator|(
name|one
operator|==
literal|null
operator|)
operator|&&
operator|(
name|two
operator|==
literal|null
operator|)
condition|)
return|return
literal|true
return|;
comment|// Both null.
if|if
condition|(
operator|(
name|one
operator|==
literal|null
operator|)
operator|||
operator|(
name|two
operator|==
literal|null
operator|)
condition|)
return|return
literal|false
return|;
comment|// One of them null, the other not.
if|if
condition|(
name|one
operator|.
name|length
operator|!=
name|two
operator|.
name|length
condition|)
return|return
literal|false
return|;
comment|// Different length.
comment|// If we get here, we know that both are non-null, and that they have the same length.
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|one
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|one
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|two
index|[
name|i
index|]
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
comment|// If we get here, all entries have matched.
return|return
literal|true
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
name|ok
condition|)
block|{
name|applyChanges
argument_list|()
expr_stmt|;
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|cancel
condition|)
block|{
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|apply
condition|)
block|{
name|applyChanges
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|typeComp
condition|)
block|{
comment|//System.out.println("add: "+e.getActionCommand());
name|typeComp
operator|.
name|selectField
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**  * Cycle through all databases, and make sure everything is updated with  * the new type customization. This includes making sure all entries have  * a valid type, that no obsolete entry editors are around, and that  * the right-click menus' change type menu is up-to-date.  */
DECL|method|updateTypesForEntries (String typeName)
specifier|private
name|void
name|updateTypesForEntries
parameter_list|(
name|String
name|typeName
parameter_list|)
block|{
if|if
condition|(
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|//messageLabel.setText(Globals.lang("Updating entries..."));
name|BibtexDatabase
name|base
decl_stmt|;
name|Iterator
name|iter
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|bp
init|=
operator|(
name|BasePanel
operator|)
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getComponentAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|anyChanges
init|=
literal|false
decl_stmt|;
name|bp
operator|.
name|entryEditors
operator|.
name|remove
argument_list|(
name|typeName
argument_list|)
expr_stmt|;
comment|//bp.rcm.populateTypeMenu(); // Update type menu for change type.
name|base
operator|=
name|bp
operator|.
name|database
argument_list|()
expr_stmt|;
name|iter
operator|=
name|base
operator|.
name|getKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|anyChanges
operator|=
name|anyChanges
operator||
operator|!
operator|(
name|base
operator|.
name|getEntryById
argument_list|(
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
argument_list|)
operator|)
operator|.
name|updateType
argument_list|()
expr_stmt|;
block|}
comment|/*if (anyChanges) {                 bp.refreshTable();                 bp.markBaseChanged();             }*/
block|}
block|}
DECL|method|updateTables ()
specifier|private
name|void
name|updateTables
parameter_list|()
block|{
if|if
condition|(
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|//messageLabel.setText(Globals.lang("Updating entries..."));
name|BibtexDatabase
name|base
decl_stmt|;
name|Iterator
name|iter
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|bp
init|=
operator|(
name|BasePanel
operator|)
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getComponentAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|//bp.markBaseChanged();
block|}
block|}
comment|// DEFAULT button pressed. Remember that this entry should be reset to default,
comment|// unless changes are made later.
DECL|class|DefaultListener
class|class
name|DefaultListener
implements|implements
name|ActionListener
block|{
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
name|lastSelected
operator|==
literal|null
condition|)
return|return;
name|defaulted
operator|.
name|add
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|lastSelected
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|rf
init|=
name|type
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|,
name|of
init|=
name|type
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
name|List
name|req
decl_stmt|,
name|opt
decl_stmt|;
if|if
condition|(
name|rf
operator|!=
literal|null
condition|)
name|req
operator|=
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|rf
argument_list|)
expr_stmt|;
else|else
name|req
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
if|if
condition|(
name|of
operator|!=
literal|null
condition|)
name|opt
operator|=
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|of
argument_list|)
expr_stmt|;
else|else
name|opt
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|reqComp
operator|.
name|setFields
argument_list|(
name|req
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
name|opt
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|DataListener
class|class
name|DataListener
implements|implements
name|ListDataListener
block|{
DECL|method|intervalAdded (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|intervalAdded
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
DECL|method|intervalRemoved (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|intervalRemoved
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
DECL|method|contentsChanged (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|contentsChanged
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
DECL|method|record ()
specifier|private
name|void
name|record
parameter_list|()
block|{
if|if
condition|(
name|lastSelected
operator|==
literal|null
condition|)
return|return;
name|defaulted
operator|.
name|remove
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|changed
operator|.
name|add
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|enable
argument_list|(
name|lastSelected
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

