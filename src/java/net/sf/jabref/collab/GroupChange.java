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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|groups
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_class
DECL|class|GroupChange
specifier|public
class|class
name|GroupChange
extends|extends
name|Change
block|{
DECL|field|tmpGroup
name|AbstractGroup
name|tmpGroup
decl_stmt|;
DECL|field|diskGroup
name|AbstractGroup
name|diskGroup
decl_stmt|;
DECL|field|removedLocally
name|boolean
name|removedLocally
init|=
literal|false
decl_stmt|;
DECL|field|md
name|MetaData
name|md
decl_stmt|;
DECL|method|GroupChange (MetaData md, AbstractGroup tmpGroup, AbstractGroup diskGroup)
specifier|public
name|GroupChange
parameter_list|(
name|MetaData
name|md
parameter_list|,
name|AbstractGroup
name|tmpGroup
parameter_list|,
name|AbstractGroup
name|diskGroup
parameter_list|)
block|{
name|super
argument_list|(
literal|"Modified group"
argument_list|)
expr_stmt|;
comment|// JZTODO
comment|//    this.tmpGroup = tmpGroup;
comment|//    this.diskGroup = diskGroup;
comment|//    this.md = md;
comment|//
comment|//    if (md == null)
comment|//      removedLocally = true;
comment|//    else {
comment|//      GroupTreeNode groups = md.getGroups();
comment|//      if ((groups == null) || (GroupSelector.findGroupByName(groups, tmpGroup.getName()) == -1))
comment|//        removedLocally = true;
comment|//    }
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
comment|// JZTODO
comment|//
comment|//    GroupTreeNode groups = md.getGroups();
comment|//    //if (groups == null)
comment|//    // Error, no groups...
comment|//
comment|//    int pos = GroupSelector.findGroupByName(groups,tmpGroup.getName());
comment|//    if (pos>= 0) {
comment|//      groups.setElementAt(diskGroup, pos);
comment|//    }
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

