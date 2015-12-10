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
name|BibLatexEntryTypes
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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_class
DECL|class|BibTypeDetection
specifier|public
class|class
name|BibTypeDetection
block|{
DECL|method|inferType (Collection<BibEntry> entries)
specifier|public
specifier|static
name|BibType
name|inferType
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
comment|// standard mode
name|BibType
name|type
init|=
name|BibType
operator|.
name|BIBTEX
decl_stmt|;
comment|// check for biblatex entries
comment|// TODO must be based on name not type!!!
comment|// TODO rename BibtexEntry and BibtexDatabase to generic names
if|if
condition|(
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|allMatch
argument_list|(
name|e
lambda|->
name|BibLatexEntryTypes
operator|.
name|ALL
operator|.
name|contains
argument_list|(
name|e
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|BibType
operator|.
name|BIBLATEX
return|;
block|}
return|return
name|type
return|;
block|}
DECL|method|typeBasedCheck ()
specifier|private
name|void
name|typeBasedCheck
parameter_list|()
block|{      }
DECL|method|fieldBasedCheck ()
specifier|private
name|void
name|fieldBasedCheck
parameter_list|()
block|{      }
block|}
end_class

end_unit

