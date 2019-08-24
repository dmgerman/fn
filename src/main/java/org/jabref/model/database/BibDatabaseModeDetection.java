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
name|stream
operator|.
name|Stream
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
name|types
operator|.
name|EntryType
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
name|types
operator|.
name|EntryTypeFactory
import|;
end_import

begin_class
DECL|class|BibDatabaseModeDetection
specifier|public
class|class
name|BibDatabaseModeDetection
block|{
DECL|method|BibDatabaseModeDetection ()
specifier|private
name|BibDatabaseModeDetection
parameter_list|()
block|{     }
comment|/**      * Tries to infer the database type by examining a BibDatabase database.      *      * All checks are based on the case-insensitive comparison of entry tag names.      * Only standard BibTex and Biblatex entry types are considered in the decision process.      *      * 1. Check if any of the entries is a type exclusive to Biblatex      * 2. Check if any exclusive Biblatex fields are present      * 3. Otherwise return BibTex      *      * @param database a BibDatabase database      * @return the inferred database type      */
DECL|method|inferMode (BibDatabase database)
specifier|public
specifier|static
name|BibDatabaseMode
name|inferMode
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
specifier|final
name|Stream
argument_list|<
name|EntryType
argument_list|>
name|entryTypes
init|=
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibEntry
operator|::
name|getType
argument_list|)
decl_stmt|;
comment|// type-based check
if|if
condition|(
name|entryTypes
operator|.
name|anyMatch
argument_list|(
name|EntryTypeFactory
operator|::
name|isExclusiveBiblatex
argument_list|)
condition|)
block|{
return|return
name|BibDatabaseMode
operator|.
name|BIBLATEX
return|;
block|}
else|else
block|{
comment|// field-based check
return|return
name|BibDatabaseMode
operator|.
name|BIBTEX
return|;
block|}
block|}
block|}
end_class

end_unit

