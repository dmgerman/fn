begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
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
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
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
name|MetadataSerializationConfiguration
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
name|io
operator|.
name|FileUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AbstractGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AllEntriesGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AutomaticGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AutomaticKeywordGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AutomaticPersonsGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|ExplicitGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|KeywordGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|RegexKeywordGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|SearchGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|TexGroup
import|;
end_import

begin_import
import|import
name|org
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

begin_class
DECL|class|GroupSerializer
specifier|public
class|class
name|GroupSerializer
block|{
DECL|method|serializeAllEntriesGroup (AllEntriesGroup group)
specifier|private
specifier|static
name|String
name|serializeAllEntriesGroup
parameter_list|(
name|AllEntriesGroup
name|group
parameter_list|)
block|{
return|return
name|MetadataSerializationConfiguration
operator|.
name|ALL_ENTRIES_GROUP_ID
return|;
block|}
DECL|method|serializeExplicitGroup (ExplicitGroup group)
specifier|private
name|String
name|serializeExplicitGroup
parameter_list|(
name|ExplicitGroup
name|group
parameter_list|)
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
name|MetadataSerializationConfiguration
operator|.
name|EXPLICIT_GROUP_ID
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|serializeKeywordGroup (KeywordGroup group)
specifier|private
name|String
name|serializeKeywordGroup
parameter_list|(
name|KeywordGroup
name|group
parameter_list|)
block|{
name|Boolean
name|isRegex
init|=
name|group
operator|instanceof
name|RegexKeywordGroup
decl_stmt|;
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
name|MetadataSerializationConfiguration
operator|.
name|KEYWORD_GROUP_ID
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
name|getSearchField
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|group
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|isRegex
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|serializeSearchGroup (SearchGroup group)
specifier|private
name|String
name|serializeSearchGroup
parameter_list|(
name|SearchGroup
name|group
parameter_list|)
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
name|MetadataSerializationConfiguration
operator|.
name|SEARCH_GROUP_ID
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|group
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|group
operator|.
name|isRegularExpression
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|appendGroupDetails (StringBuilder builder, AbstractGroup group)
specifier|private
name|void
name|appendGroupDetails
parameter_list|(
name|StringBuilder
name|builder
parameter_list|,
name|AbstractGroup
name|group
parameter_list|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|group
operator|.
name|isExpanded
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|group
operator|.
name|getColor
argument_list|()
operator|.
name|map
argument_list|(
name|Color
operator|::
name|toString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|group
operator|.
name|getIconName
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|group
operator|.
name|getDescription
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a textual representation of this node and its children. This      * representation contains both the tree structure and the textual      * representations of the group associated with each node.      * Every node is one entry in the list of strings.      *      * @return a representation of the tree based at this node as a list of strings      */
DECL|method|serializeTree (GroupTreeNode node)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|serializeTree
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|representation
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Append current node
name|representation
operator|.
name|add
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|node
operator|.
name|getLevel
argument_list|()
argument_list|)
operator|+
literal|' '
operator|+
name|serializeGroup
argument_list|(
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Append children
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|representation
operator|.
name|addAll
argument_list|(
name|serializeTree
argument_list|(
name|child
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|representation
return|;
block|}
DECL|method|serializeGroup (AbstractGroup group)
specifier|private
name|String
name|serializeGroup
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
if|if
condition|(
name|group
operator|instanceof
name|AllEntriesGroup
condition|)
block|{
return|return
name|serializeAllEntriesGroup
argument_list|(
operator|(
name|AllEntriesGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|ExplicitGroup
condition|)
block|{
return|return
name|serializeExplicitGroup
argument_list|(
operator|(
name|ExplicitGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|KeywordGroup
condition|)
block|{
return|return
name|serializeKeywordGroup
argument_list|(
operator|(
name|KeywordGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|SearchGroup
condition|)
block|{
return|return
name|serializeSearchGroup
argument_list|(
operator|(
name|SearchGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|AutomaticKeywordGroup
condition|)
block|{
return|return
name|serializeAutomaticKeywordGroup
argument_list|(
operator|(
name|AutomaticKeywordGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|AutomaticPersonsGroup
condition|)
block|{
return|return
name|serializeAutomaticPersonsGroup
argument_list|(
operator|(
name|AutomaticPersonsGroup
operator|)
name|group
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|group
operator|instanceof
name|TexGroup
condition|)
block|{
return|return
name|serializeTexGroup
argument_list|(
operator|(
name|TexGroup
operator|)
name|group
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Don't know how to serialize group"
operator|+
name|group
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
throw|;
block|}
block|}
DECL|method|serializeTexGroup (TexGroup group)
specifier|private
name|String
name|serializeTexGroup
parameter_list|(
name|TexGroup
name|group
parameter_list|)
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
name|MetadataSerializationConfiguration
operator|.
name|TEX_GROUP_ID
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|FileUtil
operator|.
name|toPortableString
argument_list|(
name|group
operator|.
name|getFilePath
argument_list|()
argument_list|)
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|serializeAutomaticPersonsGroup (AutomaticPersonsGroup group)
specifier|private
name|String
name|serializeAutomaticPersonsGroup
parameter_list|(
name|AutomaticPersonsGroup
name|group
parameter_list|)
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
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_PERSONS_GROUP_ID
argument_list|)
expr_stmt|;
name|appendAutomaticGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
name|getField
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|appendAutomaticGroupDetails (StringBuilder builder, AutomaticGroup group)
specifier|private
name|void
name|appendAutomaticGroupDetails
parameter_list|(
name|StringBuilder
name|builder
parameter_list|,
name|AutomaticGroup
name|group
parameter_list|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
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
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
block|}
DECL|method|serializeAutomaticKeywordGroup (AutomaticKeywordGroup group)
specifier|private
name|String
name|serializeAutomaticKeywordGroup
parameter_list|(
name|AutomaticKeywordGroup
name|group
parameter_list|)
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
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_KEYWORD_GROUP_ID
argument_list|)
expr_stmt|;
name|appendAutomaticGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|group
operator|.
name|getField
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
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|group
operator|.
name|getKeywordHierarchicalDelimiter
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|)
expr_stmt|;
name|appendGroupDetails
argument_list|(
name|sb
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

