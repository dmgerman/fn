begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * This group contains all entries.  */
end_comment

begin_class
DECL|class|AllEntriesGroup
specifier|public
class|class
name|AllEntriesGroup
extends|extends
name|AbstractGroup
implements|implements
name|SearchRule
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"AllEntriesGroup:"
decl_stmt|;
DECL|field|m_name
specifier|private
specifier|static
specifier|final
name|String
name|m_name
init|=
literal|"All Entries"
decl_stmt|;
DECL|method|fromString (String s)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|ID
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Internal error: AllEntriesGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\""
argument_list|)
throw|;
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
block|}
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|m_name
return|;
block|}
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|addSelection (BasePanel basePanel)
specifier|public
name|void
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// not supported -> ignore
block|}
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
name|void
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// not supported -> ignore
block|}
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
name|int
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|1
return|;
comment|// contains everything
block|}
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
block|}
DECL|method|applyRule (Map searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
return|return
literal|1
return|;
comment|// contains everything
block|}
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|AllEntriesGroup
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|ID
return|;
block|}
block|}
end_class

end_unit

