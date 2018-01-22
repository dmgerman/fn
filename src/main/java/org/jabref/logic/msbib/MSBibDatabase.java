begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.msbib
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|database
operator|.
name|BibDatabase
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|InputSource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * Microsoft Word bibliography.  * The class is uesed both for import and export  * See http://www.ecma-international.org/publications/standards/Ecma-376.htm  */
end_comment

begin_class
DECL|class|MSBibDatabase
specifier|public
class|class
name|MSBibDatabase
block|{
DECL|field|NAMESPACE
specifier|public
specifier|static
specifier|final
name|String
name|NAMESPACE
init|=
literal|"http://schemas.openxmlformats.org/officeDocument/2006/bibliography"
decl_stmt|;
DECL|field|PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|PREFIX
init|=
literal|"b:"
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MSBibDatabase
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|entries
specifier|private
name|Set
argument_list|<
name|MSBibEntry
argument_list|>
name|entries
decl_stmt|;
comment|/**      * Creates a {@link MSBibDatabase} for<b>import</b>      */
DECL|method|MSBibDatabase ()
specifier|public
name|MSBibDatabase
parameter_list|()
block|{
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
block|}
comment|// TODO: why an additonal entry list? entries are included inside database!
comment|/**      * Creates a new {@link MSBibDatabase} for<b>export</b>      * @param database The bib database      * @param entries List of {@link BibEntry}      */
DECL|method|MSBibDatabase (BibDatabase database, List<BibEntry> entries)
specifier|public
name|MSBibDatabase
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
block|{
name|addEntriesForExport
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addEntriesForExport
argument_list|(
name|entries
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Imports entries from an office xml file      * @param reader      * @return List of {@link BibEntry}      */
DECL|method|importEntriesFromXml (BufferedReader reader)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importEntriesFromXml
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
block|{
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
name|Document
name|inputDocument
decl_stmt|;
try|try
block|{
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|factory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|documentBuilder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|inputDocument
operator|=
name|documentBuilder
operator|.
name|parse
argument_list|(
operator|new
name|InputSource
argument_list|(
name|reader
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
decl||
name|SAXException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not parse document"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|NodeList
name|rootList
init|=
name|inputDocument
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Sources"
argument_list|)
decl_stmt|;
if|if
condition|(
name|rootList
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
name|rootList
operator|=
name|inputDocument
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Sources"
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|rootList
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
name|bibitems
return|;
block|}
name|NodeList
name|sourceList
init|=
operator|(
operator|(
name|Element
operator|)
name|rootList
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Source"
argument_list|)
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
name|sourceList
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|MSBibEntry
name|entry
init|=
operator|new
name|MSBibEntry
argument_list|(
operator|(
name|Element
operator|)
name|sourceList
operator|.
name|item
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|BibTeXConverter
operator|.
name|convert
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|bibitems
return|;
block|}
DECL|method|addEntriesForExport (List<BibEntry> entriesToAdd)
specifier|private
name|void
name|addEntriesForExport
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
block|{
name|entries
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entriesToAdd
control|)
block|{
name|MSBibEntry
name|newMods
init|=
name|MSBibConverter
operator|.
name|convert
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|newMods
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Gets the assembled dom for export      * @return XML Document      */
DECL|method|getDomForExport ()
specifier|public
name|Document
name|getDomForExport
parameter_list|()
block|{
name|Document
name|document
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|factory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|documentBuilder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|document
operator|=
name|documentBuilder
operator|.
name|newDocument
argument_list|()
expr_stmt|;
name|Element
name|rootNode
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|NAMESPACE
argument_list|,
name|PREFIX
operator|+
literal|"Sources"
argument_list|)
decl_stmt|;
name|rootNode
operator|.
name|setAttributeNS
argument_list|(
literal|"http://www.w3.org/2000/xmlns/"
argument_list|,
literal|"xmlns"
argument_list|,
name|NAMESPACE
argument_list|)
expr_stmt|;
name|rootNode
operator|.
name|setAttributeNS
argument_list|(
literal|"http://www.w3.org/2000/xmlns/"
argument_list|,
literal|"xmlns:"
operator|+
name|PREFIX
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|PREFIX
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|,
name|NAMESPACE
argument_list|)
expr_stmt|;
name|rootNode
operator|.
name|setAttribute
argument_list|(
literal|"SelectedStyle"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
for|for
control|(
name|MSBibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Node
name|node
init|=
name|entry
operator|.
name|getEntryDom
argument_list|(
name|document
argument_list|)
decl_stmt|;
name|rootNode
operator|.
name|appendChild
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
name|document
operator|.
name|appendChild
argument_list|(
name|rootNode
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not build XML document"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|document
return|;
block|}
block|}
end_class

end_unit

