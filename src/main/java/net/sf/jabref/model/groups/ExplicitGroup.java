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
name|ArrayList
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
name|Set
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
name|FieldName
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
comment|/**  * Select explicit bibtex entries. It is also known as static group.  *  * @author jzieren  */
end_comment

begin_class
DECL|class|ExplicitGroup
specifier|public
class|class
name|ExplicitGroup
extends|extends
name|KeywordGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"ExplicitGroup:"
decl_stmt|;
DECL|field|legacyEntryKeys
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|legacyEntryKeys
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|ExplicitGroup
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|ExplicitGroup (String name, GroupHierarchyType context, Character keywordSeparator)
specifier|public
name|ExplicitGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|FieldName
operator|.
name|GROUPS
argument_list|,
name|name
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
name|context
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
DECL|method|addLegacyEntryKey (String key)
specifier|public
name|void
name|addLegacyEntryKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|this
operator|.
name|legacyEntryKeys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
name|ExplicitGroup
name|copy
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|getName
argument_list|()
argument_list|,
name|getContext
argument_list|()
argument_list|,
name|keywordSeparator
argument_list|)
decl_stmt|;
name|copy
operator|.
name|legacyEntryKeys
operator|.
name|addAll
argument_list|(
name|legacyEntryKeys
argument_list|)
expr_stmt|;
return|return
name|copy
return|;
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
name|ExplicitGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|ExplicitGroup
name|other
init|=
operator|(
name|ExplicitGroup
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|getName
argument_list|()
argument_list|,
name|other
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getHierarchicalContext
argument_list|()
argument_list|,
name|other
operator|.
name|getHierarchicalContext
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getLegacyEntryKeys
argument_list|()
argument_list|,
name|other
operator|.
name|getLegacyEntryKeys
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns a String representation of this group and its entries.      */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|ExplicitGroup
operator|.
name|ID
argument_list|)
operator|.
name|append
argument_list|(
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
argument_list|)
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
operator|.
name|append
argument_list|(
name|getContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
comment|// write legacy entry keys in well-defined order for CVS compatibility
name|Set
argument_list|<
name|String
argument_list|>
name|sortedKeys
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
name|sortedKeys
operator|.
name|addAll
argument_list|(
name|legacyEntryKeys
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|sortedKey
range|:
name|sortedKeys
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|sortedKey
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Remove all stored cite keys, resulting in an empty group.      */
DECL|method|clearLegacyEntryKeys ()
specifier|public
name|void
name|clearLegacyEntryKeys
parameter_list|()
block|{
name|legacyEntryKeys
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
DECL|method|getLegacyEntryKeys ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getLegacyEntryKeys
parameter_list|()
block|{
return|return
name|legacyEntryKeys
return|;
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
name|ExplicitGroup
operator|.
name|ID
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
return|return
name|super
operator|.
name|hashCode
argument_list|()
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
literal|false
return|;
block|}
block|}
end_class

end_unit

