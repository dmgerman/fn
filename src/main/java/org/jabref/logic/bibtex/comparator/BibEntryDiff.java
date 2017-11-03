begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex.comparator
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
package|;
end_package

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

begin_class
DECL|class|BibEntryDiff
specifier|public
class|class
name|BibEntryDiff
block|{
DECL|field|originalEntry
specifier|private
specifier|final
name|BibEntry
name|originalEntry
decl_stmt|;
DECL|field|newEntry
specifier|private
specifier|final
name|BibEntry
name|newEntry
decl_stmt|;
DECL|method|BibEntryDiff (BibEntry originalEntry, BibEntry newEntry)
specifier|public
name|BibEntryDiff
parameter_list|(
name|BibEntry
name|originalEntry
parameter_list|,
name|BibEntry
name|newEntry
parameter_list|)
block|{
name|this
operator|.
name|originalEntry
operator|=
name|originalEntry
expr_stmt|;
name|this
operator|.
name|newEntry
operator|=
name|newEntry
expr_stmt|;
block|}
DECL|method|getOriginalEntry ()
specifier|public
name|BibEntry
name|getOriginalEntry
parameter_list|()
block|{
return|return
name|originalEntry
return|;
block|}
DECL|method|getNewEntry ()
specifier|public
name|BibEntry
name|getNewEntry
parameter_list|()
block|{
return|return
name|newEntry
return|;
block|}
block|}
end_class

end_unit
