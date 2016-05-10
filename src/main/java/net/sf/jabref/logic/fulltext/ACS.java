begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.fulltext
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|fulltext
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|logic
operator|.
name|util
operator|.
name|DOI
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

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|Jsoup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Element
import|;
end_import

begin_comment
comment|/**  * FullTextFinder implementation that attempts to find a PDF URL at ACS.  */
end_comment

begin_class
DECL|class|ACS
specifier|public
class|class
name|ACS
implements|implements
name|FullTextFinder
block|{
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
name|ACS
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|SOURCE
specifier|private
specifier|static
specifier|final
name|String
name|SOURCE
init|=
literal|"http://pubs.acs.org/doi/abs/%s"
decl_stmt|;
comment|/**      * Tries to find a fulltext URL for a given BibTex entry.      *      * Currently only uses the DOI if found.      *      * @param entry The Bibtex entry      * @return The fulltext PDF URL Optional, if found, or an empty Optional if not found.      * @throws NullPointerException if no BibTex entry is given      * @throws java.io.IOException      */
annotation|@
name|Override
DECL|method|findFullText (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// DOI search
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|build
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|source
init|=
name|String
operator|.
name|format
argument_list|(
name|SOURCE
argument_list|,
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
decl_stmt|;
comment|// Retrieve PDF link
name|Document
name|html
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|source
argument_list|)
operator|.
name|ignoreHttpErrors
argument_list|(
literal|true
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|Element
name|link
init|=
name|html
operator|.
name|select
argument_list|(
literal|".pdf-high-res a"
argument_list|)
operator|.
name|first
argument_list|()
decl_stmt|;
if|if
condition|(
name|link
operator|!=
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ ACS."
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|source
operator|.
name|replaceFirst
argument_list|(
literal|"/abs/"
argument_list|,
literal|"/pdf/"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pdfLink
return|;
block|}
block|}
end_class

end_unit

