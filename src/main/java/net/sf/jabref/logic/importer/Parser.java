begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
comment|/**  * A parser converts an {@link InputStream} into a list of {@link BibEntry}.  */
end_comment

begin_interface
DECL|interface|Parser
specifier|public
interface|interface
name|Parser
block|{
DECL|method|parseEntries (InputStream inputStream)
name|List
argument_list|<
name|BibEntry
argument_list|>
name|parseEntries
parameter_list|(
name|InputStream
name|inputStream
parameter_list|)
throws|throws
name|ParseException
function_decl|;
block|}
end_interface

end_unit

