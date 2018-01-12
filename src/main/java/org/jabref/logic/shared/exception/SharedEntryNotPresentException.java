begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared.exception
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
operator|.
name|exception
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

begin_comment
comment|/**  * This exception is thrown if a BibEntry is going to be updated while it does not exist on the shared side.  */
end_comment

begin_class
DECL|class|SharedEntryNotPresentException
specifier|public
class|class
name|SharedEntryNotPresentException
extends|extends
name|Exception
block|{
DECL|field|nonPresentBibEntry
specifier|private
specifier|final
name|BibEntry
name|nonPresentBibEntry
decl_stmt|;
DECL|method|SharedEntryNotPresentException (BibEntry nonPresentbibEntry)
specifier|public
name|SharedEntryNotPresentException
parameter_list|(
name|BibEntry
name|nonPresentbibEntry
parameter_list|)
block|{
name|super
argument_list|(
literal|"Required BibEntry is not present on shared database."
argument_list|)
expr_stmt|;
name|this
operator|.
name|nonPresentBibEntry
operator|=
name|nonPresentbibEntry
expr_stmt|;
block|}
DECL|method|getNonPresentBibEntry ()
specifier|public
name|BibEntry
name|getNonPresentBibEntry
parameter_list|()
block|{
return|return
name|this
operator|.
name|nonPresentBibEntry
return|;
block|}
block|}
end_class

end_unit

