begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|entry
operator|.
name|AuthorList
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

begin_class
DECL|class|GroupsUtil
specifier|public
class|class
name|GroupsUtil
block|{
DECL|method|findDeliminatedWordsInField (BibDatabase db, String field, String deliminator)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findDeliminatedWordsInField
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|deliminator
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|db
operator|.
name|getKeySet
argument_list|()
control|)
block|{
name|BibEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|be
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|String
name|fieldValue
init|=
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|fieldValue
argument_list|,
name|deliminator
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
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
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|res
return|;
block|}
comment|/**      * Returns a Set containing all words used in the database in the given field type. Characters in      *<code>remove</code> are not included.      *      * @param db a<code>BibDatabase</code> value      * @param field a<code>String</code> value      * @param remove a<code>String</code> value      * @return a<code>Set</code> value      */
DECL|method|findAllWordsInField (BibDatabase db, String field, String remove)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findAllWordsInField
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|remove
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|db
operator|.
name|getKeySet
argument_list|()
control|)
block|{
name|BibEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|be
operator|.
name|getFieldOptional
argument_list|(
name|field
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|o
lambda|->
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|,
name|remove
argument_list|,
literal|false
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
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
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Finds all authors' last names in all the given fields for the given database.      *      * @param db The database.      * @param fields The fields to look in.      * @return a set containing the names.      */
DECL|method|findAuthorLastNames (BibDatabase db, List<String> fields)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findAuthorLastNames
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|db
operator|.
name|getKeySet
argument_list|()
control|)
block|{
name|BibEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|s
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|String
name|val
init|=
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|val
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|val
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|val
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|al
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|AuthorList
operator|.
name|Author
name|a
init|=
name|al
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|lastName
init|=
name|a
operator|.
name|getLast
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|lastName
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|lastName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
name|lastName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

