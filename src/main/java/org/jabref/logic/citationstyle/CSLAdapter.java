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
name|ArrayList
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
name|List
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveNewlinesFormatter
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
name|layout
operator|.
name|format
operator|.
name|HTMLChars
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
name|ItemDataProvider
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

begin_comment
comment|/**  * Provides an adapter class to CSL. It holds a CSL instance under the hood that is only recreated when  * the style changes.  *  * @apiNote The first call to {@link #makeBibliography} is expensive since the  * CSL instance will be created. As long as the style stays the same, we can reuse this instance. On style-change, the  * engine is re-instantiated. Therefore, the use-case of this class is many calls to {@link #makeBibliography} with the  * same style. Changing the output format is cheap.  * @implNote The main function {@link #makeBibliography} will enforce  * synchronized calling. The main CSL engine under the hood is not thread-safe. Since this class is usually called from  * a SwingWorker, the only other option would be to create several CSL instances which is wasting a lot of resources and very slow.  * In the current scheme, {@link #makeBibliography} can be called as usual  * SwingWorker task and to the best of my knowledge, concurrent calls will pile up and processed sequentially.  */
end_comment

begin_class
DECL|class|CSLAdapter
specifier|public
class|class
name|CSLAdapter
block|{
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
DECL|field|dataProvider
specifier|private
specifier|final
name|JabRefItemDataProvider
name|dataProvider
init|=
operator|new
name|JabRefItemDataProvider
argument_list|()
decl_stmt|;
DECL|field|style
specifier|private
name|String
name|style
decl_stmt|;
DECL|field|format
specifier|private
name|CitationStyleOutputFormat
name|format
decl_stmt|;
DECL|field|cslInstance
specifier|private
name|CSL
name|cslInstance
decl_stmt|;
comment|/**      * Creates the bibliography of the provided items. This method needs to run synchronized because the underlying      * CSL engine is not thread-safe.      */
DECL|method|makeBibliography (List<BibEntry> bibEntries, String style, CitationStyleOutputFormat outputFormat)
specifier|public
specifier|synchronized
name|List
argument_list|<
name|String
argument_list|>
name|makeBibliography
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
throws|throws
name|IOException
throws|,
name|IllegalArgumentException
block|{
name|dataProvider
operator|.
name|setData
argument_list|(
name|bibEntries
argument_list|)
expr_stmt|;
name|initialize
argument_list|(
name|style
argument_list|,
name|outputFormat
argument_list|)
expr_stmt|;
name|cslInstance
operator|.
name|registerCitationItems
argument_list|(
name|dataProvider
operator|.
name|getIds
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Bibliography
name|bibliography
init|=
name|cslInstance
operator|.
name|makeBibliography
argument_list|()
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
comment|/**      * Initialized the static CSL instance if needed.      *      * @param newStyle  journal style of the output      * @param newFormat usually HTML or RTF.      * @throws IOException An error occurred in the underlying JavaScript framework      */
DECL|method|initialize (String newStyle, CitationStyleOutputFormat newFormat)
specifier|private
name|void
name|initialize
parameter_list|(
name|String
name|newStyle
parameter_list|,
name|CitationStyleOutputFormat
name|newFormat
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|cslInstance
operator|==
literal|null
operator|||
operator|!
name|Objects
operator|.
name|equals
argument_list|(
name|newStyle
argument_list|,
name|style
argument_list|)
condition|)
block|{
name|cslInstance
operator|=
operator|new
name|CSL
argument_list|(
name|dataProvider
argument_list|,
name|newStyle
argument_list|)
expr_stmt|;
name|style
operator|=
name|newStyle
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|Objects
operator|.
name|equals
argument_list|(
name|newFormat
argument_list|,
name|format
argument_list|)
condition|)
block|{
name|cslInstance
operator|.
name|setOutputFormat
argument_list|(
name|newFormat
operator|.
name|getFormat
argument_list|()
argument_list|)
expr_stmt|;
name|format
operator|=
name|newFormat
expr_stmt|;
block|}
block|}
comment|/**      * Custom ItemDataProvider that allows to set the data so that we don't have to instantiate a new CSL object      * every time.      */
DECL|class|JabRefItemDataProvider
specifier|private
specifier|static
class|class
name|JabRefItemDataProvider
implements|implements
name|ItemDataProvider
block|{
DECL|field|data
specifier|private
name|ArrayList
argument_list|<
name|BibEntry
argument_list|>
name|data
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**          * Converts the {@link BibEntry} into {@link CSLItemData}.          */
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
name|HTMLChars
name|latexToHtmlConverter
init|=
operator|new
name|HTMLChars
argument_list|()
decl_stmt|;
name|RemoveNewlinesFormatter
name|removeNewlinesFormatter
init|=
operator|new
name|RemoveNewlinesFormatter
argument_list|()
decl_stmt|;
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
name|bibEntry
operator|.
name|getField
argument_list|(
name|key
argument_list|)
operator|.
name|map
argument_list|(
name|removeNewlinesFormatter
operator|::
name|format
argument_list|)
operator|.
name|map
argument_list|(
name|latexToHtmlConverter
operator|::
name|format
argument_list|)
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
DECL|method|setData (List<BibEntry> data)
specifier|public
name|void
name|setData
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|data
parameter_list|)
block|{
name|this
operator|.
name|data
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|data
operator|.
name|addAll
argument_list|(
name|data
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|retrieveItem (String id)
specifier|public
name|CSLItemData
name|retrieveItem
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|data
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
operator|.
name|equals
argument_list|(
name|id
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|JabRefItemDataProvider
operator|::
name|bibEntryToCSLItemData
argument_list|)
operator|.
name|findFirst
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getIds ()
specifier|public
name|String
index|[]
name|getIds
parameter_list|()
block|{
return|return
name|data
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
operator|.
name|toArray
argument_list|(
name|String
index|[]
operator|::
operator|new
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

