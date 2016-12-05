begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
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
name|JabRefException
import|;
end_import

begin_comment
comment|/**  * This exception will be thrown if the same journal abbreviation already exists in the same list.  */
end_comment

begin_class
DECL|class|DuplicatedJournalAbbreviationException
specifier|public
class|class
name|DuplicatedJournalAbbreviationException
extends|extends
name|JabRefException
block|{
DECL|method|DuplicatedJournalAbbreviationException (String message)
specifier|public
name|DuplicatedJournalAbbreviationException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

