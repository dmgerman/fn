begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
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
name|groups
operator|.
name|GroupSelector
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
name|Util
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
name|KeyCollisionException
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
name|JLabel
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|javax
operator|.
name|swing
operator|.
name|JTextPane
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
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|GroupAddOrRemove
specifier|public
class|class
name|GroupAddOrRemove
extends|extends
name|Change
block|{
DECL|field|field
DECL|field|gName
DECL|field|regexp
name|String
name|field
decl_stmt|,
name|gName
decl_stmt|,
name|regexp
decl_stmt|;
DECL|field|add
name|boolean
name|add
decl_stmt|;
DECL|method|GroupAddOrRemove (String field, String gName, String regexp, boolean add)
specifier|public
name|GroupAddOrRemove
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|gName
parameter_list|,
name|String
name|regexp
parameter_list|,
name|boolean
name|add
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
if|if
condition|(
name|add
condition|)
name|name
operator|=
literal|"Added group"
expr_stmt|;
else|else
name|name
operator|=
literal|"Removed group"
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|gName
operator|=
name|gName
expr_stmt|;
name|this
operator|.
name|regexp
operator|=
name|regexp
expr_stmt|;
name|this
operator|.
name|add
operator|=
name|add
expr_stmt|;
block|}
DECL|method|makeChange (BasePanel panel, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|MetaData
name|md
init|=
name|panel
operator|.
name|metaData
argument_list|()
decl_stmt|;
name|Vector
name|groups
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|md
operator|!=
literal|null
condition|)
name|groups
operator|=
name|md
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
expr_stmt|;
comment|// Must report error if groups is null.
if|if
condition|(
name|add
condition|)
block|{
comment|// Add the group.
name|int
name|pos
init|=
name|GroupSelector
operator|.
name|findPos
argument_list|(
name|groups
argument_list|,
name|gName
argument_list|)
decl_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|pos
argument_list|,
name|regexp
argument_list|)
expr_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|pos
argument_list|,
name|gName
argument_list|)
expr_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|pos
argument_list|,
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Remove the group.
name|int
name|pos
init|=
name|Util
operator|.
name|findGroup
argument_list|(
name|gName
argument_list|,
name|groups
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|groups
operator|.
name|removeElementAt
argument_list|(
name|pos
argument_list|)
expr_stmt|;
name|groups
operator|.
name|removeElementAt
argument_list|(
name|pos
argument_list|)
expr_stmt|;
name|groups
operator|.
name|removeElementAt
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
operator|new
name|JLabel
argument_list|(
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

