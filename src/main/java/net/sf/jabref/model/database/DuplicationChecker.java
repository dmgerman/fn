begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.database
package|package
name|net
operator|.
name|sf
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
name|HashMap
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
name|model
operator|.
name|database
operator|.
name|event
operator|.
name|EntryAddedEvent
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
name|event
operator|.
name|EntryRemovedEvent
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
name|entry
operator|.
name|event
operator|.
name|FieldChangedEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_comment
comment|/**  * Determines which bibtex cite keys are duplicates in a single {@link BibDatabase}.  */
end_comment

begin_class
DECL|class|DuplicationChecker
specifier|public
class|class
name|DuplicationChecker
block|{
comment|/** use a map instead of a set since I need to know how many of each key is in there */
DECL|field|allKeys
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|allKeys
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Checks if there is more than one occurrence of this key      */
DECL|method|isDuplicateCiteKeyExisting (String citeKey)
specifier|public
name|boolean
name|isDuplicateCiteKeyExisting
parameter_list|(
name|String
name|citeKey
parameter_list|)
block|{
return|return
name|getNumberOfKeyOccurrences
argument_list|(
name|citeKey
argument_list|)
operator|>
literal|1
return|;
block|}
comment|/**      * Checks if there is more than one occurrence of the cite key      */
DECL|method|isDuplicateCiteKeyExisting (BibEntry entry)
specifier|public
name|boolean
name|isDuplicateCiteKeyExisting
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|isDuplicateCiteKeyExisting
argument_list|(
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns the number of occurrences of the given key in this database.      */
DECL|method|getNumberOfKeyOccurrences (String citeKey)
specifier|public
name|int
name|getNumberOfKeyOccurrences
parameter_list|(
name|String
name|citeKey
parameter_list|)
block|{
return|return
name|allKeys
operator|.
name|getOrDefault
argument_list|(
name|citeKey
argument_list|,
literal|0
argument_list|)
return|;
block|}
comment|/**      * Helper function for counting the number of the key usages.      * Adds the given key to the internal keyset together with the count of it.      * The counter is increased if the key already exists, otherwise set to 1.      *<br>      * Special case: If a null or empty key is passed, it is not counted and thus not added.      *      * Reasoning:      * Consider this: I add a key xxx, then I add another key xxx. I get a warning. I delete the key xxx.      * Consider JabRef simply removing this key from a set of allKeys.      * Then I add another key xxx. I don't get a warning!      * Thus, I need a way to count the number of keys of each type.      * Solution: hashmap=>int (increment each time at add and decrement each time at remove)      */
DECL|method|addKeyToSet (String key)
specifier|private
name|void
name|addKeyToSet
parameter_list|(
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|allKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|getNumberOfKeyOccurrences
argument_list|(
name|key
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
comment|/**      * Helper function for counting the number of the key usages.      * Removes the given key from the internal keyset together with the count of it, if the key is set to 1.      * If it is not set to 1, the counter will be decreased.      *<br>      * Special case: If a null or empty key is passed, it is not counted and thus not removed.      */
DECL|method|removeKeyFromSet (String key)
specifier|private
name|void
name|removeKeyFromSet
parameter_list|(
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|int
name|numberOfKeyOccurrences
init|=
name|getNumberOfKeyOccurrences
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|numberOfKeyOccurrences
operator|>
literal|1
condition|)
block|{
name|allKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|numberOfKeyOccurrences
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|allKeys
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (FieldChangedEvent fieldChangedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|FieldChangedEvent
name|fieldChangedEvent
parameter_list|)
block|{
if|if
condition|(
name|fieldChangedEvent
operator|.
name|getFieldName
argument_list|()
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
condition|)
block|{
name|removeKeyFromSet
argument_list|(
name|fieldChangedEvent
operator|.
name|getOldValue
argument_list|()
argument_list|)
expr_stmt|;
name|addKeyToSet
argument_list|(
name|fieldChangedEvent
operator|.
name|getNewValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryRemovedEvent entryRemovedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryRemovedEvent
name|entryRemovedEvent
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|citeKey
init|=
name|entryRemovedEvent
operator|.
name|getBibEntry
argument_list|()
operator|.
name|getCiteKeyOptional
argument_list|()
decl_stmt|;
if|if
condition|(
name|citeKey
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|removeKeyFromSet
argument_list|(
name|citeKey
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryAddedEvent entryAddedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryAddedEvent
name|entryAddedEvent
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|citekey
init|=
name|entryAddedEvent
operator|.
name|getBibEntry
argument_list|()
operator|.
name|getCiteKeyOptional
argument_list|()
decl_stmt|;
if|if
condition|(
name|citekey
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|addKeyToSet
argument_list|(
name|citekey
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

