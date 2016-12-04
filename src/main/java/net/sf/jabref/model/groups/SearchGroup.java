begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|MetadataSerializationConfiguration
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
name|search
operator|.
name|GroupSearchQuery
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
name|search
operator|.
name|rules
operator|.
name|SearchRule
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
name|strings
operator|.
name|StringUtil
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

begin_comment
comment|/**  * Internally, it consists of a search pattern.  *  * @author jzieren  */
end_comment

begin_class
DECL|class|SearchGroup
specifier|public
class|class
name|SearchGroup
extends|extends
name|AbstractGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"SearchGroup:"
decl_stmt|;
DECL|field|query
specifier|private
specifier|final
name|GroupSearchQuery
name|query
decl_stmt|;
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
name|SearchGroup
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|searchExpression
specifier|private
specifier|final
name|String
name|searchExpression
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
comment|/**      * Creates a SearchGroup with the specified properties.      */
DECL|method|SearchGroup (String name, String searchExpression, boolean caseSensitive, boolean regExp, GroupHierarchyType context)
specifier|public
name|SearchGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
name|this
operator|.
name|query
operator|=
operator|new
name|GroupSearchQuery
argument_list|(
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|SearchGroup
operator|.
name|ID
return|;
block|}
comment|/**      * Returns a String representation of this object that can be used to      * reconstruct it.      */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|SearchGroup
operator|.
name|ID
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|getName
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
operator|+
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
operator|+
name|getContext
argument_list|()
operator|.
name|ordinal
argument_list|()
operator|+
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|getSearchExpression
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
operator|+
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|isCaseSensitive
argument_list|()
argument_list|)
operator|+
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|isRegExp
argument_list|()
argument_list|)
operator|+
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
return|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|searchExpression
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|add (List<BibEntry> entriesToAdd)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|add
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Search group does not support adding entries."
argument_list|)
throw|;
block|}
annotation|@
name|Override
DECL|method|remove (List<BibEntry> entriesToRemove)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|remove
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToRemove
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Search group does not support removing entries."
argument_list|)
throw|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|SearchGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|SearchGroup
name|other
init|=
operator|(
name|SearchGroup
operator|)
name|o
decl_stmt|;
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|getSearchExpression
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
operator|&&
operator|(
name|this
operator|.
name|isCaseSensitive
argument_list|()
operator|==
name|other
operator|.
name|isCaseSensitive
argument_list|()
operator|)
operator|&&
operator|(
name|isRegExp
argument_list|()
operator|==
name|other
operator|.
name|isRegExp
argument_list|()
operator|)
operator|&&
operator|(
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|this
operator|.
name|query
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|SearchGroup
argument_list|(
name|getName
argument_list|()
argument_list|,
name|getSearchExpression
argument_list|()
argument_list|,
name|isCaseSensitive
argument_list|()
argument_list|,
name|isRegExp
argument_list|()
argument_list|,
name|getHierarchicalContext
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|// this should never happen, because the constructor obviously
comment|// succeeded in creating _this_ instance!
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Internal error in SearchGroup.deepCopy(). "
operator|+
literal|"Please report this on https://github.com/JabRef/jabref/issues"
argument_list|,
name|t
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|isCaseSensitive ()
specifier|public
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|regExp
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
name|super
operator|.
name|hashCode
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
name|query
operator|.
name|getRule
argument_list|()
return|;
block|}
block|}
end_class

end_unit

