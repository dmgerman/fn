begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|stream
operator|.
name|Collectors
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
name|database
operator|.
name|BibDatabase
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
name|database
operator|.
name|BibDatabases
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
comment|/**  * @author Silberer, Zirn  */
end_comment

begin_class
DECL|class|DatabaseSearcher
specifier|public
class|class
name|DatabaseSearcher
block|{
DECL|field|query
specifier|private
specifier|final
name|SearchQuery
name|query
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
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
name|DatabaseSearcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|DatabaseSearcher (SearchQuery query, BibDatabase database)
specifier|public
name|DatabaseSearcher
parameter_list|(
name|SearchQuery
name|query
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|query
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
DECL|method|getMatches ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getMatches
parameter_list|()
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Search term: "
operator|+
name|query
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|query
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|matchEntries
init|=
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|query
operator|::
name|isMatch
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|BibDatabases
operator|.
name|purgeEmptyEntries
argument_list|(
name|matchEntries
argument_list|)
return|;
block|}
block|}
end_class

end_unit

