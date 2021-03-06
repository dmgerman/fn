begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|integrity
operator|.
name|IntegrityCheck
operator|.
name|Checker
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|entry
operator|.
name|field
operator|.
name|Field
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
name|entry
operator|.
name|field
operator|.
name|FieldProperty
import|;
end_import

begin_class
DECL|class|BibStringChecker
specifier|public
class|class
name|BibStringChecker
implements|implements
name|Checker
block|{
comment|// Detect # if it doesn't have a \ in front of it or if it starts the string
DECL|field|UNESCAPED_HASH
specifier|private
specifier|static
specifier|final
name|Pattern
name|UNESCAPED_HASH
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?<!\\\\)#|^#"
argument_list|)
decl_stmt|;
comment|/**      * Checks, if there is an even number of unescaped #      */
annotation|@
name|Override
DECL|method|check (BibEntry entry)
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|check
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|results
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Field
argument_list|,
name|String
argument_list|>
name|fields
init|=
name|entry
operator|.
name|getFieldMap
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Field
argument_list|,
name|String
argument_list|>
name|field
range|:
name|fields
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|field
operator|.
name|getKey
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|VERBATIM
argument_list|)
condition|)
block|{
name|Matcher
name|hashMatcher
init|=
name|UNESCAPED_HASH
operator|.
name|matcher
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|hashCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|hashMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|hashCount
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|hashCount
operator|&
literal|1
operator|)
operator|==
literal|1
condition|)
block|{
comment|// Check if odd
name|results
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"odd number of unescaped '#'"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|results
return|;
block|}
block|}
end_class

end_unit

