begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.citationstyle
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
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
name|util
operator|.
name|Collections
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|jbibtex
operator|.
name|TokenMgrException
import|;
end_import

begin_comment
comment|/**  * Facade to unify the access to the citation style engine. Use these methods if you need rendered BibTeX item(s) in a  * given journal style. This class uses {@link CSLAdapter} to create output.  */
end_comment

begin_class
DECL|class|CitationStyleGenerator
specifier|public
class|class
name|CitationStyleGenerator
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
name|CitationStyleGenerator
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|CSL_ADAPTER
specifier|private
specifier|static
specifier|final
name|CSLAdapter
name|CSL_ADAPTER
init|=
operator|new
name|CSLAdapter
argument_list|()
decl_stmt|;
DECL|method|CitationStyleGenerator ()
specifier|private
name|CitationStyleGenerator
parameter_list|()
block|{     }
comment|/**      * Generates a Citation based on the given entry and style      * @implNote the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      */
DECL|method|generateCitation (BibEntry entry, CitationStyle style)
specifier|protected
specifier|static
name|String
name|generateCitation
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|CitationStyle
name|style
parameter_list|)
block|{
return|return
name|generateCitation
argument_list|(
name|entry
argument_list|,
name|style
operator|.
name|getSource
argument_list|()
argument_list|,
name|CitationStyleOutputFormat
operator|.
name|HTML
argument_list|)
return|;
block|}
comment|/**      * Generates a Citation based on the given entry and style      * @implNote the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      */
DECL|method|generateCitation (BibEntry entry, String style)
specifier|protected
specifier|static
name|String
name|generateCitation
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|style
parameter_list|)
block|{
return|return
name|generateCitation
argument_list|(
name|entry
argument_list|,
name|style
argument_list|,
name|CitationStyleOutputFormat
operator|.
name|HTML
argument_list|)
return|;
block|}
comment|/**      * Generates a Citation based on the given entry, style, and output format      * @implNote the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      */
DECL|method|generateCitation (BibEntry entry, String style, CitationStyleOutputFormat outputFormat)
specifier|protected
specifier|static
name|String
name|generateCitation
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|style
parameter_list|,
name|CitationStyleOutputFormat
name|outputFormat
parameter_list|)
block|{
return|return
name|generateCitations
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|style
argument_list|,
name|outputFormat
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Generates the citation for multiple entries at once.      * @implNote The citations are generated using JavaScript which may take some time, better call it from outside the main thread.      */
DECL|method|generateCitations (List<BibEntry> bibEntries, String style, CitationStyleOutputFormat outputFormat)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|generateCitations
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
parameter_list|,
name|String
name|style
parameter_list|,
name|CitationStyleOutputFormat
name|outputFormat
parameter_list|)
block|{
try|try
block|{
return|return
name|CSL_ADAPTER
operator|.
name|makeBibliography
argument_list|(
name|bibEntries
argument_list|,
name|style
argument_list|,
name|outputFormat
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ignored
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not generate BibEntry citation. The CSL engine could not create a preview for your item."
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot generate preview based on selected citation style."
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|ArrayIndexOutOfBoundsException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not generate BibEntry citation"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot generate preview based on selected citation style."
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|TokenMgrException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Bad character inside BibEntry"
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|// sadly one cannot easily retrieve the bad char from the TokenMgrError
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|StringBuilder
argument_list|()
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot generate preview based on selected citation style."
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|outputFormat
operator|.
name|getLineSeparator
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Bad character inside entry"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|outputFormat
operator|.
name|getLineSeparator
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

