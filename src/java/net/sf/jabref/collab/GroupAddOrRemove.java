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
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
DECL|class|GroupAddOrRemove
specifier|public
class|class
name|GroupAddOrRemove
extends|extends
name|Change
block|{
DECL|field|group
name|AbstractGroup
name|group
decl_stmt|;
DECL|field|add
name|boolean
name|add
decl_stmt|;
DECL|field|tp
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|method|GroupAddOrRemove (AbstractGroup group, boolean add)
specifier|public
name|GroupAddOrRemove
parameter_list|(
name|AbstractGroup
name|group
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
name|group
operator|=
name|group
expr_stmt|;
name|this
operator|.
name|add
operator|=
name|add
expr_stmt|;
name|StringBuffer
name|text
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<FONT SIZE=10>"
argument_list|)
expr_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H2>"
operator|+
operator|(
name|add
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Added group"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Removed group"
argument_list|)
operator|)
argument_list|)
expr_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
operator|+
literal|":</H3>"
operator|+
literal|" "
operator|+
name|group
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|group
operator|instanceof
name|KeywordGroup
condition|)
block|{
name|KeywordGroup
name|keywordGroup
init|=
operator|(
name|KeywordGroup
operator|)
name|group
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field"
argument_list|)
operator|+
literal|":</H3>"
operator|+
literal|" "
operator|+
name|keywordGroup
operator|.
name|getSearchField
argument_list|()
argument_list|)
expr_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Regexp"
argument_list|)
operator|+
literal|":</H3>"
operator|+
literal|" "
operator|+
name|keywordGroup
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//  else if (group instanceof ...) JZTODO
name|tp
operator|.
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|toString
argument_list|()
argument_list|)
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
comment|// JZTODO
comment|//    MetaData md = panel.metaData();
comment|//    GroupTreeNode groups = null;
comment|//    if (md != null)
comment|//      groups = md.getGroups();
comment|//
comment|//      // Must report error if groups is null.
comment|//
comment|//    int pos = GroupSelector.findGroupByName(groups, group.getName());
comment|//    if (add) {
comment|//      // Add the group.
comment|//      groups.add(group);
comment|//    } else {
comment|//      // Remove the group.
comment|//      if (pos>= 0)
comment|//        groups.removeElementAt(pos);
comment|//    }
block|}
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|tp
return|;
block|}
block|}
end_class

end_unit

