begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|stream
operator|.
name|Collectors
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
name|IdGenerator
import|;
end_import

begin_class
DECL|class|BibDatabases
specifier|public
class|class
name|BibDatabases
block|{
DECL|method|BibDatabases ()
specifier|private
name|BibDatabases
parameter_list|()
block|{     }
comment|/**      * Gets a collection of bibentries and sets an ID for every entry. After that      * all entries will be inserted into a new BibDatabase.      *      * @param bibentries a collection that contains {@link BibEntry}      * @return BibDatabase that contains the entries      */
DECL|method|createDatabase (Collection<BibEntry> bibentries)
specifier|public
specifier|static
name|BibDatabase
name|createDatabase
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|bibentries
parameter_list|)
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|bibentries
control|)
block|{
name|entry
operator|.
name|setId
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return
name|database
return|;
block|}
comment|/**      * Receives a Collection of BibEntry instances, iterates through them, and      * removes all entries that have no fields set. This is useful for rooting out      * an unsucessful import (wrong format) that returns a number of empty entries.      */
DECL|method|purgeEmptyEntries (Collection<BibEntry> entries)
specifier|public
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|purgeEmptyEntries
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
return|return
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|entry
lambda|->
operator|!
name|entry
operator|.
name|getFields
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

