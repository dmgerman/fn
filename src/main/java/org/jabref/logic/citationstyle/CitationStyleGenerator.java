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
name|Arrays
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
name|java
operator|.
name|util
operator|.
name|Optional
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
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|CSL
import|;
end_import

begin_import
import|import
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|bibtex
operator|.
name|BibTeXConverter
import|;
end_import

begin_import
import|import
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|csl
operator|.
name|CSLItemData
import|;
end_import

begin_import
import|import
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|output
operator|.
name|Bibliography
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
name|BibTeXEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jbibtex
operator|.
name|DigitStringValue
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jbibtex
operator|.
name|Key
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
comment|/**  * WARNING: the citation is generated using JavaScript which may take some time, better call it from outside the main Thread  */
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
DECL|field|BIBTEX_CONVERTER
specifier|private
specifier|static
specifier|final
name|BibTeXConverter
name|BIBTEX_CONVERTER
init|=
operator|new
name|BibTeXConverter
argument_list|()
decl_stmt|;
DECL|method|CitationStyleGenerator ()
specifier|private
name|CitationStyleGenerator
parameter_list|()
block|{     }
comment|/**      * WARNING: the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      * Generates a Citation based on the given entry and style      */
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
comment|/**      * WARNING: the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      * Generates a Citation based on the given entry and style      */
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
comment|/**      * WARNING: the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      * Generates a Citation based on the given entry, style, and output format      */
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
comment|/**      * WARNING: the citation is generated using JavaScript which may take some time, better call it from outside the main Thread      * Generates the citation for multiple entries at once. This is useful when the Citation Style has an increasing number      */
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
name|CSLItemData
index|[]
name|cslItemData
init|=
operator|new
name|CSLItemData
index|[
name|bibEntries
operator|.
name|size
argument_list|()
index|]
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
name|bibEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|cslItemData
index|[
name|i
index|]
operator|=
name|bibEntryToCSLItemData
argument_list|(
name|bibEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Bibliography
name|bibliography
init|=
name|CSL
operator|.
name|makeAdhocBibliography
argument_list|(
name|style
argument_list|,
name|outputFormat
operator|.
name|getFormat
argument_list|()
argument_list|,
name|cslItemData
argument_list|)
decl_stmt|;
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|bibliography
operator|.
name|getEntries
argument_list|()
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
comment|/**      * Converts the {@link BibEntry} into {@link CSLItemData}.      */
DECL|method|bibEntryToCSLItemData (BibEntry bibEntry)
specifier|private
specifier|static
name|CSLItemData
name|bibEntryToCSLItemData
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|String
name|citeKey
init|=
name|bibEntry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|BibTeXEntry
name|bibTeXEntry
init|=
operator|new
name|BibTeXEntry
argument_list|(
operator|new
name|Key
argument_list|(
name|bibEntry
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Key
argument_list|(
name|citeKey
argument_list|)
argument_list|)
decl_stmt|;
comment|// Not every field is already generated into latex free fields
for|for
control|(
name|String
name|key
range|:
name|bibEntry
operator|.
name|getFieldMap
argument_list|()
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|latexFreeField
init|=
name|bibEntry
operator|.
name|getLatexFreeField
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|latexFreeField
operator|.
name|ifPresent
argument_list|(
name|value
lambda|->
name|bibTeXEntry
operator|.
name|addField
argument_list|(
operator|new
name|Key
argument_list|(
name|key
argument_list|)
argument_list|,
operator|new
name|DigitStringValue
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|BIBTEX_CONVERTER
operator|.
name|toItemData
argument_list|(
name|bibTeXEntry
argument_list|)
return|;
block|}
block|}
end_class

end_unit

